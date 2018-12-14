package com.arthurivanets.sample

import android.app.Application
import com.arthurivanets.arvi.ktx.playerProvider

class ArviApplication : Application() {


    override fun onTrimMemory(level : Int) {
        super.onTrimMemory(level)

        if(level >= TRIM_MEMORY_BACKGROUND) {
            playerProvider.release()
        }
    }


}