package com.example.car_e_cm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CBidangAdapter extends RecyclerView.Adapter<CBidangAdapter.ViewHolder> {
    Context context;
    List<CBidang> cbidangList;
    FirebaseFirestore mDatabase;
    FirebaseAuth mAuth;

    public CBidangAdapter(Context context, List<CBidang> cbidangList) {
        this.context = context;
        this.cbidangList = cbidangList;
    }
    @NonNull
    @Override
    public CBidangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.list_bidang, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CBidangAdapter.ViewHolder holder, int position) {
        holder.mNama.setText(cbidangList.get(position).getNama());

        final String NamaBidang = cbidangList.get(position).getNama();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent a = new Intent(context, ProyekBidangActivity.class);
//                a.putExtra("NamaBidang", NamaBidang);
//                a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(a);
            }
        });
        UpdateBidang();
    }

    private void UpdateBidang() {
        mDatabase = FirebaseFirestore.getInstance();

        mDatabase.collection("Bidang").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Log.d("BidangAdapter", "Error :"+e.getMessage());
                }
                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() != DocumentChange.Type.ADDED && doc.getType() != DocumentChange.Type.MODIFIED && doc.getType() != DocumentChange.Type.REMOVED ) {
                        String bidang_id = doc.getDocument().getId();
                        CBidang cbidang = doc.getDocument().toObject( CBidang.class).withId(bidang_id);
                        if (cbidangList.size() > 0){
                            cbidangList.clear();
                        }
                        cbidangList.add(cbidang);
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cbidangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mNama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            mNama   = mView.findViewById(R.id.namaBidang);
        }
    }
}
