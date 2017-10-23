package com.brayadiaz.agendafirebase;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    EditText eID, eName, eEmail, ePhone;
    Button bCreate, bUpdate, bRead, bDelete;
    FirebaseDatabase database ;
    DatabaseReference myRef;
    int uid=0;
    users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

        eID = (EditText) findViewById(R.id.eID);
        eName = (EditText) findViewById(R.id.eName);
        eEmail = (EditText) findViewById(R.id.eEmail);
        ePhone = (EditText) findViewById(R.id.ePhone);
        bCreate = (Button) findViewById(R.id.bCreate);
        bUpdate = (Button) findViewById(R.id.bUpdate);
        bRead = (Button) findViewById(R.id.bRead);
        bDelete = (Button) findViewById(R.id.bDelete);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        //myRef.setValue("Hello, World!");


    }

    public void onClick(View view) {
        int id=view.getId();
        final String nombre,ID,email,phone,uidd;
        nombre=eName.getText().toString();
        email=eEmail.getText().toString();
        ID=eID.getText().toString();
        phone=ePhone.getText().toString();
        database=FirebaseDatabase.getInstance();

        switch (id){
            case R.id.bCreate:
                myRef=database.getReference("users").child("user"+uid);
                user=new users(nombre,email,phone,"user"+uid);
                myRef.setValue(user);
                uid++;
                clean();

                break;
            case R.id.bRead:
                uidd = eID.getText().toString();
                myRef = database.getReference("users");
                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("user"+uidd).exists()){

                            user = dataSnapshot.child("user"+uidd).getValue(users.class);
                            eName.setText(user.getName());
                            eEmail.setText(user.getEmail());
                            ePhone.setText(user.getPhone());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });

                break;

            case R.id.bUpdate:

                uidd = eID.getText().toString();
                myRef = database.getReference("users").child("user"+uidd);

                Map<String, Object> newData = new HashMap<>();
                newData.put("name", nombre);
                newData.put("email", email);
                newData.put("phone", phone);

                myRef.updateChildren(newData);
                break;
            case R.id.bDelete:
                uidd = eID.getText().toString();
                myRef = database.getReference("users").child("user"+uidd);
                myRef.removeValue();
                break;

        }
    }

    private void clean() {
        eEmail.setText("");
        ePhone.setText("");
        eName.setText("");
        eID.setText("");
    }
}
