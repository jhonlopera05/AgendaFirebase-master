package com.brayadiaz.agendafirebase;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListaActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    ListView list;
    ArrayList <users> usuarios;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_lista);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.frame);
        getLayoutInflater().inflate(R.layout.activity_lista, contentFrameLayout);

        usuarios =new ArrayList<users>();
        list= (ListView)  findViewById(R.id.list);

        final Adapter adapter=new Adapter(this,usuarios);
        list.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnaphot: dataSnapshot.getChildren()){
                    usuarios.add(userSnaphot.getValue(users.class));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }

    class Adapter extends ArrayAdapter<users> {

        public Adapter(@NonNull Context context, ArrayList<users> usuario) {
            super(context, R.layout.lista_item,usuario);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater= LayoutInflater.from(getContext());
            View view=inflater.inflate(R.layout.lista_item,null);

            users usuario=getItem(position);

            TextView  tUid=view.findViewById(R.id.tID);
            tUid.setText(usuario.getUid());
            TextView  tName=view.findViewById(R.id.tName);
            tName.setText(usuario.getName());
            TextView  tEmail=view.findViewById(R.id.tEmail);
            tEmail.setText(usuario.getEmail());
            TextView  tPhone=view.findViewById(R.id.tPhone);
            tPhone.setText(usuario.getEmail());

            return view;


        }
    }
}
