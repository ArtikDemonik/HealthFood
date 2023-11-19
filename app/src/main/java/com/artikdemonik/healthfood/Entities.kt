package com.artikdemonik.healthfood

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDbEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "needsCalories") val needCalories: Int,
)

@Entity(tableName = "product")
data class ProductDbEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "calories") val calories: Int,
    @ColumnInfo(name = "proteins") val proteins: Int,
    @ColumnInfo(name = "fats") val fats: Int,
    @ColumnInfo(name = "carbohydrates") val carbohydrates: Int,
)

@Entity(tableName = "meal")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "product")
    val product: Int,

    @ColumnInfo(name = "weight")
    val weight: Double,

    @ColumnInfo(name = "user")
    val user: Int,

    @ColumnInfo(name = "date")
    val date: String
)

data class UserTuple(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "needsCalories") val needCalories: Int
)

@Entity(
    tableName = "dailyConsumption",
    foreignKeys = [
        ForeignKey(
            entity = UserDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["user"]
        )]
    )
data class DailyConsumption(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "date") val name: String,
    @ColumnInfo(name = "advisableCalories") val calories: Int,
    @ColumnInfo(name = "eatenCalories") val proteins: Int,
    @ColumnInfo(name = "user") val fats: Int
)
