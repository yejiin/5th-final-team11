package com.doubleslas.fifith.alcohol.model

import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

object FirebaseConfig {
    private val remoteConfig by lazy {
        Firebase.remoteConfig
    }

    init {
        remoteConfig.fetch(24 * 60 * 60).addOnSuccessListener {
            remoteConfig.activate()
        }
    }

    fun getMinVersion(): Long {
        return remoteConfig.getLong("min_version")
    }
}