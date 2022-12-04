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
import be.lennertsoffers.demo_room_annotation_processor.adapters.PersonsAdapter;
import be.lennertsoffers.demo_room_annotation_processor.models.Person;

public class PersonsFragment extends Fragment {
    private PersonsAdapter personsAdapter;
    private RecyclerView personRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_persons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Load data into recyclerView
        var db = MainActivity.Companion.getDb();
        var personDao = db.personDao();

        RecyclerView personRecyclerView = view.findViewById(R.id.persons);
        personRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.personsAdapter = new PersonsAdapter(personDao.findAll());
        personRecyclerView.setAdapter(this.personsAdapter);
        this.personRecyclerView = personRecyclerView;

        // Add event listener for creating new person
        Button savePersonButton = view.findViewById(R.id.savePersonButton);
        savePersonButton.setOnClickListener((v) -> {
            TextView nameInput = view.findViewById(R.id.nameInput);
            TextView ageInput = view.findViewById(R.id.ageInput);

            String name = nameInput.getText().toString();
            int age;
            if (name.isBlank() || name.equals("Name")) return;
            try {
                age = Integer.parseInt(ageInput.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return;
            }

            Person person = new Person();
            person.setName(name);
            person.setAge(age);
            long personId = personDao.insert(person);
            person.setId((int) personId);

            this.personsAdapter.add(person);

            nameInput.setText(R.string.name);
            ageInput.setText(R.string.age);
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
                int personId = personsAdapter.delete(position).getId();
                var personDao = MainActivity.Companion.getDb().personDao();
                personDao.delete(personId);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchCallback);
        itemTouchHelper.attachToRecyclerView(this.personRecyclerView);
    }
}