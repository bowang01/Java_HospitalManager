package com.bo.hospital.service.serviceImpl;

import com.bo.hospital.mapper.AdminMapper;
import com.bo.hospital.pojo.Admin;
import com.bo.hospital.service.AdminService;
import com.bo.hospital.utils.InputLengthValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("AdminService")
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * Login validation
     * */
    @Override
    public Admin login(int aId, String aPassword){
        InputLengthValidator.check("password", aPassword, InputLengthValidator.PASSWORD);
        Admin admin = this.adminMapper.selectById(aId);
        if (admin == null) {
            return null;
        }
        if (!(admin.getAPassword()).equals(aPassword)) {
            return null;
        }
        return admin;
    }



}
