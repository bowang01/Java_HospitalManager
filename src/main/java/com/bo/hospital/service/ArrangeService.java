package com.bo.hospital.service;

import com.bo.hospital.pojo.Arrange;

import java.util.List;

public interface ArrangeService {
    /**
     * Find schedules by date
     */
    List<Arrange> findByTime(String arTime, String dSection);
    /**
     * Add schedule
     */
    Boolean addArrange(Arrange arrange);
    /**
     * Delete schedule
     */
    Boolean deleteArrange(String arId);
}
