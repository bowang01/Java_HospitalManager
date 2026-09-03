package com.bo.hospital.service;

import com.bo.hospital.pojo.Doctor;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface DoctorService {
    /**
     * Login validation
     * */
    Doctor login(int dId, String dPassword);
    /**
     * Paginated fuzzy search of all doctors
     */
    HashMap<String, Object> findAllDoctors(int pageNumber, int size, String query);
    /**
     * Find doctor by id
     */
    Doctor findDoctor(int dId);
    /**
     * Add doctor
     */
    Boolean addDoctor(Doctor doctor);
    /**
     * Delete doctor
     */
    Boolean deleteDoctor(int dId);
    /**
     * Update doctor
     */
    Boolean modifyDoctor(Doctor doctor);
    /**
     * Find all doctors by department
     */
     HashMap<String, Object> findDoctorBySection(String dSection);
    /**
     * Paginated find all doctors by department
     */
    HashMap<String, Object> findDoctorBySectionPage(int pageNumber, int size, String query, String arrangeDate, String dSection);
    /**
     * User rating
     */
    Boolean updateStar(int dId, Double dStar);
    /**
     * Upload Excel and import data
     */
    Boolean uploadExcel(MultipartFile multipartFile) throws Exception;
    /**
     * Export Excel data
     */
    Boolean downloadExcel(HttpServletResponse response) throws IOException;
    /**
     * Find all doctors without pagination
     */
    List<Doctor> findAll();


}
