package com.bo.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bo.hospital.mapper.DrugMapper;
import com.bo.hospital.pojo.Drug;
import com.bo.hospital.service.DrugService;
import com.bo.hospital.utils.InputLengthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("DrugService")
public class DrugServiceImpl implements DrugService {
    @Autowired
    private DrugMapper drugMapper;
    /**
     * Paginated fuzzy search of all drugs
     */
    @Override
    public HashMap<String, Object> findAllDrugs(int pageNumber, int size, String query){
        InputLengthValidator.checkQuery(query);
        Page<Drug> page = new Page<>(pageNumber, size);
        QueryWrapper<Drug> wrapper = new QueryWrapper<>();
        wrapper.like("dr_name", query);
        IPage<Drug> iPage = this.drugMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       // total count
        hashMap.put("size", iPage.getPages());       // total pages
        hashMap.put("pageNumber", iPage.getCurrent());// current page
        hashMap.put("drugs", iPage.getRecords()); // records
        return hashMap;
    }

    /**
     * Find drug by id
     */
    @Override
    public Drug findDrug(int drId){
        return this.drugMapper.selectById(drId);
    }
    /**
     * Reduce drug stock by id
     */
    @Override
    public Boolean reduceDrugNumber(int drId,int usedNumber){
        Drug drug = this.drugMapper.selectById(drId);
        if(drug.getDrNumber() < usedNumber)
            return false;
        drug.setDrNumber(drug.getDrNumber()-usedNumber);
        this.drugMapper.updateById(drug);
        return true;
    }
    /**
     * Add drug
     */
    public Boolean addDrug(Drug drug){
        InputLengthValidator.checkDrug(drug);
        // return false if account already exists
        List<Drug> drugs = this.drugMapper.selectList(null);
        for (Drug drug1 : drugs) {
            if (drug.getDrId() == drug1.getDrId()) {
                return false;
            }
        }
        this.drugMapper.insert(drug);
        return true;
    }
    /**
     * Delete drug
     */
    @Override
    public Boolean deleteDrug(int drId) {
        this.drugMapper.deleteById(drId);
        return true;
    }
    /**
     * Update drug
     */
    @Override
    public Boolean modifyDrug(Drug drug) {
        InputLengthValidator.checkDrug(drug);
        int i = this.drugMapper.updateById(drug);
        System.out.println("affected rows: "+i);
        return true;
    }
}
