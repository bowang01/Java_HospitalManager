package com.bo.hospital.service;

import com.bo.hospital.pojo.Patient;

import java.util.HashMap;
import java.util.List;

public interface PatientService {
    /**
     * Login validation
     * */
    Patient login(int pId, String pPassword);
    /**
     * Paginated fuzzy search of all patients
     */
    HashMap<String, Object> findAllPatients(int pageNumber, int size, String query);
    /**
     * Delete patient
     */
    Boolean deletePatient(int pId);
    /**
     * Find patient info by patient id
     */
    Patient findPatientById(int pId);
    /**
     * Add patient
     */
    Boolean addPatient(Patient patient);
    /**
     * Count patient gender stats
     */
    List<Integer> patientAge();
}
