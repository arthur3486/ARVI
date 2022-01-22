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

package com.arthurivanets.arvi;

import android.content.Context;
import android.net.Uri;

import com.arthurivanets.arvi.player.Player;
import com.google.android.exoplayer2.source.MediaSource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Defines a base contract for the concrete {@link PlayerProvider} implementations.
 */
public interface PlayerProvider {

    /**
     * Creates the a non-looping {@link MediaSource}. (See {@link #createMediaSource(Uri, boolean)})
     *
     * @param uri the uri to create the media source for
     * @return the created media source
     */
    @NonNull
    MediaSource createMediaSource(@NonNull Uri uri);

    /**
     * Creates the {@link MediaSource} for the specified {@link Uri}.
     * The created {@link MediaSource} can later be used with the {@link Player},
     * as a data source.
     * Uses the default {@link Player} {@link Config}.
     *
     * @param uri the uri to create the media source for
     * @param isLooping whether to loop the video
     * @return the created media source
     */
    @NonNull
    MediaSource createMediaSource(@NonNull Uri uri, boolean isLooping);

    /**
     * Creates the a non-looping {@link MediaSource}. (See {@link #createMediaSource(Config, Uri, boolean)})
     *
     * @param config the player configuration
     * @param uri the uri to created the media source for
     * @return the created media source
     */
    @NonNull
    MediaSource createMediaSource(@NonNull Config config, @NonNull Uri uri);

    /**
     * Creates the {@link MediaSource} for the specified {@link Uri} and Player{@link Config}
     * The created {@link MediaSource} can later be used with the {@link Player},
     * as a data source.
     *
     * @param config the player configuration
     * @param uri the uri to created the media source for
     * @param isLooping whether to loop the video
     * @return the created media source
     */
    @NonNull
    MediaSource createMediaSource(@NonNull Config config, @NonNull Uri uri, boolean isLooping);

    /**
     * Retrieves the library name.
     *
     * @return the library name
     */
    @NonNull
    String getLibraryName();

    /**
     * Retrieves the application {@link Context}.
     *
     * @return the application context
     */
    @NonNull
    Context getContext();

    /**
     * Retrieves an existing {@link Player} instance for the specified key, if there's any.
     * Uses the default {@link Config}.
     *
     * @param key the key to retrieve the player for
     * @return the retrieved Player, or <strong>null</strong> if no Player was found.
     */
    @Nullable
    Player getPlayer(@NonNull String key);

    /**
     * Retrieves an existing {@link Player} instance for the specified key and Player {@link Config}, if there's any.
     *
     * @param config the player configuration
     * @param key the key to retrieve the player for
     * @return the retrieved Player, or <strong>null</strong> if no Player was found.
     */
    @Nullable
    Player getPlayer(@NonNull Config config, @NonNull String key);

    /**
     * Retrieves an existing or create a brand-new {@link Player} instance for the specified key.
     * Users the default player {@link Config}.
     *
     * @param key the key to retrieve the player for
     * @return the retrieved or created Player
     */
    @NonNull
    Player getOrInitPlayer(@NonNull String key);

    /**
     * Retrieves an existing or create a brand-new {@link Player} instance for the specified key and Player {@link Config}.
     *
     * @param config the player configuration
     * @param key the key to retrieve the player for
     * @return the retrieved or created Player
     */
    @NonNull
    Player getOrInitPlayer(@NonNull Config config, @NonNull String key);

    /**
     * Checks if there's a {@link Player} available for the specified key.
     * Uses the default Player {@link Config}.
     *
     * @param key the key to check the player presence for
     * @return <strong>true</strong> if the player is available, <strong>false</strong> otherwise
     */
    boolean hasPlayer(@NonNull String key);

    /**
     * Checks if there's a {@link Player} available for the specified key and Player {@link Config}.
     *
     * @param config the player configuration
     * @param key the key to check the player presence for
     * @return <strong>true</strong> if the player is available, <strong>false</strong> otherwise
     */
    boolean hasPlayer(@NonNull Config config, @NonNull String key);

    /**
     * Unregisters the {@link Player}, thus making it available within the Player Pool as a "free" Player.
     * Uses the default Player {@link Config}.
     *
     * @param key the key to unregister the Player for
     */
    void unregister(@NonNull String key);

    /**
     * Unregisters the {@link Player}, thus making it available within the Player Pool as a "free" Player.
     *
     * @param config the player configuration
     * @param key the key to unregister the Player for
     */
    void unregister(@NonNull Config config, @NonNull String key);

    /**
     * Releases the {@link Player} for the specified key.
     * Uses the default Player {@link Config}.
     *
     * @param key the key to release the Player for
     */
    void release(@NonNull String key);

    /**
     * Releases all the {@link Player}s that match the specified {@link Config}.
     *
     * @param config the player configuration
     */
    void release(@NonNull Config config);

    /**
     * Releases the {@link Player} for the specified key and {@link Config}.
     *
     * @param config the player configuration
     * @param key the key to release the Player for
     */
    void release(@NonNull Config config, @NonNull String key);

    /**
     * Releases all the currently available (initialized) {@link Player}s.
     */
    void release();

}
