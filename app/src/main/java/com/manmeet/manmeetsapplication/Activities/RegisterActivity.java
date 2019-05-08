package com.manmeet.manmeetsapplication.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.manmeet.manmeetsapplication.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView ImgUserPhoto;
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;

    private EditText userName;
    private EditText userEmail;
    private EditText userPassword;
    private EditText confirmPassword;
    private ProgressBar loadingProgress;
    private Button regButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //views

        userName = findViewById(R.id.regName);
        userEmail = findViewById(R.id.regEmail);
        userPassword = findViewById(R.id.regPassword);
        confirmPassword = findViewById(R.id.regPassword2);
        loadingProgress = findViewById(R.id.progressBar);
        regButton = findViewById(R.id.regBtn);
        loadingProgress.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regButton.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);
                final String email = userEmail.getText().toString();
                final String name = userName.getText().toString();
                final String password = userPassword.getText().toString();
                final String password2 = confirmPassword.getText().toString();

                if(name.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty() || !password.equals(password2))
                {
                    // something goes wrong: all fields must be filled.
                    // display an error message.
                    showMessage("Please verify the all fields.");
                    regButton.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);
                }

                else
                {
                    // everything is okay and all the fields are filled so now we can start creating user account.
                    // CreateUserAccount method will try to create the user account if the email is valid.

                    CreateUserAccount(name,email,password);
                }







            }
        });


        ImgUserPhoto = findViewById(R.id.regUserPhoto);

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 22){

                    checkAndRequestForPermission();

                }

                else {

                    openGallery();

                }




            }
        });
    }

    private void CreateUserAccount(final String name, String email, String password)
    {
        // this method will create user account with specific email and password.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            // user account created successfully.
                            showMessage("Account created");

                            // after we created the user account we need to update his profile picture and info.
                            updateUserInfo(name, pickedImgUri, mAuth.getCurrentUser());

                        }

                        else
                        {
                            // account creation failed.
                            showMessage("account not registered" + task.getException().getMessage());
                            regButton.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility(View.INVISIBLE);
                        }


                    }
                });
    }


    // update user photo and info.
    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser)
    {

        // first we need to upload the user photo to firebase storage and get url.

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photo");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // image loaded successfully.
                // now we can get our image url.

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        // Uri contain user image url.

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    // user info updated successfully.
                                    showMessage("Register Complete");
                                    updateUI();
                                }
                            }
                        });

                    }
                });
            }
        });
    }

    private void updateUI()
    {
        Intent homeActivity = new Intent(getApplicationContext(), Home.class);
        startActivity(homeActivity);
        finish();
    }

    private void showMessage(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void openGallery() {

        //TODO: open gallery intent and wait for user to pick an image!

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);

    }

    private void checkAndRequestForPermission() {

        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(RegisterActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            }

            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                                                          new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                          PReqCode);
            }
        }

        else
        {
            openGallery();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){

            //The user has successfully picked an image.
            //We need to save its references to a Uri variable.
            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);
        }


    }
}




