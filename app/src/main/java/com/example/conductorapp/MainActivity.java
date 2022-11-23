package com.example.conductorapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.text.*;
import java.util.*;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


    RecyclerView recview;
    ArrayList<model> datalist,datalist1;
    FirebaseFirestore db;
    myadapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent i = new Intent(this,LoginRegisterActivity.class);
            startActivity(i);
            this.finish();
        }

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new myadapter(datalist);
        recview.setAdapter(adapter);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("bus").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            model obj=d.toObject(model.class);


                            SimpleDateFormat sd = new SimpleDateFormat( "yyyy-MM-dd");
                            Date date = new Date();


                            String strDate =sd.format(date);


                            SimpleDateFormat dd = new SimpleDateFormat( "hh:mm");
                           // dd.setTimeZone(TimeZone.getTimeZone("IST"));
                            String strTime =dd.format(date);

                          Log.d("Im",strTime+ " "+obj.getDate());

                            if(strDate.equals(obj.getDate())  && strTime.compareTo(obj.getTime())<=0  )
                                datalist.add(obj);


                        }
                        adapter.notifyDataSetChanged();
                    }
                });



        //Fetching Bus List
//        db.collection("bus").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("LNM", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w("LNM", "Error getting documents.", task.getException());
//                        }
//                    }
//                });



        //Fetching Wallet

//        String uid="7cjGiVHgbVTlrxR1WpbwCXrObLd2";
//        db.collection("wallet").document(uid).get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            Wallet wt=documentSnapshot.toObject(Wallet.class);
//
//                            if(wt.getTokenNo()>1)
//                            {
//                                Log.d("BOOK",String.valueOf(wt.getTokenNo()));
//
//                                //Book Ticket
//
//                                //update Wallet
//
//
//                            }
//                            else{
//                                Log.d("Book","Insufficient Balance");
//                            }
//
//                    }
//                });






    }


    void startLogin(){
        Intent i = new Intent(this,LoginRegisterActivity.class);
        startActivity(i);
        this.finish();
    }

    public void handleLogout(View view) {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    startLogin();

                }
            }
        });
    }



}