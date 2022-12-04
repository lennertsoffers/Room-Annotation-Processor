package be.lennertsoffers.demo_custom_annotation_processor.models;

import be.lennertsoffers.annotations_library.Column;
import be.lennertsoffers.annotations_library.Entity;
import be.lennertsoffers.annotations_library.PrimaryKey;

@Entity
public class Person {
    @PrimaryKey
    private int id;
    @Column
    private String name;
    @Column
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
