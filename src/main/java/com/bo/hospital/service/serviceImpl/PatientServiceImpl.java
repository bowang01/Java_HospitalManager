package com.bo.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bo.hospital.mapper.PatientMapper;
import com.bo.hospital.pojo.Patient;
import com.bo.hospital.service.PatientService;
import com.bo.hospital.utils.Md5Util;
import com.bo.hospital.utils.TodayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("PatientService")
public class PatientServiceImpl implements PatientService {
    protected static final Logger Log = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Resource
    private PatientMapper patientMapper;


    /**
     * Login validation
     */
    @Override
    public Patient login(int pId, String pPassword) {
        Patient patient = this.patientMapper.selectById(pId);
        if (patient == null || 0 == patient.getPState()) {
            return null;
        }
        String password = Md5Util.getMD5(pPassword);
        if ((patient.getPPassword()).equals(password)) {
            return patient;
        }
        return null;
    }

    /**
     * Paginated fuzzy search of all patients
     */
    @Override
    public HashMap<String, Object> findAllPatients(int pageNumber, int size, String query) {
        Page<Patient> page = new Page<>(pageNumber, size);
        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        wrapper.like("p_name", query).eq("p_state", 1);
        IPage<Patient> iPage = this.patientMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       // total count
        hashMap.put("pages", iPage.getPages());       // total pages
        hashMap.put("pageNumber", iPage.getCurrent());// current page
        hashMap.put("patients", iPage.getRecords()); // records
        return hashMap;
    }

    /**
     * Delete patient
     */
    @Override
    public Boolean deletePatient(int pId) {
        Patient patient = new Patient();
        patient.setPId(pId);
        patient.setPState(0);
        this.patientMapper.updateById(patient);
        return true;
    }

    /**
     * Find patient info by patient id
     */
    @Override
    public Patient findPatientById(int pId) {
        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        wrapper.eq("p_id", pId);
        return this.patientMapper.selectOne(wrapper);
    }

    /**
     * Add patient
     */
    @Override
    public Boolean addPatient(Patient patient) {
        // return false if account already exists
        List<Patient> patients = this.patientMapper.selectList(null);
        for (Patient patient1 : patients) {
            if (patient.getPId() == patient1.getPId()) {
                return false;
            }
            if ((patient.getPEmail()).equals(patient1.getPEmail())) {
                return false;
            }
        }
        int yourYear = Integer.parseInt(patient.getPBirthday().substring(0, 4));
        int todayYear = Integer.parseInt(TodayUtil.getTodayYmd().substring(0, 4));
        // encrypt password
        String password = Md5Util.getMD5(patient.getPPassword());
        patient.setPPassword(password);
        patient.setPAge(todayYear - yourYear);
        patient.setPState(1);
        this.patientMapper.insert(patient);
        return true;
    }

    /**
     * Count patient gender stats
     */
    public List<Integer> patientAge() {
        List<Integer> ageList = new ArrayList<>();
        Integer age1 = this.patientMapper.patientAge(0, 9);
        Integer age2 = this.patientMapper.patientAge(10, 19);
        Integer age3 = this.patientMapper.patientAge(20, 29);
        Integer age4 = this.patientMapper.patientAge(30, 39);
        Integer age5 = this.patientMapper.patientAge(40, 49);
        Integer age6 = this.patientMapper.patientAge(50, 59);
        Integer age7 = this.patientMapper.patientAge(60, 69);
        Integer age8 = this.patientMapper.patientAge(70, 79);
        Integer age9 = this.patientMapper.patientAge(80, 89);
        Integer age10 = this.patientMapper.patientAge(90, 99);
        ageList.add(age1);
        ageList.add(age2);
        ageList.add(age3);
        ageList.add(age4);
        ageList.add(age5);
        ageList.add(age6);
        ageList.add(age7);
        ageList.add(age8);
        ageList.add(age9);
        ageList.add(age10);
        return ageList;

    }


}


