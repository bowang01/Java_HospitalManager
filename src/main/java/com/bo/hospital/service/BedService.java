package com.bo.hospital.service;

import com.bo.hospital.pojo.Bed;

import java.util.HashMap;
import java.util.List;

public interface BedService {
    /**
     * Find all empty beds
     */
    List<Bed> findNullBed();
    /**
     * Update bed info
     */
    Boolean updateBed(Bed bed);
    /**
     * Find inpatient record by pId
     */
    List<Bed> findBedByPid(int pId);
    /**
     * Paginated fuzzy search of all beds
     */
    HashMap<String, Object> findAllBeds(int pageNumber, int size, String query);
    /**
     * Find bed by id
     */
    Bed findBed(int bId);
    /**
     * Add bed info
     */
    Boolean addBed(Bed bed);
    /**
     * Delete bed
     */
    Boolean deleteBed(int bId);
    /**
     * Clear bed info
     */
    Boolean emptyBed(int bId);
    /**
     * Count today's inpatients
     */
    int bedPeople(String bStart);
}
