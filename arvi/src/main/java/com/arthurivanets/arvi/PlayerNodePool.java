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

package com.arthurivanets.arvi;

import com.arthurivanets.arvi.player.Player;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A base contract for the concrete implementations of the {@link PlayerNodePool}.
 */
interface PlayerNodePool {

    /**
     * Adds the specified {@link PlayerNode} to the current player node pool.
     *
     * @param player the player node to be added to the pool
     */
    void add(@NonNull PlayerNode player);

    /**
     * Creates the {@link PlayerNode} from the specified key and {@link Player},
     * and adds it to the current player node pool.
     *
     * @param key the key to map the player to
     * @param player the player to be added to the pool
     */
    void add(@NonNull String key, @NonNull Player player);

    /**
     * Removes the specified {@link PlayerNode} from the current player node pool.
     *
     * @param playerNode the node to be removed from the pool
     * @return the removed player node
     */
    @Nullable
    PlayerNode remove(@NonNull PlayerNode playerNode);

    /**
     * Removes the {@link PlayerNode} for the specified key, if there's any.
     *
     * @param key the key to remove the player node for
     * @return the removed Player Node, or <strong>null</strong> if there was no Player Node for the specified key
     */
    @Nullable
    PlayerNode remove(@NonNull String key);

    /**
     * Unregisters (makes available) the {@link PlayerNode} mapped to the specified key.
     */
    void unregister(@NonNull String key);

    /**
     * Registers (acquires) the available {@link PlayerNode} for the specified key.
     *
     * @param key the key to register the player node for
     * @return the registered player node
     */
    @Nullable
    PlayerNode acquire(@NonNull String key);

    /**
     * Registers (acquires) the available free {@link PlayerNode} for the specified key, if there's any.
     *
     * @param key the key to register the player node for
     * @return the registered player node
     */
    @Nullable
    PlayerNode acquireFree(@NonNull String key);

    /**
     * Registers (acquires) the oldest available {@link PlayerNode} for the specified key.
     * If no available {@link PlayerNode} was found, unregisters the oldest one and re-registers it (now for the new key).
     *
     * @param key the key to register the player node for
     * @return the registered player node
     */
    @Nullable
    PlayerNode acquireOldest(@NonNull String key);

    /**
     * Releases the specified {@link PlayerNode}.
     *
     * @param playerNode the player node to be released
     */
    void release(@NonNull PlayerNode playerNode);

    /**
     * Releases the {@link PlayerNode} that corresponds to the specified key.
     *
     * @param key the key for which the {@link PlayerNode} to be released
     */
    void release(@NonNull String key);

    /**
     * Releases all the {@link PlayerNode}s present within this pool.
     */
    void release();

    /**
     * Retrieves the {@link PlayerNode} for the specified key.
     *
     * @param key the key to retrieve the player node for
     * @return the retrieved player node
     */
    @Nullable
    PlayerNode get(@NonNull String key);

    /**
     * Retrieves the free {@link PlayerNode} present in the current pool.
     *
     * @return the retrieved free player node
     */
    @Nullable
    PlayerNode getFree();

    /**
     * Retrieves the oldest {@link PlayerNode} in the current pool.
     *
     * @return the retrieved oldest player node
     */
    @Nullable
    PlayerNode getOldest();

    /**
     * Retrieves the number of the {@link PlayerNode}s held by the current pool.
     *
     * @return the number of the player nodes held by the current pool
     */
    int getPlayerCount();

    /**
     * Determines whether the current pool is full (whether the number of the {@link PlayerNode}s held
     * by the current pool reached the player node limit imposed by the current pool).
     *
     * @return <strong>true</strong> if it's full, <strong>false</strong> otherwise
     */
    boolean isFull();

    /**
     * Determines whether the current pool contains a {@link PlayerNode} that corresponds to the specified key.
     *
     * @param key the key to check the player node presence for
     * @return <strong>true</strong> if the pool contains the corresponding player node, <strong>false</strong> otherwise
     */
    boolean contains(@NonNull String key);

}
