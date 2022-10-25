package com.icraus.dao;

import com.icraus.model.Medication;
import org.springframework.data.repository.CrudRepository;

public interface MedicationDAO extends CrudRepository<Medication, Long> {
}
