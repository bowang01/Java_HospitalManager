package com.bo.hospital.service.serviceImpl;

import com.bo.hospital.service.ArrangeService;
import com.bo.hospital.mapper.ArrangeMapper;
import com.bo.hospital.pojo.Arrange;
import com.bo.hospital.utils.InputLengthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;

@Service("ArrangeService")
public class ArrangeServiceImpl implements ArrangeService {
    @Autowired
    private ArrangeMapper arrangeMapper;
    @Autowired
    private JedisPool jedisPool;// Redis connection pool

    /**
     * Find schedules by date
     */
    @Override
    public List<Arrange> findByTime(String arTime, String dSection) {
        InputLengthValidator.check("schedule date", arTime, InputLengthValidator.DATE);
        InputLengthValidator.check("department", dSection, InputLengthValidator.SHORT_TEXT);
        return this.arrangeMapper.findByTime(arTime, dSection);
    }
    /**
     * Add schedule
     */
    public Boolean addArrange(Arrange arrange){
        InputLengthValidator.checkArrange(arrange);
        Arrange arrange1 = this.arrangeMapper.selectById(arrange.getArId());
        Jedis jedis = jedisPool.getResource();
        HashMap<String, String> map = new HashMap<>();
        map.put("eTOn","40");
        map.put("nTOt","40");
        map.put("tTOe","40");
        map.put("fTOf","40");
        map.put("fTOs","40");
        map.put("sTOs","40");
        if (arrange1 == null) {
            // Redis operations start
//            jedis.hset(arrange.getArId(), map);
            // or use hmset to set the entire hash
            jedis.hmset(arrange.getArId(), map);
            jedis.expire(arrange.getArId(), 604800);
            // Redis operations end
            this.arrangeMapper.insert(arrange);
            return true;
        }
        return false;
    }

    /**
     * Delete schedule
     */
    public Boolean deleteArrange(String arId){
        InputLengthValidator.check("schedule id", arId, InputLengthValidator.SHORT_TEXT);
        Arrange arrange = this.arrangeMapper.selectById(arId);
        Jedis jedis = jedisPool.getResource();
        if (arrange != null) {
            jedis.del(arId);
            this.arrangeMapper.deleteById(arId);
            return true;
        }
        return false;
    }

}
