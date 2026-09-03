package com.bo.hospital.service;

import com.bo.hospital.pojo.Orders;

import java.util.HashMap;
import java.util.List;

public interface OrderService {
    /**
     * Paginated fuzzy search of all appointments
     */
    HashMap<String, Object> findAllOrders(int pageNumber, int size, String query);
    /**
     * Permanently delete appointment
     */
    Boolean deleteOrder(int oId);
    /**
     * Add appointment
     */
    Boolean addOrder(Orders order, String arId);
    /**
     * Find appointments by pId
     */
    List<Orders> findOrderByPid(int pId) ;
    /**
     * Find today's appointment list
     */
    List<Orders> findOrderByNull(int dId, String oStart) ;
    /**
     * Update appointment by id
     */
    Boolean updateOrder(Orders orders);
    /**
     * Set payment status by id
     */
    Boolean updatePrice(int oId);
    /**
     * Find doctor's completed appointments
     */
    HashMap<String, Object> findOrderFinish(int pageNumber, int size, String query, int dId) ;
    /**
     * Find appointments by dId
     */
    HashMap<String, Object> findOrderByDid(int pageNumber, int size, String query, int dId) ;
    /**
     * Count today's appointments
     */
    int orderPeople(String oStart);
    /**
     * Count today's appointments for a doctor
     */
    int orderPeopleByDid(String oStart, int dId);
    /**
     * Count appointment gender stats
     */
    List<String> orderGender();
    /**
     * Add diagnosis and doctor notes
     */
    Boolean updateOrderByAdd(Orders order);
    /**
     * Check whether extra drugs after diagnosis have been paid
     */
    Boolean findTotalPrice(int oId);
    /**
     * Request appointment time slots
     */
    HashMap<String, String> findOrderTime(String arId);
    /**
     * Count department appointments for the last 20 days
     */
    List<String> orderSection();

}
