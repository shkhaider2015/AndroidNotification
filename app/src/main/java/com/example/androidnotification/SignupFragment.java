package com.example.androidnotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupFragment extends Fragment
{
    FirebaseAuth mAuth;
    EditText mEmail, mPassword;
    Button mSignup;
    ProgressBar mProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup, container, false);

        mAuth = FirebaseAuth.getInstance();
        mProgress = view.findViewById(R.id.progressbar_signup);
        mEmail = view.findViewById(R.id.email1);
        mPassword = view.findViewById(R.id.password1);
        mSignup = view.findViewById(R.id.signup);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setVisibility(View.VISIBLE);
                loadInformation();
            }
        });

        return view;
    }

    private void loadInformation()
    {
        String email, password;
        email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            Toast.makeText(getActivity(), "Email required", Toast.LENGTH_SHORT).show();
            mProgress.setVisibility(View.GONE);
            return;

        }
        if(password.isEmpty())
        {
            Toast.makeText(getActivity(), "Password required", Toast.LENGTH_SHORT).show();
            mProgress.setVisibility(View.GONE);
            return;

        }
        if(password.length() < 6)
        {
            Toast.makeText(getActivity(), "Password length is too short", Toast.LENGTH_SHORT).show();
            mProgress.setVisibility(View.GONE);
            return;

        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            startProfileActivity();

                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }

                    }
                });


    }

    private void startProfileActivity()
    {
        mProgress.setVisibility(View.GONE);
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

}
