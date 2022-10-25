package com.icraus.ontroller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icraus.bussinesslogic.DroneBusinessImpl;
import com.icraus.dao.DroneDAO;
import com.icraus.dao.MedicationDAO;
import com.icraus.model.Drone;
import com.icraus.model.Medication;
import controller.DispatchController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackageClasses = {DispatchController.class})
public class DispatchControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private DroneDAO droneDAO;

    @Autowired
    MedicationDAO medicationDAO;

    @Autowired
    private MockMvc mockMvc;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    void testRegisterEntityApi() throws Exception {
        Drone drone = new Drone();
        drone.setSerialNumber("ADAQE");
        drone.setBatteryCapacity(25);
        drone.setWeightLimit(400);
        droneDAO.save(drone);
        String droneJson = this.mapToJson(drone);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/drone/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(droneJson))
                .andReturn();
        Assertions.assertEquals(409, result.getResponse().getStatus());
        droneDAO.delete(drone);
        drone = new Drone();
        drone.setSerialNumber("ADAQEc");
        drone.setBatteryCapacity(25);
        drone.setWeightLimit(400);
        droneJson = this.mapToJson(drone);
        System.out.println(droneJson);
        result = mockMvc.perform(MockMvcRequestBuilders.post("/drone/register").contentType(MediaType.APPLICATION_JSON).content(droneJson)).andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
        droneDAO.delete(drone);
    }

    @Test
    void testLoadDroneWithMedicationFailsWeightLimit() throws Exception {
        Drone drone = new Drone();
        drone.setSerialNumber("AD");
        drone.setWeightLimit(20);
        Medication medication1 = new Medication();
        medication1.setWeight(50);
        Medication medication2 = new Medication();
        medication2.setWeight(50);

        droneDAO.save(drone);
        medicationDAO.save(medication1);
        medicationDAO.save(medication2);
        List<Long> medicationIdList = List.of(medication1.getId(), medication2.getId());
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/drone/load/"  + drone.getSerialNumber())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(medicationIdList)))
                .andReturn();
        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Error Drone Can't carry this weight.", result.getResponse().getContentAsString());

        medicationDAO.delete(medication1);
        medicationDAO.delete(medication2);
        droneDAO.delete(drone);
    }

    @Test
    void testLoadDroneWithMedicationRightWeightLimit() throws Exception {
        Drone drone = new Drone();
        drone.setSerialNumber("AD");
        drone.setWeightLimit(150);
        drone.setBatteryCapacity(50);
        Medication medication = new Medication();
        medication.setWeight(50);
        Medication medication2 = new Medication();
        medication2.setWeight(50);

        droneDAO.save(drone);
        medicationDAO.save(medication);
        medicationDAO.save(medication2);
        List<Long> medicationIdList = List.of(medication.getId(), medication2.getId());
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/drone/load/"  + drone.getSerialNumber())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(medicationIdList)))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        Assertions.assertEquals(200, result.getResponse().getStatus());
        medicationDAO.delete(medication);
        medicationDAO.delete(medication2);
        droneDAO.delete(drone);
    }
    @Test
    void testLoadDroneWithMedicationBatteryError() throws Exception {
        Drone drone = new Drone();
        drone.setSerialNumber("AD");
        drone.setWeightLimit(150);
        drone.setBatteryCapacity(20);
        Medication medication1 = new Medication();
        medication1.setWeight(50);
        Medication medication2 = new Medication();
        medication2.setWeight(50);

        droneDAO.save(drone);
        medicationDAO.save(medication1);
        medicationDAO.save(medication2);
        List<Long> medicationIdList = List.of(medication1.getId(), medication2.getId());
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/drone/load/"  + drone.getSerialNumber())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(medicationIdList)))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        Assertions.assertEquals(400, result.getResponse().getStatus());
        medicationDAO.delete(medication1);
        medicationDAO.delete(medication2);
        droneDAO.delete(drone);
    }
}

