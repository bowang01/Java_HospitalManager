package com.bo.hospital.service.serviceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bo.hospital.mapper.ArrangeMapper;
import com.bo.hospital.mapper.DoctorMapper;
import com.bo.hospital.pojo.Arrange;
import com.bo.hospital.pojo.Doctor;
import com.bo.hospital.service.DoctorService;
import com.bo.hospital.utils.Md5Util;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Service("DoctorService")
public class DoctorServiceImpl implements DoctorService {
    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private ArrangeMapper arrangeMapper;

    /**
     * Login validation
     * */
    @Override
    public Doctor login(int dId, String dPassword){
        Doctor doctor = this.doctorMapper.selectById(dId);
        String password = Md5Util.getMD5(dPassword);
        if (doctor == null) {
            return null;
        } else {
            if ((doctor.getdPassword()).equals(password)) {
                return doctor;
            }
        }
        return null;
    }
    /**
     * Paginated fuzzy search of all medical staff
     */
    @Override
    public HashMap<String, Object> findAllDoctors(int pageNumber, int size, String query) {
        Page<Doctor> page = new Page<>(pageNumber, size);
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        wrapper.like("d_name", query).eq("d_state", 1);
        IPage<Doctor> iPage = this.doctorMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       // total count
        hashMap.put("pages", iPage.getPages());       // total pages
        hashMap.put("pageNumber", iPage.getCurrent());// current page
        hashMap.put("doctors", iPage.getRecords()); // records
        return hashMap;
    }

    /**
     * Find doctor by id
     */
    @Override
    public Doctor findDoctor(int dId) {
        return this.doctorMapper.selectById(dId);
    }

    /**
     * Add doctor
     */
    @Override
    public Boolean addDoctor(Doctor doctor) {
        // return false if account already exists
        List<Doctor> doctors = this.doctorMapper.selectList(null);
        for (Doctor doctor1 : doctors) {
            if (doctor.getdId() == doctor1.getdId()) {
                return false;
            }
        }
        // encrypt password
        String password = Md5Util.getMD5(doctor.getdPassword());
        doctor.setdPassword(password);
        doctor.setdState(1);
        doctor.setdStar(0.00);
        doctor.setdPeople(0);
        this.doctorMapper.insert(doctor);
        return true;
    }

    /**
     * Delete doctor
     */
    @Override
    public Boolean deleteDoctor(int dId) {
        Doctor doctor = new Doctor();
        doctor.setdId(dId);
        doctor.setdState(0);
        this.doctorMapper.updateById(doctor);
        return true;
    }

    /**
     * Update doctor
     */
    @Override
    public Boolean modifyDoctor(Doctor doctor) {
//        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("d_id", doctor.getDId());
//        this.doctorMapper.update(doctor, queryWrapper);
        int i = this.doctorMapper.updateById(doctor);
        System.out.println("affected rows: "+i);
        return true;
    }
    /**
     * Find all doctors by department
     */
    @Override
    public HashMap<String, Object> findDoctorBySection(String dSection){
//        HashMap<String, Object> hashMap = new HashMap<>();
//        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("d_section", dSection).eq("d_state", 1);
//        List<Doctor> doctors = this.doctorMapper.selectList(queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("doctors", this.doctorMapper.findDoctorBySection(dSection));
        return map;

    }
    /**
     * Paginated find all doctors by department
     */
    @Override
    public HashMap<String, Object> findDoctorBySectionPage(int pageNumber, int size, String query, String arrangeDate, String dSection) {
        Page<Doctor> page = new Page<>(pageNumber, size);
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        wrapper.select("d_id", "d_name", "d_gender", "d_post", "d_section").like("d_name", query).eq("d_section", dSection).orderByDesc("d_state");
        IPage<Doctor> iPage = this.doctorMapper.selectPage(page, wrapper);
        List<Doctor> records = iPage.getRecords();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       // total count
        hashMap.put("pages", iPage.getPages());       // total pages
        hashMap.put("pageNumber", iPage.getCurrent());// current page
        hashMap.put("doctors", records); // records

        // Check whether the doctor is already scheduled
        for (Doctor doctor : records) {
            Arrange arrange = arrangeMapper.selectOne(
                    new QueryWrapper<Arrange>().eq("ar_time", arrangeDate).eq("d_id", doctor.getdId())
            );
            if(arrange != null) {
                doctor.setArrangeId(arrange.getArId());
            }

        }
        return hashMap;
    }

    /**
     * User rating
     */
    @Override
    public Boolean updateStar(int dId, Double dStar){

        if(this.doctorMapper.updateStar(dId, dStar))
            return true;
        return false;
    }
    /**
     * Upload Excel and import data
     */
    @Override
    public Boolean uploadExcel(MultipartFile multipartFile) throws Exception {
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
       List<Doctor> doctors = ExcelImportUtil.importExcel(multipartFile.getInputStream(), Doctor.class, params);
        for (Doctor doctor: doctors){
            doctor.setdPassword(Md5Util.getMD5(doctor.getdPassword()));
            this.addDoctor(doctor);
        }
        return true;
    }
    /**
     * Export Excel data
     */
    @Override
    public Boolean downloadExcel(HttpServletResponse response) throws IOException {
        List<Doctor> doctors = this.findAll();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), Doctor.class, doctors);
        ServletOutputStream stream = response.getOutputStream();
        response.setHeader("content-disposition", "attachment;fileName="+ URLEncoder.encode("HospitalDoctors.xlsx", "UTF-8"));
        workbook.write(stream);
        stream.close();
        workbook.close();
        return true;
    }
    /**
     * Find all doctors without pagination
     */
    @Override
    public List<Doctor> findAll(){
        return this.doctorMapper.selectList(null);
    }
}
