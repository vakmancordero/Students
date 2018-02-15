package com.kaizensoftware.students.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kaizensoftware.students.dao.common.BaseDAO;
import com.kaizensoftware.students.dao.common.ResponseDescription;
import com.kaizensoftware.students.db.util.DBContract;
import com.kaizensoftware.students.model.Student;
import com.kaizensoftware.students.util.ResponseValue;

import java.util.List;

/**
 * Created by Arturo Cordero
 */

public class StudentsDAO extends BaseDAO {

    public StudentsDAO(Context context) {
        super(context, null, DBContract.Student.version, DBContract.Student.table, DBContract.Student.create_student_table);
    }

    public ResponseValue<Long> createStudent(Student student) {

        SQLiteDatabase writableDatabase = null;

        ResponseValue<Long> responseValue = new ResponseValue<>();

        try {

            writableDatabase = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(DBContract.Student.name, student.getName());
            values.put(DBContract.Student.email, student.getEmail());
            values.put(DBContract.Student.password, student.getPassword());
            values.put(DBContract.Student.age, student.getAge());
            values.put(DBContract.Student.gender, student.getGender());

            responseValue.setResponseItem(
                    writableDatabase.insert(
                            DBContract.Student.table, null, values
                    )
            );

            if (responseValue.getResponseItem() < 0) {

                responseValue.setResponseDescription(ResponseDescription.INSERT_ERROR);

            }

        } catch(RuntimeException ex) {

            Log.e("SQLite", "createStudent: ", ex);

            responseValue.setResponseDescription(ResponseDescription.SQL_ERROR);

        } finally {

            writableDatabase.close();

        }

        return responseValue;

    }

    public ResponseValue<Student> getStudents() {

        SQLiteDatabase writableDatabase = null;

        ResponseValue<Student> responseValue = new ResponseValue<>();

        try {

            writableDatabase = this.getWritableDatabase();

            String sortOrder = DBContract.Student._ID + " DESC";

            Cursor cursor = writableDatabase.query(
                    DBContract.Student.table,
                    new String[]{
                            DBContract.Student.name,
                            DBContract.Student.email,
                            DBContract.Student.age,
                            DBContract.Student.gender
                    },
                    null,
                    null,
                    null,
                    null,
                    sortOrder
            );

            if (cursor != null && cursor.moveToFirst()) {

                do {

                    int index;

                    index = cursor.getColumnIndexOrThrow(DBContract.Student.name);
                    String name = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow(DBContract.Student.email);
                    String email = cursor.getString(index);

                    index = cursor.getColumnIndexOrThrow(DBContract.Student.age);
                    int age = cursor.getInt(index);

                    index = cursor.getColumnIndexOrThrow(DBContract.Student.gender);
                    String gender = cursor.getString(index);

                    responseValue.getResponseList().add(new Student(name, email, age, gender));

                } while (cursor.moveToNext());

                cursor.close();

            } else {

                responseValue.setResponseDescription(ResponseDescription.NO_DATA_FOUND);

            }

        } catch (RuntimeException ex) {

            Log.e("SQLite", "getStudents: ", ex);

            responseValue.setResponseDescription(ResponseDescription.SQL_ERROR);

        } finally {

            writableDatabase.close();

        }

        return responseValue;
    }

}
