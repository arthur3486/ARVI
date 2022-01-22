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
import android.util.Pair;

import com.arthurivanets.arvi.player.Player;
import com.arthurivanets.arvi.player.creators.PlayerCreator;
import com.arthurivanets.arvi.util.misc.Preconditions;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.arthurivanets.arvi.BuildConfig.LIBRARY_NAME;
import static com.google.android.exoplayer2.util.Util.getUserAgent;

/**
 * The main {@link PlayerProvider} responsible for the management of all the {@link Player}s
 * in the context of the application.
 */
public final class PlayerProviderImpl implements PlayerProvider {


    public static final Config DEFAULT_CONFIG = new Config.Builder().build();

    @SuppressWarnings("StaticFieldLeak")
    private volatile static PlayerProvider sInstance;

    private final String mLibraryName;

    private final Context mContext;

    private final Map<Config, PlayerCreator> mConfigCreatorMap;
    private final Map<PlayerCreator, PlayerNodePool> mCreatorNodePoolMap;




    public static PlayerProvider getInstance(@NonNull Context context) {
        Preconditions.nonNull(context);

        if(sInstance == null) {
            synchronized(PlayerProviderImpl.class) {
                if(sInstance == null) {
                    sInstance = new PlayerProviderImpl(context.getApplicationContext());
                }
            }
        }

        return sInstance;
    }




    private PlayerProviderImpl(Context context) {
        ArviPlugins.lockDown();

        mLibraryName = getUserAgent(context, LIBRARY_NAME);
        mContext = context.getApplicationContext();
        mConfigCreatorMap = new HashMap<>();
        mCreatorNodePoolMap = new HashMap<>();

        initCookieManager();
    }




    private void initCookieManager() {
        // Adapt from ExoPlayer demo app. Start this on demand.
        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);

