package com.example.manjum_cardiobook;


import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;

public class BloodPressure {

    @DocumentId
    private String docID;

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    private String date, time, systolic, diastolic, heartrate, comment, info;

    public BloodPressure() {

    }

    public BloodPressure(String date, String time, String systolic, String diastolic, String heartrate, String comment, String info) {

        //Now we store the data in the fields
        this.date = date;
        this.time = time;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heartrate = heartrate;
        this.comment = comment;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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


    @NonNull
    @Override
    public String toString() {
        return "Date: " + date + " Time: " + time + "Sys: " + systolic + " dia: " + diastolic + "Heartrate: " + heartrate + " Comment" + comment;
    }


}
