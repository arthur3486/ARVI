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

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.math.MathUtils;

/**
 * A model object used to represent the Video-related Volume Info.
 * (This info is, in most cases, used for proper handling of the {@link androidx.recyclerview.widget.RecyclerView}-specific Video Item Volume management)
 */
public final class VolumeInfo implements Parcelable {


    private float audioVolume;

    private boolean isMuted;




    public VolumeInfo() {
        this(1f, false);
    }




    public VolumeInfo(float audioVolume, boolean isMuted) {
        this.audioVolume = audioVolume;
        this.isMuted = isMuted;
    }




    public VolumeInfo(@NonNull VolumeInfo info) {
        Preconditions.nonNull(info);

        this.audioVolume = info.audioVolume;
        this.isMuted = info.isMuted;
    }




    private VolumeInfo(Parcel in) {
        this.audioVolume = in.readFloat();
        this.isMuted = (in.readByte() != 0);
    }




    /**
     * Sets the Audio Volume.
     *
     * @param audioVolume the audio volume ratio (a value between 0.0 and 1.0)
     * @return the same instance of the {@link VolumeInfo} for chaining purposes.
     */
    @NonNull
    public final VolumeInfo setVolume(@FloatRange(from = 0.0, to = 1.0) float audioVolume) {
        this.audioVolume = MathUtils.clamp(audioVolume, 0f, 1f);
        return this;
    }




    /**
     * Retrieves the Audio Volume Ratio.
     *
     * @return the audio volume ratio (a value between 0.0 and 1.0).
     */
    @FloatRange(from = 0.0, to = 1.0)
    public final float getVolume() {
        return this.audioVolume;
    }




    /**
     * Sets the Audio "Muted" state.
     *
     * @param isMuted whether the Audio is muted or not
     * @return the same instance of the {@link VolumeInfo} for chaining purposes.
     */
    @NonNull
    public final VolumeInfo setMuted(boolean isMuted) {
        this.isMuted = isMuted;
        return this;
    }




    /**
     * Retrieves the muted state of the Audio.
     *
     * @return <strong>true</strong> if the audio is muted, <strong>false</strong> otherwise.
     */
    public final boolean isMuted() {
        return this.isMuted;
    }




    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 17;
        result = ((result * prime) + Float.floatToIntBits(this.audioVolume));
        result = ((result * prime) + (this.isMuted ? 1 : 0));

        return result;
    }




    @Override
    public final boolean equals(@Nullable Object obj) {
        return ((obj instanceof VolumeInfo) && (obj.hashCode() == hashCode()));
    }




    @Override
    public final int describeContents() {
        return 0;
    }




    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.audioVolume);
        dest.writeByte(this.isMuted ? ((byte) 1) : ((byte) 0));
    }




    public static final Creator<VolumeInfo> CREATOR = new ClassLoaderCreator<VolumeInfo>() {

        @Override
        public VolumeInfo createFromParcel(Parcel source, ClassLoader loader) {
            return new VolumeInfo(source);
        }

        @Override
        public VolumeInfo createFromParcel(Parcel source) {
            return new VolumeInfo(source);
        }

        @Override
        public VolumeInfo[] newArray(int size) {
            return new VolumeInfo[size];
        }

    };




}
