package com.kaizensoftware.students.adapter.model;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Arturo Cordero
 */
public class ViewHolder {

    private TextView nameTV;
    private TextView ageTV;
    private ImageView infoButton;

    public ViewHolder() {

    }

    public TextView getNameTV() {
        return nameTV;
    }

    public void setNameTV(TextView nameTV) {
        this.nameTV = nameTV;
    }

    public TextView getAgeTV() {
        return ageTV;
    }

    public void setAgeTV(TextView ageTV) {
        this.ageTV = ageTV;
    }

    public ImageView getInfoButton() {
        return infoButton;
    }

    public void setInfoButton(ImageView infoButton) {
        this.infoButton = infoButton;
    }

}