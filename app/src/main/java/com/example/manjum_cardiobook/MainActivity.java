/**

 Purpose: This is the main activity, it displyes the lsitview and get information from the fragemnt class (Addfragmetn)
 Design rationale: the fragments are created on Add click or Edit click and the databace is updated in the methods that communicate with the fragment.
 This class implements 2 interfacecs one for the add and one for the Edit fragment
 Outstanding issues: NO

*/

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


    //______________________________Constants_______________________________________________________
    public static final String TAG = " MainActivity ";
    private Button Add, Delete, Edit;
    private ListView list_view;
    private ArrayList<BloodPressure> data; //Array of class objects
    public static LinearLayout buttons;
    public static FragmentManager fragmentManager;
    private BloodPressureListAdapter adapter;
    private FirebaseFirestore database;
    private CollectionReference collectionReference;
    static int last = -1;
    String DocID = "";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Oncreate Started");

        //_____________________Initializing_________________________________________________________

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


        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                last = position;
                Log.d(TAG, "we have selected what we want to delete or Edit  ");
            }
        });


        collectionReference = database.collection("BloodPressure");




        //____________get everything from database and put it in the list view on app start_________
        collectionReference.orderBy("timestamp").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d(TAG, "onEvent: Firestore Error", e);
                    return;
                }


                data.clear();

                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Log.d("log", "There were additions ");
                    //android.util.Log.d(TAG, "onEvent: " + doc.getData());
                    //***************Very Important Line: Converts Firestore document to object*****
                    BloodPressure storeddata = doc.toObject(BloodPressure.class);
                    android.util.Log.d(TAG, "onEvent: " + storeddata.toString());
                    data.add(storeddata);
                }

                adapter.notifyDataSetChanged(); //update out list once due to snapshotlistener
            }
        });


        //________________________________Delete Button_____________________________________________
        Delete.setOnClickListener(new View.OnClickListener() {      //instantiating an anonymous class but we don't need syntax cause we arne using it again

            @Override
            //overriding a method in the anonymous class
            public void onClick(View v) {
                if (last != -1) {

                    //Get the document id  to delete the doc.
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
                last = -1;
            }

        });


        //________________________________Add Button________________________________________________
        Add.setOnClickListener(new View.OnClickListener() {      //instantiating an anonymous class but we don't need syntax cause we arne using it again
            @Override
            //overriding a method in the anonymous class
            public void onClick(View v) {
                //Create a fragment
                FragmentTransaction fragtra = fragmentManager.beginTransaction();
                AddFragment HFragment = new AddFragment();
                Log.w("myApp", "at run time ");
                fragtra.add(R.id.AddFragcont, HFragment, null); // useing the container
                fragtra.commit();

                //hide the buttons bar
                buttons.setVisibility(View.GONE);

            }
        });

        //________________________________Edit/View Button__________________________________________
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (last != -1) {
                    DocID = data.get(last).getDocID(); //get the correct docId reference
                    Log.d(TAG, "go to new frag  ");

                    //Create a fragment
                    FragmentTransaction fragtra = fragmentManager.beginTransaction();
                    AddFragment HFragment = new AddFragment(data.get(last).getDate(), data.get(last).getTime(),
                            data.get(last).getSystolic(), data.get(last).getDiastolic(),
                            data.get(last).getHeartrate(), data.get(last).getComment(), data.get(last).getDocID());

                    Log.w("myApp", "at run time ");
                    fragtra.add(R.id.AddFragcont, HFragment, null); // useing the container
                    fragtra.commit();

                    //hide the buttons bar
                    buttons.setVisibility(View.GONE);

                }

                last = -1;
                buttons.setVisibility(View.VISIBLE);

                }
        });

    }



    //_________________fragment communicates with the activity on this 2 methods____________________


    public void OnDataRead(BloodPressure obj) {
        //for add.onclick()
        Log.d(TAG, "Data has been received by the activity");


        //close the fragment.
        Fragment fragment = fragmentManager.findFragmentById(R.id.AddFragcont);

        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }

        buttons.setVisibility(View.VISIBLE);

        //store data gotten from fragment into the databace and which gives it to the lsitview

        data.add(obj);

       //Now add the data to the databacce
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
        Log.d(TAG, "Data has been relived by the activity");

    }



    public void OnDataReplace(final BloodPressure obj) {
        //for Edit.onclick() which opens up the fragment and pass the data to the fragment
        //we receive the data from the fragment here
        Log.d(TAG, "Data has been received and replaced by the activity");


        //close the fragment.
        Fragment fragment = fragmentManager.findFragmentById(R.id.AddFragcont);

        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }

        buttons.setVisibility(View.VISIBLE);

        //update the database which updates the listview
        database.collection("BloodPressure").document(DocID)
                .set(obj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: we have edited an existing item into the database ");
                    }
                });
        buttons.setVisibility(View.VISIBLE);

    }





}



