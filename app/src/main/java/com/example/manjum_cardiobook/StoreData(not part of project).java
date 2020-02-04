package com.example.manjum_cardiobook;


import android.util.Log;


import static com.example.manjum_cardiobook.MainActivity.TAG;





public class StoreData {

    private String date, time, systolic, diastolic, heartrate, comment;



    public StoreData( String date, String time, String systolic, String diastolic, String heartrate, String comment){
        // First we will check the data the user entered is valid
        //call a method that dose that


        Check();

        //Now we store the data in the fields
        this.date = date;
        this.time = time;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heartrate = heartrate;
        this.comment = comment;


        //for testing this class
        this.date = "data";
        this.time = "time";
        this.systolic = "systolic";
        this.diastolic = "diastolic";
        this.heartrate = "heartrate";
        this.comment = "comment";
        //now we store the data in a file
            Store(date, time, systolic, diastolic, heartrate, comment);

         //gets get it now ?

    }




    public void Store(String date, String time, String systolic, String diastolic, String heartrate, String comment){
        Log.d(TAG, "now we store???");


        return;
    }

    public String retrieveData() {


        return this.comment;


    }







    public void Check(){
        /*
    date measured (presented in yyyy-mm-dd format)
    time measured (presented in hh:mm format)
    systolic pressure in mm Hg (non-negative integer)
    diastolic pressure in mm Hg (non-negative integer)
    heart rate in beats per minute (non-negative integer)
    comment (textual, up to 20 characters)

*/
        return;

    }
}
