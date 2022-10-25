package com.icraus.bussinesslogic;

import com.icraus.dao.DroneDAO;
import com.icraus.dao.MedicationDAO;
import com.icraus.model.Drone;
import com.icraus.model.DroneState;
import com.icraus.model.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Service
public class DroneBusinessImpl implements IDroneBusiness {

    @Autowired
    private DroneDAO droneDAO;

    @Autowired
    private MedicationDAO medicationDAO;


    @Override
    public Drone registerDrone(Drone drone) throws DroneException {
        Optional<Drone> tmpDrone = droneDAO.findById(drone.getSerialNumber());
        if(tmpDrone.isPresent()){
            throw new DroneException("Error Drone with the same serial number Already Exists");
        }
        drone = droneDAO.save(drone);
        return drone;
    }

    @Override
    public Drone loadDroneWithMedication(String droneId, List<Long> medicationIds) throws DroneException {
        Optional<Drone> droneOptional = droneDAO.findById(droneId);
        if(!droneOptional.isPresent()){
            throw new DroneException("Error Drone Not found");
        }
        Drone drone = droneOptional.get();
        Supplier<Stream<Medication>> medicationStreamSupplier = () -> StreamSupport.stream(medicationDAO.findAllById(medicationIds).spliterator(), false);
        double medicationsWeight = medicationStreamSupplier.get().mapToDouble(Medication::getWeight).sum();
        if(medicationsWeight >= drone.getWeightLimit()){
            throw  new DroneException("Error Drone Can't carry this weight.");
        }
        if(drone.getBatteryCapacity() < 25){
            throw new DroneException("Error Drone Battery Capacity must be above 25% So you can't load it");
        }
        drone.setDroneState(DroneState.LOADING);
        drone.getMedications().addAll(medicationStreamSupplier.get().collect(Collectors.toList()));
        return drone;
    }

    @Override
    public List<String> getDronesInfo() {
        return StreamSupport.stream(droneDAO.findAll().spliterator(), false).map(e -> e.toString()).collect(Collectors.toList());
    }
}
