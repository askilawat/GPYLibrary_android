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
import android.widget.Toast;

import com.ask.gpylibrary.adapter.BooksAdapter;
import com.ask.gpylibrary.datemodel.Book;
import com.ask.gpylibrary.datemodel.Student;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class StudentProfile extends AppCompatActivity {

    private TextInputEditText nameedt,mobedt,emailedt,branchedt,yearedt,addressedt,genderedt,rollnoedt;
    private String nametxt,mobtxt,emailtxt,branchtxt,yeartxt,addresstxt,gendertxt,rollnotxt,thumbnailtxt;

    private CircleImageView profileimg;

    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;
    private UploadTask uploadTask;
    private Student student;
    private static final String TAG = "StudentProfile";

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        preferences = getApplicationContext().getSharedPreferences("gpylibrary",0);

        initUI();

        edIt(false);

        firebaseSetup();

        Glide.with(getApplicationContext()).load(preferences.getString("thumbnail","")).into(profileimg);
    }

    private void takeInput() {
        nametxt = nameedt.getText().toString();
        addresstxt = addressedt.getText().toString();
        mobtxt = mobedt.getText().toString();
        emailtxt = emailedt.getText().toString();
        rollnotxt = rollnoedt.getText().toString();
        gendertxt = genderedt.getText().toString();
        branchtxt = branchedt.getText().toString();
        yeartxt = yearedt.getText().toString();

        if (nametxt.isEmpty()||mobtxt.isEmpty()||addresstxt.isEmpty()||emailtxt.isEmpty()||rollnotxt.isEmpty()||gendertxt.isEmpty()||branchtxt.isEmpty()||yeartxt.isEmpty())
        {
           //show msg Log.w(TAG, "Error getting documents.", task.getException());
           Toast.makeText(getApplicationContext(),"enter data",Toast.LENGTH_LONG).show();
        }else{
            student = new Student(mobtxt,branchtxt,yeartxt,nametxt,mobtxt,emailtxt,addresstxt,gendertxt,rollnotxt,thumbnailtxt);
        }
    }

    private void edIt(Boolean b)
    {
        nameedt.setEnabled(b);
        mobedt.setEnabled(b);
        emailedt.setEnabled(b);
        branchedt.setEnabled(b);
        yearedt.setEnabled(b);
        addressedt.setEnabled(b);
        genderedt.setEnabled(b);
        rollnoedt.setEnabled(b);

        profileimg.setEnabled(b);
    }

    private void initUI() {
        nameedt = findViewById(R.id.name);
        mobedt = findViewById(R.id.mobno);
        emailedt = findViewById(R.id.email);
        branchedt = findViewById(R.id.branch);
        yearedt = findViewById(R.id.year);
        addressedt = findViewById(R.id.address);
        genderedt = findViewById(R.id.gender);
        rollnoedt = findViewById(R.id.rollno);

        profileimg = findViewById(R.id.profile_image);
    }

    private void firebaseSetup() {

        firebaseAuth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();

        //getData
        firestore.collection("student").document(firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            try {
                                Student student = task.getResult().toObject(Student.class);
                                //settingData
                                nameedt.setText(student.getName());
                                emailedt.setText(student.getEmail());
                                mobedt.setText(student.getMob_no());
                                addressedt.setText(student.getAddress());
                                genderedt.setText(student.getGender());
                                rollnoedt.setText(student.getRollno());
                                branchedt.setText(student.getBranch());
                                yearedt.setText(student.getYear());
                                thumbnailtxt = student.getThumbnail();

                                SharedPreferences.Editor editor = preferences.edit();

                                editor.putString("name",student.getName());
                                editor.putString("email",student.getEmail());
                                editor.putString("address",student.getAddress());
                                editor.putString("gender",student.getGender());
                                editor.putString("branch",student.getBranch());
                                editor.putString("year",student.getYear());
                                editor.putString("rollno",student.getRollno());
                                if (!thumbnailtxt.isEmpty()) {
                                    editor.putString("thumbnail",thumbnailtxt);
                                }
                                editor.putString("phone",student.getMob_no());
                                editor.apply();
                            }catch (NullPointerException e)
                            {
                                Toast.makeText(StudentProfile.this,"Data : Error In Retriving Data",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Log.w(TAG, "User Not Exist.", task.getException());
                            Toast.makeText(getApplicationContext(),"User Not Exist",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void saveIt(View view) {
        takeInput();

        firestore.collection("student").document(firebaseAuth.getUid())
                .set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(StudentProfile.this,"Updated",Toast.LENGTH_SHORT).show();
                edIt(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentProfile.this,"Failed To Update",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editIt(View view) {
        edIt(true);
        findViewById(R.id.save_student).setEnabled(true);
        findViewById(R.id.save_student).setBackgroundResource(R.drawable.savebtn);
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
                    Toast.makeText(StudentProfile.this,"link: "+url,Toast.LENGTH_SHORT).show();
                    Glide.with(getApplicationContext()).load(url).into(profileimg);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(StudentProfile.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(StudentProfile.this,"link: PAUSED ",Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Toast.makeText(StudentProfile.this,"Uploading: "+(float)progress+"%",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
