package com.example.manjum_cardiobook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

    private FirebaseFirestore database;

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

        database = FirebaseFirestore.getInstance();

        final CollectionReference collectionReference = database.collection("Cities");

        final ArrayList<City> cities = new ArrayList<>();

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d(TAG, "onEvent: Firestore Error", e);
                    return;
                }

                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    android.util.Log.d(TAG, "onEvent: " + doc.getData());
                    City city = doc.toObject(City.class);   // Converts Firestore document to object
                    android.util.Log.d(TAG, "onEvent: " + city.toString());

                    cities.add(city);
                }

            }
        });

        collectionReference.add(new City("Fake city", "Fake province"))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        android.util.Log.d(TAG, "Failed uploading");
                    }
                });



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
