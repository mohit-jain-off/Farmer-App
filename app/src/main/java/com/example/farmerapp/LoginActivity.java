package com.example.farmerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.DragStartHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public EditText email,pass;
    public Button signin;
    public TextView text;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        text=findViewById(R.id.text);
        pass=findViewById(R.id.password);
        signin=findViewById(R.id.signin);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Toast.makeText(LoginActivity.this,"You are Logged in !!",Toast.LENGTH_SHORT);
                    Intent i=new Intent(LoginActivity.this,MenuActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Try again!",Toast.LENGTH_SHORT);
                }
            }
        };
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e=email.getText().toString();
                String p=pass.getText().toString();
                if (e.isEmpty()){
                    email.setError("Please Enter Email Id");
                    email.requestFocus();
                }
                else if (p.isEmpty()){
                    pass.setError("Please Enter Password");
                    pass.requestFocus();
                }
                else if (e.isEmpty() && p.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields are empty",Toast.LENGTH_SHORT);
                }
                else if (!(e.isEmpty() && p.isEmpty())){
                    firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Unexpected Error Occured ",Toast.LENGTH_SHORT);
                            }
                            else {
                                startActivity(new Intent(LoginActivity.this,MenuActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Unexpected Error Occured ",Toast.LENGTH_SHORT);
                }
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
