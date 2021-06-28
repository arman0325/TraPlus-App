package com.arman.traplus.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arman.traplus.LoadingDialog;
import com.arman.traplus.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.Arrays;

public class AddNewItemActivity extends AppCompatActivity {

    EditText editText;
    private String currentPhotoPath;
    private String menuStrOld;

    private String previousClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        editText = (EditText) findViewById(R.id.editTextMenu);
        currentPhotoPath  = getIntent().getStringExtra("id");
        previousClassName = getIntent().getStringExtra("className");
        // for online menu second time import menu
        try {
            menuStrOld = getIntent().getStringExtra("old");
        }catch (Exception e){
            Log.e("old message", "onCreate: "+e);
        };
        if (menuStrOld==null){
            menuStrOld ="";
        }
        ORC(currentPhotoPath);

    }

    public void ORC(String path){
        // call the dialog to loading
        LoadingDialog loadingDialog = new LoadingDialog(AddNewItemActivity.this);
        loadingDialog.startLoadingDialog();
        // set the image

        String currentPhotoPath = getIntent().getStringExtra("uri"); // get the uri for before activity
        Uri uri = Uri.parse(currentPhotoPath);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //send the image to firebase to check the word in image
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVision fbVision = FirebaseVision.getInstance();
        FirebaseVisionCloudTextRecognizerOptions options = new FirebaseVisionCloudTextRecognizerOptions.Builder()
                .setLanguageHints(Arrays.asList("ja"))
                .build();
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = fbVision.getCloudTextRecognizer(options);
        Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(image);
        //if task is success
        task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                String OCRText = firebaseVisionText.getText();
                Log.e("String", "onSuccess: "+ OCRText);
                editText.setText(OCRText.replaceAll("[-+.^:,*…]",""));
                // if success to close the loading dialog
                loadingDialog.dismissDialog();

            }
        });
        //if task is fail
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    // send the string to menuActivity to translate the string and make the menu
    public void doTranslate(View view) {
        String menuStr;
        if (menuStrOld!=""){
            menuStr = menuStrOld +"\n"+ editText.getText().toString();
        }else{
            menuStr = editText.getText().toString();
        }
        if (previousClassName.equals("offlineMenuActivity")) {
            Intent i = new Intent(AddNewItemActivity.this, offlineMenuActivity.class);
            i.putExtra("menuString", menuStr);
            setResult(RESULT_OK, i);
            finish();
        } else if (previousClassName.equals("MenuActivity")) {
            Intent i = new Intent(AddNewItemActivity.this, MenuActivity.class);
            i.putExtra("menuString", menuStr);
            setResult(RESULT_OK, i);
            finish();
        }



    }
}