package com.school.fortouristsbytourists;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ContentActivity extends AppCompatActivity {


    private static final String TAG = "";
    String l;
    TextView title_tv_con, description_tv_con;
    ImageView image_tv_con;
    Button review_btn, book_btn;
    Toolbar toolbar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        //Make app fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //

        String t = getIntent().getStringExtra("title");
        String i = getIntent().getStringExtra("image");
        String d = getIntent().getStringExtra("description");
        l = getIntent().getStringExtra("link");

        //
        mAuth = FirebaseAuth.getInstance();

        //Content items initialise
        title_tv_con = findViewById(R.id.title_tv_cont);
        image_tv_con = findViewById(R.id.image_iv);
        description_tv_con = findViewById(R.id.description_tv_cont);
        review_btn = findViewById(R.id.review_btn);
        book_btn = findViewById(R.id.book_btn);

        //ToolBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_btn));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContentActivity.this, CategoriesActivity.class);
                startActivity(i);
            }
        });

        //


        //Content fillers
        title_tv_con.setText(String.valueOf(t));
        Picasso.get().load(String.valueOf(i)).into(image_tv_con);
        description_tv_con.setText(String.valueOf(d));
        //

        //Btn actions
        review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i =   new Intent(ContentActivity.this,ReviewsActivity.class);
              startActivity(i);

            }
        });

        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(l)));
                startActivity(browserIntent);
            }
        });
        //Booking visibility according to user status
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
           book_btn.setVisibility(View.VISIBLE);
        }else {
            book_btn.setVisibility(View.GONE);
        }

        //




    }


}
