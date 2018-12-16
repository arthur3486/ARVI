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

package com.arthurivanets.sample.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {


    final override fun onCreate(savedInstanceState : Bundle?) {
        intent?.extras?.let(::fetchExtras)
        preInit()
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init(savedInstanceState)
        postInit()
    }


    @CallSuper
    protected open fun fetchExtras(extras : Bundle) {
        //
    }


    protected open fun preInit() {
        //
    }


    protected open fun init(savedInstanceState : Bundle?) {
        //
    }


    protected open fun postInit() {
        //
    }


    final override fun onDestroy() {
        onRecycle()
        super.onDestroy()
    }


    protected open fun onRecycle() {
        //
    }


    @LayoutRes
    abstract fun getLayoutId() : Int


}