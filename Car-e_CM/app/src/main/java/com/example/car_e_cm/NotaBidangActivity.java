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

public class NotaBidangActivity extends AppCompatActivity {
    RecyclerView mBidang_list;
    BidangAdapter bidangAdapter;
    List<Bidang> bidangList;
    FirebaseFirestore mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_bidang);

        mDatabase   = FirebaseFirestore.getInstance();
        mAuth       = FirebaseAuth.getInstance();

        bidangList = new ArrayList<>();
        bidangAdapter = new BidangAdapter(getApplicationContext(), bidangList);

        mBidang_list    = findViewById(R.id.bidang_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mBidang_list.setHasFixedSize(true);
        mBidang_list.setLayoutManager(manager);
        mBidang_list.setAdapter(bidangAdapter);

        UpdateBidang(mDatabase);
    }

    private void UpdateBidang(FirebaseFirestore mDatabase) {
        if (bidangList.size() > 0){
            bidangList.clear();
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
                        Bidang bidang = doc.getDocument().toObject( Bidang.class).withId(bidang_id);

                        bidangList.add(bidang);
                        bidangAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
}
