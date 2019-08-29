package com.example.instagrramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("wcDeHinrYY6bJEoDsMB8CvgP74eTWXkaY1GztQ1a")
                // if defined
                .clientKey("w10i22z4f6KjVMhDHkDPougBjQgifMfoYSfQAQbW")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
