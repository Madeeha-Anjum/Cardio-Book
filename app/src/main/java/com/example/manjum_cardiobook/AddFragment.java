package com.example.manjum_cardiobook;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import static com.example.manjum_cardiobook.MainActivity.fragmentManager;


/**
 * A simple {@link Fragment} subclass.
 */
    public class AddFragment extends Fragment {


    //_____________________Constants_______________________________
    private EditText date,time, systolic, diastolic, heartrate, comment;
    private Button Enter;
    private ImageButton X;
    private Activity activity;
    private OnMessageReadListener messageReadListener;  //call back on the interface



    public AddFragment() {
        // Required empty public constructor
    }



    //SPECIFY AN INTERFACE FOR ESTABLISHING COMMUNICATION

    public interface OnMessageReadListener{
        //o access the interface methods, the interface must be "implemented"  (instead of extends).
        //If you declare a variable in an; automatically assigned public, static, and final modifiers.
        // In addition you cannot call a method private.

        public void OnDataRead(StoreData object); //Method for the abstract interface (aka cannot have a body )
        //rule: CHECK WATHER THE INTERFACE IS IMPLEMENTED BY THE PARENT ACTIVITY (onattach methos )
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        date = view.findViewById(R.id.data);
        time = view.findViewById(R.id.time);
        systolic = view.findViewById(R.id.systolic);
        diastolic = view.findViewById(R.id.diastolic);
        heartrate = view.findViewById(R.id.heartrate);
        comment = view.findViewById(R.id.comment);

        Enter = view.findViewById(R.id.Enter);
        X = view.findViewById(R.id.X);



        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we need to use an interface method now
                // hen the fragment prses the send button we have to
                // //call  the method (on meeage read) using the call back interface

                String Date = date.getText().toString();
                String Time = time.getText().toString();
                String Systolic = systolic.getText().toString();
                String Diastolic = diastolic.getText().toString();
                String Heartrate = heartrate.getText().toString();
                String Comment = comment.getText().toString();


                StoreData new_object_data = new StoreData(Date,Time, Systolic, Diastolic, Heartrate, Comment);



                messageReadListener.OnDataRead(new_object_data); //passes it to the mainactivty method (OnmessageRead)

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
    public void onAttach(Context context){
        super.onAttach(context);

        //tells you what activity the fragment is in
        //storing the activity that the fragment is called from
        //can have different activities call this
//        this.activity = (Activity) context;
//



        Activity activity = (Activity) context;  //___________Activity instance
//        this.messageReadListener = (OnMessageReadListener) (Activity) context; //get the call back for the interface


        try{
            messageReadListener = (OnMessageReadListener) activity;  //get the call back for the interface

        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+ "Must override on message read");
        }

    }

}
