package com.bo.hospital.utils;

import com.bo.hospital.exception.InputLimitException;
import com.bo.hospital.pojo.Arrange;
import com.bo.hospital.pojo.Bed;
import com.bo.hospital.pojo.Checks;
import com.bo.hospital.pojo.Doctor;
import com.bo.hospital.pojo.Drug;
import com.bo.hospital.pojo.Orders;
import com.bo.hospital.pojo.Patient;

public final class InputLengthValidator {
    public static final int NAME = 50;
    public static final int PHONE = 20;
    public static final int CARD = 32;
    public static final int EMAIL = 100;
    public static final int PASSWORD = 64;
    public static final int GENDER = 20;
    public static final int DATE = 32;
    public static final int START_TIME = 64;
    public static final int SHORT_TEXT = 50;
    public static final int TEXT = 255;
    public static final int QUERY = 50;
    public static final int PARAM = 512;
    public static final int BODY_BYTES = 32 * 1024;

    private InputLengthValidator() {
    }

    public static void check(String field, String value, int max) {
        if (value == null) {
            return;
        }
        if (value.length() > max) {
            throw new InputLimitException(field + " exceeds maximum length of " + max);
        }
    }

    public static void checkQuery(String query) {
        check("query", query, QUERY);
    }

    public static void checkPatient(Patient patient) {
        if (patient == null) {
            return;
        }
        check("name", patient.getPName(), NAME);
        check("phone", patient.getPPhone(), PHONE);
        check("ID number", patient.getPCard(), CARD);
        check("email", patient.getPEmail(), EMAIL);
        check("password", patient.getPPassword(), PASSWORD);
        check("gender", patient.getPGender(), GENDER);
        check("birthday", patient.getPBirthday(), DATE);
    }

    public static void checkDoctor(Doctor doctor) {
        if (doctor == null) {
            return;
        }
        check("name", doctor.getdName(), NAME);
        check("phone", doctor.getdPhone(), PHONE);
        check("ID number", doctor.getdCard(), CARD);
        check("email", doctor.getdEmail(), EMAIL);
        check("password", doctor.getdPassword(), PASSWORD);
        check("gender", doctor.getdGender(), GENDER);
        check("title", doctor.getdPost(), SHORT_TEXT);
        check("department", doctor.getdSection(), SHORT_TEXT);
        check("bio", doctor.getdIntroduction(), TEXT);
    }

    public static void checkOrders(Orders orders) {
        if (orders == null) {
            return;
        }
        check("diagnosis", orders.getORecord(), TEXT);
        check("start time", orders.getOStart(), START_TIME);
        check("end time", orders.getOEnd(), DATE);
        check("drugs", orders.getODrug(), TEXT);
        check("exams", orders.getOCheck(), TEXT);
        check("advice", orders.getOAdvice(), TEXT);
    }

    public static void checkDrug(Drug drug) {
        if (drug == null) {
            return;
        }
        check("drug name", drug.getDrName(), SHORT_TEXT);
        check("unit", drug.getDrUnit(), SHORT_TEXT);
        check("publisher", drug.getDrPublisher(), SHORT_TEXT);
    }

    public static void checkChecks(Checks checks) {
        if (checks == null) {
            return;
        }
        check("exam name", checks.getChName(), SHORT_TEXT);
    }

    public static void checkBed(Bed bed) {
        if (bed == null) {
            return;
        }
        check("start date", bed.getBStart(), DATE);
        check("reason", bed.getBReason(), TEXT);
    }

    public static void checkArrange(Arrange arrange) {
        if (arrange == null) {
            return;
        }
        check("schedule id", arrange.getArId(), SHORT_TEXT);
        check("schedule date", arrange.getArTime(), DATE);
    }
}
