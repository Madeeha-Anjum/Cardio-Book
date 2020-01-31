package com.example.manjum_cardiobook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements AddFragment.OnMessageReadListener {


    //_____________________Constants_______________________________
    public static final String TAG = " MainActivity ";
    private Button Add, Delete, Edit;
    private TextView City;
    private ListView list;
    private ArrayAdapter adaptor;
    private ArrayList<StoreData> data; //Array of class objects
    private ArrayList<String> data2;
    public static LinearLayout buttons;
    private Log Log;

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Oncreate Started");

        //----------------- Initializing ------------------------------------//

        Add = (Button) findViewById(R.id.Add);
        Delete = (Button) findViewById(R.id.Delete);
        Edit = (Button) findViewById(R.id.Edit);
        list = (ListView) findViewById(R.id.DataView);
        buttons = (LinearLayout) findViewById(R.id.buttons);

        data = new ArrayList<>();
        data2 = new ArrayList<>();
        adaptor = new ArrayAdapter<>(this, R.layout.listview, data2);
        list.setAdapter(adaptor);



        fragmentManager = getSupportFragmentManager();


         if (data.size() > 0){
             Log.d(TAG, "Oreload");
             StoreData v = (StoreData) data.get(0);
             String s = v.retrieveData().toString();

             Log.d(  s, "jychjgchgcghcjgvcjgv");


             adaptor.notifyDataSetChanged();

        }






        //----------------- Adding Data ------------------------------------//
        Add.setOnClickListener(new View.OnClickListener() {      //instantiating an anonymous class but we don't need syntax cause we arne using it again
            @Override
            //overriding a method in the anonymous class
            public void onClick(View v) {

                FragmentTransaction fragtra = fragmentManager.beginTransaction();
                AddFragment HFragment = new AddFragment();

                Log.w("myApp", "at run time ");
                fragtra.add(R.id.AddFragcont, HFragment, null); // useing the container
                fragtra.commit();
                //hide the buttons bar
                buttons.setVisibility(View.GONE);


//                fragmentManager.beginTransaction().remove(HFragment).commit();

            }
        });


    }

    //___________________________//fragment communicates with the activity on this method____________________





    @Override
    public void OnDataRead(StoreData object) {
        //fragment communicaters with the activity on this method


        //Acstivity gets the message

        Log.d("log", "Data has been recived by the activity");


        data.add(object);

        adaptor.notifyDataSetChanged();

        //close the fragment.
        Fragment fragment = fragmentManager.findFragmentById(R.id.AddFragcont);

        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }

        buttons.setVisibility(View.VISIBLE);



        StoreData v = (StoreData) data.get(0);
        String s = v.retrieveData();

        Log.d(  s, "jychjgchgcghcjgvcjgv");

        data2.add(s);
        adaptor.notifyDataSetChanged();


    }
}
