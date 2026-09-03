package com.bo.hospital.controller;

import com.bo.hospital.pojo.Arrange;
import com.bo.hospital.service.ArrangeService;
import com.bo.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arrange")
public class ArrangeController {
    @Autowired
    private ArrangeService arrangeService;
    /**
     * Find schedules by date
     */
    @RequestMapping("findByTime")
    public ResponseData findByTime(@RequestParam(value = "arTime") String arTime, @RequestParam(value = "dSection") String dSection) {
        return ResponseData.success("Schedules loaded", this.arrangeService.findByTime(arTime, dSection));
    }
    /**
     * Add schedule
     */
    @RequestMapping("addArrange")
    public ResponseData addArrange(Arrange arrange){
        if (this.arrangeService.addArrange(arrange))
            return ResponseData.success("Schedule added");
        return ResponseData.fail("Doctor already scheduled on this day");
    }

    /**
     * Delete schedule
     */
    @RequestMapping("deleteArrange")
    public ResponseData deleteArrange(String arId){
        if (this.arrangeService.deleteArrange(arId))
            return ResponseData.success("Schedule deleted");
        return ResponseData.fail("Schedule not found");
    }

}
