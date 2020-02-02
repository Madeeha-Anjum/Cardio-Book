package com.example.manjum_cardiobook;


import android.graphics.Color;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class BloodPressure {

    @DocumentId
    private String docID;

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    private String date;
    private String time;
    public String systolic;
    private String diastolic;
    private String heartrate;
    private String comment;
    public Date timestamp;

    public BloodPressure() {

    }

    public BloodPressure(String date, String time, String systolic, String diastolic, String heartrate, String comment) {

        //Now we store the data in the fields
        this.date = date;
        this.time = time;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heartrate = heartrate;
        this.comment = comment;

    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getHeartrate() {
        return heartrate;
    }

    public void setHeartrate(String heartrate) {
        this.heartrate = heartrate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    @Override
    public String toString() {
        return "Date: " + date + " Time: " + time + "Sys: " + systolic + " dia: " + diastolic + "Heartrate: " + heartrate + " Comment" + comment;
    }


}
