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

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProyekAdapter extends RecyclerView.Adapter<ProyekAdapter.ViewHolder> {
    Context context;
    List<Proyek> proyekList;
    FirebaseFirestore mDatabase;

    public ProyekAdapter(Context context, List<Proyek> proyekList) {
        this.context = context;
        this.proyekList = proyekList;
    }

    @NonNull
    @Override
    public ProyekAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.list_proyek, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ProyekAdapter.ViewHolder holder, int position) {
        holder.mNamaProyek.setText(proyekList.get(position).getNama());

        final String NamaBidang = proyekList.get(position).getBidang();
        final String NamaProyek = proyekList.get(position).getNama();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(context, NotaProyekActivity.class);
                a.putExtra("NamaBidang", NamaBidang);
                a.putExtra("NamaProyek", NamaProyek);
                a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(a);
            }
        });
        RefreshProyek(NamaBidang);
    }

    private void RefreshProyek(String namaBidang) {
        mDatabase   = FirebaseFirestore.getInstance();

        Query q = mDatabase.collection("Proyek").whereEqualTo("bidang", namaBidang)
                .whereEqualTo("status", "Ongoing");

        q.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d("ProyekAdapter", "Error : "+e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() != DocumentChange.Type.ADDED && doc.getType() != DocumentChange.Type.MODIFIED && doc.getType() != DocumentChange.Type.REMOVED ) {
                        String proyek_id = doc.getDocument().getId();
                        Proyek proyek = doc.getDocument().toObject( Proyek.class).withId(proyek_id);
                        if (proyekList.size() > 0){
                            proyekList.clear();
                        }
                        proyekList.add(proyek);
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return proyekList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mNamaProyek;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            mNamaProyek = mView.findViewById(R.id.namaProyek);
        }
    }
}
