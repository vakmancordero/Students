package com.kaizensoftware.students.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kaizensoftware.students.dao.StudentsDAO;
import com.kaizensoftware.students.model.Student;
import com.kaizensoftware.students.util.ResponseValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Arturo Cordero
 */
public class StudentsService {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /* Http */
    private OkHttpClient client;
    private String baseUrl = "http://10.0.2.2:8080/students/";

    /* SQLite */
    private StudentsDAO studentsDAO;

    private String type;

    public StudentsService(String type, Context context) {

        this.type = type;

        if (this.type.equalsIgnoreCase("sqlite")) {

            this.studentsDAO = new StudentsDAO(context);

        } else if (this.type.equalsIgnoreCase("http")) {

            this.client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();

        }

    }

    public long createStudent(Student student, final Handler handler) {

        final Message message = handler.obtainMessage();

        if (this.type.equalsIgnoreCase("sqlite")) {

            ResponseValue<Long> response = this.studentsDAO.createStudent(student);

            message.obj = response.getResponseItem() > 0 ? true : false;

            handler.sendMessage(message);

        } else if (this.type.equalsIgnoreCase("http")) {

            JSONObject jsonObject = new JSONObject();

            try {

                jsonObject.put("name", student.getName());
                jsonObject.put("email", student.getEmail());
                jsonObject.put("age", student.getAge());
                jsonObject.put("gender", student.getGender());

            } catch(JSONException ex) {
                Log.e("ERROR", "createStudent: ", ex);
            }

            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(this.baseUrl)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException ex) {

                    message.obj = false;
                    handler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    message.obj = true;
                    handler.sendMessage(message);
                }

            });

        }

        return -1;
    }

    public void getStudents(final Handler handler) {

        final Message message = handler.obtainMessage();

        final List<Student> students = new ArrayList<>();

        if (this.type.equalsIgnoreCase("sqlite")) {

            students.addAll(
                    this.studentsDAO.getStudents().getResponseList());

            message.obj = students;
            handler.sendMessage(message);

        } else if (this.type.equalsIgnoreCase("http")) {

            Request request = new Request.Builder()
                    .url(this.baseUrl)
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException ex) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final String responseBody = response.body().string();

                    try {

                        JSONArray studentsArray = new JSONArray(responseBody);

                        for (int i = 0; i < studentsArray.length(); i++) {

                            JSONObject studentObject = (JSONObject) studentsArray.get(i);

                            Student student = new Student(
                                    studentObject.getInt("id"),
                                    studentObject.getString("name"),
                                    studentObject.getString("email"),
                                    studentObject.getInt("age"),
                                    studentObject.getString("gender")
                            );

                            students.add(student);

                        }

                        message.obj = students;
                        handler.sendMessage(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            });

        }

    }

}
