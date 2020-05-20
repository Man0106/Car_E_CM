package com.example.car_e_cm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NotaProyekActivity extends AppCompatActivity {
    FirebaseFirestore mDatabase;
    FirebaseAuth mAuth;
    EditText Nota;
    Button Submit;
    TextView judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_proyek);

        mDatabase = FirebaseFirestore.getInstance();
        mAuth       = FirebaseAuth.getInstance();

        Nota    = findViewById(R.id.edtNota);
        Submit  = findViewById(R.id.submit);
        judul   = findViewById(R.id.te);

        final String NamaBidang = getIntent().getStringExtra("NamaBidang");
        final String NamaProyek = getIntent().getStringExtra("NamaProyek");

        judul.setText("Pengeluaran untuk proyek "+NamaProyek);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mNota = Nota.getText().toString();

                if (mNota.isEmpty()){
                    Toast.makeText(getBaseContext(), "Harap diisi terlebih dahulu", Toast.LENGTH_LONG).show();
                }

                Map<String, Object> rkp = new HashMap<>();
                rkp.put("Dana", mNota);
                rkp.put("Nama_Proyek", NamaProyek);
                rkp.put("Nama_Bidang", NamaBidang);
                mDatabase.collection("Rekap").add(rkp).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getBaseContext(), "Data berhasil dimasukan", Toast.LENGTH_LONG).show();
                        Intent a = new Intent(NotaProyekActivity.this, DashboardActivity.class);
                        startActivity(a);
                    }
                });
            }
        });
    }
}
