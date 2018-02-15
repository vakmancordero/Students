package com.kaizensoftware.students.util;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.kaizensoftware.students.dao.common.ResponseDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Arturo Cordero
 */
public class ValidatorUtil {

    public static final String EMAIL_PATTERN = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z]).{8,20})";

    private View[] views;

    public ValidatorUtil(View... views) {
        this.views = views;
    }

    public static String validatePasswords(EditText passwordET, EditText rePasswordET) {

        Editable password = passwordET.getText();

        if (password.toString().equals(rePasswordET.getText().toString())) {

            return password.toString();

        }

        return null;
    }

    public boolean validateEmptyFields() {

        for (View child : views) {

            if (child instanceof EditText) {

                EditText editText = (EditText) child;

                if (TextUtils.isEmpty(editText.getText())) {

                    editText.setError(ResponseDescription.REQUIRED_DATA.description());

                    return false;
                }

            } else if (child instanceof Spinner) {

                Spinner spinner = (Spinner) child;

                if (spinner.getSelectedItem() == null) {
                    return false;
                }

            } else if (child instanceof RadioGroup) {

                RadioGroup radioGroup = (RadioGroup) child;

                int checkedId = radioGroup.getCheckedRadioButtonId();

                if (checkedId < 0) {
                    return false;
                }

            }

        }


        return true;
    }

    public void clearFields() {

        for (View child : views) {

            if (child instanceof EditText) {

                ((EditText) child).getEditableText().clear();

            } else if (child instanceof RadioGroup) {

                RadioGroup radioGroup = (RadioGroup) child;

                int checkedId = radioGroup.getCheckedRadioButtonId();

                if (checkedId > 0) {

                    ((RadioButton) radioGroup.getChildAt(
                            radioGroup.indexOfChild(
                                    radioGroup.findViewById(
                                            radioGroup.getCheckedRadioButtonId()
                                    )
                            )
                    )).setSelected(false);

                }

            }

        }

    }

    public static boolean isEmailValid(String email) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

        return pattern.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN, Pattern.CASE_INSENSITIVE);

        return pattern.matcher(password).matches();
    }

    private List<View> getChildrenBFS(View view) {

        List<View> visited = new ArrayList<View>();
        List<View> unvisited = new ArrayList<View>();

        unvisited.add(view);

        while (!unvisited.isEmpty()) {

            View child = unvisited.remove(0);

            visited.add(child);

            if (!(child instanceof ViewGroup)) continue;

            ViewGroup group = (ViewGroup) child;

            int childCount = group.getChildCount();

            for (int i=0; i < childCount; i++) {

                unvisited.add(group.getChildAt(i));

            }

        }

        return visited;
    }

}
