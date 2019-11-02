package com.school.fortouristsbytourists;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends FirestoreRecyclerAdapter<ReviewClass, ReviewsAdapter.ModelHolder>{


    private static final String TAG = "";
    View v;

    public ReviewsAdapter(@NonNull FirestoreRecyclerOptions<ReviewClass> commentary) {
        super(commentary);
    }

    @Override
    protected void onBindViewHolder(@NonNull ModelHolder modelHolder, int i, @NonNull ReviewClass reviewClass) {

        modelHolder.name.setText(reviewClass.getName());
        modelHolder.comment.setText(reviewClass.getComment());
        modelHolder.date.setText(reviewClass.getDate());

    }

    @NonNull
    @Override
    public ModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review_layout, parent, false);
        return new ModelHolder(v);
    }

    class ModelHolder extends RecyclerView.ViewHolder{

        TextView name, comment, date;

        public ModelHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name_tv);
            comment = itemView.findViewById(R.id.comment_tv);
            date = itemView.findViewById(R.id.date_tv);

        }
    }
}
