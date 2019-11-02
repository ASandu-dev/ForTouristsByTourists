package com.school.fortouristsbytourists.CatFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.school.fortouristsbytourists.ContentActivity;
import com.school.fortouristsbytourists.ModelAdapter;
import com.school.fortouristsbytourists.ModelClass;
import com.school.fortouristsbytourists.R;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MuseumsFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference myCollection = db.collection("attractions").document("KPCFmFqaRf9GdhlpO45J").collection("museum");
    private View v;

    private ModelAdapter adapter;


    public MuseumsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_model, container, false);
        FirestoreRecyclerOptions<ModelClass> options = new FirestoreRecyclerOptions.Builder<ModelClass>().setQuery(myCollection, ModelClass.class).build();

        adapter = new ModelAdapter(options);

        RecyclerView recyclerView = rootView.findViewById(R.id.cat_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        return rootView ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
        adapter.setOnItemClickListener(new ModelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                ModelClass model = documentSnapshot.toObject(ModelClass.class);
                assert model != null;
                String t = String.valueOf(model.getTitle());
                String i = String.valueOf(model.getImage());
                String d = String.valueOf(model.getFull_desc());
                String l = String.valueOf(model.getLink());
                Intent intent = new Intent(getContext(), ContentActivity.class);
                intent.putExtra("title", t);
                intent.putExtra("image", i);
                intent.putExtra("description", d);
                intent.putExtra("link", l);
                startActivity(intent);



            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
