package com.bo.hospital.controller;

import com.bo.hospital.pojo.Bed;
import com.bo.hospital.service.BedService;
import com.bo.hospital.utils.ResponseData;
import com.bo.hospital.utils.TodayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bed")
public class BedController {
    @Autowired
    private BedService bedService;

    /**
     * Find all empty beds
     */
    @RequestMapping("findNullBed")
    public ResponseData findNullBed(){
        return ResponseData.success("Empty beds loaded", this.bedService.findNullBed());
    }

    /**
     * Add bed info
     */
    @RequestMapping("updateBed")
    public ResponseData updateBed(Bed bed) {
        if (this.bedService.updateBed(bed)){
            return ResponseData.success("Bed added");
        }
        return ResponseData.fail("Failed to add bed");
    }
    /**
     * Find inpatient record by pId
     */
    @RequestMapping("findBedByPid")
    public ResponseData findBedByPid(@RequestParam(value = "pId") int pId){
        return ResponseData.success("Inpatient record loaded", this.bedService.findBedByPid(pId)) ;
    }
    /**
     * Paginated fuzzy search of all beds
     */
    @RequestMapping("findAllBeds")
    public ResponseData findAllBeds(int pageNumber, int size, String query){
        return ResponseData.success("Beds loaded", this.bedService.findAllBeds(pageNumber, size, query));
    }
    /**
     * Find bed by id
     */
    @RequestMapping("findBed")
    public ResponseData findBed(int bId){
        return ResponseData.success("Bed loaded", this.bedService.findBed(bId));
    }
    /**
      * Add bed info
     */
    @RequestMapping("addBed")
    public ResponseData addBed(Bed bed) {
        Boolean bo = this.bedService.addBed(bed);
        if (bo) {
            return ResponseData.success("Bed added");
        }
        return ResponseData.fail("Failed to add bed: bed number taken");
    }
    /**
     * Delete drug info
     */
    @RequestMapping("deleteBed")
    public ResponseData deleteBed(@RequestParam(value = "bId") int bId) {
        Boolean bo = this.bedService.deleteBed(bId);
        if (bo){
            return ResponseData.success("Bed deleted");
        }
        return ResponseData.fail("Failed to delete bed");
    }
    /**
     * Clear bed info
     */
    @RequestMapping("emptyBed")
    public ResponseData emptyBed(int bId){
        if(this.bedService.emptyBed(bId)){
            return ResponseData.success("Bed cleared");
        }
        return ResponseData.fail("Failed to clear bed");
    }
    /**
     * Count today's appointments
     */
    @RequestMapping("bedPeople")
    public ResponseData bedPeople(){
        String bStart = TodayUtil.getTodayYmd();
        return ResponseData.success("Today's inpatient count loaded", this.bedService.bedPeople(bStart));
    }
}
