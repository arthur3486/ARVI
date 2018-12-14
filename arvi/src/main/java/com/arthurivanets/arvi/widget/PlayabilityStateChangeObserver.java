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

package com.arthurivanets.arvi.widget;

/**
 * A playability state change observer used by the {@link Playable} items held by the {@link PlayableItemsContainer}.
 */
public interface PlayabilityStateChangeObserver {

    /**
     * Gets called when the item playability state changes.
     *
     * @param isPlayable whether the item is playable or not
     */
    void onPlayabilityStateChanged(boolean isPlayable);

}
