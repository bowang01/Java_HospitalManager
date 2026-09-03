package com.bo.hospital.service;

import com.bo.hospital.pojo.Admin;

public interface AdminService {
    /**
     * Login validation
     * */
    Admin login(int aId, String aPassword);
}
