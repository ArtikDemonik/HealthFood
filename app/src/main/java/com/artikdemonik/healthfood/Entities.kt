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
    @ColumnInfo(name = "activityFactor") val activityFactor: Int,
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
