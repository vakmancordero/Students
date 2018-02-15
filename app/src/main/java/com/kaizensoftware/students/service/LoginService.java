package com.kaizensoftware.students.service;

import android.content.Context;

import com.kaizensoftware.students.dao.LoginDAO;
import com.kaizensoftware.students.model.Student;
import com.kaizensoftware.students.util.ResponseValue;

import okhttp3.Response;

/**
 * Created by Arturo Cordero
 */
public class LoginService {

    private LoginDAO loginDAO;

    private String type;

    public LoginService(String type, Context context) {

        this.type = type;

        if (this.type.equalsIgnoreCase("sqlite")) {

            this.loginDAO = new LoginDAO(context);

        } else if (this.type.equalsIgnoreCase("http")) {

        }

    }

    public ResponseValue<Student> login(String username, String password) {
       return this.loginDAO.login(username, password);
    }
}
