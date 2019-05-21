package com.example.androidnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

// 1 - Notification Channel
    // 2 - Notification Channel
    // 3 - Notification Manager

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    public static final String CHANNEL_ID = "Android Notification";
    private static final String CHANNEL_NAME = "Shakeel Haider";
    private static final String CHANNEL_DESC = "Shakkel Haider send notification";

    EditText mEmail, mPassword;
    Button mSignin;
    TextView mCreateAccount;
    ProgressBar mProgress;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mEmail = findViewById(R.id.email);
       mPassword = findViewById(R.id.password);
       mSignin = findViewById(R.id.signin);
       mCreateAccount = findViewById(R.id.create_account);
       mProgress = findViewById(R.id.progressbar_signin);

       mSignin.setOnClickListener(this);
       mCreateAccount.setOnClickListener(this);
       mAuth = FirebaseAuth.getInstance();




        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }





    }



    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null)
        {
            Log.d(TAG, "onStart: mAuth is not NULL");
            startProfileActivity();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.signin:
                mProgress.setVisibility(View.VISIBLE);
                loadInformation();
                break;
            case R.id.create_account:
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.container, new SignupFragment());
                transaction.commit();
                break;
        }

    }

    private void loadInformation()
    {
        String email, password;
        email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            Toast.makeText(this, "Email required", Toast.LENGTH_SHORT).show();
            mProgress.setVisibility(View.GONE);
            return;

        }
        if(password.isEmpty())
        {
            Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
            mProgress.setVisibility(View.GONE);
            return;

        }
        if(password.length() < 6)
        {
            Toast.makeText(this, "Password length is too short", Toast.LENGTH_SHORT).show();
            mProgress.setVisibility(View.GONE);
            return;

        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            mProgress.setVisibility(View.GONE);
                            startProfileActivity();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void startProfileActivity()
    {
        mProgress.setVisibility(View.GONE);
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

}
