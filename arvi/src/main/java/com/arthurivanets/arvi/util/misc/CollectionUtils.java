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

package com.arthurivanets.arvi.util.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A set of the internal {@link Collection} utils.
 */
public final class CollectionUtils {




    /**
     * Attempts to retrieve the first item from the specified {@link List}, if there's any.
     *
     * @param list the list to retrieve the item from
     * @param <T> the type of the items held by the list
     * @return the retrieved item if the list is not empty, or <strong>null</strong> otherwise
     */
    @Nullable
    public static <T> T takeFirstOrNull(@NonNull List<T> list) {
        Preconditions.nonNull(list);
        return (!list.isEmpty() ? list.get(0) : null);
    }




    /**
     * Attempts to retrieve the first item from the specified Array, if there's any.
     *
     * @param array the array to retrieve the item from
     * @param <T> the type of the items held by the array
     * @return the retrieved item if the array is not empty, or <strong>null</strong> otherwise
     */
    @Nullable
    public static <T> T takeFirstOrNull(@NonNull T[] array) {
        Preconditions.nonNull(array);
        return ((array.length > 0) ? array[0] : null);
    }




    /**
     * Creates the {@link HashSet} out of the specified items of type {@link T}.
     *
     * @param items the items to create the {@link HashSet} of
     * @param <T> the type of the items
     * @return the created {@link Set}
     */
    @NonNull
    public static <T> Set<T> hashSetOf(@NonNull T... items) {
        Preconditions.nonNull(items);

        final Set<T> hashSet = new HashSet<>(items.length);

        for(T item : items) {
            hashSet.add(item);
        }

        return hashSet;
    }




    /**
     * Creates the {@link HashSet} out of the specified {@link Collection} of items.
     *
     * @param collection the collection of items to create the {@link HashSet} of
     * @param <T> the type of the items
     * @return the created {@link Set}
     */
    @NonNull
    public static <T> Set<T> toHashSet(@NonNull Collection<T> collection) {
        Preconditions.nonNull(collection);
        return new HashSet<>(collection);
    }




    /**
     * Creates the {@link List} of the items of generic type {@link T} from the specified
     * array of {@link Object}s.
     * <strong>NOTE</strong>: your array of {@link Object}s must contain the {@link Object}s
     * of exactly the same generic type {@link T}, otherwise the {@link ClassCastException} will be thrown.
     *
     * @param array the array of items to be converted into a {@link List}
     * @param <T> the type of items
     * @return the created {@link List} of items of generic type {@link T}
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public static <T> List<T> toListUnchecked(@NonNull Object[] array) {
        Preconditions.nonNull(array);

        final int itemCount = array.length;
        final List<T> list = new ArrayList<>(itemCount);

        for(int i = 0; i < itemCount; i++) {
            list.set(i, ((T) array[i]));
        }

        return list;
    }




}
