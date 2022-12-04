package be.lennertsoffers.demo_room_annotation_processor

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import be.lennertsoffers.demo_room_annotation_processor.models.Person

@Dao
interface PersonDao {
    @Insert
    fun insert(item: Person) : Long

    @Query("SELECT * from person")
    fun findAll(): List<Person>

    @Query("DELETE FROM person WHERE id = :itemId")
    fun delete(itemId: Int)
}