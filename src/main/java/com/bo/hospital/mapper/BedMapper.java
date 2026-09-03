package com.bo.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bo.hospital.pojo.Bed;

public interface BedMapper extends BaseMapper<Bed> {
    /**
     * Count today's inpatients
     */
    int bedPeople(String bStart);
}
