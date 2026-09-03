package com.bo.hospital.controller;

import com.bo.hospital.pojo.Checks;
import com.bo.hospital.service.CheckService;
import com.bo.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("check")
public class CheckController {
    @Autowired
    private CheckService checkService;
    /**
     * Paginated fuzzy search of all exam items
     */
    @RequestMapping("findAllChecks")
    public ResponseData findAllChecks(int pageNumber, int size, String query){
        return ResponseData.success("Exam items loaded",checkService.findAllChecks(pageNumber, size, query));
    }
    /**
     * Find exam item by id
     */
    @RequestMapping("findCheck")
    public ResponseData findCheck(int chId){
        return ResponseData.success("Exam item loaded", checkService.findCheck(chId));
    }
    /**
     * Add exam item
     */
    @RequestMapping("addCheck")
    public ResponseData addCheck(Checks checks) {
        Boolean bo = checkService.addCheck(checks);
        if (bo) {
            return ResponseData.success("Exam item added");
        }
        return ResponseData.fail("Failed to add exam item: ID taken");
    }
    /**
     * Delete drug info
     */
    @RequestMapping("deleteCheck")
    public ResponseData deleteCheck(@RequestParam(value = "chId") int chId) {
        Boolean bo = checkService.deleteCheck(chId);
        if (bo){
            return ResponseData.success("Exam item deleted");
        }
        return ResponseData.fail("Failed to delete exam item");
    }
    /**
     * Update exam item
     */
    @RequestMapping("modifyCheck")
    public ResponseData modifyCheck(Checks checks) {
        if(checkService.modifyCheck(checks)){
            return ResponseData.success("Exam item updated");
        }
        return ResponseData.fail("Failed to update exam item");
    }

}
