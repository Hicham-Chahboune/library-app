package com.example.ttss.util

import com.google.gson.GsonBuilder

import com.google.gson.Gson




class GsonHandler {
    companion object{
        private var gson: Gson? = null

        fun getGsonParser(): Gson? {
            if (null == gson) {
                val builder = GsonBuilder()
                gson = builder.create()
            }
            return gson
        }
    }

}