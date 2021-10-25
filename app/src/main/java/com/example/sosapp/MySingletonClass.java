package com.example.sosapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.sosapp.EmergencyContactAdd.PERMISSIONS_REQUEST_READ_CONTACTS;

public class MySingletonClass extends AppCompatActivity {
    private static MySingletonClass instance;
    DatabaseReference reff= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth fAuth=FirebaseAuth.getInstance();


    public HashMap<String,String>PhonexUID_DB=new HashMap<>();
    //PhoneNumber Registered --> Uid Mapping
    public HashMap<String,String>NamexUID_DB=new HashMap<>();

    public ArrayList<String> currentEmergencyContacts;


    public HashMap<String,ArrayList<Double>> PoliceStation=new HashMap<>();

    public void setPoliceStationCoordinates(){
        ArrayList<Double> cc=new ArrayList<>();
        cc.add(12.982314992539667);
        cc.add(80.19169419084243);
        PoliceStation.put("S9",cc);

        cc=new ArrayList<>();
        cc.add(12.987205749252615);
        cc.add(80.17473700898647);
        PoliceStation.put("S3",cc);

        cc=new ArrayList<>();
        cc.add(12.973107952854557);
        cc.add(80.1483090175329);
        PoliceStation.put("S5",cc);

        cc=new ArrayList<>();
        cc.add(12.957798623217682);
        cc.add(80.18555536110628);
        PoliceStation.put("S7",cc);

    }

    public static MySingletonClass getInstance() {
        if (instance == null)
            instance = new MySingletonClass();
        return instance;
    }

    private MySingletonClass() {

    }

    public void setValuesPhoneUIDHashMap() {

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.child("Phone_UID").getChildren()){
                    String ss=i.getKey().toString();
                    String sss=i.getValue().toString();
                    if(ss!=null && sss!=null && !ss.equals("AAA"))
                    PhonexUID_DB.put(ss,sss);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setValuesNameUIDHashMap(){
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.child("Name_UID").getChildren()){
                    String ss=i.getKey().toString();
                    String sss=i.getValue().toString();
                    if(ss!=null && sss!=null && !ss.equals("AAA"))
                        NamexUID_DB.put(ss,sss);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setValuesEmergencyContactList(){
        currentEmergencyContacts=new ArrayList<>();
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.child("Users").child(fAuth.getUid()).child("EmergencyContacts").getChildren()){
                    String ss=i.getKey().toString();
                    if(!ss.equals("AAA")){
                        String sss=snapshot.child("Users").child(ss).child("Phone_Number").getValue().toString();
                        currentEmergencyContacts.add(sss);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
