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

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the in-memory (RAM) {@link Cache}.
 *
 * @param <K> the cache key type
 * @param <V> the cache value type
 */
final class InMemoryCache<K, V> implements Cache<K, V> {


    private final Map<K, V> mCacheMap;




    InMemoryCache() {
        mCacheMap = new HashMap<>();
    }




    @Override
    public final V put(K key, V value) {
        return mCacheMap.put(key, value);
    }




    @Override
    public final V get(K key) {
        return get(key, null);
    }




    @Override
    public final V get(K key, V defaultValue) {
        return (contains(key) ? mCacheMap.get(key) : defaultValue);
    }




    @Override
    public final <RV> RV getAs(K key) {
        return getAs(key, null);
    }




    @SuppressWarnings("unchecked")
    @Override
    public final <RV> RV getAs(K key, RV defaultValue) {
        return (RV) (contains(key) ? mCacheMap.get(key) : defaultValue);
    }




    @Override
    public final V remove(K key) {
        return remove(key, null);
    }




    @Override
    public final V remove(K key, V defaultValue) {
        return (contains(key) ? mCacheMap.remove(key) : defaultValue);
    }




    @Override
    public final <RV> RV removeAs(K key) {
        return removeAs(key, null);
    }




    @SuppressWarnings("unchecked")
    @Override
    public final <RV> RV removeAs(K key, RV defaultValue) {
        return (RV) (contains(key) ? mCacheMap.remove(key) : defaultValue);
    }




    @Override
    public final boolean contains(K key) {
        return (mCacheMap.get(key) != null);
    }




    @Override
    public final boolean clear() {
        mCacheMap.clear();
        return true;
    }




}
