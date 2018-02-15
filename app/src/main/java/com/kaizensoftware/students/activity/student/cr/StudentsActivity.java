package com.kaizensoftware.students.activity.student.cr;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.kaizensoftware.students.R;
import com.kaizensoftware.students.activity.student.list.ContactsListActivity;
import com.kaizensoftware.students.dao.common.ResponseDescription;
import com.kaizensoftware.students.model.Student;
import com.kaizensoftware.students.util.AlertUtil;
import com.kaizensoftware.students.util.ResponseValue;
import com.kaizensoftware.students.util.ValidatorUtil;

import butterknife.ButterKnife;

/**
 * Created by Arturo Cordero
 */
public class StudentsActivity extends StudentsContainer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.students);

        ButterKnife.bind(this);

        this.initServices();
        super.initElements();
        super.initValidator();

        Student student = (Student) getIntent().getParcelableExtra("student");

        if (student != null) {

            this.setStudent(student);

        }

    }

    public void createStudent(final View view) {

        this.hideKeyboard();

        if (super.getValidator().validateEmptyFields()) {

            if (ValidatorUtil.isEmailValid(getEmailET().getText().toString())) {

                final ResponseValue<Student> studentResponse = this.getStudent();

                final AlertDialog dialog = AlertUtil.informationDialog(
                        "Creando estudiante..", view.getContext()
                );

                dialog.show();

                new Handler().postDelayed(new Runnable() {

                    public void run() {

                        if (!studentResponse.getResponseDescription()
                                .equals(ResponseDescription.PASSWORDS_DONT_MATCH)) {

                            getStudentsService().createStudent(studentResponse.getResponseItem(), new Handler() {

                                @Override
                                public void handleMessage(Message msg) {

                                    boolean saved = (boolean) msg.obj;

                                    if (saved) {

                                        Toast.makeText(getApplicationContext(), "Estudiante creado", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(getApplicationContext(), ContactsListActivity.class));

                                    } else {

                                        new AlertDialog.Builder(view.getContext())
                                                .setTitle("Error")
                                                .setMessage("El estudiante no pudo ser creado")
                                                .show();

                                    }

                                    dialog.dismiss();

                                }

                            });

                        } else {

                            dialog.dismiss();

                            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();

                        }

                    }
                }, 1500);

            } else {

                Toast.makeText(
                        getApplicationContext(), ResponseDescription.INVALID_EMAIL.description(), Toast.LENGTH_LONG
                ).show();

            }

        } else {

            Toast.makeText(getApplicationContext(), ResponseDescription.EMPTY_FIELDS.description(), Toast.LENGTH_LONG).show();

        }

    }

    public void setStudent(Student student) {

        super.getNameET().setText(student.getName());
        super.getEmailET().setText(student.getEmail());

        super.getAgeSR().setSelection(
                super.getAdapter().getPosition(String.valueOf(student.getAge()))
        );

        super.selectRB(super.getGenderGroup(), student.getGender());

    }

    public ResponseValue<Student> getStudent() {

        ResponseValue<Student> responseValue = new ResponseValue<>();

        String name = super.getNameET().getText().toString();
        String email = super.getEmailET().getText().toString();
        int age = Integer.parseInt(super.getAgeSR().getSelectedItem().toString());
        String gender = super.getValueFromRG(super.getGenderGroup());

        Student student = new Student(
                name, email, age, gender
        );

        String password = ValidatorUtil.validatePasswords(super.getPasswordET(), super.getRePasswordET());

        if (password != null) {

            if (ValidatorUtil.isPasswordValid(password)) {

                student.setPassword(password);

                getPasswordET().setError(null);

                responseValue.setResponseItem(student);
                responseValue.setResponseDescription(ResponseDescription.OK);

            } else {

                getPasswordET().setError(getString(R.string.error_invalid_password));

                responseValue.setResponseDescription(ResponseDescription.INVALID_PASSWORD);

            }

        } else {

            responseValue.setResponseDescription(ResponseDescription.PASSWORDS_DONT_MATCH);

        }

        return responseValue;
    }

}
