package com.saumya.pgtask.Tenant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.saumya.pgtask.R;

import java.io.IOException;
import java.util.UUID;

public class UploadPicture extends AppCompatActivity {

    private ImageView ProfilePicture ;
    private Uri filePath;
    private  ImageView ChoosePicture;
    Button btnUpload;
    FirebaseStorage storage;
    StorageReference storageReference;
    private  final int PICK_IMAGE_REQUEST=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);
        ProfilePicture=findViewById(R.id.imgProfile);
        ChoosePicture=findViewById(R.id.imgChoose);
        btnUpload=findViewById(R.id.btnSavePic);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        ChoosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               uploadImage();
            }
        });
    }

    private void ChooseImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData() != null){
            filePath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                ProfilePicture.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    private void uploadImage(){
     if (filePath!=null){
         final ProgressDialog progressDialog=new ProgressDialog(this);
         progressDialog.setTitle("Uploading...");
         progressDialog.show();

         StorageReference ref=storageReference.child("images/"+ UUID.randomUUID().toString());
         ref.putFile(filePath)
                 .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                         Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         progressDialog.dismiss();
                         Toast.makeText(getApplicationContext(), "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 })
                 .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                         double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                 .getTotalByteCount());
                         progressDialog.setMessage("Uploaded "+(int)progress+"%");
                     }
                 });
     }
    }
}
