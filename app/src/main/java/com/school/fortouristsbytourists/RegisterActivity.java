package com.school.fortouristsbytourists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "";
    EditText name,email, password;
    Button register_btn;
    TextView login_screen_btn, guest_btn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Make app fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //
        //input out of focus
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //

        //Initialize Auth

        mAuth = FirebaseAuth.getInstance();
        //

        //Initialize UI elements
        name = findViewById(R.id.input_name);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        register_btn = findViewById(R.id.btn_register);
        login_screen_btn = findViewById(R.id.link_login);
        guest_btn = findViewById(R.id.link_guest);
        //


        //Buttons actions

        login_screen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        guest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, CategoriesActivity.class));
            }
        });
        //
    }

    //Methods

    public void registerUser(){
        final String fullName = name.getText().toString().trim();
        final String eMail = email.getText().toString().trim();
        String passWord = password.getText().toString().trim();

        if (fullName.isEmpty()){
            name.setError("Full name must be entered!");
        }
        if (passWord.isEmpty()){
            password.setError("Password must be entered!");
        }
        if (passWord.length()<6){
            password.setError("Password must be at least 6 characters long!");
        }
        if (eMail.isEmpty()){
            email.setError("Email must be entered!");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(eMail).matches()){
            email.setError("Email must be entered!");
        }

        try {


            mAuth.createUserWithEmailAndPassword(eMail, passWord)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //***

                                // Create a new user with a first and last name
                                HashMap<String, String> newUser = new HashMap<>();
                                newUser.put("userId", user.getUid());
                                newUser.put("name", fullName);
                                newUser.put("email", eMail);

                                // Add a new document with a generated ID
                                db.collection("users").document(user.getUid()).set(newUser);


                                //
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Sorry! You need to add details first!", Toast.LENGTH_LONG);
            name.setFocusable(true);
            View view = toast.getView();
            view.setBackgroundResource(R.drawable.toast_error);
            toast.show();
        }

    }
}
