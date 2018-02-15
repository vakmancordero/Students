package com.kaizensoftware.students.activity.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kaizensoftware.students.R;
import com.kaizensoftware.students.activity.security.UserSession;
import com.kaizensoftware.students.activity.student.cr.StudentsActivity;
import com.kaizensoftware.students.activity.student.list.ContactsListActivity;
import com.kaizensoftware.students.model.Student;
import com.kaizensoftware.students.model.User;
import com.kaizensoftware.students.task.login.UserLoginTask;

import butterknife.ButterKnife;

public class LoginActivity extends LoginContainer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.login);

        ButterKnife.bind(this);

        super.initValidator();
        super.initServices();

    }

    public void home(Student student) {

        new UserSession(getApplicationContext()).createSession(
                new User(
                        student.getId(), student.getEmail()
                )
        );

        super.startActivity(
                new Intent(getApplicationContext(), ContactsListActivity.class)
        );

        super.getValidator().clearFields();

    }

    public void userNotFound() {

        Toast.makeText(
                getApplicationContext(), getString(R.string.user_not_found), Toast.LENGTH_LONG
        ).show();

    }

    public void signUp(View view) {

        super.startActivity(
                new Intent(getApplicationContext(), StudentsActivity.class)
        );

    }

    public void attemptLogin(View view) {

        super.hideKeyboard();

        if (getAuthTask() != null) {
            return;
        }

        getEmailView().setError(null);
        getPasswordView().setError(null);

        String email = super.getEmailView().getText().toString();
        String password = super.getPasswordView().getText().toString();

        if (super.checkAuthFields(email, password)) {

            showProgress(false);

            super.setAuthTask(new UserLoginTask(email, password));

            super.getAuthTask().setLoginService(super.getLoginService());

            super.getAuthTask().setLoginActivity(this);
            super.getAuthTask().execute((Void) null);

        }

    }

    @Override
    protected void onRestart() {

        super.getValidator().clearFields();

        this.setAuthTask(null);

        super.onRestart();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            getLoginFormView().setVisibility(show ? View.GONE : View.VISIBLE);
            getLoginFormView().animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    getLoginFormView().setVisibility(show ? View.GONE : View.VISIBLE);
                }

            });

            getProgressLayout().setVisibility(show ? View.VISIBLE : View.GONE);

            getProgressLayout().animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    getProgressLayout().setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

        } else {

            getProgressLayout().setVisibility(show ? View.VISIBLE : View.GONE);
            getLoginFormView().setVisibility(show ? View.GONE : View.VISIBLE);

        }

    }

}

