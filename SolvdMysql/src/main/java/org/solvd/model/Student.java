package org.solvd.model;

public class Student {
    private String firstName;
    private int carId;

    // Default constructor
    public Student() {}

    // Parameterized constructor
    public Student(String firstName, int carId) {
        this.firstName = firstName;
        this.carId = carId;
    }

    // Getter and Setter for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for carId
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", carId=" + carId +
                '}';
    }
}