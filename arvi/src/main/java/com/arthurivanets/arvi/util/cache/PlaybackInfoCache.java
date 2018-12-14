/*
 * Copyright 2017 Arthur Ivanets, arthur.ivanets.l@gmail.com
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

package com.arthurivanets.arvi.util.cache;

import com.arthurivanets.arvi.model.PlaybackInfo;

import androidx.annotation.RestrictTo;

/**
 * An implementation of {@link Cache} used for the management of the {@link com.arthurivanets.arvi.player.Player}'s {@link PlaybackInfo}.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class PlaybackInfoCache implements Cache<String, PlaybackInfo> {


    private static volatile PlaybackInfoCache sInstance;

    private final Cache<String, PlaybackInfo> mCache;




    /**
     * Lazily creates an instance of the {@link PlaybackInfoCache} (if necessary).
     *
     * @return the instance of the {@link PlaybackInfoCache}
     */
    public static PlaybackInfoCache getInstance() {
        if(sInstance == null) {
            synchronized(PlaybackInfoCache.class) {
                if(sInstance == null) {
                    sInstance = new PlaybackInfoCache();
                }
            }
        }

        return sInstance;
    }




    private PlaybackInfoCache() {
        mCache = CacheType.IN_MEMORY.create(true);
    }




    @Override
    public final PlaybackInfo put(String key, PlaybackInfo value) {
        return mCache.put(key, value);
    }




    @Override
    public final PlaybackInfo get(String key) {
        return mCache.get(key);
    }




    @Override
    public final PlaybackInfo get(String key, PlaybackInfo defaultValue) {
        return mCache.get(key, defaultValue);
    }




    @Override
    public final <RV> RV getAs(String key) {
        return mCache.getAs(key);
    }




    @Override
    public final <RV> RV getAs(String key, RV defaultValue) {
        return mCache.getAs(key, defaultValue);
    }




    @Override
    public final PlaybackInfo remove(String key) {
        return mCache.remove(key);
    }




    @Override
    public final PlaybackInfo remove(String key, PlaybackInfo defaultValue) {
        return mCache.remove(key, defaultValue);
    }




    @Override
    public final <RV> RV removeAs(String key) {
        return mCache.removeAs(key);
    }




    @Override
    public final <RV> RV removeAs(String key, RV defaultValue) {
        return mCache.removeAs(key, defaultValue);
    }




    @Override
    public final boolean contains(String key) {
        return mCache.contains(key);
    }




    @Override
    public final boolean clear() {
        return mCache.clear();
    }




}
