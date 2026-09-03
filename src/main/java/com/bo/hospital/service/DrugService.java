package com.bo.hospital.service;

import com.bo.hospital.pojo.Drug;

import java.util.HashMap;

public interface DrugService {
    /**
     * Paginated fuzzy search of all drugs
     */
    HashMap<String, Object> findAllDrugs(int pageNumber, int size, String query);
    /**
     * Find drug by id
     */
    Drug findDrug(int drId);
    /**
     * Reduce drug stock by id
     */
    Boolean reduceDrugNumber(int drId,int usedNumber);
    /**
     * Add drug
     */
    Boolean addDrug(Drug drug);
    /**
     * Delete drug
     */
    Boolean deleteDrug(int drId);
    /**
     * Update drug
     */
    Boolean modifyDrug(Drug drug);
}
