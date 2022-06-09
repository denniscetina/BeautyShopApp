package com.example.beautyshopapp.ui.gallery;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beautyshopapp.R;
import com.example.beautyshopapp.ui.objetc.Productos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EntryProductActivity extends AppCompatActivity {

    private EditText codigo;
    private EditText nombre;
    private EditText cantidad;
    private EditText precio;
    private EditText proveedor;
    private ImageButton btnSubirProd;
    private StorageReference mStorage;
    private Uri uri,downloadUri;
    private FirebaseStorage storage;
    private StorageReference storageReference, imageRef;
    ProgressDialog progressDialog;
    UploadTask uploadTask;

    public String getUriDownload() {
        return uriDownload;
    }

    public void setUriDownload(String uriDownload) {
        this.uriDownload = uriDownload;
    }

    String uriDownload;

    private ImageView imageViewPro;
    private static final int GALLERY_INTENT = 100;


    private DatabaseReference databaseReference;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_product);


        //FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://beautyshopapp-4ac0a-default-rtdb.firebaseio.com/");
         databaseReference= FirebaseDatabase.getInstance().getReference().child("ProductosE");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


         codigo = findViewById(R.id.editTextCodigo);
         nombre= findViewById(R.id.editTextNombre);
         cantidad = findViewById(R.id.editTextCantidad);
         precio = findViewById(R.id.editTextPrecio);
         proveedor = findViewById(R.id.editTextProveedor);
         btnSubirProd = findViewById(R.id.imageButton);
         mStorage = FirebaseStorage.getInstance().getReference();

         imageViewPro = (ImageView)findViewById(R.id.imageProd);
         imageViewPro.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(Intent.ACTION_PICK);
                 intent.setType("image/*");
                 startActivityForResult(intent,GALLERY_INTENT);
             }
         });


         btnSubirProd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String cod = codigo.getText().toString();
                 String nomb = nombre.getText().toString();
                 String cant = cantidad.getText().toString();
                 String prec = precio.getText().toString();
                 String prov = proveedor.getText().toString();
                 if(!TextUtils.isEmpty(cod)||!TextUtils.isEmpty(nomb)||!TextUtils.isEmpty(cant)
                 ||!TextUtils.isEmpty(prec)||!TextUtils.isEmpty(prov)){
                     uploadData();
                 }

             }
         });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode==RESULT_OK){
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imageViewPro.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Picasso.get().load(uri).into(imageViewPro);

        }
    }
    private void uploadData() {
        String datos1 = codigo.getText().toString();
        String datos2 = nombre.getText().toString();
        String datos3 = cantidad.getText().toString();
        String datos4 = precio.getText().toString();
        String datos5 = proveedor.getText().toString();



        imageRef = storageReference.child("productos/"+datos1+datos2+"."+GetExtension(uri));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Subiendo datos...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        uploadTask = imageRef.putFile(uri);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.incrementProgressBy((int) progress);
            }
        });

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Falló al subir datos",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Éxito al subir datos",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                //Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_SHORT).show();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setUriDownload(uri.toString());

                            String id = databaseReference.push().getKey();
                            Productos producto = new Productos(datos1,datos2,datos3,datos4,datos5,uri.toString());
                            databaseReference.child("Productos").child(datos1).setValue(producto);

                    }
                });

            }
        });


    }

    private String GetExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}