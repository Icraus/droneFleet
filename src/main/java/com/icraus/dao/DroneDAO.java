package com.icraus.dao;

import com.icraus.model.Drone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneDAO extends CrudRepository<Drone, String> {
}
