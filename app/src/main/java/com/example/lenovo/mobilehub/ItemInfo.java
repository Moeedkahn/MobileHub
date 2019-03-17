package com.example.lenovo.mobilehub;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemInfo extends AppCompatActivity {
    Item temp;
    ImageView image;
    Button watch;
    Item show;
    Button btnShareLink;
    DatabaseReference myRef;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        Intent intent = getIntent();
        show=(Item)intent.getSerializableExtra("MyClass");
        image=findViewById(R.id.img);
        watch=findViewById(R.id.watchbtn);
        btnShareLink=(Button)findViewById(R.id.btnShareLink);

        callbackManager=CallbackManager.Factory.create();
        shareDialog=new ShareDialog(this);

        String imageUri = show.imageLink;
        Picasso.with(this).load(imageUri).into(image);

        btnShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(ItemInfo.this, "Share successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(ItemInfo.this, "Share cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
                ShareLinkContent linkContent=new ShareLinkContent.Builder().setQuote("This is useful link").setContentUrl(Uri.parse("https://firebasestorage.googleapis.com/v0/b/moviehub-cb3ee.appspot.com/o/simple.mp4?alt=media&token=bf35f548-b2cd-42d2-8d96-c0818be6c196")).build();
                if(ShareDialog.canShow(ShareLinkContent.class))
                {
                    shareDialog.show(linkContent);
                }
            }
        });

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Detail.class);
                startActivity(intent);
            }
        });



        myRef = FirebaseDatabase.getInstance().getReference("Items");


    }
}
