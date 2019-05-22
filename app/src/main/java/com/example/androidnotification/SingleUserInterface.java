package com.example.androidnotification;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleUserInterface extends AppCompatActivity {

    private EditText title, body;
    private TextView email;
    private Button send;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user_interface);

        users = (Users) getIntent().getSerializableExtra("object");

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        send = findViewById(R.id.single_user_send);
        email = findViewById(R.id.single_user_email);

        email.setText(users.email);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendNotify(users);

            }
        });

    }

    private void sendNotify(Users users)
    {
        String Title = title.getText().toString();
        String Body = body.getText().toString();

        if(Title.isEmpty() || Body.isEmpty())
        {
            Toast.makeText(this, "Empty Field !!", Toast.LENGTH_SHORT).show();
            title.requestFocus();
            body.requestFocus();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bbcurdu.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

       Call<ResponseBody> call = api.sendNotification(users.token, Title, Body);

       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
           {
               Toast.makeText(getApplicationContext(), "Response is : " + response, Toast.LENGTH_SHORT).show();

           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {

           }
       });


    }

}
