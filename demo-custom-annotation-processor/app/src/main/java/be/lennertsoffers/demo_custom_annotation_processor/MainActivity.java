package be.lennertsoffers.demo_custom_annotation_processor;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import be.lennertsoffers.annotations_library.Configure;
import be.lennertsoffers.demo_custom_annotation_processor.fragments.HomeFragmentDirections;


@Configure(packageLocation = "be.lennertsoffers.demo_custom_annotation_processor", databaseName = "test_db", databaseVersion = 1)
public class MainActivity extends AppCompatActivity {
    public static be.lennertsoffers.demo_custom_annotation_processor.EasyPersistenceDatabase db;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (navHostFragment != null) this.navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, this.navController);

        db = new be.lennertsoffers.demo_custom_annotation_processor.EasyPersistenceDatabase(this.getApplicationContext());
    }

    @Override
    protected void onStop() {
        db.close();
        super.onStop();
    }

    public void handleShowCarsClick(View view) {
        this.navController.navigate(HomeFragmentDirections.actionHomeFragmentToCarsFragment());
    }

    public void handleShowPersonsClick(View view) {
        this.navController.navigate(HomeFragmentDirections.actionHomeFragmentToPersonsFragment());
    }
}