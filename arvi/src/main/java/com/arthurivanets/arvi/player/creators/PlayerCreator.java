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

package com.arthurivanets.arvi.player.creators;

import android.net.Uri;

import com.arthurivanets.arvi.player.Player;
import com.google.android.exoplayer2.source.MediaSource;

import androidx.annotation.NonNull;

/**
 * A base contract to be implemented by the concrete implementations
 * of the {@link PlayerCreator}s.
 * Should be able to create the {@link Player}s and related {@link MediaSource}s.
 */
public interface PlayerCreator {

    /**
     * Creates a brand-new {@link Player} instance.
     *
     * @return the created {@link Player} instance
     */
    @NonNull
    Player createPlayer();

    /**
     * Creates a {@link MediaSource} for the specified {@link Uri}.
     *
     * @param uri the media uri
     * @return the created {@link MediaSource}
     */
    @NonNull
    MediaSource createMediaSource(@NonNull Uri uri);

    /**
     * Creates a {@link MediaSource} for the specified {@link Uri} and extension.
     *
     * @param uri the media uri
     * @param extension the media extension
     * @return the created {@link MediaSource}
     */
    @NonNull
    MediaSource createMediaSource(@NonNull Uri uri, @NonNull String extension);

}
