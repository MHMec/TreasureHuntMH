package com.mh.treasurehuntmh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AuthActivity";
    private ArrayList<String> tokens;

    Button submitBtn;
    EditText authToken;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authToken = (EditText) findViewById(R.id.editAccessToken);
        submitBtn = (Button) findViewById(R.id.EnterBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                retrieveToken();
                if (tokens != null) {
                    if(tokens.contains(authToken.getText().toString())) {
                        Toast.makeText(AuthActivity.this, "Success", Toast.LENGTH_SHORT)
                                .show();
                        sharedPreferences = getSharedPreferences("mhonam.token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("status", "true");
                        editor.putString("token", authToken.getText().toString());
                        editor.commit();
                        Log.d(TAG, "onClick: success "  + tokens.get(0));

                        Intent intent = new Intent(AuthActivity.this, ClueActivity.class);
                        intent.putExtra("token", authToken.getText().toString());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(AuthActivity.this, "Invalid Token, recheck your token",
                                Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onClick: failed " );

                    }
                }
                else {
                    Toast.makeText(AuthActivity.this, "Invalid Token, recheck your token",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: failed " );

                }
            }
        });
    }

    private void retrieveToken() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("access_tokens")
                .orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 tokens = (ArrayList<String>) dataSnapshot.getValue();

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
