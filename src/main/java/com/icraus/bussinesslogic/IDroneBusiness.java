package com.icraus.bussinesslogic;


import com.icraus.model.Drone;

import java.util.List;

public interface IDroneBusiness {
    Drone registerDrone(Drone drone) throws DroneException;
    Drone loadDroneWithMedication(String droneId, List<Long> medicationIds) throws DroneException;
}
