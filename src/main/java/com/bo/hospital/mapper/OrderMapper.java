package com.bo.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bo.hospital.pojo.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Orders> {
    /**
     * Count today's appointments
     */
    int orderPeople(String oStart);
    /**
     * Count today's appointments for a doctor
     */
    int orderPeopleByDid(@Param("o_start") String oStart, @Param("d_id") int dId);
    /**
     * Count appointment gender stats
     */
    List<String> orderGender();
    /**
     * Find appointment by order number
     */
    Orders findOrderByOid(int oId);
    /**
     * Add diagnosis and doctor notes
     */
    Integer updateOrderByAdd(Orders order);
    /**
     * Count department appointments for the last 20 days
     */
    List<String> orderSection(@Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * Find today's appointment list
     */
    List<Orders> findOrderByNull(@Param("dId") int dId, @Param("oStart") String oStart);
    /**
     * Find appointments by pId
     */
    List<Orders> findOrderByPid(int pId);

}
