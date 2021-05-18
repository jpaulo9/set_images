package com.example.set_images;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Tela_Search extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();
    private  DatabaseReference getReference = reference.child("DADOS");


    TextView nom;
    private Button selecionarImagem;
    private ImageView  ResultImagem;
    private EditText  searchI;
    private  Local local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__search);


        selecionarImagem = (Button) findViewById(R.id.btn_selecte);
        ResultImagem = (ImageView) findViewById(R.id.img_select);
        nom = (TextView) findViewById(R.id.text_nome);

        searchI = (EditText) findViewById(R.id.edt_pesqui);

        selecionarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PesquisarImagem();
//              onStart();
            }
        });
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        String campo = "rio";
//
//
//        getReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//              String fotos = dataSnapshot.getValue(String.class);
//              Picasso.with(Tela_Search.this).load(fotos).into(ResultImagem);
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


    private void PesquisarImagem (){


   String texto = searchI.getText().toString().trim();


        Query search = FirebaseDatabase.getInstance().getReference("DADOS").orderByChild("nome").equalTo(texto);

        search.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Local locaisSearch = snapshot.getValue(Local.class);
                    local = locaisSearch;
                }

                if (!local.getFoto().trim().equals("") ||local != null){

                    Picasso.with(Tela_Search.this).load(local.getFoto()).into(ResultImagem);
                    nom.setText(local.getNome());
                    Toast.makeText(Tela_Search.this,"Achado "+local.getFoto(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
