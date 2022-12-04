package be.lennertsoffers.demo_custom_annotation_processor.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import be.lennertsoffers.demo_custom_annotation_processor.R;

public class CarItemViewHolder extends RecyclerView.ViewHolder {
    TextView carIdView;
    TextView carMakeView;
    TextView carModelView;
    TextView carPriceView;

    public CarItemViewHolder(@NonNull View view) {
        super(view);

        this.carIdView = view.findViewById(R.id.carId);
        this.carMakeView = view.findViewById(R.id.carMake);
        this.carModelView = view.findViewById(R.id.carModel);
        this.carPriceView = view.findViewById(R.id.carPrice);
    }
}