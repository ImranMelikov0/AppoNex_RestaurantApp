package com.imranmelikov.easyfood.Db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imranmelikov.easyfood.pojo.Meal

@androidx.room.Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal:Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query( "SELECT * FROM mealInformation" )
    fun getAllMeals():LiveData<List<Meal>>
}