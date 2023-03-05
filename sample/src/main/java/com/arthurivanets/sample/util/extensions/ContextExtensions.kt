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

@file:JvmName("ContextExtensions")

package com.arthurivanets.sample.util.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.use

@SuppressLint("Recycle")
fun Context.extractStyledAttributes(
    attributes: AttributeSet,
    attrs: IntArray,
    block: TypedArray.() -> Unit
) {
    obtainStyledAttributes(attributes, attrs, 0, 0).use {
        block(it)
    }
}

fun Context.getColorCompat(@ColorRes colorResourceId: Int): Int {
    return ContextCompat.getColor(this, colorResourceId)
}

fun Context.getColoredDrawable(@DrawableRes drawableResourceId: Int, @ColorInt color: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableResourceId)?.applyColor(color)
}
