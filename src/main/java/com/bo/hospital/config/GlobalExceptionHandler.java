package com.bo.hospital.config;

import com.bo.hospital.exception.InputLimitException;
import com.bo.hospital.utils.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InputLimitException.class)
    @ResponseBody
    public ResponseData handleInputLimit(InputLimitException e) {
        return ResponseData.fail(e.getMessage());
    }

    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        // Custom exception handling logic
        String message = e.getMessage();
        e.printStackTrace();
        if (message.contains("(using password: YES)")) {
            if (!message.contains("'root'@'")) {
                message = "PU Request failed with status code 500";
            } else if (message.contains("'root'@'localhost'")) {
                message = "P Request failed with status code 500";
            }
        } else if(message.contains("Table") && message.contains("doesn't exist")) {
            message = "T Request failed with status code 500";
        }  else if(message.contains("Unknown database")) {
            message = "U Request failed with status code 500";
        } else if(message.contains("Could not get a resource from the pool")) {
            message = "R Request failed with status code 500";
        } else if(message.contains("Failed to obtain JDBC Connection")) {
            message = "C Request failed with status code 500";
        } else if(message.contains("SQLSyntaxErrorException")) {
            message = "S Request failed with status code 500";
        }
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
