package com.kaizensoftware.students.db.util;

import android.provider.BaseColumns;

/**
 * Created by Arturo Cordero
 */
public class DBContract {

    public static final String database_name = "school";

    public DBContract() {

    }

    public static class Student implements BaseColumns {

        public static final String table = "students";
        public static final int version = 5;

        public static final String name = "name";
        public static final String email = "email";
        public static final String password = "password";
        public static final String age = "age";
        public static final String gender = "gender";

        public static final String create_student_table =
                "CREATE TABLE " + DBContract.Student.table + "(" +
                        _ID + " INTEGER PRIMARY KEY," +
                        name + " TEXT" + "," +
                        email + " TEXT" + "," +
                        password + " TEXT" + "," +
                        age + " INTEGER" + ", " +
                        gender + " TEXT" +
                ");";

    }

}
