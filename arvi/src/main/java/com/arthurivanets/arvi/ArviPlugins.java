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

import androidx.annotation.NonNull;

/**
 * Utility class used for the injection of certain {@link PlayerProvider} components.
 */
public final class ArviPlugins {


    private static volatile PlayerCreatorFactory playerCreatorFactory = new DefaultPlayerCreatorFactory();
    private static volatile PlayerNodePoolFactory playerNodePoolFactory = new DefaultPlayerNodePoolFactory();

    private static volatile boolean isLockedDown = false;


    /**
     * "Locks down" the plugin injection.
     * All the plugin injections performed after the lock-down will fail with an {@link IllegalStateException}.
     */
    static void lockDown() {
        isLockedDown = true;
    }


    /**
     * Retrieves the current lock-down state.
     *
     * @return whether the plugin injection is locked down or not
     */
    public static boolean isLockedDown() {
        return isLockedDown;
    }


    /**
     * Injects the {@link PlayerCreatorFactory} to be used by the {@link PlayerProvider}.
     *
     * @param factory the new player creator factory
     */
    public static void setPalayerCreatorFactory(@NonNull PlayerCreatorFactory factory) {
        checkLockDownState();

        playerCreatorFactory = factory;
    }


    /**
     * Retrieves the currently injected {@link PlayerCreatorFactory}.
     *
     * @return the current {@link PlayerCreatorFactory}
     */
    @NonNull
    public static PlayerCreatorFactory getPlayerCreatorFactory() {
        return playerCreatorFactory;
    }


    /**
     * Injects the {@link PlayerNodePoolFactory} to be used by the {@link PlayerProvider}.
     *
     * @param factory the new player node pool factory
     */
    public static void setPlayerNodePoolFactory(@NonNull PlayerNodePoolFactory factory) {
        checkLockDownState();

        playerNodePoolFactory = factory;
    }


    /**
     * Retrieves the currently injected {@link PlayerNodePoolFactory}.
     *
     * @return the current {@link PlayerNodePoolFactory}
     */
    @NonNull
    public static PlayerNodePoolFactory getPlayerNodePoolFactory() {
        return playerNodePoolFactory;
    }


    private static void checkLockDownState() {
        if(isLockedDown) {
            throw new IllegalStateException("The Plugins can not be changed anymore.");
        }
    }


    private ArviPlugins() {
        throw new IllegalStateException("Not instantiatable!");
    }


}