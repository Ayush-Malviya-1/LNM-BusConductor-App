package com.example.conductorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class BookTicket extends AppCompatActivity {
    Button scanbtn,bt2,bt3;
   TextView tt1,tt2,tt3;
    String uid;
    String busid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static TextView scantext;
    Button fetchBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);







    }

    @Override
    protected void onResume() {
        super.onResume();



        scantext=(TextView)findViewById(R.id.scantext);
        scanbtn=(Button) findViewById(R.id.scanbtn);

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //intent.putExtra("busid",getIntent().getStringExtra("busid").toString());
                startActivity(new Intent(getApplicationContext(),scannerView.class));



            }




        });




        busid=getIntent().getStringExtra("busid").toString();



        bt2=(Button) findViewById(R.id.button2);
        bt3=(Button) findViewById(R.id.button3);


        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Toast.makeText(this, getIntent().getStringExtra("busid").toString(), Toast.LENGTH_SHORT).show();


                tt1=(TextView)  findViewById(R.id.textView1);
                tt2=(TextView) findViewById(R.id.textView2);


                tt3=(TextView) findViewById(R.id.scantext);



                uid=tt3.getText().toString();

                Log.d("e",uid);






               // Toast.makeText(BookTicket.this, uid, Toast.LENGTH_SHORT).show();
                //chk uid;

                db.collection("wallet").document(uid).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override


                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                Wallet wt=documentSnapshot.toObject(Wallet.class);

                                String res=String.valueOf(wt.getTokenNo()) ;
                                Log.d("ss",res);

                                tt2.setText(res.toString());
                                if(wt.getTokenNo()>1)
                                {
                                    Log.d("BOOK",String.valueOf(wt.getTokenNo()));




                                }
                                else{
                                    Log.d("Book","Insufficient Balance");
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.d("Atg","e");
                            }
                        });




                //Toast.makeText(this,zz,  Toast.LENGTH_SHORT).show();


                DocumentReference docRef = db.collection("bus").document(busid);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        model c_bus = documentSnapshot.toObject(model.class);

                        tt1.setText(c_bus.getSeatsAvailable());

                    }
                });












            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float bal=Float.parseFloat(tt2.getText().toString());
                int Available=Integer.parseInt((tt1.getText().toString()));


                if(Available==0){

                    Toast.makeText(BookTicket.this, "Seats Not Available", Toast.LENGTH_SHORT).show();
                }

                else if(bal>=1 )
                {
                    //Log.d("BOOK",String.valueOf(wt.getTokenNo()));

                    //Book Ticket

                    //update Wallet





                    db.collection("bus").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d("Er",document.getId() + " "+busid);
                                    if(busid.equals(document.getId()))
                                    {
                                        Log.d("as","Ayus");
                                        model myBus=document.toObject(model.class);
                                        String seatsAvail=String.valueOf(Integer.parseInt(myBus.getSeatsAvailable())-1);


                                        DocumentReference busRef=db.collection("bus").document(busid);

                                        busRef
                                                .update("seatsAvailable",seatsAvail )
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("Isd", "DocumentSnapshot successfully updated!");



                                                        //Generating Ticket

                                                        Map<String, Object> ticket = new HashMap<>();
                                                        ticket.put("busDate",myBus.getDate());
                                                        ticket.put("busId",myBus.get_id());
                                                        ticket.put("busTime",myBus.getTime());
                                                        ticket.put("source",myBus.getSource());
                                                        ticket.put("destination",myBus.getDestination());
                                                        ticket.put("uid",uid);
                                                        ticket.put("status","Upcoming");


                                                        db.collection("ticket").add(ticket);


                                                        //Update Ticket

                                                        DocumentReference walletRef=db.collection("wallet").document(uid);

                                                        walletRef.update("tokenNo",bal-1)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        Log.d("wallet","Wallet Updated Successfully");


                                                                        tt2.setText(String.valueOf(bal-1));
                                                                        tt1.setText(String.valueOf(Available-1));
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Log.d("wallet","Wallet Not  Updated Successfully");
                                                                    }
                                                                });




                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("Isd", "DocumentSnapshot not   updated!");
                                                    }
                                                });




                                    }
                                }
                            } else {
                                Log.w("LNM", "Error getting documents.", task.getException());
                            }
                        }
                    });



                    Toast.makeText(BookTicket.this, "Ticket Booked Successfully", Toast.LENGTH_SHORT).show();
                }

                else{
                    Log.d("Book","Insufficient Balance");

                    Toast.makeText(BookTicket.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}