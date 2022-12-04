package be.lennertsoffers.demo_custom_annotation_processor.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import be.lennertsoffers.demo_custom_annotation_processor.R;

public class PersonItemViewHolder extends RecyclerView.ViewHolder {
    TextView personIdView;
    TextView personNameView;
    TextView personAgeView;

    public PersonItemViewHolder(@NonNull View view) {
        super(view);

        this.personIdView = view.findViewById(R.id.personId);
        this.personNameView = view.findViewById(R.id.personName);
        this.personAgeView = view.findViewById(R.id.personAge);
    }
}