package com.example.farmerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    public EditText email,pass;
    public Button signup,signin;
    public TextView text;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        text=findViewById(R.id.text);
        pass=findViewById(R.id.password);
        signup=findViewById(R.id.signin);
        signin=findViewById(R.id.signinbutton);
        signup.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT);
                }
                else if (!(e.isEmpty() && p.isEmpty())){
                    firebaseAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Unexpected Error Occured ",Toast.LENGTH_SHORT);
                            }
                            else {
                                Toast.makeText(MainActivity.this,"Sign Up Successful ",Toast.LENGTH_SHORT);
                                startActivity(new Intent(MainActivity.this,MenuActivity.class));

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"Unexpected Error Occured ",Toast.LENGTH_SHORT);
                }

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }


}
