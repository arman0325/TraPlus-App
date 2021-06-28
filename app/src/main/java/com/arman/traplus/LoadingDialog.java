package com.arman.traplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    AlertDialog dialog;
    Activity activity;

    public LoadingDialog(Activity myActivity){
        activity =  myActivity;
    }

    // this is call start the loading message of dialog
    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog,null));
        builder.setCancelable(true);

        dialog = builder.create();
        // lock the other place cannot cancel the dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    // close the loading dialog
    public void dismissDialog(){
        dialog.dismiss();
    }

}

