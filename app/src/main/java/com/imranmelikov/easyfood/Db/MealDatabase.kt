package com.imranmelikov.easyfood.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.imranmelikov.easyfood.pojo.Meal

@Database(entities = arrayOf(Meal::class), version = 1)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase :RoomDatabase(){
    abstract fun mealDao():Dao

    companion object{
        @Volatile
        var INSTANCE:MealDatabase?=null

        @Synchronized
        fun getInstance(context:Context):MealDatabase{
            if(INSTANCE==null){
                INSTANCE= Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }
}