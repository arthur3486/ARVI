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

package com.arthurivanets.arvi.util.cache;

import androidx.annotation.NonNull;

import com.arthurivanets.arvi.util.misc.Preconditions;

/**
 * A synchronized (thread-safe) implementation of the {@link Cache}.
 * Used as a {@link Cache} wrapper, to provide the thread safety for any
 * {@link Cache} implementation.
 *
 * @param <K> the cache key type
 * @param <V> the cache value type
 */
final class ConcurrentCache<K, V> implements Cache<K, V> {

    private final Object mLock;

    private final Cache<K, V> mCache;

    ConcurrentCache(@NonNull Cache<K, V> cache) {
        mLock = new Object();
        mCache = Preconditions.checkNonNull(cache);
    }

    @Override
    public final V put(K key, V value) {
        synchronized (mLock) {
            return mCache.put(key, value);
        }
    }

    @Override
    public final V get(K key) {
        synchronized (mLock) {
            return mCache.get(key);
        }
    }

    @Override
    public final V get(K key, V defaultValue) {
        synchronized (mLock) {
            return mCache.get(key, defaultValue);
        }
    }

    @Override
    public final <RV> RV getAs(K key) {
        synchronized (mLock) {
            return mCache.getAs(key);
        }
    }

    @Override
    public final <RV> RV getAs(K key, RV defaultValue) {
        synchronized (mLock) {
            return mCache.getAs(key, defaultValue);
        }
    }

    @Override
    public final V remove(K key) {
        synchronized (mLock) {
            return mCache.remove(key);
        }
    }

    @Override
    public final V remove(K key, V defaultValue) {
        synchronized (mLock) {
            return mCache.remove(key, defaultValue);
        }
    }

    @Override
    public final <RV> RV removeAs(K key) {
        synchronized (mLock) {
            return mCache.removeAs(key);
        }
    }

    @Override
    public final <RV> RV removeAs(K key, RV defaultValue) {
        synchronized (mLock) {
            return mCache.removeAs(key, defaultValue);
        }
    }

    @Override
    public final boolean contains(K key) {
        synchronized (mLock) {
            return mCache.contains(key);
        }
    }

    @Override
    public final boolean clear() {
        synchronized (mLock) {
            return mCache.clear();
        }
    }

}
