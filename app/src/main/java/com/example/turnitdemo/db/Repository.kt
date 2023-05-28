package com.example.turnitdemo.db

import android.os.Looper
import android.util.Log
import com.example.turnitdemo.di.DispatcherProvider
import com.example.turnitdemo.entity.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val db: AppDatabase,
    private val dispatchers: DispatcherProvider
) {
    fun observeDatabase(): Flow<List<Person>> =
        db.personDao().getAll()
            .map { dbPersonList ->
                dbPersonList.map { dbPerson ->
                    Person(id = dbPerson.id, fullName = dbPerson.fullName, age = dbPerson.age)
                }
            }
                // 'default' dispatcher is for computation stuff (= non-IO / non-UI)
            .flowOn(dispatchers.default)

    suspend fun addPerson(person: Person) {
        withContext(dispatchers.io) {
            db.personDao().insertPerson(mapToDb(person))
        }
    }

    suspend fun deleteAll() {
        withContext(dispatchers.io) {
            db.personDao().deleteAll()
        }
    }

    private fun mapToDb(person: Person): com.example.turnitdemo.db.entity.Person {
        return com.example.turnitdemo.db.entity.Person(
            // 0 means auto-generate when autoGenerate = true
            id = 0,
            fullName = person.fullName,
            age = person.age
        )
    }
}