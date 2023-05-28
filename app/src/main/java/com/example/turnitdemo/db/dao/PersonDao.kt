package com.example.turnitdemo.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turnitdemo.db.entity.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)

    @Query("SELECT * FROM person")
    fun getAll(): Flow<List<Person>>

    @Query("DELETE FROM person")
    suspend fun deleteAll()
}