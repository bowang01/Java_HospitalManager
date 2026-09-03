package com.bo.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bo.hospital.pojo.Patient;
import org.apache.ibatis.annotations.Param;

public interface PatientMapper extends BaseMapper<Patient> {
    /**
     * Count patient gender stats
     */
    Integer patientAge(@Param("startAge") int startAge, @Param("endAge") int endAge);
}
