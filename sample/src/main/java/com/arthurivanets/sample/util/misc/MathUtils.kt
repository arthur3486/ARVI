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

@file:JvmName("MathUtils")

package com.arthurivanets.sample.util.misc


/**
 *
 */
fun Int.randomUpTo(max : Int) = (this + (Math.random() * (max - this)).toInt())


/**
 *
 */
fun Long.randomUpTo(max : Long) = (this + (Math.random() * (max - this)).toLong())


/**
 *
 */
fun Float.randomUpTo(max : Float) = (this + (Math.random() * (max - this)))


/**
 *
 */
fun Double.randomUpTo(max : Double) = (this + (Math.random() * (max - this)))


/**
 *
 */
fun randomPositiveInt() = 1.randomUpTo(Int.MAX_VALUE)


/**
 *
 */
fun randomInt(includeNegativeNumbers : Boolean = true) = (if(includeNegativeNumbers) Int.MIN_VALUE else 0).randomUpTo(Int.MAX_VALUE)


/**
 *
 */
fun randomPositiveLong() = 1L.randomUpTo(Long.MAX_VALUE)


/**
 *
 */
fun randomLong(includeNegativeNumbers : Boolean = true) = (if(includeNegativeNumbers) Long.MIN_VALUE else 0L).randomUpTo(Long.MAX_VALUE)


/**
 *
 */
fun randomPositiveFloat() = 1.0F.randomUpTo(Float.MAX_VALUE)


/**
 *
 */
fun randomFloat(includeNegativeNumbers : Boolean = true) = (if(includeNegativeNumbers) Float.MIN_VALUE else 0.0F).randomUpTo(Float.MAX_VALUE)


/**
 *
 */
fun randomPositiveDouble() = 1.0.randomUpTo(Double.MAX_VALUE)


/**
 *
 */
fun randomDouble(includeNegativeNumbers : Boolean = true) = (if(includeNegativeNumbers) Double.MIN_VALUE else 0.0).randomUpTo(Double.MAX_VALUE)