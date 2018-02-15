package com.kaizensoftware.students.activity.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kaizensoftware.students.R;
import com.kaizensoftware.students.service.LoginService;
import com.kaizensoftware.students.task.login.UserLoginTask;
import com.kaizensoftware.students.util.ValidatorUtil;

import butterknife.BindView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Arturo Cordero
 */
@Getter @Setter
@NoArgsConstructor
public class LoginContainer extends AppCompatActivity {

    @BindView(R.id.email)
    TextView emailView;

    @BindView(R.id.password)
    EditText passwordView;

    @BindView(R.id.progressLayout)
    View loginFormView;

    @BindView(R.id.login_form)
    View progressLayout;

    private ValidatorUtil validator;

    private UserLoginTask authTask;

    private LoginService loginService;

    protected void initServices() {

        this.loginService = new LoginService("sqlite", getApplicationContext());

    }

    protected void initValidator() {

        this.validator = new ValidatorUtil(
                this.emailView,
                this.passwordView
        );

    }

    protected void hideKeyboard() {

        View view = this.getCurrentFocus();

        if (view != null) {

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }

    protected boolean checkAuthFields(String email, String password) {

        boolean accepted = true;

        View view = this.emailView;

        if (!ValidatorUtil.isPasswordValid(password)) {

            this.passwordView.setError(getString(R.string.error_invalid_password));

            view = this.passwordView;
            accepted = false;

        } else if (!ValidatorUtil.isEmailValid(email)) {

            this.emailView.setError(getString(R.string.error_invalid_email));

            view = this.emailView;
            accepted = false;
        }

        view.requestFocus();

        return accepted;
    }

}
