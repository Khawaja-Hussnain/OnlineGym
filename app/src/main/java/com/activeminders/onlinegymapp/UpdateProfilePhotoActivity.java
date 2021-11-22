package com.activeminders.onlinegymapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfilePhotoActivity extends AppCompatActivity {

    Button update;
    ImageView profileimage,selectimage,back;
    String profiletype;
    DatabaseReference profileRef;

    //Upload Profile Image code
    private Uri uri;
    private Uri myUri;
    private StorageTask storageTask;
    private String checker="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_photo);

        profiletype=getIntent().getStringExtra("profiletype");

            Toast.makeText(this, ""+profiletype, Toast.LENGTH_SHORT).show();



        update=findViewById(R.id.updatebtn);
        profileimage=findViewById(R.id.profileimage);
        selectimage=findViewById(R.id.selectimg);
        back=findViewById(R.id.backarrow);

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker="clicked";
                CropImage.activity(uri).setAspectRatio(1,1).start(UpdateProfilePhotoActivity.this);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (profiletype.equals("UserInfo")){
                    Toast.makeText(UpdateProfilePhotoActivity.this, ""+profiletype, Toast.LENGTH_SHORT).show();
                    UpdateUserProfile();
                }else{
                    UpdateGymProfile();
                }
            }
        });

    }

    private void UpdateGymProfile() {
        profileRef= FirebaseDatabase.getInstance().getReference().child("Gyms").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(profiletype);

        if (checker.equals("clicked")) {
            final ProgressDialog progressDialog = new ProgressDialog(UpdateProfilePhotoActivity.this);
            String filePathAndName = "Profile_Images/" + "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);

            progressDialog.setTitle("Updating");
            progressDialog.setMessage("Please Wait,While We are updating profile photo");
            progressDialog.setCanceledOnTouchOutside(false);

            if (uri != null) {
                progressDialog.show();
                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        myUri = uriTask.getResult();
                        if (uriTask.isSuccessful()) {
                            Map<String, Object> profileMap = new HashMap<>();

                            profileMap.put("profileimage", "" + myUri);
                            profileRef.updateChildren(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(UpdateProfilePhotoActivity.this, "Profile Photo Updated successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateProfilePhotoActivity.this, "Error..try again", Toast.LENGTH_SHORT).show();
                        }

                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateProfilePhotoActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(UpdateProfilePhotoActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(UpdateProfilePhotoActivity.this, "Please An Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateUserProfile() {
        profileRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(profiletype);
        if (checker.equals("clicked")) {
            final ProgressDialog progressDialog = new ProgressDialog(UpdateProfilePhotoActivity.this);
            String filePathAndName = "Profile_Images/" + "" + FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);

            progressDialog.setTitle("Updating");
            progressDialog.setMessage("Please Wait,While We are updating profile photo");
            progressDialog.setCanceledOnTouchOutside(false);

            if (uri != null) {
                progressDialog.show();
                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        myUri = uriTask.getResult();
                        if (uriTask.isSuccessful()) {
                            Map<String, Object> profileMap = new HashMap<>();

                            profileMap.put("profileimage", "" + myUri);
                            profileRef.updateChildren(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(UpdateProfilePhotoActivity.this, "Profile Photo Updated successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateProfilePhotoActivity.this, "Error..try again", Toast.LENGTH_SHORT).show();
                        }

                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateProfilePhotoActivity.this, "Image not uploaded", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(UpdateProfilePhotoActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(UpdateProfilePhotoActivity.this, "Please An Image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            uri=result.getUri();
            profileimage.setImageURI(uri);
        }else {
            Toast.makeText(UpdateProfilePhotoActivity.this, "Error,try again", Toast.LENGTH_SHORT).show();
        }
    }

   
}