package be.lennertsoffers.demo_room_annotation_processor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import be.lennertsoffers.demo_room_annotation_processor.MainActivity;
import be.lennertsoffers.demo_room_annotation_processor.R;
import be.lennertsoffers.demo_room_annotation_processor.adapters.CarsAdapter;
import be.lennertsoffers.demo_room_annotation_processor.models.Car;

public class CarsFragment extends Fragment {
    private CarsAdapter carsAdapter;
    private RecyclerView carRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cars, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Load data into recyclerView
        var db = MainActivity.Companion.getDb();
        var carDao = db.carDao();

        RecyclerView carRecyclerView = view.findViewById(R.id.cars);
        carRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.carsAdapter = new CarsAdapter(carDao.findAll());
        carRecyclerView.setAdapter(this.carsAdapter);
        this.carRecyclerView = carRecyclerView;

        // Add event listener for creating new car
        Button saveCarButton = view.findViewById(R.id.saveCarButton);
        saveCarButton.setOnClickListener((v) -> {
            TextView makeInput = view.findViewById(R.id.makeInput);
            TextView modelInput = view.findViewById(R.id.modelInput);
            TextView priceInput = view.findViewById(R.id.priceInput);

            String make = makeInput.getText().toString();
            String model = modelInput.getText().toString();
            double price;
            if (make.isBlank() || model.isBlank() || make.equals("Make") || model.equals("Model")) return;
            try {
                 price = Double.parseDouble(priceInput.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return;
            }

            Car car = new Car();
            car.setMake(make);
            car.setModel(model);
            car.setPrice(price);
            long carId = carDao.insert(car);
            car.setId((int) carId);

            this.carsAdapter.add(car);

            makeInput.setText(R.string.make);
            modelInput.setText(R.string.model);
            priceInput.setText(R.string.price);
        });

        this.initSwipe();
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback touchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                int carId = carsAdapter.delete(position).getId();
                var carDao = MainActivity.Companion.getDb().carDao();
                carDao.delete(carId);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchCallback);
        itemTouchHelper.attachToRecyclerView(this.carRecyclerView);
    }
}