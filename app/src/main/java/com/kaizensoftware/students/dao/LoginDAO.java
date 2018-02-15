package com.kaizensoftware.students.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kaizensoftware.students.dao.common.BaseDAO;
import com.kaizensoftware.students.dao.common.ResponseDescription;
import com.kaizensoftware.students.db.util.DBContract;
import com.kaizensoftware.students.model.Student;
import com.kaizensoftware.students.util.ResponseValue;

/**
 * Created by Arturo Cordero
 */
public class LoginDAO extends BaseDAO {

    public LoginDAO(Context context) {
        super(context, null, DBContract.Student.version, DBContract.Student.table, DBContract.Student.create_student_table);
    }

    public ResponseValue<Student> login(String username, String password) {

        SQLiteDatabase readeableDataBase = null;

        ResponseValue<Student> responseValue = new ResponseValue<Student>();

        try {

            readeableDataBase = this.getReadableDatabase();

            String selection = DBContract.Student.email + " = ?" + " AND " + DBContract.Student.password + " = ?";
            String[] selectionArgs = {username, password};

            Cursor cursor = readeableDataBase.query(
                    DBContract.Student.table,
                    new String[]{
                            DBContract.Student._ID,
                            DBContract.Student.name,
                            DBContract.Student.email,
                            DBContract.Student.password,
                            DBContract.Student.age,
                            DBContract.Student.gender
                    },
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            int count = cursor.getCount();

            if (cursor != null && cursor.moveToFirst()) {

                int index;

                index = cursor.getColumnIndexOrThrow(DBContract.Student._ID);
                int id = cursor.getInt(index);

                index = cursor.getColumnIndexOrThrow(DBContract.Student.name);
                String name = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow(DBContract.Student.email);
                String email = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow(DBContract.Student.age);
                int age = cursor.getInt(index);

                index = cursor.getColumnIndexOrThrow(DBContract.Student.gender);
                String gender = cursor.getString(index);

                responseValue.setResponseItem(new Student(id, name, email, age, gender));
                responseValue.setResponseDescription(ResponseDescription.OK);

                cursor.close();

            } else {

                responseValue.setResponseDescription(ResponseDescription.NO_DATA_FOUND);

            }

        } catch (RuntimeException ex) {

            Log.e("SQLite", "getStudents: ", ex);

            responseValue.setResponseDescription(ResponseDescription.SQL_ERROR);

        } finally {

            readeableDataBase.close();

        }

        return responseValue;
    }

}
