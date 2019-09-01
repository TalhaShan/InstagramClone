package com.example.instagrramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Activity_Sign_in extends AppCompatActivity {

    Button btnlogin;
    EditText edsigninemail,edsigninpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sign_in);
        btnlogin=(Button) findViewById(R.id.btnlogin);
        edsigninemail=(EditText) findViewById(R.id.ed_sigin_email);
        edsigninpass=(EditText) findViewById(R.id.ed_signin_pass);

        if(ParseUser.getCurrentUser()!=null) {
           ParseUser.getCurrentUser().logOut();   //user mmay newly signed in so he is login so thats why
            //transitionSocailmediaActivity();                                        // use should cant login from different activtiy
        }
    btnlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ParseUser.logInInBackground(edsigninemail.getText().toString(), edsigninpass.getText().toString(),
                    new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user!=null && e==null){
                        Toast.makeText(getApplicationContext(),"User signed is "+ user.getUsername(),Toast.LENGTH_LONG).show();
                        transitionSocailmediaActivity();
                    }else {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    });
    }

    private  void transitionSocailmediaActivity(){
        Intent intent = new Intent(Activity_Sign_in.this,SocialMediaActivity.class);
        startActivity(intent);
    }

}
