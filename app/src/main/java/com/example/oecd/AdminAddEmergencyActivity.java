package com.example.oecd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class AdminAddEmergencyActivity extends AppCompatActivity {

    Button upload;
    EditText txtName,txtOfficeNumber,txtContactNumber,txtAddress,txtState,txtWorkingDay,txtWorkingHour,txtDes;
    ImageView imageView;
    String [] state = {"Select State","Kuala Lumpur","Labuan","Putrajaya","Johor","Kedah",
            "Kelantan","Melaka","Negeri Sembilan","Pahang","Perak","Perlis","Penang","Sabah"
            ,"Sarawak","Selangor","Terengganu"};
    DatabaseReference ref;
    ProgressDialog progressDialog;
    Uri image_uri = null ;
    private static final  int GALLERY_IMAGE_CODE = 100 ;
    private static final  int CAMERA_IMAGE_CODE = 200 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_emergency);

        Toolbar toolbar = findViewById(R.id.AddEmergencyPageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        upload = findViewById(R.id.add_BtnSubmit);
        txtName = findViewById(R.id.add_etName);
        txtOfficeNumber = findViewById(R.id.add_etOfficeNumber);
        txtContactNumber = findViewById(R.id.add_etContactNumber);
        txtAddress = findViewById(R.id.add_etAddress);
        txtState = findViewById(R.id.add_etState);
        txtWorkingDay = findViewById(R.id.add_etWorkingDay);
        txtWorkingHour = findViewById(R.id.add_etWorkingHour);
        txtDes = findViewById(R.id.add_etDescription);
        imageView = findViewById(R.id.add_ImageView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminAddEmergencyActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,state);
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals("Select State")){
                    //empty
                }
                else {
                    String value = adapterView.getItemAtPosition(i).toString();
                    txtState.setText(value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ApName = txtName.getText().toString().trim();
                String ApOfficeNumber = txtOfficeNumber.getText().toString().trim();
                String ApContactNumber = txtContactNumber.getText().toString().trim();
                String ApAddress = txtAddress.getText().toString().trim();
                String ApState = txtState.getText().toString().trim();
                String ApWorkingDay = txtWorkingDay.getText().toString().trim();
                String ApWorkingHour = txtWorkingHour.getText().toString().trim();
                String ApDescription = txtDes.getText().toString().trim();

                if (TextUtils.isEmpty(ApName)){
                    txtName.setError("Please Enter Store Name");
                    txtName.requestFocus();
                } else if (TextUtils.isEmpty(ApOfficeNumber)){
                    txtOfficeNumber.setError("Please Enter Office Number");
                    txtOfficeNumber.requestFocus();
                } else if (TextUtils.isEmpty(ApContactNumber)){
                    txtContactNumber.setError("Please Enter Contact Number");
                    txtContactNumber.requestFocus();
                }else if (TextUtils.isEmpty(ApAddress)){
                    txtAddress.setError("Please Enter Address");
                    txtAddress.requestFocus();
                }else if (TextUtils.isEmpty(ApState)){
                    txtState.setError("Please Select State");
                    txtState.requestFocus();
                }else if (TextUtils.isEmpty(ApWorkingDay)){
                    txtWorkingDay.setError("Please Enter Working Day Details");
                    txtWorkingDay.requestFocus();
                }else if (TextUtils.isEmpty(ApWorkingHour)){
                    txtWorkingHour.setError("Please Enter Working Hour Details");
                    txtWorkingHour.requestFocus();
                }else if (TextUtils.isEmpty(ApDescription)){
                    txtDes.setError("Please Enter Description Details");
                    txtDes.requestFocus();
                }
                else {
                    uploadData(ApName,ApOfficeNumber,ApContactNumber,ApAddress,ApState,ApWorkingDay,ApWorkingHour,ApDescription);
                }

            }
        });

    }

    private void uploadData(String apName, String apOfficeNumber, String apContactNumber, String apAddress, String apState,
                            String apWorkingDay, String apWorkingHour, String apDescription) {

        progressDialog.setMessage("Saving Info...");
        progressDialog.show();
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        String filepath = "Emergency/"+"emergency"+timeStamp;

        if (imageView.getDrawable() != null){
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG , 100 , baos);
            byte[] data = baos.toByteArray();

            StorageReference reference = FirebaseStorage.getInstance().getReference().child(filepath);
            reference.putBytes(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                            while (!uriTask.isSuccessful());

                            String downloadUri = uriTask.getResult().toString();

                            if (uriTask.isSuccessful()){

                                HashMap<String , Object> hashMap = new HashMap<>();
                                hashMap.put("ApFid" , timeStamp);
                                hashMap.put("ApName" , apName);
                                hashMap.put("ApOfficeNumber" , apOfficeNumber);
                                hashMap.put("ApContactNumber" , apContactNumber);
                                hashMap.put("ApAddress" , apAddress);
                                hashMap.put("ApState" , apState);
                                hashMap.put("ApWorkingDay" , apWorkingDay);
                                hashMap.put("ApWorkingHour" , apWorkingHour);
                                hashMap.put("ApDescription" , apDescription);
                                hashMap.put("ApImage" , downloadUri);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Emergency");
                                ref.child(timeStamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();;
                                                Toast.makeText(AdminAddEmergencyActivity.this, "Info Has Been Uploaded", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(AdminAddEmergencyActivity.this, AdminViewEmergencyActivity.class));
                                                finish();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(AdminAddEmergencyActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminAddEmergencyActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }


    }

    private void imagePickDialog() {

        String[] options = {"Camera" , "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose image from");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    cameraPick();
                }
                if (which == 1){
                    galleryPick();

                }
            }
        });

        builder.create().show();

    }

    private void cameraPick() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE , "Temp Pick");
        contentValues.put(MediaStore.Images.Media.TITLE , "Temp desc");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT , image_uri);
        startActivityForResult(intent , CAMERA_IMAGE_CODE);
    }

    private void galleryPick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent , GALLERY_IMAGE_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == GALLERY_IMAGE_CODE){
                image_uri = data.getData();
                imageView.setImageURI(image_uri);
            }
            if (requestCode == CAMERA_IMAGE_CODE){
                imageView.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}