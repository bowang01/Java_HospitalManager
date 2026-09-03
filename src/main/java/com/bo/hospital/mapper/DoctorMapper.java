package com.bo.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bo.hospital.pojo.Doctor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DoctorMapper extends BaseMapper<Doctor> {

    List<Doctor> findDoctorBySection(String dSection);
    /**
     * User rating
     */
    Boolean updateStar(@Param("dId")int dId, @Param("dStar")Double dStar);
}
