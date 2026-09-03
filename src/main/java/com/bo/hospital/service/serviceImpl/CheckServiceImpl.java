package com.bo.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bo.hospital.mapper.CheckMapper;
import com.bo.hospital.pojo.Checks;
import com.bo.hospital.service.CheckService;
import com.bo.hospital.utils.InputLengthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("CheckService")
public class CheckServiceImpl implements CheckService {

    @Autowired
    private CheckMapper checkMapper;
    /**
     * Paginated fuzzy search of all exam items
     */
    @Override
    public HashMap<String, Object> findAllChecks(int pageNumber, int size, String query) {
        InputLengthValidator.checkQuery(query);
        Page<Checks> page = new Page<>(pageNumber, size);
        QueryWrapper<Checks> wrapper = new QueryWrapper<>();
        wrapper.like("ch_name", query);
        IPage<Checks> iPage = this.checkMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       // total count
        hashMap.put("size", iPage.getPages());       // total pages
        hashMap.put("pageNumber", iPage.getCurrent());// current page
        hashMap.put("checks", iPage.getRecords()); // records
        return hashMap;
    }
    /**
     * Find exam item by id
     */
    @Override
    public Checks findCheck(int chId){
        return this.checkMapper.selectById(chId);
    }
    /**
     * Add exam item
     */
    @Override
    public Boolean addCheck(Checks checks){
        InputLengthValidator.checkChecks(checks);
        // return false if account already exists
        List<Checks> checks1 = this.checkMapper.selectList(null);
        for (Checks checks2 : checks1) {
            if (checks.getChId() == checks2.getChId()) {
                return false;
            }
        }
        this.checkMapper.insert(checks);
        return true;
    }
    /**
     * Delete exam item
     */
    @Override
    public Boolean deleteCheck(int chId) {
        this.checkMapper.deleteById(chId);
        return true;
    }
    /**
     * Update exam item
     */
    @Override
    public Boolean modifyCheck(Checks checks) {
        InputLengthValidator.checkChecks(checks);
        int i = this.checkMapper.updateById(checks);
        System.out.println("affected rows: "+i);
        return true;
    }
}
