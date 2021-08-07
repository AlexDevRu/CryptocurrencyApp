package com.example.data.database.converters

import androidx.room.TypeConverter
import java.util.*

class ArticleTypeConverters {
    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
    @TypeConverter
    fun toDate(milliseconds: Long): Date {
        return Date(milliseconds)
    }
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
    @TypeConverter
    fun toStringList(string: String?): List<String>? {
        return if(string.isNullOrEmpty()) null else string.split(";")
    }
    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return if(list.isNullOrEmpty()) null else list.joinToString(";")
    }
}