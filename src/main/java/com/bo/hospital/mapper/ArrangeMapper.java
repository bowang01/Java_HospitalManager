package com.bo.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bo.hospital.pojo.Arrange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArrangeMapper extends BaseMapper<Arrange> {

    /**
     * Find schedules by date
     */
    List<Arrange> findByTime(@Param("ar_time") String arTime, @Param("d_section") String dSection);

}
