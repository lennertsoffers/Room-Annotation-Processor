package be.lennertsoffers.demo_room_annotation_processor

import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import be.lennertsoffers.demo_room_annotation_processor.fragments.HomeFragmentDirections

class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = this.supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
        if (navHostFragment != null) navController = navHostFragment.navController
        setupActionBarWithNavController(this, navController!!)

        db = Room.databaseBuilder(
            applicationContext,
            DemoRoomDatabase::class.java,
            "room_demo_db"
        ).allowMainThreadQueries().build()
    }

    override fun onStop() {
        db.close()
        super.onStop()
    }

    fun handleShowCarsClick() {
        navController!!.navigate(HomeFragmentDirections.actionHomeFragmentToCarsFragment())
    }

    fun handleShowPersonsClick() {
        navController!!.navigate(HomeFragmentDirections.actionHomeFragmentToPersonsFragment())
    }

    companion object {
        private lateinit var db: DemoRoomDatabase

        fun getDb(): DemoRoomDatabase {
            return this.db
        }
    }
}