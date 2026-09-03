package com.bo.hospital.controller;

import com.bo.hospital.pojo.Admin;
import com.bo.hospital.pojo.Doctor;
import com.bo.hospital.service.AdminService;
import com.bo.hospital.service.DoctorService;
import com.bo.hospital.service.OrderService;
import com.bo.hospital.service.PatientService;
import com.bo.hospital.utils.JwtUtil;
import com.bo.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private OrderService orderService;

    /**
     * Login validation
     */
    @PostMapping("/login")
    public ResponseData login(@RequestParam("aId") int aId, @RequestParam("aPassword") String aPassword) {
        Admin admin = this.adminService.login(aId, aPassword);
        if (admin != null) {
            Map<String,String> map = new HashMap<>();
            map.put("aName", admin.getAName());
            map.put("aId", String.valueOf(admin.getAId()));
            String token = JwtUtil.getToken(map);
            map.put("token", token);
            return ResponseData.success("Login successful", map);
        } else {
            return ResponseData.fail("Login failed: incorrect account or password");
        }
    }

    /**
     * Paginated fuzzy search of all medical staff
     */
    @RequestMapping("findAllDoctors")
    public ResponseData findAllDoctors(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size, @RequestParam(value = "query") String query){
        return ResponseData.success("Doctors loaded",  this.doctorService.findAllDoctors(pageNumber, size, query));
    }
    /**
     * Find doctor by id
     */
    @RequestMapping("findDoctor")
    public ResponseData findDoctor(@RequestParam(value = "dId") int dId) {
        return ResponseData.success("Doctor loaded", this.doctorService.findDoctor(dId));
    }
    /**
     * Add doctor
     */
    @RequestMapping("addDoctor")
    public ResponseData addDoctor(Doctor doctor) {
        Boolean bo = this.doctorService.addDoctor(doctor);
        if (bo) {
            return ResponseData.success("Doctor added");
        }
        return ResponseData.fail("Failed to add doctor: account taken");
    }
    /**
     * Delete doctor
     */
    @RequestMapping("deleteDoctor")
    public ResponseData deleteDoctor(@RequestParam(value = "dId") int dId) {
        Boolean bo = this.doctorService.deleteDoctor(dId);
        if (bo){
            return ResponseData.success("Doctor deleted");
        }
        return ResponseData.fail("Failed to delete doctor");
    }
    /**
     * Update doctor
     * bug: dState will be auto-updated to 0
     */
    @RequestMapping("modifyDoctor")
    public ResponseData modifyDoctor(Doctor doctor) {
        this.doctorService.modifyDoctor(doctor);
        return ResponseData.success("Doctor updated");
    }
    /**
     * Paginated fuzzy search of all patients
     */
    @RequestMapping("findAllPatients")
    public ResponseData findAllPatients(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size, @RequestParam(value = "query") String query){
        return ResponseData.success("Patient info loaded",  this.patientService.findAllPatients(pageNumber, size, query));
    }
    /**
     * Delete patient
     */
    @RequestMapping("deletePatient")
    public ResponseData deletePatient(@RequestParam(value = "pId") int pId) {
        Boolean bo = this.patientService.deletePatient(pId);
        if (bo){
            return ResponseData.success("Patient deleted");
        }
        return ResponseData.fail("Failed to delete patient");
    }
    /**
     * Paginated fuzzy search of all appointments
     */
    @RequestMapping("findAllOrders")
    public ResponseData findAllOrders(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size, @RequestParam(value = "query") String query){
        return ResponseData.success("Appointments loaded",  this.orderService.findAllOrders(pageNumber, size, query));
    }
    /**
     * Delete appointment
     */
    @RequestMapping("deleteOrder")
    public ResponseData deleteOrder(@RequestParam(value = "oId") int oId) {
        Boolean bo = this.orderService.deleteOrder(oId);
        if (bo){
            return ResponseData.success("Appointment deleted");
        }
        return ResponseData.fail("Failed to delete appointment");
    }

}
