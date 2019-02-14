package com.example.android.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private EditText editTextPhone,editTextCode;
    private int flag=0;
    private DatabaseReference reference;
    private FirebaseUser mUser;
    Button editPhoneText,editCodeText;
    FirebaseAuth mAuth;
    String codeSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPhone = findViewById(R.id.editTextPhone);
        editPhoneText = findViewById(R.id.buttonGetVerificationCode);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Farmer1");
        mUser = mAuth.getCurrentUser();
        if(mUser != null){
            Intent intent = new Intent(MainActivity.this,UserActivity.class);
            String phoneNumber = mUser.getPhoneNumber();
            intent.putExtra("phoneNumber",phoneNumber);
            startActivity(intent);
        }


       editPhoneText.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeSent = s;
            }
        };
    }

    // this function is to check the user typed his phone number to sign in into his account
    private boolean validatePhoneNumberAndCode() {

        String phoneNumber = editTextPhone.getText().toString();

        if (TextUtils.isEmpty(phoneNumber)) {
            editTextPhone.setError("Invalid phone number.");
            return false;
        }
        return true;

    }

    //this function is to log in with firebase authentication and check whether the sign up is successful or not
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            signUpTheUser();

                            Toast.makeText(getApplicationContext(),"Sign in successful",Toast.LENGTH_SHORT).show();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(getApplicationContext(),"Sign in unsuccessful",Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void signUpTheUser() {
        final String phoneNumber=editTextPhone.getText().toString().trim();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey()==editTextPhone.getText().toString().trim()){
                        flag=1;
                        break;
                    }else{
                        flag=0;
                    }
                }
                if(flag==0){
                    profile p = new profile("none","none","none",phoneNumber,"none","none");
                    Products p1 = new Products("none","none","none","none");
                    reference.child(phoneNumber).child("profile").setValue(p);
                    reference.child(phoneNumber).child("products").setValue(p1);
                }
                Intent intent = new Intent(MainActivity.this,UserActivity.class);
                intent.putExtra("phoneNumber",phoneNumber);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //this function is to generate the OTP number
    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    //  The Verification code which is sent to your phone is       codesent
    //  and the     code      should be typed by the user to compare and login the user


    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case R.id.buttonGetVerificationCode:
                if (!validatePhoneNumberAndCode()) {
                    return;
                }
                startPhoneNumberVerification(editTextPhone.getText().toString());
                break;
        }
    }
}
