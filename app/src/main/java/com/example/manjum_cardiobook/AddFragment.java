package com.example.manjum_cardiobook;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.manjum_cardiobook.MainActivity.TAG;
import static com.example.manjum_cardiobook.MainActivity.fragmentManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {


    //_____________________Constants_______________________________
    private EditText date, time, systolic, diastolic, heartrate, comment;
    private Button Enter;
    private ImageButton X;
    private Activity activity;
    private OnMessageReadListener messageReadListener;  //call back on the interface
    private OnMessageReplaceListener messageReplaceListener;
    private String Replacedate, Replacetime, Replacesystolic, Replacediastolic, Replaceheartrate, Replacecomment = ""; //default
    private String DocID ="";

    public AddFragment() {
        // Required empty public constructor
    }

    public AddFragment(String replacedate, String replacetime, String replacesystolic, String replacediastolic, String replaceheartrate, String replacecomment, String docID) {
        Replacedate = replacedate;
        Replacetime = replacetime;
        Replacesystolic = replacesystolic;
        Replacediastolic = replacediastolic;
        Replaceheartrate = replaceheartrate;
        Replacecomment = replacecomment;
        DocID = docID;
    }


    //SPECIFY AN INTERFACE FOR ESTABLISHING COMMUNICATION

    public interface OnMessageReadListener {
        //o access the interface methods, the interface must be "implemented"  (instead of extends).
        //If you declare a variable in an; automatically assigned public, static, and final modifiers.
        // In addition you cannot call a method private.

        public void OnDataRead(BloodPressure obj); //Method for the abstract interface (aka cannot have a body )
        //rule: CHECK WATHER THE INTERFACE IS IMPLEMENTED BY THE PARENT ACTIVITY (onattach methos )
    }

    public interface OnMessageReplaceListener {
        //o access the interface methods, the interface must be "implemented"  (instead of extends).
        //If you declare a variable in an; automatically assigned public, static, and final modifiers.
        // In addition you cannot call a method private.

        public void OnDataReplace(BloodPressure obj); //Method for the abstract interface (aka cannot have a body )
        //rule: CHECK WATHER THE INTERFACE IS IMPLEMENTED BY THE PARENT ACTIVITY (onattach methos )
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        systolic = view.findViewById(R.id.systolic);
        diastolic = view.findViewById(R.id.diastolic);
        heartrate = view.findViewById(R.id.heartrate);
        comment = view.findViewById(R.id.comment);

        //default empty
        date.setText(Replacedate);
        time.setText(Replacetime);
        systolic.setText(Replacesystolic);
        diastolic.setText(Replacediastolic);
        heartrate.setText(Replaceheartrate);
        comment.setText(Replacecomment);

        Enter = view.findViewById(R.id.Enter);
        X = view.findViewById(R.id.X);
        Log.d(TAG, "Oncreate View Started");


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                https://stackoverflow.com/questions/45283857/how-to-get-date-and-time-from-date-time-picker-dialog-and-show-it/*/
                final Calendar getDate = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        getDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        format.format(getDate.getTime());
                    }
                }, getDate.get(Calendar.YEAR),
                        getDate.get(Calendar.MONTH),
                        getDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                format.format(getDate.getTime());

                date.setText(format.format(getDate.getTime()));

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                https://www.journaldev.com/9976/android-date-time-picker-dialog
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                                time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();


            }
        });

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we need to use an interface method now
                // hen the fragment prses the send button we have to
                // //call  the method (on meeage read) using the call back interface

                Log.d(TAG, Replacecomment);


                String Date = date.getText().toString();
                String Time = time.getText().toString();
                String Systolic = systolic.getText().toString();
                String Diastolic = diastolic.getText().toString();
                String Heartrate = heartrate.getText().toString();
                String Comment = comment.getText().toString();


                BloodPressure obj = new BloodPressure(Date, Time, Systolic, Diastolic, Heartrate,Comment);


                if ( Date.length() > 0 && Time.length() > 0 && Systolic.length() > 0 && Diastolic.length() > 0 && Heartrate.length() > 0 && Comment.length() > 0) {

                    Log.d(TAG, Date.toLowerCase());
                    if ( DocID.length() > 0) {
                        Toast.makeText(activity, "succesfull", Toast.LENGTH_SHORT).show();
                         messageReplaceListener.OnDataReplace(obj);
                    }

                    else {
                    messageReadListener.OnDataRead(obj); //passes it to the mainactivty method (OnmessageRead)

                }
            }
                else {

                    //close the fragment.
                    Fragment fragment = fragmentManager.findFragmentById(R.id.AddFragcont);

                    if (fragment != null) {
                        fragmentManager.beginTransaction().remove(fragment).commit();
                    }
                    Toast.makeText(activity, "Error: Blank entry was unsuccesfull", Toast.LENGTH_SHORT).show();
                    MainActivity.buttons.setVisibility(View.VISIBLE);
                }
            }
        });


        X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close fragment


                //close the fragment.
                Fragment fragment = fragmentManager.findFragmentById(R.id.AddFragcont);

                if (fragment != null) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                }
                MainActivity.buttons.setVisibility(View.VISIBLE);

            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //tells you what activity the fragment is in
        //storing the activity that the fragment is called from
        //can have different activities call this
//        this.activity = (Activity) context;
//


        this.activity = (Activity) context;  //___________Activity instance
//        this.messageReadListener = (OnMessageReadListener) (Activity) context; //get the call back for the interface


        try {
            messageReadListener = (OnMessageReadListener) activity;  //get the call back for the interface


        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "Must override on message read");
        }


        try {
              //get the call back for the interface
            messageReplaceListener =  (OnMessageReplaceListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "Must override on message read");
        }


    }

}
