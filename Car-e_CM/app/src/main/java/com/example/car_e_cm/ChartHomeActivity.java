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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChartHomeActivity extends AppCompatActivity {
    RecyclerView mChart_list;
    CBidangAdapter cbidangAdapter;
    List<CBidang> cbidangList;
    FirebaseFirestore mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_home);

        mDatabase   = FirebaseFirestore.getInstance();
        mAuth       = FirebaseAuth.getInstance();

        cbidangList = new ArrayList<>();
        cbidangAdapter = new CBidangAdapter(getApplicationContext(), cbidangList);

        mChart_list    = findViewById(R.id.chartRecycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mChart_list.setHasFixedSize(true);
        mChart_list.setLayoutManager(manager);
        mChart_list.setAdapter(cbidangAdapter);

        UpdateCBidang(mDatabase);
    }

    private void UpdateCBidang(FirebaseFirestore mDatabase) {
        if (cbidangList.size() > 0){
            cbidangList.clear();
        }

        mDatabase.collection("Bidang").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Log.d("NotaBidangActivity", "Error : "+e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED ) {
                        String bidang_id = doc.getDocument().getId();
                        CBidang cbidang = doc.getDocument().toObject( CBidang.class).withId(bidang_id);

                        cbidangList.add(cbidang);
                        cbidangAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
