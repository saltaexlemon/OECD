package com.example.registrationandprofile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class changeProfile extends AppCompatActivity {

    private ImageView profilePic;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_picture);

        profilePic = findViewById(R.id.imagechange);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                changePfp();
            }
        });
    }

    private void changePfp() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            changeProfilePic();
        }
    }

    private void changeProfilePic() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image..");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        FirebaseUser currentuserid = FirebaseAuth.getInstance().getCurrentUser();
        final String userpfpid = currentuserid.getUid();
        StorageReference profilepic = storageReference.child("images/" + userpfpid);

        profilepic.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image uploaded.", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception exception){
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to Upload", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent = (100.00 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) progressPercent + "%");
                    }
                });
    }
}
