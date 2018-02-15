package com.kaizensoftware.students.activity.student.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.kaizensoftware.students.R;
import com.kaizensoftware.students.activity.login.LoginActivity;

import butterknife.ButterKnife;

public class ContactsListActivity extends ContactsListContainer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.students_list);

        ButterKnife.bind(this);

        this.initServices();
        this.initElements();
        this.populateLV();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            startActivity(
                    new Intent(getApplicationContext(), LoginActivity.class)
            );

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
