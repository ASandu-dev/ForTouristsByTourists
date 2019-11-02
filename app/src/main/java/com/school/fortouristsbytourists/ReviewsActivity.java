package com.school.fortouristsbytourists;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReviewsActivity extends AppCompatActivity {
    private static final String TAG = "";
    Toolbar toolbar;
    public String name, email, formattedDate, UserId;
    int ID = 0;
    FloatingActionButton add_btn;
    private ReviewsAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference myReviews = db.collection("/attractions/KPCFmFqaRf9GdhlpO45J/site/Eye of London/comment");
    private FirebaseAuth mAuth;
    private View v;
    private FirebaseFirestore mFirestore;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        //Make app fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //
        mAuth = FirebaseAuth.getInstance();
        //UI init
        add_btn = findViewById(R.id.add_review_btn);

        //

        //Auth User details
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {


            final String uid = user.getUid();
            //Getting user name from fire store
            mAuth = FirebaseAuth.getInstance();
            UserId = mAuth.getCurrentUser().getUid();


            mFirestore = FirebaseFirestore.getInstance();
            mFirestore.collection("users").document(UserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    name = documentSnapshot.getString("name");

                }
            });

            //Display add button if user is registered
               add_btn.setVisibility(View.VISIBLE);
            //



        }else {
            add_btn.setVisibility(View.GONE);
        }
        //

        //Get system date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);
        //
        //RecyclerView for comments
        Query query = (Query) myReviews.orderBy("ID", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ReviewClass> commentary = new FirestoreRecyclerOptions.Builder<ReviewClass>().setQuery(query, ReviewClass.class).build();

        RecyclerView recyclerView = findViewById(R.id.comment_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ReviewsAdapter(commentary);
        recyclerView.setAdapter(adapter);


        //
        //ToolBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        //



        //Btn Actions

        add_btn.setOnClickListener(new View.OnClickListener() {
            private String TAG;

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReviewsActivity.this);
                builder.setTitle("Add a comment");

                        // Set up the input

                final EditText comment = new EditText(ReviewsActivity.this);
                        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text

                comment.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(comment);

                        // Set up the buttons

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = comment.getText().toString();
                        // Create a new user with a first and last name
                        Map<String, Object> comment = new HashMap<>();
                        comment.put("ID", ID++);
                        comment.put("name", String.valueOf(name));
                        comment.put("comment", String.valueOf(m_Text));
                        comment.put("date", String.valueOf(formattedDate));


                        // Add a new document with a generated ID

                        db.collection("/attractions/KPCFmFqaRf9GdhlpO45J/site/Eye of London/comment")
                                .add(comment)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void incrementID(){
       if (ID != 0){

           ID++;

       }
    }
}

