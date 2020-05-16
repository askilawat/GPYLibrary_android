package com.ask.gpylibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ask.gpylibrary.datemodel.Collage_staff;
import com.ask.gpylibrary.datemodel.Student;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

public class CollageStaffProfile extends AppCompatActivity {

    private TextInputEditText nameedt,mobedt,emailedt,addressedt,genderedt,departmentedt;
    private String nametxt,mobtxt,emailtxt,addresstxt,gendertxt,departmenttxt,thumbnailtxt;

    private CircleImageView profileimg;

    private FirebaseFirestore firestore;
    private Collage_staff collage_staff;
    private static final String TAG = "CollageProfile";

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private UploadTask uploadTask;

    private ProgressBar progressBar;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_staff_profile);

        preferences = getApplicationContext().getSharedPreferences("gpylibrary",0);

        initUI();

        edIt(false);

        firebaseSetup();

        Glide.with(getApplicationContext()).load(preferences.getString("thumbnail","")).into(profileimg);
    }

    private void edIt(Boolean b)
    {
        nameedt.setEnabled(b);
        mobedt.setEnabled(b);
        emailedt.setEnabled(b);
        addressedt.setEnabled(b);
        genderedt.setEnabled(b);
        departmentedt.setEnabled(b);

        profileimg.setEnabled(b);
    }

    private void initUI() {
        nameedt = findViewById(R.id.name);
        mobedt = findViewById(R.id.mobno);
        emailedt = findViewById(R.id.email);
        addressedt = findViewById(R.id.address);
        genderedt = findViewById(R.id.gender);
        departmentedt = findViewById(R.id.department);

        profileimg = findViewById(R.id.profile_image);
        progressBar = findViewById(R.id.uploadprogress);
    }

    private void takeInput() {
        nametxt = nameedt.getText().toString();
        addresstxt = addressedt.getText().toString();
        mobtxt = mobedt.getText().toString();
        emailtxt = emailedt.getText().toString();
        gendertxt = genderedt.getText().toString();
        departmenttxt = departmentedt.getText().toString();

        if (nametxt.isEmpty() || mobtxt.isEmpty() || addresstxt.isEmpty() || emailtxt.isEmpty() ||  gendertxt.isEmpty() || departmenttxt.isEmpty()) {
            Toast.makeText(getApplicationContext(), "enter data", Toast.LENGTH_LONG).show();
        }else{
            collage_staff = new Collage_staff(mobtxt,departmenttxt,nametxt,mobtxt,emailtxt,gendertxt,thumbnailtxt,addresstxt);
        }
    }

    private void firebaseSetup() {

        firebaseAuth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();

        //getData
        firestore.collection("collagestaff").document(firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            try {
                                Collage_staff collage_staff = task.getResult().toObject(Collage_staff.class);
                                //settingData
                                nameedt.setText(collage_staff.getName());
                                emailedt.setText(collage_staff.getEmail());
                                mobedt.setText(collage_staff.getMob_no());
                                addressedt.setText(collage_staff.getAddress());
                                genderedt.setText(collage_staff.getGender());
                                departmentedt.setText(collage_staff.getDepartment());
                                thumbnailtxt = collage_staff.getThumbnail();

                                SharedPreferences.Editor editor = preferences.edit();

                                editor.putString("name",collage_staff.getName());
                                editor.putString("email",collage_staff.getEmail());
                                editor.putString("department",collage_staff.getDepartment());
                                editor.putString("address",collage_staff.getAddress());
                                editor.putString("gender",collage_staff.getGender());
                                if (!thumbnailtxt.isEmpty()) {
                                    editor.putString("thumbnail",thumbnailtxt);
                                }
                                editor.putString("phone",collage_staff.getMob_no());
                                editor.apply();
                            }catch (NullPointerException e)
                            {
                                Toast.makeText(CollageStaffProfile.this,"Data : Error In Retriving Data",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Log.w(TAG, "User Not Exist.", task.getException());
                            Toast.makeText(getApplicationContext(),"User Not Exist",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void goBack(View view) {

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    public void changeProfile(View view) {

        firebaseStorage = FirebaseStorage.getInstance();

        //ChooseImage
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,101);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && data.getData() != null)
        {
            final Uri url = data.getData();
            //upload to firebase
            uploadTask = firebaseStorage.getReference("profileimages/"+firebaseAuth.getUid()).putFile(data.getData());

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CollageStaffProfile.this,"link: "+url,Toast.LENGTH_SHORT).show();
                    Glide.with(getApplicationContext()).load(url).into(profileimg);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CollageStaffProfile.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CollageStaffProfile.this,"link: PAUSED ",Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Toast.makeText(CollageStaffProfile.this,"Uploading: "+(float)progress+"%",Toast.LENGTH_SHORT).show();

                    progressBar.setProgress((int)progress);
                }
            });
        }
    }

    public void editIt(View view) {
        edIt(true);
        findViewById(R.id.save_student).setEnabled(true);
        findViewById(R.id.save_student).setBackgroundResource(R.drawable.savebtn);
    }

    public void saveIt(View view) {
        takeInput();

        firestore.collection("collagestaff").document(firebaseAuth.getUid())
                .set(collage_staff).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CollageStaffProfile.this,"Updated",Toast.LENGTH_SHORT).show();
                edIt(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CollageStaffProfile.this,"Failed To Update",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
