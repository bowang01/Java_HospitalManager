package com.bo.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bo.hospital.mapper.OrderMapper;
import com.bo.hospital.pojo.Orders;
import com.bo.hospital.service.OrderService;
import com.bo.hospital.utils.InputLengthValidator;
import com.bo.hospital.utils.RandomUtil;
import com.bo.hospital.utils.TodayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private JedisPool jedisPool;// Redis connection pool
    /**
     * Paginated fuzzy search of all appointments
     */
    @Override
    public HashMap<String, Object> findAllOrders(int pageNumber, int size, String query) {
        InputLengthValidator.checkQuery(query);
        Page<Orders> page = new Page<>(pageNumber, size);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.like("p_id", query);
        IPage<Orders> iPage = this.orderMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       // total count
        hashMap.put("pages", iPage.getPages());       // total pages
        hashMap.put("pageNumber", iPage.getCurrent());// current page
        hashMap.put("orders", iPage.getRecords()); // records
        return hashMap;
    }

    /**
     * Delete appointment
     */
    @Override
    public Boolean deleteOrder(int oId) {
        this.orderMapper.deleteById(oId);
        return true;
    }
    /**
     * Add appointment
     */
    @Override
    public Boolean addOrder(Orders order, String arId){
        InputLengthValidator.checkOrders(order);
        InputLengthValidator.check("schedule id", arId, InputLengthValidator.SHORT_TEXT);
        // Redis start
        Jedis jedis = jedisPool.getResource();
        String time = order.getOStart().substring(11, 22);
        synchronized (this) {
            if (time.equals("08:30-09:30")) {
                if (jedis.hget(arId, "eTOn").equals("0"))
                    return false;
                jedis.hincrBy(arId, "eTOn", -1);
            }

            if (time.equals("09:30-10:30")) {
                if (jedis.hget(arId, "nTOt").equals("0"))
                    return false;
                jedis.hincrBy(arId, "nTOt", -1);
            }
            if (time.equals("10:30-11:30")) {
                if (jedis.hget(arId, "tTOe").equals("0"))
                    return false;
                jedis.hincrBy(arId, "tTOe", -1);
            }
            if (time.equals("14:30-15:30")) {
                if (jedis.hget(arId, "fTOf").equals("0"))
                    return false;
                jedis.hincrBy(arId, "fTOf", -1);
            }
            if (time.equals("15:30-16:30")) {
                if (jedis.hget(arId, "fTOs").equals("0"))
                    return false;
                jedis.hincrBy(arId, "fTOs", -1);
            }
            if (time.equals("16:30-17:30")) {
                if (jedis.hget(arId, "sTOs").equals("0"))
                    return false;
                jedis.hincrBy(arId, "sTOs", -1);
            }
        }
        jedis.close();
        // Redis end
        order.setOId(RandomUtil.randomOid(order.getPId()));
        order.setOState(0);
        order.setOPriceState(0);
        order.setOStart(order.getOStart().substring(0,22));
        this.orderMapper.insert(order);
        return true;
    }
    /**
     * Find appointments by pId
     */
    public List<Orders> findOrderByPid(int pId){

        return this.orderMapper.findOrderByPid(pId);
    }
    /**
     * Find today's appointment list
     */
    @Override
    public List<Orders> findOrderByNull(int dId, String oStart){
        InputLengthValidator.check("start time", oStart, InputLengthValidator.DATE);
        return this.orderMapper.findOrderByNull(dId, oStart);
    }
    /**
     * Update appointment by id
     */
    @Override
    public Boolean updateOrder(Orders orders) {
        InputLengthValidator.checkOrders(orders);
        orders.setOState(1);
        orders.setOEnd(TodayUtil.getToday());
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("o_id", orders.getOId());
        this.orderMapper.update(orders, wrapper);
        return true;
    }
    /**
     * Set payment status by id
     */
    @Override
    public Boolean updatePrice(int oId){
        /**
         * QueryWrapper errors unless foreign key values are also passed in
         * UpdateWrapper works normally
         */
        UpdateWrapper<Orders> wrapper = new UpdateWrapper<>();
        wrapper.eq("o_id", oId).set("o_price_state", 1).set("o_total_price", 0.00);
        int i = this.orderMapper.update(null, wrapper);
        System.out.println("affected rows "+i);
        return true;
    }
    /**
     * Find doctor's completed appointments
     */
    @Override
    public HashMap<String, Object> findOrderFinish(int pageNumber, int size, String query, int dId){
        InputLengthValidator.checkQuery(query);
        Page<Orders> page = new Page<>(pageNumber, size);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.like("p_id", query).eq("d_id", dId).orderByDesc("o_start").eq("o_state", 1);
        IPage<Orders> iPage = this.orderMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       // total count
        hashMap.put("pages", iPage.getPages());       // total pages
        hashMap.put("pageNumber", iPage.getCurrent());// current page
        hashMap.put("orders", iPage.getRecords()); // records

        return hashMap;
    }
    /**
     * Find appointments by dId
     */
    public HashMap<String, Object> findOrderByDid(int pageNumber, int size, String query, int dId){
        InputLengthValidator.checkQuery(query);
        Page<Orders> page = new Page<>(pageNumber, size);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.like("p_id", query).eq("d_id", dId).orderByDesc("o_start");
        IPage<Orders> iPage = this.orderMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       // total count
        hashMap.put("pages", iPage.getPages());       // total pages
        hashMap.put("pageNumber", iPage.getCurrent());// current page
        hashMap.put("orders", iPage.getRecords()); // records
        return hashMap;
    }
    /**
     * Count today's appointments
     */
    @Override
    public int orderPeople(String oStart){
        return this.orderMapper.orderPeople(oStart);
    }
    /**
     * Count today's appointments for a doctor
     */
    @Override
    public int orderPeopleByDid(String oStart, int dId){
        return this.orderMapper.orderPeopleByDid(oStart, dId);
    }
    /**
     * Count appointment gender stats
     */
    public List<String> orderGender(){
        return this.orderMapper.orderGender();
    }
    /**
     * Add diagnosis and doctor notes
     */
    public Boolean updateOrderByAdd(Orders order){
        InputLengthValidator.checkOrders(order);

        if (this.orderMapper.updateOrderByAdd(order) == 0){
            return false;
        }

        return true;
    }
    /**
     * Check whether extra drugs after diagnosis have been paid
     */
    public Boolean findTotalPrice(int oId){
        Orders order = this.orderMapper.selectById(oId);
        if (order.getOTotalPrice() != 0.00){
            order.setOPriceState(0);
            this.orderMapper.updateById(order);
            return true;
        }
        return false;
    }
    /**
     * Request appointment time slots
     */
    @Override
    public HashMap<String, String> findOrderTime(String arId){
        InputLengthValidator.check("schedule id", arId, InputLengthValidator.SHORT_TEXT);
        Jedis jedis = jedisPool.getResource();
        HashMap<String, String> map = (HashMap<String, String>) jedis.hgetAll(arId);

        if(map == null) {
            map = new HashMap<>();
            map.put("tTOe", "40");
            map.put("nTOt", "40");
            map.put("sTOs", "40");
            map.put("eTOn", "40");
            map.put("fTOf", "40");
            map.put("fTOs", "40");
        }

        map.putIfAbsent("tTOe", "40");
        map.putIfAbsent("nTOt", "40");
        map.putIfAbsent("sTOs", "40");
        map.putIfAbsent("eTOn", "40");
        map.putIfAbsent("fTOf", "40");
        map.putIfAbsent("fTOs", "40");

        jedis.hmset(arId, map);
        jedis.expire(arId, 604800);

        return map;
    }
    /**
     * Count department appointments for the last 20 days
     */
    @Override
    public List<String> orderSection(){
        String startTime = TodayUtil.getPastDate(10);
        String endTime = TodayUtil.getPastDate(-10);
        return this.orderMapper.orderSection(startTime, endTime);
    }
}
