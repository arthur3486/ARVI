/*
 * Copyright 2017 Arthur Ivanets, arthur.ivanets.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmName("ArviExtensions")

package com.arthurivanets.arvi.ktx

import android.content.Context
import com.arthurivanets.arvi.PlayerProvider
import com.arthurivanets.arvi.PlayerProviderImpl
import com.arthurivanets.arvi.util.misc.ExoPlayerUtils
import com.google.android.exoplayer2.upstream.cache.Cache


/**
 * Obtains the global [PlayerProvider] instance.
 */
val Context.playerProvider : PlayerProvider
    get() = PlayerProviderImpl.getInstance(this)


/**
 * Creates/obtains the Exo Player [Cache] of the default size [ExoPlayerUtils.DEFAULT_CACHE_SIZE].
 *
 * @return the created/obtains [Cache]
 */
fun Context.defaultExoCache() : Cache {
    return ExoPlayerUtils.getCache(this)
}


/**
 * Creates/obtains the Exo Player [Cache] of the specified size (in bytes).
 *
 * @return the created/obtains [Cache]
 */
fun Context.exoCache(size : Long) : Cache {
    return ExoPlayerUtils.getCache(this, size)
}