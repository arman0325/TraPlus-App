package com.arman.traplus;


import android.os.Handler;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.translate.AmazonTranslateAsyncClient;
import com.amazonaws.services.translate.model.GetTerminologyRequest;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

public class translate {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String str1 = "unknown"; //save the word after translate
    private boolean reString = false;

    private int n = 0;

    // main translate function call
    public String translateMethod(String word, String oType, String nType){
        AWSCredentials awsCredentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return "**********************"; // access key
            }

            @Override
            public String getAWSSecretKey() {
                return "**********************"; // secret key
            }
        };

        AmazonTranslateAsyncClient translateAsyncClient = new AmazonTranslateAsyncClient(awsCredentials);
        TranslateTextRequest translateTextRequest = new TranslateTextRequest()
                .withText(word)
                .withSourceLanguageCode(oType) // original type of word
                .withTargetLanguageCode(nType)// translate target type of word
                .withTerminologyNames("new"); // name of terminology that will using in translate
        GetTerminologyRequest getTerminologyRequest = new GetTerminologyRequest()
                .withName("test");
        translateAsyncClient.translateTextAsync(translateTextRequest, new AsyncHandler<TranslateTextRequest, TranslateTextResult>() {
            @Override
            public void onError(Exception e) {
                Log.e(LOG_TAG, "Error occurred in translating the text: " + e.getLocalizedMessage());
                str1 ="null in error";
                reString = true;// left in error
            }

            @Override
            public void onSuccess(TranslateTextRequest request, TranslateTextResult translateTextResult) {
                //Success message
                //Log.d(LOG_TAG, "Original Text: " + request.getText());
                //Log.d(LOG_TAG, "Translated Text: " + translateTextResult.getTranslatedText());//print the result message
                str1 = translateTextResult.getTranslatedText();//save the result to public str
                reString = true;

            }

        });

        while (reString==false){  //check the result get
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 0.1 seconds
                    n = n + 1;
                }
            }, 100);// 1000 = 1sec
        }
        Log.d(LOG_TAG, "waiting time:" + n  + "sec");
        return str1; //return the result


    }

}
