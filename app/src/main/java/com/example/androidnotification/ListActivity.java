package com.example.androidnotification;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    FirebaseAuth mAuth;
    DatabaseReference mRef;
    ProgressBar mProgressBar;
    private static final String USER_NODE = "Users";
    private static final String TAG = "ListActivity";
    private ArrayList<Users> usersArrayList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference(USER_NODE);
        mProgressBar = findViewById(R.id.list_progressbar);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                usersArrayList = new ArrayList<>();
                for(DataSnapshot x : dataSnapshot.getChildren())
                {
                    Log.d(TAG, "List User Datasnapshot : " + x);
                    String email = String.valueOf(x.child("email").getValue());
                    String token = String.valueOf(x.child("token").getValue());
                    Log.d(TAG, "onDataChange: Email is " + email);
                    Log.d(TAG, "onDataChange: Token is " + token);
                    Users users = new Users(email, token);
                    usersArrayList.add(users);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.e(TAG, "onCancelled: ", databaseError.toException() );

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                listView = findViewById(R.id.listView);
                ListAdapter listAdapter = new ListAdapter(ListActivity.this, usersArrayList);
                listView.setAdapter(listAdapter);
                mProgressBar.setVisibility(View.GONE);
                listView.setOnItemClickListener(ListActivity.this);

            }
        }, 3000);



    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Users usersClick = (Users) parent.getItemAtPosition(position);
        String email = usersClick.email;
        Toast.makeText(ListActivity.this, email, Toast.LENGTH_SHORT).show();

    }
}