        if(CookieHandler.getDefault() != cookieManager) {
            CookieHandler.setDefault(cookieManager);
        }
    }




    @NonNull
    @Override
    public final MediaSource createMediaSource(@NonNull Uri uri) {
        return createMediaSource(DEFAULT_CONFIG, uri);
    }




    @NonNull
    @Override
    public final MediaSource createMediaSource(@NonNull Uri uri, boolean isLooping) {
        return createMediaSource(DEFAULT_CONFIG, uri, isLooping);
    }




    @NonNull
    @Override
    public final MediaSource createMediaSource(@NonNull Config config, @NonNull Uri uri) {
        return createMediaSource(config, uri, false);
    }




    @NonNull
    @Override
    public final MediaSource createMediaSource(@NonNull Config config,
                                               @NonNull Uri uri,
                                               boolean isLooping) {
        Preconditions.nonNull(config);
        Preconditions.nonNull(uri);

        final PlayerCreator creator = getOrInitCreator(config);
        final MediaSource mediaSource = creator.createMediaSource(uri);

        return (isLooping ? new LoopingMediaSource(mediaSource) : mediaSource);
    }




    @NonNull
    @Override
    public final String getLibraryName() {
        return mLibraryName;
    }




    @NonNull
    @Override
    public final Context getContext() {
        return mContext;
    }




    @Nullable
    @Override
    public final Player getPlayer(@NonNull String key) {
        return getPlayer(DEFAULT_CONFIG, key);
    }




    @Nullable
    @Override
    public final Player getPlayer(@NonNull Config config, @NonNull String key) {
        Preconditions.nonNull(config);
        Preconditions.nonEmpty(key);

        final PlayerNodePool correspondingPool = getPoolForConfig(config);

        return (((correspondingPool != null) && correspondingPool.contains(key)) ? correspondingPool.get(key).getPlayer() : null);
    }




    @NonNull
    @Override
    public final Player getOrInitPlayer(@NonNull String key) {
        return getOrInitPlayer(DEFAULT_CONFIG, key);
    }




    @NonNull
    @Override
    public final Player getOrInitPlayer(@NonNull Config config, @NonNull String key) {
        Preconditions.nonNull(config);
        Preconditions.nonEmpty(key);

        final Pair<PlayerCreator, PlayerNodePool> pair = getOrInit(config);
        final PlayerCreator playerCreator = pair.first;
        final PlayerNodePool playerNodePool = pair.second;

        PlayerNode playerNode = playerNodePool.get(key);

        if(playerNode == null) {
            // checking to see if there's a free (detached) PlayerNode to be reused
            final PlayerNode freePlayerNode = playerNodePool.acquireFree(key);

            // in case of the absence of the free (detached) PlayerNode
            if(freePlayerNode == null) {
                // If the pool is full, we need to pick the PlayerNode
                // that is considered "the oldest" in terms of the last access time,
                // otherwise we can create a brand-new instance of the PlayerNode and add it to the pool
                if(playerNodePool.isFull()) {
                    playerNode = playerNodePool.acquireOldest(key);
                } else {
                    // creating a brand-new PlayerNode instance
                    playerNode = new PlayerNode(playerCreator.createPlayer()).setKey(key);

                    // adding it to the general pool
                    playerNodePool.add(playerNode);
                }
            } else {
                // using the available "Free" (Detached) PlayerNode
                playerNode = freePlayerNode;
            }
        }

        return playerNode.getPlayer();
    }




    private Pair<PlayerCreator, PlayerNodePool> getOrInit(Config config) {
        final PlayerCreator creator = getOrInitCreator(config);
        final PlayerNodePool nodePool = getOrInitNodePool(creator);

        return new Pair<>(creator, nodePool);
    }




    private PlayerCreator getOrInitCreator(Config config) {
        PlayerCreator creator = mConfigCreatorMap.get(config);

        if(creator == null) {
            creator = ArviPlugins.getPlayerCreatorFactory().create(this, config);

            mConfigCreatorMap.put(config, creator);
        }

        return creator;
    }




    private PlayerNodePool getOrInitNodePool(PlayerCreator creator) {
        PlayerNodePool nodePool = mCreatorNodePoolMap.get(creator);

        if(nodePool == null) {
            nodePool = ArviPlugins.getPlayerNodePoolFactory().create();

            mCreatorNodePoolMap.put(creator, nodePool);
        }

        return nodePool;
    }




    private PlayerNodePool getPoolForConfig(Config config) {
        final PlayerCreator creator = mConfigCreatorMap.get(config);
        return ((creator != null) ? mCreatorNodePoolMap.get(creator) : null);
    }




    private PlayerNodePool removePoolForConfig(Config config) {
        final PlayerCreator creator = mConfigCreatorMap.get(config);
        return ((creator != null) ? mCreatorNodePoolMap.remove(creator) : null);
    }




    @Override
    public final boolean hasPlayer(@NonNull String key) {
        return hasPlayer(DEFAULT_CONFIG, key);
    }




    @Override
    public final boolean hasPlayer(@NonNull Config config, @NonNull String key) {
        Preconditions.nonNull(config);
        Preconditions.nonEmpty(key);

        return (getPlayer(config, key) != null);
    }




    @Override
    public final void unregister(@NonNull String key) {
        unregister(DEFAULT_CONFIG, key);
    }




    @Override
    public final void unregister(@NonNull Config config, @NonNull String key) {
        Preconditions.nonNull(config);
        Preconditions.nonEmpty(key);

        // unregistering the Player within a corresponding pool (if there's any)
        final PlayerNodePool correspondingPool = getPoolForConfig(config);

        if(correspondingPool != null) {
            correspondingPool.unregister(key);
        }
    }




    @Override
    public final void release(@NonNull String key) {
        release(DEFAULT_CONFIG, key);
    }




    @Override
    public final void release(@NonNull Config config) {
        Preconditions.nonNull(config);

        // releasing and removing the corresponding Player Node Pool (if there's any)
        final PlayerNodePool correspondingPool = removePoolForConfig(config);

        if(correspondingPool != null) {
            correspondingPool.release();
        }
    }




    @Override
    public final void release(@NonNull Config config, @NonNull String key) {
        Preconditions.nonNull(config);
        Preconditions.nonEmpty(key);

        // releasing the corresponding Player Node (if there's any)
        final PlayerNodePool correspondingPool = getPoolForConfig(config);

        if(correspondingPool != null) {
            correspondingPool.release(key);
        }
    }




    @Override
    public final void release() {
        for(PlayerNodePool playerNodePool : mCreatorNodePoolMap.values()) {
            playerNodePool.release();
        }

        mConfigCreatorMap.clear();
        mCreatorNodePoolMap.clear();
    }




}