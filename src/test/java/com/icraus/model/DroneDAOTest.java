package com.icraus.model;

import com.icraus.dao.DroneDAO;
import com.icraus.dao.MedicationDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DroneDAOTest {
    @Autowired
    private DroneDAO droneDAO;

    @Autowired
    private MedicationDAO medicationDAO;

    @Test
    public void testDroneCreate(){
        Drone drone = new Drone();
        drone.setSerialNumber("AC0ACAQQASDCQasd");
        drone.setDroneState(DroneState.DELIVERED);
        drone.setDroneModel(DroneModel.CruiserWeight);
        drone.setWeightLimit(400);
        drone.setBatteryCapacity(50);
        Assertions.assertDoesNotThrow(()->{
            droneDAO.save(drone);
        });
        Medication medication = new Medication();
        medication.setName("aa");
        medication.setWeight(1.0);
        Assertions.assertDoesNotThrow(()->{
            medicationDAO.save(medication);
            droneDAO.save(drone);
        });


    }
}
