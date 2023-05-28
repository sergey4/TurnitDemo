package com.example.turnitdemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.turnitdemo.db.dao.PersonDao
import com.example.turnitdemo.db.entity.Person

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}
