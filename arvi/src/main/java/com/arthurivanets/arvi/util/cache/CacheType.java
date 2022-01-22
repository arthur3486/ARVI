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

import androidx.annotation.RestrictTo;

/**
 * The {@link Cache} factory.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public enum CacheType {


    /**
     * An in-memory (RAM) implementation of {@link Cache}.
     */
    IN_MEMORY {
        @Override
        <K, V> Cache<K, V> create(boolean concurrent) {
            final Cache<K, V> cache = new InMemoryCache<>();
            return (concurrent ? new ConcurrentCache<>(cache) : cache);
        }
    };


    /**
     * Creates a new instance of the {@link Cache}.
     *
     * @param concurrent whether to create a synchronized (thread-safe) version of {@link Cache}, or not
     * @param <K> cache key type
     * @param <V> cache value type
     * @return the created {@link Cache}
     */
    abstract <K, V> Cache<K, V> create(boolean concurrent);


}
