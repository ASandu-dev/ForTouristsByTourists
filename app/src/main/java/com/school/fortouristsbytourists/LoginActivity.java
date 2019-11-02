package com.school.fortouristsbytourists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="" ;
    EditText email, password;
    Button login_btn;
    TextView register_screen_btn, guest_btn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent i = new Intent(LoginActivity.this, CategoriesActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        login_btn = findViewById(R.id.btn_login);
        register_screen_btn = findViewById(R.id.link_signup);
        guest_btn = findViewById(R.id.link_guest);
        //


        //Buttons actions

        register_screen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        guest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CategoriesActivity.class));
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLoggedIn();
            }
        });

    }
    //Methods
    public void userLoggedIn(){

        //String fullName = name.getText().toString().trim();
        String eMail = email.getText().toString().trim();
        String passWord = password.getText().toString().trim();


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


            mAuth.signInWithEmailAndPassword(eMail, passWord)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(LoginActivity.this, CategoriesActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Email or password incorrect",
                                        Toast.LENGTH_SHORT).show();


                            }

                            // ...
                        }
                    });
        }catch (Exception e) {

            // This will catch any exception, because they are all descended from Exception


            Toast toast = Toast.makeText(this, "Sorry! You need to create an account first!", Toast.LENGTH_LONG);
            View view = toast.getView();
            view.setBackgroundResource(R.drawable.toast_error);
            toast.show();

        }
    }




    //
}
