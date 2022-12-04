package be.lennertsoffers.demo_room_annotation_processor.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.lennertsoffers.demo_room_annotation_processor.R;
import be.lennertsoffers.demo_room_annotation_processor.models.Person;

public class PersonsAdapter extends RecyclerView.Adapter<PersonItemViewHolder> {
    private List<Person> persons;

    public PersonsAdapter(List<Person> persons) {
        this.persons = persons;
    }

    @NonNull
    @Override
    public PersonItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.person_item, parent, false);
        return new PersonItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonItemViewHolder holder, int position) {
        Person person = this.persons.get(position);

        holder.personIdView.setText(String.valueOf(person.getId()));
        holder.personNameView.setText(person.getName());
        holder.personAgeView.setText(String.valueOf(person.getAge()));
    }

    @Override
    public int getItemCount() {
        return this.persons.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<Person> persons) {
        this.persons = persons;
        this.notifyDataSetChanged();
    }

    public void add(Person person) {
        int position = this.getItemCount();
        this.persons.add(person);
        this.notifyItemChanged(position);
    }

    public Person delete(int position) {
        Person person = this.persons.remove(position);
        this.notifyItemRemoved(position);

        return person;
    }
}
