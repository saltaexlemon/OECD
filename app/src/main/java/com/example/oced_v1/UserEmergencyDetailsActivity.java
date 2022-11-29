package com.example.oced_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class UserEmergencyDetailsActivity extends AppCompatActivity {

    ImageView back;
    EditText txtName,txtOfficeNumber,txtContactNumber,txtAddress,txtState,txtWorkingDay,txtWorkingHour,txtEmail;
    Button Submit;
    EditText txtUserName,txtUserAddress,txtUserState,txtUserContactNumber,txtUserEmail;
    ImageView imageView;
    DatabaseReference ref;
    Button whatsApp,map,call;
    FirebaseDatabase firebaseDatabase;
    ImageView imageView2;
    ProgressDialog progressDialog;
    FirebaseAuth auth ;
    Uri image_uri = null ;
    private static final  int GALLERY_IMAGE_CODE = 100 ;
    private static final  int CAMERA_IMAGE_CODE = 200 ;
    String [] state = {"Select State","Kuala Lumpur","Labuan","Putrajaya","Johor","Kedah",
            "Kelantan","Melaka","Negeri Sembilan","Pahang","Perak","Perlis","Penang","Sabah"
            ,"Sarawak","Selangor","Terengganu"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_emergency_details);

        back = findViewById(R.id.back_DetailsEmergency);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtName = findViewById(R.id.add_etName);
        txtOfficeNumber = findViewById(R.id.add_etOfficeNumber);
        txtContactNumber = findViewById(R.id.add_etContactNumber);
        txtAddress = findViewById(R.id.add_etAddress);
        txtState = findViewById(R.id.add_etState);
        txtWorkingDay = findViewById(R.id.add_etWorkingDay);
        txtWorkingHour = findViewById(R.id.add_etWorkingHour);
        txtEmail = findViewById(R.id.add_etEmail);
        imageView = findViewById(R.id.add_ImageView);
        //User

        Submit = findViewById(R.id.add_BtnSubmit);
        txtUserName = findViewById(R.id.request_etName);
        txtUserAddress = findViewById(R.id.request_etAddress);
        txtUserState = findViewById(R.id.request_etState);
        txtUserContactNumber = findViewById(R.id.request_etContactNumber);
        txtUserEmail = findViewById(R.id.request_etEmail);
        imageView2 = findViewById(R.id.request_ImageView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String emailName = user.getEmail();
        txtUserEmail.setText(emailName);

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserEmergencyDetailsActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,state);
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
                    txtUserState.setText(value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String postFID = getIntent().getExtras().getString("ApFid");

        ref = FirebaseDatabase.getInstance().getReference("Emergency")
                .child(postFID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String pImage = dataSnapshot.child("ApImage").getValue().toString();
                    Glide.with(UserEmergencyDetailsActivity.this).load(pImage).into(imageView);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeName = dataSnapshot.child("ApName").getValue().toString();
                    txtName.setText(DeName);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeApOfficeNumber = dataSnapshot.child("ApOfficeNumber").getValue().toString();
                    txtOfficeNumber.setText(DeApOfficeNumber);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeContact = dataSnapshot.child("ApContactNumber").getValue().toString();
                    txtContactNumber.setText(DeContact);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeAddress = dataSnapshot.child("ApAddress").getValue().toString();
                    txtAddress.setText(DeAddress);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeApState = dataSnapshot.child("ApState").getValue().toString();
                    txtState.setText(DeApState);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeApWorkingDay= dataSnapshot.child("ApWorkingDay").getValue().toString();
                    txtWorkingDay.setText(DeApWorkingDay);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeApWorkingHour = dataSnapshot.child("ApWorkingHour").getValue().toString();
                    txtWorkingHour.setText(DeApWorkingHour);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeApEmail = dataSnapshot.child("ApEmail").getValue().toString();
                    txtEmail.setText(DeApEmail);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //user
        ref = FirebaseDatabase.getInstance().getReference("Profile")
                .child(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try{
                    String prName = snapshot.child("prName").getValue().toString();
                    txtUserName.setText(prName);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try{
                    String prContactNumber = snapshot.child("prContactNumber").getValue().toString();
                    txtUserContactNumber.setText(prContactNumber);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try{
                    String prAddress = snapshot.child("prAddress").getValue().toString();
                    txtUserAddress.setText(prAddress);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try{
                    String prState = snapshot.child("prState").getValue().toString();
                    txtUserState.setText(prState);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try{
                    String prEmail = snapshot.child("uEmail").getValue().toString();
                    txtUserEmail.setText(prEmail);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ApName = txtUserName.getText().toString().trim();
                String ApAddress = txtUserAddress.getText().toString().trim();
                String ApContactNumber = txtUserContactNumber.getText().toString().trim();
                String ApEmail = txtUserEmail.getText().toString().trim();
                String ApState = txtUserState.getText().toString().trim();
                String ApUi = user.getUid();
                String ApCompanyEmail = txtEmail.getText().toString().trim();

                if (TextUtils.isEmpty(ApName)){
                    txtUserName.setError("Please Enter Your Name");
                    txtUserName.requestFocus();
                } else if (TextUtils.isEmpty(ApContactNumber)){
                    txtUserContactNumber.setError("Please Enter Your Contact Number");
                    txtUserContactNumber.requestFocus();
                } else if (TextUtils.isEmpty(ApAddress)){
                    txtUserAddress.setError("Please Enter Your Address");
                    txtUserAddress.requestFocus();
                }else if (TextUtils.isEmpty(ApState)){
                    txtUserState.setError("Please Select State");
                    txtUserState.requestFocus();
                }
                else {
                    uploadData(ApName,ApAddress,ApContactNumber,ApEmail,ApState,ApUi,ApCompanyEmail);
                }
            }
        });

    }

    private void uploadData(String apName, String apAddress, String apContactNumber, String apEmail, String apState, String apUi, String apCompanyEmail) {

        progressDialog.setMessage("Saving Request Info...");
        progressDialog.show();
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        String filepath = "Request/"+"request"+timeStamp;

        if (imageView2.getDrawable() != null){
            Bitmap bitmap = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
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

                                String status="Pending";
                                String remark="";
                                String CompanyStatus=apCompanyEmail+status;

                                String StatusUid = apUi + status;
                                HashMap<String , Object> hashMap = new HashMap<>();

                                hashMap.put("ApUid" , apUi);
                                hashMap.put("ApEmail" , apEmail);
                                hashMap.put("ApFid" , timeStamp);
                                hashMap.put("ApName" , apName);
                                hashMap.put("ApContactNumber" , apContactNumber);
                                hashMap.put("ApAddress" , apAddress);
                                hashMap.put("ApState" , apState);
                                hashMap.put("ApCompanyEmail" , apCompanyEmail);
                                hashMap.put("ApImage" , downloadUri);
                                hashMap.put("ApStatus" , status);
                                hashMap.put("ApStatusUid" , StatusUid);
                                hashMap.put("ApCompanyStatus" , CompanyStatus);
                                hashMap.put("ApRemark",remark);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Request");
                                ref.child(timeStamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();;
                                                Toast.makeText(UserEmergencyDetailsActivity.this, "New Request has been submitted", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(UserEmergencyDetailsActivity.this, UserViewRequestActivity.class));
                                                finish();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(UserEmergencyDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserEmergencyDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                imageView2.setImageURI(image_uri);
            }
            if (requestCode == CAMERA_IMAGE_CODE){
                imageView2.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}