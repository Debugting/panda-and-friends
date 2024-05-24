package com.example.lib_frame.datacenter

interface IUser {
    fun id(): Long?
    fun name(): String
    fun province(): String
    fun imgUrl(): String
}