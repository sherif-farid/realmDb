package com.example.realm

import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch


/*
 * Created by Sherif farid
 * Date: 7/2/2023.
 * email: sherffareed39@gmail.com.
 * phone: 00201007538470
 */



object Repository {

    private fun realmInst() = Realm.getDefaultInstance()
    private fun workerThread(runnable: Runnable){
//        val thread = Thread(runnable)
//        thread.start()
// if you prefer using thread , uncomment above lines and comment below
        CoroutineScope(Dispatchers.IO).launch {
            runnable.run()
        }
    }
    private fun mainThr(runnable: Runnable){
//        val handler = Handler(Looper.getMainLooper())
//        handler.post(runnable)
// if you prefer using thread , uncomment above lines and comment below
        CoroutineScope(Dispatchers.Main).launch {
            runnable.run()
        }
    }
    private fun safeWrite(block: Runnable) {
        workerThread{
            if (realmInst().isInTransaction) {
                block.run()
            } else {
                realmInst().executeTransaction {
                    block.run()
                }
            }
        }
    }

    fun interface RepoResult<T> {
        fun result(model: T)
    }

    fun getAllUsers(result: RepoResult<ArrayList<UserModel>>) {
        safeWrite{
            val userList = realmInst().where(RmUserModel::class.java).findAll()
            val allList = ArrayList<UserModel>()
            for (userModel in userList) {
                 allList.add(userModel.getUserModel())
            }
            mainThr{
                result.result(allList)
            }
        }
    }

    fun addUser(name: String?, age: String?, repoResult: RepoResult<UserModel>) {
        safeWrite{
            val rmUserModel = RmUserModel()
            val id = realmInst().where(RmUserModel::class.java)?.max("id")
            val nextId: Long = if (id == null) {
                1
            } else {
                (id.toInt() + 1).toLong()
            }
            rmUserModel.id = nextId
            rmUserModel.name = name
            rmUserModel.age = age
            val model = realmInst().copyToRealm(rmUserModel).getUserModel()
            mainThr{
                repoResult.result(model)
            }
        }
    }
}