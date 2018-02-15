package com.kaizensoftware.students.activity.student.cr;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.kaizensoftware.students.R;
import com.kaizensoftware.students.dao.common.ResponseDescription;
import com.kaizensoftware.students.service.StudentsService;
import com.kaizensoftware.students.util.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Arturo Cordero
 */
@Getter @Setter
@NoArgsConstructor
public class StudentsContainer extends AppCompatActivity {

    @BindView(R.id.nameET)
    EditText nameET;

    @BindView(R.id.emailET)
    EditText emailET;

    @BindView(R.id.passwordET)
    EditText passwordET;

    @BindView(R.id.rePasswordET)
    EditText rePasswordET;

    @BindView(R.id.ageSR)
    Spinner ageSR;

    @BindView(R.id.genderGroup)
    RadioGroup genderGroup;

    private ArrayAdapter<String> adapter;

    private ValidatorUtil validator;

    private StudentsService studentsService;

    protected void initServices() {
        this.studentsService = new StudentsService(
                "sqlite", getApplicationContext());
    }

    protected void initElements() {
        this.populateAgeList(this.ageSR, 6, 99, 21);
        this.initEmailListener();
    }

    private void initEmailListener() {

        final String email = this.emailET.getEditableText().toString().trim();

        this.emailET.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {

                if (!ValidatorUtil.isEmailValid(emailET.getEditableText().toString()) && editable.length() > 0) {

                    String errorDescription = ResponseDescription.INVALID_EMAIL.description();

                    emailET.setError(errorDescription);

                } else {

                    emailET.setError(null);

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });

    }

    protected void initValidator() {

        this.validator = new ValidatorUtil(
                this.nameET, this.emailET, this.passwordET,
                this.rePasswordET, this.ageSR, this.genderGroup
        );

    }

    private void populateAgeList(Spinner spinner, int begin, int end, int selection) {

        List<String> list =  new ArrayList<String>();

        for (int i = begin; i < end; i ++) list.add(String.valueOf(i));

        this.adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, list);

        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(this.adapter);
        spinner.setSelection(selection);

    }

    protected String getValueFromRG(RadioGroup radioGroup) {

        View radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

        return ((RadioButton) radioGroup.getChildAt(radioGroup.indexOfChild(radioButton))).getText().toString();
    }

    protected void selectRB(RadioGroup radioGroup, String text) {

        int count = radioGroup.getChildCount();

        for (int i = 0; i < count; i++) {

            View view = radioGroup.getChildAt(i);

            if (view instanceof RadioButton) {

                RadioButton radioButton = ((RadioButton) view);

                if (radioButton.getText().toString().equalsIgnoreCase(text)) {

                    radioButton.setChecked(true);

                }

            }
        }

    }

    protected void hideKeyboard() {

        View view = this.getCurrentFocus();

        if (view != null) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }

}
