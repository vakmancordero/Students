package com.kaizensoftware.students.task.login;

import android.os.AsyncTask;

import com.kaizensoftware.students.activity.login.LoginActivity;
import com.kaizensoftware.students.R;
import com.kaizensoftware.students.activity.security.UserSession;
import com.kaizensoftware.students.model.Student;
import com.kaizensoftware.students.service.LoginService;

/**
 * Created by Arturo Cordero
 */
public class UserLoginTask extends AsyncTask<Void, Void, Student> {

    private LoginActivity loginActivity;
    private LoginService loginService;

    private final String mEmail;
    private final String mPassword;

    public UserLoginTask(String email, String password) {
        this.mEmail = email;
        this.mPassword = password;
    }

    public void setLoginActivity(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected Student doInBackground(Void... params) {

        try {

            Thread.sleep(1100);

        } catch (InterruptedException e) {

            return null;

        }

        return this.loginService.login(this.mEmail, this.mPassword).getResponseItem();
    }

    @Override
    protected void onPostExecute(final Student student) {

        if (student != null) {

            loginActivity.home(student);

        } else {

            loginActivity.userNotFound();

            loginActivity.getEmailView().requestFocus();

        }

        loginActivity.showProgress(true);

        loginActivity.setAuthTask(null);

    }

    @Override
    protected void onCancelled() {

        loginActivity.setAuthTask(null);
        loginActivity.showProgress(true);

    }

}