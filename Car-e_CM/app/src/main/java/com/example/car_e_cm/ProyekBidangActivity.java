package com.example.car_e_cm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProyekBidangActivity extends AppCompatActivity {
    RecyclerView mProyek_list;
    ProyekAdapter proyekAdapter;
    List<Proyek> proyekList;
    FirebaseFirestore mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyek_bidang);

        mDatabase   = FirebaseFirestore.getInstance();
        mAuth       = FirebaseAuth.getInstance();

        proyekList = new ArrayList<>();
        proyekAdapter = new ProyekAdapter(getApplicationContext(), proyekList);

        mProyek_list    = findViewById(R.id.proyek_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mProyek_list.setHasFixedSize(true);
        mProyek_list.setLayoutManager(manager);
        mProyek_list.setAdapter(proyekAdapter);

        String NamaBidang = getIntent().getStringExtra("NamaBidang");

        UpdateProyek(mDatabase, NamaBidang);
    }

    private void UpdateProyek(FirebaseFirestore mDatabase, String namaBidang) {
        if (proyekList.size() > 0){
            proyekList.clear();
        }
        Query q = mDatabase.collection("Proyek").whereEqualTo("bidang", namaBidang)
                .whereEqualTo("status", "Ongoing");
        q.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d("ProyekBidangActivity", "Error : "+e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED ) {
                        String proyek_id = doc.getDocument().getId();
                        Proyek proyek = doc.getDocument().toObject( Proyek.class).withId(proyek_id);

                        proyekList.add(proyek);
                        proyekAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
