package be.lennertsoffers.demo_custom_annotation_processor.models;

import be.lennertsoffers.annotations_library.Column;
import be.lennertsoffers.annotations_library.Entity;
import be.lennertsoffers.annotations_library.PrimaryKey;

@Entity(tableName = "car")
public class Car {
    @PrimaryKey
    private int id;
    @Column
    private String make;
    @Column
    private String model;
    @Column
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
