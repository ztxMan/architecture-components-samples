/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.paging.pagingwithnetwork

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.example.paging.pagingwithnetwork.reddit.repository.RedditPostRepository
import com.android.example.paging.pagingwithnetwork.reddit.ui.RedditActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * chooser activity for the demo.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        withDatabase.setOnClickListener {
            show(RedditPostRepository.Type.DB)
        }
        networkOnly.setOnClickListener {
            show(RedditPostRepository.Type.IN_MEMORY_BY_ITEM)
        }
        networkOnlyWithPageKeys.setOnClickListener {
            show(RedditPostRepository.Type.IN_MEMORY_BY_PAGE)
        }

        runBlocking(Dispatchers.IO) {
            Log.d("AAAA", "${trivialFun2()} + ${trivialFun()}")
        }

        runBlocking(Dispatchers.Main) {
            Log.d("AAAA", "${trivialFun2()} + ${trivialFun()}")
        }
    }

    private fun show(type: RedditPostRepository.Type) {
        val intent = RedditActivity.intentFor(this, type)
        startActivity(intent)
    }

    suspend fun trivialFun(): Int {
        return suspendCoroutine { it.resume(3) }
    }

    suspend fun trivialFun2(): Int {
        val a = suspendCoroutine<Int> { it.resume(2) }
        return trivialFun() + a
    }
}
