package com.icraus.dao;

import com.icraus.model.Medication;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationDAO extends CrudRepository<Medication, Long> {
}
