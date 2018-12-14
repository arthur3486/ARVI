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

package com.arthurivanets.arvi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.arthurivanets.arvi.util.misc.Preconditions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A model object used to represent the Video Playback-related Info.
 * (This info is, in most cases, used for proper handling of the {@link androidx.recyclerview.widget.RecyclerView}-specific Video Item Playbacks)
 */
public final class PlaybackInfo implements Parcelable {


    private long playbackPosition;
    private long duration;

    private VolumeInfo volumeInfo;

    private boolean isEnded;




    public PlaybackInfo() {
        this.playbackPosition = 0L;
        this.duration = 0L;
        this.volumeInfo = new VolumeInfo();
        this.isEnded = false;
    }




    public PlaybackInfo(@NonNull PlaybackInfo info) {
        Preconditions.nonNull(info);

        this.playbackPosition = info.playbackPosition;
        this.duration = info.duration;
        this.volumeInfo = new VolumeInfo(info.volumeInfo);
        this.isEnded = info.isEnded;
    }




    private PlaybackInfo(Parcel in) {
        this.playbackPosition = in.readLong();
        this.duration = in.readLong();
        this.volumeInfo = in.readParcelable(VolumeInfo.class.getClassLoader());
        this.isEnded = (in.readByte() != 0);
    }




    /**
     * Sets the Video Playback Position (in milliseconds).
     *
     * @param playbackPosition video playback position in milliseconds
     * @return the same instance of the {@link PlaybackInfo} for chaining purposes.
     */
    @NonNull
    public final PlaybackInfo setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
        return this;
    }




    /**
     * Retrieves the Video Playback Position (in milliseconds).
     *
     * @return the playback position in milliseconds.
     */
    public final long getPlaybackPosition() {
        return this.playbackPosition;
    }




    /**
     * Sets the Video Duration (in milliseconds).
     *
     * @param duration video duration in milliseconds
     * @return the same instance of the {@link PlaybackInfo} for chaining purposes.
     */
    @NonNull
    public final PlaybackInfo setDuration(long duration) {
        this.duration = duration;
        return this;
    }




    /**
     * Retrieves the Video Duration (in milliseconds).
     *
     * @return the video duration in milliseconds.
     */
    public final long getDuration() {
        return this.duration;
    }




    /**
     * Sets the Video Volume Info.
     *
     * @param volumeInfo video volume info
     * @return the same instance of the {@link PlaybackInfo} for chaining purposes.
     */
    @NonNull
    public final PlaybackInfo setVolumeInfo(@NonNull VolumeInfo volumeInfo) {
        this.volumeInfo = Preconditions.checkNonNull(volumeInfo);
        return this;
    }




    /**
     * Retrieves the Video Volume Info.
     *
     * @return the video volume info.
     */
    @NonNull
    public final VolumeInfo getVolumeInfo() {
        return this.volumeInfo;
    }




    /**
     * Sets the Video Playback Ended State.
     *
     * @param isEnded whether the video playback ended or not
     * @return the same instance of the {@link PlaybackInfo} for chaining purposes.
     */
    @NonNull
    public final PlaybackInfo setEnded(boolean isEnded) {
        this.isEnded = isEnded;
        return this;
    }




    /**
     * Retrieves the Video Playback Ended State.
     *
     * @return <strong>true</strong> if the video playback ended, <strong>false</strong> otherwise.
     */
    public final boolean isEnded() {
        return this.isEnded;
    }




    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 17;
        result = ((result * prime) + Long.valueOf(this.playbackPosition).hashCode());
        result = ((result * prime) + Long.valueOf(this.duration).hashCode());
        result = ((result * prime) + this.volumeInfo.hashCode());
        result = ((result * prime) + (this.isEnded ? 1 : 0));

        return result;
    }




    @Override
    public final boolean equals(@Nullable Object obj) {
        return ((obj instanceof PlaybackInfo) && (obj.hashCode() == hashCode()));
    }




    @Override
    public final int describeContents() {
        return 0;
    }




    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.playbackPosition);
        dest.writeLong(this.duration);
        dest.writeParcelable(this.volumeInfo, flags);
        dest.writeByte(this.isEnded ? ((byte) 1) : ((byte) 0));
    }




    public static final Creator<PlaybackInfo> CREATOR = new ClassLoaderCreator<PlaybackInfo>() {

        @Override
        public PlaybackInfo createFromParcel(Parcel source, ClassLoader loader) {
            return new PlaybackInfo(source);
        }

        @Override
        public PlaybackInfo createFromParcel(Parcel source) {
            return new PlaybackInfo(source);
        }

        @Override
        public PlaybackInfo[] newArray(int size) {
            return new PlaybackInfo[size];
        }

    };




}
