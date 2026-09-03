package com.bo.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bo.hospital.mapper.BedMapper;
import com.bo.hospital.pojo.Bed;
import com.bo.hospital.service.BedService;
import com.bo.hospital.utils.TodayUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("BedService")
public class BedServiceImpl implements BedService {

    @Resource
    private BedMapper bedMapper;

    /**
     * Find all empty beds
     */
    @Override
    public List<Bed> findNullBed(){
        QueryWrapper<Bed> wrapper = new QueryWrapper<>();
        wrapper.select("b_id").eq("b_state", 0);
        return this.bedMapper.selectList(wrapper);
    }

    /**
     * Add bed info
     */
    @Override
    /**
     * Update bed info
     */
    public Boolean updateBed(Bed bed){
        Bed bed1 = this.bedMapper.selectById(bed.getBId());
        if (bed1.getBState() == 1)
            return false;
        bed.setBStart(TodayUtil.getTodayYmd());
        bed.setBState(1);
        bed.setVersion(bed1.getVersion());

        this.bedMapper.updateById(bed);
        return true;
    }
    /**
     * Find appointments by pId
     */
    public List<Bed> findBedByPid(int pId){
        QueryWrapper<Bed> wrapper = new QueryWrapper<>();
        wrapper.eq("p_id", pId);
        return this.bedMapper.selectList(wrapper);
    }
    /**
     * Paginated fuzzy search of all exam items
     */
    @Override
    public HashMap<String, Object> findAllBeds(int pageNumber, int size, String query) {
        Page<Bed> page = new Page<>(pageNumber, size);
        QueryWrapper<Bed> wrapper = new QueryWrapper<>();
        wrapper.like("p_id", query);
        IPage<Bed> iPage = this.bedMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       // total count
        hashMap.put("size", iPage.getPages());       // total pages
        hashMap.put("pageNumber", iPage.getCurrent());// current page
        hashMap.put("beds", iPage.getRecords()); // records
        return hashMap;
    }
    /**
     * Find exam item by id
     */
    @Override
    public Bed findBed(int bId){
        return this.bedMapper.selectById(bId);
    }
    /**
     * Add bed info
     */
    @Override
    public Boolean addBed(Bed bed){
        // return false if account already exists
        List<Bed> beds = this.bedMapper.selectList(null);
        for (Bed bed1 : beds) {
            if (bed1.getBId() == bed.getBId()) {
                return false;
            }
        }
        bed.setBState(0);
        this.bedMapper.insert(bed);
        return true;
    }
    /**
     * Delete bed
     */
    @Override
    public Boolean deleteBed(int bId) {
        this.bedMapper.deleteById(bId);
        return true;
    }
    /**
     * Clear bed info
     */
    public Boolean emptyBed(int bId){
        UpdateWrapper<Bed> wrapper = new UpdateWrapper<>();
        wrapper.set("p_id", -1).set("d_id", -1).set("b_reason", null).set("b_start", null).set("b_state", 0).eq("b_id", bId);
        this.bedMapper.update(null, wrapper);
        return true;

    }
    /**
     * Count today's appointments
     */
    @Override
    public int bedPeople(String bStart){
        return this.bedMapper.bedPeople(bStart);
    }

}
