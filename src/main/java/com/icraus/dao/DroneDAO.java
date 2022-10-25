package com.icraus.dao;

import com.icraus.model.Drone;
import org.springframework.data.repository.CrudRepository;

public interface DroneDAO extends CrudRepository<Drone, String> {
}
