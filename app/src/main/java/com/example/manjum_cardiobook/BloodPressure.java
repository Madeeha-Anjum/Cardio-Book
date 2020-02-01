package com.example.manjum_cardiobook;


import androidx.annotation.NonNull;

public class BloodPressure {

    private String date, time, systolic, diastolic, heartrate, comment;

    public BloodPressure() {

    }

    public BloodPressure(String date, String time, String systolic, String diastolic, String heartrate, String comment){

        //Now we store the data in the fields
        this.date = date;
        this.time = time;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heartrate = heartrate;
        this.comment = comment;

//
//        //for testing this class
//        this.date = "data";
//        this.time = "time";
//        this.systolic = "systolic";
//        this.diastolic = "diastolic";
//        this.heartrate = "heartrate";
//        this.comment = "comment";

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

//    public void Check(){
//        /*
//    date measured (presented in yyyy-mm-dd format)
//    time measured (presented in hh:mm format)
//    systolic pressure in mm Hg (non-negative integer)
//    diastolic pressure in mm Hg (non-negative integer)
//    heart rate in beats per minute (non-negative integer)
//    comment (textual, up to 20 characters)
//
//*/
//        return;
//
//    }

}
