package com.school.fortouristsbytourists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.school.fortouristsbytourists.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ModelAdapter extends FirestoreRecyclerAdapter<ModelClass, ModelAdapter.ModelHolder> {

    private OnItemClickListener listener;
    private static final String TAG = "";
    Context context;
    View v;


    public ModelAdapter(@NonNull FirestoreRecyclerOptions<ModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ModelHolder modelHolder, int position, @NonNull ModelClass model) {

        modelHolder.title_tv.setText(String.valueOf(model.getTitle()));
        Picasso.get().load(model.getImage()).into(modelHolder.image_iv);
        modelHolder.description_tv.setText(model.getFull_desc());
        modelHolder.link_tv.setText(model.getLink());
    }



    @NonNull
    @Override
    public ModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout, parent, false);

        return new ModelHolder(v);
    }

    class ModelHolder extends RecyclerView.ViewHolder {

        TextView title_tv, link_tv;
        TextView description_tv;
        ImageView image_iv;



        public ModelHolder(@NonNull final View itemView) {
            super(itemView);

            title_tv = itemView.findViewById(R.id.title);
            image_iv = itemView.findViewById(R.id.image);
            description_tv = itemView.findViewById(R.id.description_tv);
            link_tv = itemView.findViewById(R.id.link_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }




                }
            });


        }

    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);


    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
