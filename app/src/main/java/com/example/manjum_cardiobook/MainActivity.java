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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements AddFragment.OnMessageReadListener, AddFragment.OnMessageReplaceListener {


    //_____________________Constants_______________________________
    public static final String TAG = " MainActivity ";
    private Button Add, Delete, Edit;
    private ListView list_view;
    private ArrayList<BloodPressure> data; //Array of class objects

    public static LinearLayout buttons;
    public static FragmentManager fragmentManager;
    private BloodPressureListAdapter adapter;
    private FirebaseFirestore database;
    private CollectionReference collectionReference;
    public int last, lastrep = -1;
    String DocID = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Oncreate Started");

        //----------------- Initializing ------------------------------------//

        Add = (Button) findViewById(R.id.Add);
        Delete = (Button) findViewById(R.id.Delete);
        Edit = (Button) findViewById(R.id.Edit);
        list_view = (ListView) findViewById(R.id.DataView);
        buttons = (LinearLayout) findViewById(R.id.buttons);
        data = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        database = FirebaseFirestore.getInstance();
        adapter = new BloodPressureListAdapter(MainActivity.this, R.layout.listview, data);
        list_view.setAdapter(adapter);


//        final ArrayList<City> cities = new ArrayList<>(); //__________________________________________________________________________________________________________
        collectionReference = database.collection("BloodPressure");

        //get everything from database and put it in the listview when app starts
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d(TAG, "onEvent: Firestore Error", e);
                    return;
                }
                data.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Log.d("log", "when do i move here ");

//                    android.util.Log.d(TAG, "onEvent: " + doc.getData());
                    //***********************************Converts Firestore document to object*****************************************
                    BloodPressure storeddata = doc.toObject(BloodPressure.class);
                    //***********************************|||||||||||||||||||||||||||||||||||||*****************************************
                    android.util.Log.d(TAG, "onEvent: " + storeddata.toString());

                    data.add(storeddata);

                    adapter.notifyDataSetChanged();

                }

            }
        });

        android.util.Log.d(TAG, "On delet ");

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                last = position;
                Log.d(TAG, "we have selected what we want to delete  ");
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {      //instantiating an anonymous class but we don't need syntax cause we arne using it again

            @Override
            //overriding a method in the anonymous class
            public void onClick(View v) {
                if (last != -1) {
                    //Get the document id
                    database.collection("BloodPressure").document(data.get(last).getDocID()).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Onsucess: we have deleted");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Onsucess: we have not sucessfully deleted");
                                }
                            });


                }
                data.remove(last);
                last = -1;

                adapter.notifyDataSetChanged();
            }

        });




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


        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                lastrep = position;
                Log.d(TAG, "we have selected what we want to  replace  ");
            }
        });



        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastrep != -1) {

                    DocID = data.get(lastrep).getDocID();

                    Log.d(TAG, "go to new frag  ");
                    FragmentTransaction fragtra = fragmentManager.beginTransaction();
                    AddFragment HFragment = new AddFragment(data.get(lastrep).getDate(), data.get(lastrep).getTime(),
                            data.get(lastrep).getSystolic(), data.get(lastrep).getDiastolic(), data.get(lastrep).getHeartrate(), data.get(lastrep).getComment(), data.get(lastrep).getDocID());

                    Log.w("myApp", "at run time ");
                    fragtra.add(R.id.AddFragcont, HFragment, null); // useing the container
                    fragtra.commit();

                    //hide the buttons bar
                    buttons.setVisibility(View.GONE);
                }

                lastrep = -1;
                buttons.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }

            //cal methos to check blood pressure


        });

    }


//    Normal pressures are systolic between 90 and 140 and diastolic between 60 and 90.




    //___________________________//fragment communicates with the activity on this method____________________


    public void OnDataRead(BloodPressure obj) {
        //fragment communicaters with the activity on this method


        //Acstivity gets the message

        Log.d(TAG, "Data has been recived by the activity");


        //close the fragment.
        Fragment fragment = fragmentManager.findFragmentById(R.id.AddFragcont);

        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }

        buttons.setVisibility(View.VISIBLE);

        //store data gotten from fragemtn into the data bace and also give it to the lsitview

        data.add(obj);

        //Now add the data to the databacce also
        collectionReference.add(obj)
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
        Log.d(TAG, "Data has been recived by the activity");


    }

    public void OnDataReplace(final BloodPressure obj) {
        //open up the fragment and pass the data to the fragment


        Log.d(TAG, "Data has been recived and replaced by the activity");


        //close the fragment.
        Fragment fragment = fragmentManager.findFragmentById(R.id.AddFragcont);

        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }

        buttons.setVisibility(View.VISIBLE);




        //update the database
        database.collection("BloodPressure").document(DocID)
                .set(obj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: we have edited an existing item into the databace ");
                    }
                });
        buttons.setVisibility(View.VISIBLE);
        //update the list_view

    }





}