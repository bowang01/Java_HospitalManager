package com.bo.hospital.controller;

import com.bo.hospital.pojo.Orders;
import com.bo.hospital.service.OrderService;
import com.bo.hospital.utils.ResponseData;
import com.bo.hospital.utils.TodayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    /**
     * Update appointment by id
     */
    @PostMapping("updateOrder")
    public ResponseData updateOrder(@RequestBody Orders orders) {
        if (this.orderService.updateOrder(orders))
            return ResponseData.success("Appointment updated");

        return ResponseData.fail("Failed to update appointment");
    }
    /**
     * Set payment status by id
     */
    @RequestMapping("updatePrice")
    public ResponseData updatePrice(int oId){
        if (this.orderService.updatePrice(oId))
        return ResponseData.success("Payment status updated");
        return ResponseData.success("Failed to update payment status");
    }
    /**
     * Find doctor's completed appointments
     */
    @RequestMapping("findOrderFinish")
    public ResponseData findOrderFinish(int pageNumber, int size, String query, int dId){
        return ResponseData.success("Completed appointments loaded", this.orderService.findOrderFinish(pageNumber, size, query, dId));
    }
    /**
     * Find appointments by dId
     */
    @RequestMapping("findOrderByDid")
    public ResponseData findOrderByDid(int pageNumber, int size, String query, int dId){
        return ResponseData.success("Appointments loaded", this.orderService.findOrderByDid(pageNumber, size, query, dId)) ;
    }
    /**
     * Count today's appointments
     */
    @RequestMapping("orderPeople")
    public ResponseData oderPeople(){
        String oStart = TodayUtil.getTodayYmd();
        return ResponseData.success("Today's appointment count loaded", this.orderService.orderPeople(oStart));
    }
    /**
     * Count today's appointments for a doctor
     */
    @RequestMapping("orderPeopleByDid")
    public ResponseData orderPeopleByDid(int dId){
        String oStart = TodayUtil.getTodayYmd();
        return ResponseData.success("Today's appointment count loaded", this.orderService.orderPeopleByDid(oStart, dId));
    }
    /**
     * Get appointment counts for the last 20 days
     */
    @RequestMapping("orderSeven")
    public ResponseData orderSeven(){
        ArrayList<Integer> list = new ArrayList<>();
        String oStart = null;
        for(int i = 10; i > -10; i--){
            oStart = TodayUtil.getPastDate(i);
            int people = this.orderService.orderPeople(oStart);
            list.add(people);
        }
        return ResponseData.success("Last 20 days appointment counts loaded", list);
    }
    /**
     * Count appointment gender stats
     */
    @RequestMapping("orderGender")
    public ResponseData orderGender(){
        return ResponseData.success("Appointment gender stats loaded", this.orderService.orderGender());
    }
    /**
     * Add diagnosis and doctor notes
     */
    @PostMapping("updateOrderByAdd")
    public ResponseData updateOrderByAdd(@RequestBody Orders order){
        if (this.orderService.updateOrderByAdd(order))
            return ResponseData.success("Diagnosis saved");
        return ResponseData.fail("Failed to save diagnosis");
    }
    /**
     * Check whether extra drugs after diagnosis have been paid
     */
    @RequestMapping("findTotalPrice")
    public ResponseData findTotalPrice(int oId){
       if(this.orderService.findTotalPrice(oId))
           return ResponseData.success("Unpaid");
       return ResponseData.fail("No payment needed");
    }
    /**
     * Request appointment time slots
     */
    @RequestMapping("findOrderTime")
    public ResponseData findOrderTime(String arId){
        return ResponseData.success("Time slots loaded", this.orderService.findOrderTime(arId));

    }
    /**
     * Count department appointments for the last 20 days
     */
    @RequestMapping("orderSection")
    public ResponseData orderSection(){
        return ResponseData.success("Department appointment stats loaded", this.orderService.orderSection());
    }

}
