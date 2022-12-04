package be.lennertsoffers.demo_room_annotation_processor

import androidx.room.Database
import androidx.room.RoomDatabase
import be.lennertsoffers.demo_room_annotation_processor.models.Car
import be.lennertsoffers.demo_room_annotation_processor.models.Person

@Database(entities = [Car::class, Person::class], version = 1)
abstract class DemoRoomDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun personDao(): PersonDao
}