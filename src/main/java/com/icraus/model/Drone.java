package com.icraus.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.*;

@Entity
public class Drone {

    @Id
    @Column(length = 100)
    @Length(max = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModel droneModel;

    @Max(500)
    private double weightLimit;

    @Max(100)
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private DroneState droneState;


    @OneToMany()
    private final Set<Medication> medications = new HashSet<>();

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DroneModel getDroneModel() {
        return droneModel;
    }

    public void setDroneModel(DroneModel droneModel) {
        this.droneModel = droneModel;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public DroneState getDroneState() {
        return droneState;
    }

    public void setDroneState(DroneState droneState) {
        this.droneState = droneState;
    }

    public Set<Medication> getMedications() {
        return medications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return getSerialNumber().equals(drone.getSerialNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSerialNumber());
    }

    @Override
    public String toString() {
        return "Drone{" +
                " serialNumber='" + serialNumber + '\'' +
                ", droneModel=" + droneModel +
                ", weightLimit=" + weightLimit +
                ", batteryCapacity=" + batteryCapacity +
                ", droneState=" + droneState +
                '}';
    }
}
