package com.example.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/*
 * Created by Sherif farid
 * Date: 7/2/2023.
 * email: sherffareed39@gmail.com.
 * phone: 00201007538470
 */

open class RmUserModel : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name: String? = null
    var age: String? = null
    fun getUserModel(): UserModel {
        return UserModel(id = this.id ,
        name = this.name ,
        age = this.age)
    }
}

 data class UserModel  ( var id: Long = 0 ,
                         var name: String? = null ,
                         var age: String? = null)