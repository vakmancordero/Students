package com.kaizensoftware.students.activity.student.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaizensoftware.students.R;
import com.kaizensoftware.students.activity.student.cr.StudentsActivity;
import com.kaizensoftware.students.adapter.CustomAdapter;
import com.kaizensoftware.students.adapter.model.ViewHolder;
import com.kaizensoftware.students.model.Student;
import com.kaizensoftware.students.service.StudentsService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Arturo Cordero
 */
@Getter
@Setter
@NoArgsConstructor
public class ContactsListContainer extends AppCompatActivity {

    @BindView(R.id.studentsLV)
    ListView studentsLV;

    private List<Student> studentsList = new ArrayList<>();

    private StudentsService studentsService;

    protected void initServices() {
        this.studentsService = new StudentsService(
                "sqlite", getApplicationContext());
    }

    protected void initElements() {

        CustomAdapter adapter = new CustomAdapter(studentsList, getApplicationContext());

        studentsLV.setAdapter(adapter);

        studentsLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Student student = studentsList.get(i);

                showDialog(student);

                Snackbar.make(view, student.getName() + "\nEmail: " + student.getEmail(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                return false;
            }

        });

    }

    private void showDialog(final Student student) {

        final String[] options = getResources().getStringArray(R.array.student_item_options);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setTitle("Selecciona una opción:");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String action = options[which];

                if (action.equalsIgnoreCase("eliminar")) {

                    //studentsService.deleteStudent();

                } else if (action.equalsIgnoreCase("editar")) {

                    Intent intent = new Intent(getApplicationContext(), StudentsActivity.class);

                    intent.putExtra("student", student);

                    startActivity(intent);

                }

            }

        });

        builder.setNegativeButton("Cancelar", null);

        builder.show();

    }

    protected void populateLV() {

        this.studentsService.getStudents(new Handler() {

            @Override
            public void handleMessage(Message msg){

                studentsList.addAll((List<Student>) msg.obj);

            }

        });

    }



}
