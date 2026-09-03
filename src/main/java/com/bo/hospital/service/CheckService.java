package com.bo.hospital.service;

import com.bo.hospital.pojo.Checks;

import java.util.HashMap;

public interface CheckService {
    /**
     * Paginated fuzzy search of all exam items
     */
    HashMap<String, Object> findAllChecks(int pageNumber, int size, String query);
    /**
     * Find drug by id
     */
    Checks findCheck(int chId);
    /**
     * Add exam item
     */
    Boolean addCheck(Checks checks);
    /**
     * Delete exam item
     */
    Boolean deleteCheck(int chId);
    /**
     * Update exam item
     */
    Boolean modifyCheck(Checks checks);
}
