package com.icraus.controller;

import com.icraus.bussinesslogic.DroneException;
import com.icraus.bussinesslogic.IDroneBusiness;
import com.icraus.model.Drone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
public class DispatchController {

    private final IDroneBusiness droneBusiness;

    public DispatchController(IDroneBusiness droneBusiness) {
        this.droneBusiness = droneBusiness;
    }

    @PostMapping(value = "/drone/register")
    public ResponseEntity<Drone> registerDrone(@RequestBody Drone drone){
        try {
            droneBusiness.registerDrone(drone);
        } catch (DroneException e) {
            Logger.getAnonymousLogger().severe(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(drone, HttpStatus.OK);
    }

    @PostMapping(value = "/drone/load/{droneSerialNumber}")
    public ResponseEntity<Drone> loadDroneWithMedication(@PathVariable String droneSerialNumber, @RequestBody List<Long> medicationIdList){
        try {
            Drone drone = droneBusiness.loadDroneWithMedication(droneSerialNumber, medicationIdList);
            return new ResponseEntity<>(drone, HttpStatus.OK);
        } catch (DroneException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
