package com.example.android.farmer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CameraFragment extends Fragment implements View.OnClickListener{

    private static final int REQUEST_IMAGE_CAPTURE = 111;
    Uri uri;
    DatabaseReference reference;
    String imageEncoded;
    ImageView imageView;
    Button setProduct,notSetProduct;
    View mView;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.camera_upload,container,false);
        return mView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mView = view;
        mBundle = getArguments();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference("UserPhotos");


        imageView = (ImageView)mView.findViewById(R.id.ChooseProfileImage);
        setProduct = (Button)mView.findViewById(R.id.SendPhotoToDatabase);
        notSetProduct = (Button)mView.findViewById(R.id.CancelPhotoToDatabase);

        imageView.setOnClickListener(this);
        setProduct.setOnClickListener(this);
        notSetProduct.setOnClickListener(this);

        reference = FirebaseDatabase.getInstance().getReference("Farmer1");
    }

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void onLaunchGallery(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

                if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode==getActivity().RESULT_OK){
                    uri = data.getData();

                }
                else if(requestCode == 1 && resultCode==getActivity().RESULT_OK) {
                    uri = data.getData();
                }
        }

    private String UploadUriIntoFirebaseStorage()
    {
        String phoneNumber = mBundle.getString("phoneNumber");
        if(phoneNumber != null) {
            StorageReference filePath = mStorage.child(phoneNumber).child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });
            //ref.setValue(imageEncoded);
        }
        return phoneNumber;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id)
        {
            case R.id.ChooseProfileImage:
                alertSimpleListView();
            case R.id.SendPhotoToDatabase:

        }
    }
    public void alertSimpleListView() {
        /*
         * WebView is created programatically here.
         *
         * @Here are the list of items to be shown in the list
         */
        final CharSequence[] items = {"Gallery","Camera"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // will toast your selection
                if(item == 1)
                {
                    onLaunchCamera();
                }
                if(item == 0)
                {
                    onLaunchGallery();
                }
            }
        }).show();
    }
}
