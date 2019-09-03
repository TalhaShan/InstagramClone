package com.example.instagrramclone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class PicTab extends Fragment implements View.OnClickListener {

    private ImageView imgplace;
    private EditText eddescription;
    private Button btnshareimg;
    Bitmap receviedbitmapimage;

    public PicTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic_tab, container, false);

        imgplace = view.findViewById(R.id.imgplaceholder);
        eddescription =view.findViewById(R.id.edimgdesc);
        btnshareimg = view.findViewById(R.id.btnshareImage);

            btnshareimg.setOnClickListener(PicTab.this);

            imgplace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(android.os.Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },1000);
                    }else {
                        getchoosenimage();
                    }
                }
            });
                  return  view;

    }

    private void getchoosenimage() {

       // Toast.makeText(getContext(),"Accessed to gallery",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnshareImage:
                if(receviedbitmapimage!=null){
                        if(eddescription.getText().toString().equals("")){
                            Toast.makeText(getContext(),"Enter some desc plz",Toast.LENGTH_LONG).show();

                        }else {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            receviedbitmapimage.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                            byte[] bytes = byteArrayOutputStream.toByteArray();

                            ParseFile parseFile= new ParseFile("img.png",bytes);
                            ParseObject parseObject= new ParseObject("Photo");
                            parseObject.put("picture",parseFile);
                            parseObject.put("Img_desc",eddescription.getText().toString());
                            parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                            final ProgressDialog dialog = new ProgressDialog(getContext());
                            dialog.setMessage("Loading..");
                            dialog.show();
                             parseObject.saveInBackground(new SaveCallback() {
                                 @Override
                                 public void done(ParseException e) {
                                     if(e==null){
                                         Toast.makeText(getContext(),"Done",Toast.LENGTH_LONG).show();
                                     }else {
                                         Toast.makeText(getContext(),"Error in saving :" +e.getMessage(),Toast.LENGTH_LONG).show();

                                     }
                                     dialog.dismiss();
                                 }
                             });
                        }

                }else {
                    Toast.makeText(getContext(),"Select Image first",Toast.LENGTH_LONG).show();
                }
                break;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1000){
            if(grantResults.length>0 &&  grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getchoosenimage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000){
            if(resultCode== Activity.RESULT_OK){
                try{
                    Uri selectedimage= data.getData();
                    String[] filepathcolumn ={MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedimage,
                            filepathcolumn,null,null,null);
                    cursor.moveToFirst();
                    int columnindex = cursor.getColumnIndex(filepathcolumn[0]);
                    String picturpath = cursor.getString(columnindex);
                    cursor.close();
                    receviedbitmapimage = BitmapFactory.decodeFile(picturpath);
                    imgplace.setImageBitmap(receviedbitmapimage);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
