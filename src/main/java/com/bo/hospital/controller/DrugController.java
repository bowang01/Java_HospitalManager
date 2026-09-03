package com.bo.hospital.controller;

import com.bo.hospital.pojo.Drug;
import com.bo.hospital.service.DrugService;
import com.bo.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("drug")
public class DrugController {
    @Autowired
    private DrugService drugService;
    /**
     * Paginated fuzzy search of all drugs
     */
    @RequestMapping("findAllDrugs")
    public ResponseData findAllDrugs(int pageNumber, int size, String query){
        return ResponseData.success("Drugs loaded", this.drugService.findAllDrugs(pageNumber, size, query));
    }
    /**
     * Find drug by id
     */
    @RequestMapping("findDrug")
    public ResponseData findDrug(int drId){
        return ResponseData.success("Drug loaded", this.drugService.findDrug(drId));
    }
    /**
     * Reduce drug stock by id
     */
    @RequestMapping("reduceDrugNumber")
    public ResponseData reduceDrugNumber(int drId,int usedNumber){
        if (this.drugService.reduceDrugNumber(drId, usedNumber))
            return ResponseData.success("Drug stock updated");
        return ResponseData.fail("Failed to update drug stock");
    }
    /**
     * Add drug
     */
    @RequestMapping("addDrug")
    public ResponseData addDrug(Drug drug) {
        Boolean bo = this.drugService.addDrug(drug);
        if (bo) {
            return ResponseData.success("Drug added");
        }
        return ResponseData.fail("Failed to add drug: ID taken");
    }
    /**
     * Delete drug
     */
    @RequestMapping("deleteDrug")
    public ResponseData deleteDrug(@RequestParam(value = "drId") int drId) {
        Boolean bo = this.drugService.deleteDrug(drId);
        if (bo){
            return ResponseData.success("Drug deleted");
        }
        return ResponseData.fail("Failed to delete drug");
    }
    /**
     * Update drug
     */
    @RequestMapping("modifyDrug")
    public ResponseData modifyDrug(Drug drug) {
        this.drugService.modifyDrug(drug);
        return ResponseData.success("Drug updated");
    }
}
