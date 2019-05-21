package com.example.androidnotification;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ProfileActivity";
    public static final String NODE_USERS = "Users";
    Button logout, mToken, mListButton;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.logout);
        mToken = findViewById(R.id.sendToken);
        mListButton = findViewById(R.id.listButton);

        logout.setOnClickListener(this);
        mToken.setOnClickListener(this);
        mListButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        FirebaseMessaging.getInstance().subscribeToTopic("hire");

        FirebaseInstanceId
                .getInstance()
                .getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Log.d(TAG, "onComplete: Task is successful");
                            String token = task.getResult().getToken();
                            saveToken(token);

                        }
                        else
                        {

                        }

                    }
                });
    }

    private void saveToken(String token)
    {
        String email = mAuth.getCurrentUser().getEmail();

        Users users = new Users(email, token);
        DatabaseReference mRefrence = FirebaseDatabase.getInstance().getReference(NODE_USERS);

        mRefrence.child(mAuth.getCurrentUser().getUid())
                .setValue(users)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ProfileActivity.this, "Data sent", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.logout:
                mAuth.signOut();
                mAuth = null;
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sendToken:
                break;
            case R.id.listButton:
                Intent intent1 = new Intent(ProfileActivity.this, ListActivity.class);
                startActivity(intent1);
                break;
        }

    }
}
