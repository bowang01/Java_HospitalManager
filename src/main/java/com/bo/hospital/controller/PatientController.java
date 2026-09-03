package com.bo.hospital.controller;

import com.bo.hospital.mapper.OrderMapper;
import com.bo.hospital.pojo.Orders;
import com.bo.hospital.pojo.Patient;
import com.bo.hospital.service.DoctorService;
import com.bo.hospital.service.OrderService;
import com.bo.hospital.service.PatientService;
import com.bo.hospital.utils.JwtUtil;
import com.bo.hospital.utils.PdfUtil;
import com.bo.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("patient")
public class PatientController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    @Resource
    private OrderMapper orderMapper;

    /**
     * Login validation
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseData login(@RequestParam(value = "pId") int pId, @RequestParam(value = "pPassword") String pPassword) {
        Patient patient = this.patientService.login(pId, pPassword);
        if (patient != null) {
            Map<String,String> map = new HashMap<>();
            map.put("pName", patient.getPName());
            map.put("pId", String.valueOf(patient.getPId()));
            map.put("pCard", patient.getPCard());
            String token = JwtUtil.getToken(map);
            map.put("token", token);
            //response.setHeader("token", token);
            return ResponseData.success("Login successful", map);
        } else {
            return ResponseData.fail("Login failed: incorrect account or password");
        }
    }
    /**
     * Find all doctors by department
     */
    @RequestMapping("findDoctorBySection")
    public ResponseData findDoctorBySection(@RequestParam(value = "dSection") String dSection){
        return ResponseData.success("Doctors by department loaded", this.doctorService.findDoctorBySection(dSection));
    }
    /**
     * Add appointment
     */
    @RequestMapping("addOrder")
    public ResponseData addOrder(Orders order, String arId){
        System.out.println(arId);
        if (this.orderService.addOrder(order, arId))
        return ResponseData.success("Appointment created");
        return ResponseData.fail("Failed to create appointment");
    }
    /**
     * Find appointments by pId
     */
    @RequestMapping("findOrderByPid")
    public ResponseData findOrderByPid(@RequestParam(value = "pId") int pId){
        return ResponseData.success("Appointments loaded", this.orderService.findOrderByPid(pId)) ;
    }

    /**
     * Add patient
     */
    @RequestMapping("addPatient")
    public ResponseData addPatient(Patient patient) {
        Boolean bo = this.patientService.addPatient(patient);
        if (bo) {
            return ResponseData.success("Registered successfully");
        }
        return ResponseData.fail("Registration failed: account or email taken");
    }

    @GetMapping("/pdf")
    public void downloadPDF(HttpServletRequest request, HttpServletResponse response, int oId) throws Exception {
        Orders order = this.orderMapper.findOrderByOid(oId);
        PdfUtil.ExportPdf(request, response, order);
    }
    /**
     * Count patient gender stats
     */
    @RequestMapping("patientAge")
    public ResponseData patientAge(){
        return  ResponseData.success("Patient age stats loaded", this.patientService.patientAge());

    }
}
