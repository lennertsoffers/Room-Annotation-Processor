package be.lennertsoffers.demo_custom_annotation_processor.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.lennertsoffers.demo_custom_annotation_processor.R;
import be.lennertsoffers.demo_custom_annotation_processor.models.Car;

public class CarsAdapter extends RecyclerView.Adapter<CarItemViewHolder> {
    private List<Car> cars;

    public CarsAdapter(List<Car> cars) {
        this.cars = cars;
    }

    @NonNull
    @Override
    public CarItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.car_item, parent, false);
        return new CarItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarItemViewHolder holder, int position) {
        Car car = this.cars.get(position);

        holder.carIdView.setText(String.valueOf(car.getId()));
        holder.carMakeView.setText(car.getMake());
        holder.carModelView.setText(car.getModel());
        holder.carPriceView.setText(String.valueOf(car.getPrice()));
    }

    @Override
    public int getItemCount() {
        return this.cars.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<Car> cars) {
       this.cars = cars;
       this.notifyDataSetChanged();
    }

    public void add(Car car) {
        int position = this.getItemCount();
        this.cars.add(car);
        this.notifyItemChanged(position);
    }

    public Car delete(int position) {
        Car car = this.cars.remove(position);
        this.notifyItemRemoved(position);

        return car;
    }
}
