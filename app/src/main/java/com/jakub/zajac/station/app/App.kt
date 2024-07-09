package com.jakub.zajac.station.app

import android.app.Application

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application()
//    ImageLoaderFactory
{

//    override fun newImageLoader(): ImageLoader {
//        return ImageLoader(this).newBuilder()
//            .memoryCachePolicy(CachePolicy.ENABLED)
//            .memoryCache {
//                MemoryCache.Builder(this)
//                    .maxSizePercent(0.2)
//                    .strongReferencesEnabled(true)
//                    .build()
//            }
//            .diskCachePolicy(CachePolicy.ENABLED)
//            .diskCache {
//                DiskCache.Builder()
//                    .maxSizePercent(0.05)
//                    .directory(cacheDir)
//                    .build()
//            }
//            .logger(DebugLogger())
//            .build()
//    }
}