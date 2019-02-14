package com.example.android.farmer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    View mView;
    private Button ProfileButton;
    private TextView Name, name, Age, age, PhoneNo, EmailId, State, state, Gender, gender;
    private ImageView phoneLogo, emailLogo;
    private CircleImageView ProfileImageView;
    private DatabaseReference mReference;
    private Bundle mBundle;
    private String phoneNumber;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*" + "@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.profile_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mView = view;

        mBundle = getArguments();

        phoneNumber = mBundle.getString("phoneNumber");

        mReference = FirebaseDatabase.getInstance().getReference("Farmer1").child(phoneNumber).child("profile");

        ProfileButton = (Button) mView.findViewById(R.id.EditProfile);
        Name = (TextView) mView.findViewById(R.id.nameTag);
        name = (TextView) mView.findViewById(R.id.NameDisplay);
        Age = (TextView) mView.findViewById(R.id.AgeField);
        age = (TextView) mView.findViewById(R.id.Age);
        PhoneNo = (TextView) mView.findViewById(R.id.phoneNumber);
        EmailId = (TextView) mView.findViewById(R.id.email_id);
        State = (TextView) mView.findViewById(R.id.state);
        state = (TextView) mView.findViewById(R.id.StateName);

        phoneLogo = (ImageView) mView.findViewById(R.id.phone_logo);
        emailLogo = (ImageView) mView.findViewById(R.id.email_logo);
        ProfileImageView = (CircleImageView) mView.findViewById(R.id.profile_photo);

        Gender = (TextView) mView.findViewById(R.id.GenderType);
        gender = (TextView) mView.findViewById(R.id.Gender);

        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromFarmer();
            }
        });

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profile p = dataSnapshot.getValue(profile.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getDataFromFarmer() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.edit_profile, null);
        final EditText name = view.findViewById(R.id.name);
        final EditText email = view.findViewById(R.id.EmailEdit);
        final EditText age = view.findViewById(R.id.EnterAge);
        final Spinner spinner = view.findViewById(R.id.StateSpinner);

        final Spinner gender = view.findViewById(R.id.GenderSpinner);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Edit Profile");
        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        final String Name = name.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Age = age.getText().toString().trim();

        final ArrayAdapter<CharSequence> States = ArrayAdapter.createFromResource(getActivity(), R.array.state_array, android.R.layout.simple_spinner_item);
        States.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(States);

        final ArrayAdapter<CharSequence> Gender = ArrayAdapter.createFromResource(getActivity(), R.array.gender_array, android.R.layout.simple_list_item_1);
        States.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender.setAdapter(Gender);

        if (TextUtils.isEmpty(Name)) {
            name.setError("Enter your name");
        }
        // Java program to validate email in Jav
        if (TextUtils.isEmpty(Age)) {
            age.setError("Enter your Age");
        }

        if (TextUtils.isEmpty(Email)) {

            email.setError("Enter your Email");

        } else if (!emailValidator(Email)) {

            email.setError("Invalid Email id");

        }

        final String Gender1 = String.valueOf(gender.getSelectedItem());
        final String State = String.valueOf(spinner.getSelectedItem());
        ;

        spinner.setOnItemSelectedListener(this);
        gender.setOnItemSelectedListener(this);

        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                profile p = new profile(Age, Gender1, Email, phoneNumber, Name, State);

                mReference.setValue(p);
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static boolean emailValidator(String email) {

        if (email == null) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}

