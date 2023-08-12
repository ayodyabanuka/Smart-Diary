package com.example.smartdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdiary.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {

    Button signup;
    EditText username,email,password;
    TextView signin;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        signin = findViewById(R.id.registerSignin);
        signup = findViewById(R.id.registerSignup);
        username = findViewById(R.id.registerUsername);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);






        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signinIntent = new Intent(Register.this, LoginActivity.class);
                startActivity(signinIntent);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailaddress = email.getText().toString();
                String passwordtext = password.getText().toString();
                String user = username.getText().toString();

                if(user.isEmpty() || passwordtext.isEmpty() || emailaddress.isEmpty()){
                    Toast.makeText(Register.this, "Fill all the data", Toast.LENGTH_SHORT).show();
                } else{
                    Map<String, Object> data = new HashMap<>();
                    data.put("username", user);
                    data.put("email", emailaddress);
                    auth.createUserWithEmailAndPassword(emailaddress, passwordtext)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user12 = auth.getCurrentUser();
                                    db.collection("user")
                                            .document("username")
                                            .collection(user12.getUid())
                                            .add(data)
                                            .addOnSuccessListener(documentReference -> {
                                                Toast.makeText(Register.this, "Registration Success!", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {

                                            });


                                } else {
                                    Toast.makeText(Register.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}