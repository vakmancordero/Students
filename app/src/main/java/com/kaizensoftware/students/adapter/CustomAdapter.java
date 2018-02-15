package com.kaizensoftware.students.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaizensoftware.students.R;
import com.kaizensoftware.students.adapter.model.ViewHolder;
import com.kaizensoftware.students.model.Student;

import java.util.List;

/**
 * Created by Arturo Cordero
 */
public class CustomAdapter extends ArrayAdapter<Student> {

    private List<Student> data;

    private Context customContext;

    private int lastPosition = -1;

    public CustomAdapter(List<Student> data, Context context) {
        super(context, R.layout.row_item, data);
        this.data = data;
        this.customContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Student student = super.getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);

            viewHolder.setNameTV((TextView) convertView.findViewById(R.id.name));
            viewHolder.setAgeTV((TextView) convertView.findViewById(R.id.age));
            viewHolder.setInfoButton((ImageView) convertView.findViewById(R.id.item_info));

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        lastPosition = position;

        viewHolder.getNameTV().setText(student.getName());
        viewHolder.getAgeTV().setText(String.valueOf(student.getAge()));

        viewHolder.getInfoButton().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int position = (Integer) view.getTag();
                Student student = (Student) getItem(position);

                switch (view.getId()) {

                    case R.id.item_info:

                        Snackbar.make(view, "Estudiante: " + student.getName(), Snackbar.LENGTH_LONG).setAction("No action", null).show();

                        break;
                }

            }
        });

        viewHolder.getInfoButton().setTag(position);

        return convertView;
    }


}
