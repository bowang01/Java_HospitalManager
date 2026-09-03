package com.bo.hospital.controller;

import com.bo.hospital.pojo.Doctor;
import com.bo.hospital.service.DoctorService;
import com.bo.hospital.service.OrderService;
import com.bo.hospital.service.PatientService;
import com.bo.hospital.utils.JwtUtil;
import com.bo.hospital.utils.ResponseData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PatientService patientService;
    /**
     * Login validation
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseData login(@RequestParam(value = "dId") int dId, @RequestParam(value = "dPassword") String dPassword) {
        Doctor doctor = this.doctorService.login(dId, dPassword);
        if (doctor != null) {
            Map<String,String> map = new HashMap<>();
            map.put("dName", doctor.getdName());
            map.put("dId", String.valueOf(doctor.getdId()));
            String token = JwtUtil.getToken(map);
            map.put("token", token);
            //response.setHeader("token", token);
            return ResponseData.success("Login successful", map);
        } else {
            return ResponseData.fail("Login failed: incorrect account or password");
        }
    }
    /**
     * Find today's appointment list
     */
    @RequestMapping("findOrderByNull")
    public ResponseData findOrderByNull(@Param(value = "dId") int dId, @RequestParam(value = "oStart") String oStart){
        System.out.println("Account and time: "+dId+oStart);
        return ResponseData.success("Today's appointments loaded", this.orderService.findOrderByNull(dId,oStart));

    }
    /**
     * Find patient info by patient id
     */
    @RequestMapping("findPatientById")
    public ResponseData findPatientById(int pId){
        return ResponseData.success("Patient info loaded", this.patientService.findPatientById(pId));
    }
    /**
     * Paginated find all doctors by department
     */
    @RequestMapping("findDoctorBySectionPage")
    public ResponseData findDoctorBySectionPage(int pageNumber, int size, String query, String arrangeDate, String dSection){
        return ResponseData.success("Doctors by department loaded", this.doctorService.findDoctorBySectionPage(pageNumber, size, query, arrangeDate, dSection));
    }
    /**
     * User rating
     */
    @RequestMapping("updateStar")
    public ResponseData updateStar(int dId, Double dStar){
        if(this.doctorService.updateStar(dId, dStar)){
            return ResponseData.success("Rating submitted");
        }
        return ResponseData.fail("Rating failed");
    }
    /**
     * Upload Excel and import data
     */
    @RequestMapping(value = "uploadExcel", method = RequestMethod.POST)
    public ResponseData uploadExcel(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        if (this.doctorService.uploadExcel(multipartFile)){
            return ResponseData.success("Excel import succeeded");
        }
        return ResponseData.fail("Excel import failed");

    }
    /**
     * Export Excel data
     */
    @RequestMapping("downloadExcel")
    public ResponseData downloadExcel(HttpServletResponse response) throws IOException {
        if (this.doctorService.downloadExcel(response)){
            return ResponseData.success("Excel export succeeded");
        }
        return ResponseData.fail("Excel export failed");
    }
}
