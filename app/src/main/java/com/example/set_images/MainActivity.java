package com.example.set_images;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Uri filePath;
    private Button salvarDados;
    private ImageView visualizar;
    private Spinner selecao;
    private EditText nomeLocal, endereco;

    ArrayAdapter <String> listaCategorias;
    int localCat = 0;

    String categorias [] = {"Lanche","Restaurante","Pizzaria","Bar","Saúde","Lazer","Hotel","Aluguel"};
    private final int PICK_IMAGE_REQUEST = 71;


    StorageReference reference;

    DatabaseReference tabela;
    private TextView px;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabela = FirebaseDatabase.getInstance().getReference("DADOS");

        reference = FirebaseStorage.getInstance().getReference("DADOS");



        nomeLocal = (EditText) findViewById(R.id.edt_nome);
        endereco = (EditText) findViewById(R.id.edt_end);

        salvarDados = (Button) findViewById(R.id.btn_download);
        visualizar = (ImageView)findViewById(R.id.img_view);
        px = (TextView) findViewById(R.id.textView3);


        selecao = (Spinner) findViewById(R.id.sp_cat);

        listaCategorias = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, categorias);
        selecao.setAdapter(listaCategorias);




        selecao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {

                    case 0:
                        localCat = 1;
                        break;
                    case 1:
                        localCat = 2;
                        break;
                    case 2:
                        localCat = 3;
                        break;
                    case 3:
                        localCat = 4;
                        break;
                    case 4:
                        localCat = 5;
                        break;
                    case 5:
                        localCat = 6;
                        break;
                    case 6:
                        localCat = 7;
                        break;
                    case 7:
                        localCat = 8;
                        break;


                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        visualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarregarImagem();
            }
        });


        salvarDados.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                SalvarIamgem();
        }
    });

        px.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Tela_Search.class);
                startActivity(intent);
            }
        });



//        Tela

    }


            private void CarregarImagem(){

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);


            }


            private String getExtension(Uri uri){
                ContentResolver cr = getContentResolver();
                MimeTypeMap map = MimeTypeMap.getSingleton();

                return map.getExtensionFromMimeType(cr.getType(uri));

            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null){


            filePath = data.getData();


                Picasso.with(MainActivity.this).load(filePath).into(visualizar);






        }
    }





    private void SalvarIamgem(){

        final String nomes = nomeLocal.getText().toString().trim();
        final String ender = endereco.getText().toString().trim();
            if (filePath != null){


                final StorageReference ref =
                        reference.child(System.currentTimeMillis()+"."+getExtension(filePath));



                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Local itemLocal = new Local(nomes, localCat,ender, taskSnapshot.getDownloadUrl().toString());

                        String id = tabela.push().getKey();
                        tabela.child(id).setValue(itemLocal);
         Toast.makeText(MainActivity.this, "Salvo "+ taskSnapshot.getDownloadUrl().toString(),
                              Toast.LENGTH_SHORT).show();
                  }
              })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,
                                        "Falha ao Salvar imagem "+e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        });


            }



    }







}
