package com.example.ecommerce.Buyers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, addressEditText;
    private TextView profileChangeTextBtn, closeTextBtn, saveTextButton;
    private Button securityQuestionBtn;
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePictureRef;
    private String checker = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        profileImageView = findViewById(R.id.settings_profile_image);
        fullNameEditText = findViewById(R.id.settings_full_name);
        userPhoneEditText = findViewById(R.id.settings_phone_number);
        addressEditText = findViewById(R.id.settings_address);
        profileChangeTextBtn = findViewById(R.id.profile_image_change_btn);
        closeTextBtn = findViewById(R.id.close_settings_btn);
        saveTextButton = findViewById(R.id.update_settings_btn);
        securityQuestionBtn=findViewById(R.id.security_question_btn);


        userInfoDisplay(profileImageView, fullNameEditText, userPhoneEditText, addressEditText);

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        securityQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check","settings");
                startActivity(intent);
            }
        });

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked")) {
                    userInfoSaved();
                } else {
                    updateOnlyUserInfo();
                }
            }
        });
        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(SettingsActivity.this);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Error, Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
            finish();
        }
    }


    private void userInfoSaved() {

        if (TextUtils.isEmpty(fullNameEditText.getText().toString())) {
            fullNameEditText.setError("Name is mandatory");
        } else if (TextUtils.isEmpty(userPhoneEditText.getText().toString())) {
            userPhoneEditText.setError("Phone is mandatory");
        } else if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            addressEditText.setError("address is mandatory");
        } else if (checker.equals("clicked")) {
            uploadImage();
        }


    }

    private void uploadImage() {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Update profile");
            progressDialog.setMessage("please wait, while we are updating your account information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            if (imageUri != null) {
                final StorageReference fileRef = storageProfilePictureRef
                        .child(Prevalent.currentonlineUser.getPhone() + ".jpg");
                uploadTask = fileRef.putFile(imageUri);

                uploadTask.continueWith(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUrl = task.getResult();
                            myUrl = downloadUrl.toString();

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap.put("name", fullNameEditText.getText().toString());
                            userMap.put("address", addressEditText.getText().toString());
                            userMap.put("phoneOrder", userPhoneEditText.getText().toString());
                            userMap.put("image", myUrl);
                            ref.child(Prevalent.currentonlineUser.getPhone()).updateChildren(userMap);

                            progressDialog.dismiss();

                            startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
                            Toast.makeText(SettingsActivity.this, "Profile Info Update Succesfully", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SettingsActivity.this, "Error : ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                Toast.makeText(this, "Image is not Selected", Toast.LENGTH_SHORT).show();
            }
        }

    private void updateOnlyUserInfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", fullNameEditText.getText().toString());
        userMap.put("address", addressEditText.getText().toString());
        userMap.put("phone", userPhoneEditText.getText().toString());
        ref.child(Prevalent.currentonlineUser.getPhone()).updateChildren(userMap);

        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
        Toast.makeText(SettingsActivity.this, "Profile Info Update Succesfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameEditText, final EditText userPhoneEditText, final EditText addressEditText) {
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentonlineUser.getPhone());
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //try {

                    if (dataSnapshot.exists()) {

                        if (dataSnapshot.child("image").exists()) {

                            String image = dataSnapshot.child("image").getValue().toString();
                            String name = dataSnapshot.child("name").getValue().toString();
                            String phone = dataSnapshot.child("phone").getValue().toString();
                            String address = dataSnapshot.child("address").getValue().toString();
                            Picasso.get().load(image).into(profileImageView);
                            fullNameEditText.setText(name);
                            userPhoneEditText.setText(phone);
                            addressEditText.setText(address);
                        }
                    }
                //catch (Exception e)
               /* {
                    String s=e.getMessage();
                    Toast.makeText(SettingsActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                }*/

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
