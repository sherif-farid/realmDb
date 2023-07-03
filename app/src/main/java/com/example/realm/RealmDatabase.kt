package com.example.realm

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/*
 * Created by Sherif farid
 * Date: 7/2/2023.
 * email: sherffareed39@gmail.com.
 * phone: 00201007538470
 */
class RealmDatabase : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .allowWritesOnUiThread(false)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}