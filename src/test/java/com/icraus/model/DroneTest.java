package com.icraus.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class DroneTest {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    @Test
    public void testDroneValidation(){
        Drone drone = new Drone();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 103; ++i){
            stringBuilder.append("*");
        }
        drone.setSerialNumber(stringBuilder.toString());
        Set<ConstraintViolation<Drone>> constraintViolations = validator.validate(drone);
        Assertions.assertNotEquals(0, constraintViolations.size());
        drone.setSerialNumber("AA");
        drone.setWeightLimit(600);
        constraintViolations = validator.validate(drone);
        Assertions.assertEquals(1, constraintViolations.size());
        drone.setWeightLimit(400);
        constraintViolations = validator.validate(drone);
        Assertions.assertEquals(0, constraintViolations.size());
        drone.setBatteryCapacity(400);
        constraintViolations = validator.validate(drone);
        Assertions.assertEquals(1, constraintViolations.size());
        drone.setBatteryCapacity(20);
        constraintViolations = validator.validate(drone);
        Assertions.assertEquals(0, constraintViolations.size());
    }
}