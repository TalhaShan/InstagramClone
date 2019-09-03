package com.example.instagrramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class Userposts extends AppCompatActivity {

        private  LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userposts);
        linearLayout= (LinearLayout)findViewById(R.id.linearscrolllayout);


        Intent receievedintentobject = getIntent();
        final String receivedusername = receievedintentobject.getStringExtra("username");
        Toast.makeText(getApplicationContext(),receivedusername,Toast.LENGTH_LONG).show();

        setTitle(receivedusername +"'s post");
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedusername);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.show();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size()>0 && e==null){
                    for(ParseObject posts: objects){

                        final TextView postdesc = new TextView(Userposts.this);
                        postdesc.setText(posts.get("Img_desc")+""); //for nulll +"";
                        ParseFile postPicture = (ParseFile) posts.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if(data!=null && e==null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postimageview = new ImageView(Userposts.this);
                                    LinearLayout.LayoutParams imageviewparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT); //(Width,height);
                                    imageviewparams.setMargins(5,5,5,5);
                                    postimageview.setLayoutParams(imageviewparams);
                                    postimageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postimageview.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5,5,5,5);
                                      postdesc.setLayoutParams(des_params);
                                      postdesc.setGravity(Gravity.CENTER);
                                      postdesc.setBackgroundColor(Color.BLUE);
                                      postdesc.setTextColor(Color.WHITE);
                                      postdesc.setTextSize(30f);
                                      linearLayout.addView(postimageview);
                                      linearLayout.addView(postdesc);

                                }
                                dialog.dismiss();
                            }
                        });
                    }
                }else{
                        Toast.makeText(getApplicationContext(),receivedusername +"doesn't have any post",Toast.LENGTH_LONG).show();
                            finish();
                }
            }
        });
    }
}
