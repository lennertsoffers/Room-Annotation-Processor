package be.lennertsoffers.demo_room_annotation_processor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import be.lennertsoffers.demo_room_annotation_processor.models.Car

@Dao
interface CarDao {
    @Insert
    fun insert(item: Car) : Long

    @Query("SELECT * from car")
    fun findAll(): List<Car>

    @Query("DELETE FROM car WHERE id = :itemId")
    fun delete(itemId: Int)
}