package com.example.lenovo.mobilehub;


import android.app.Person;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    EditText Email;
    EditText Password;
    RadioButton Basic;
    RadioButton Premium;
    CheckBox Horror;
    CheckBox Comedy;
    CheckBox Fantasy;
    CheckBox Scifi;
    Button Registerr;


    RadioButton userType;
    RadioGroup  User;


    String mEmail;
    String mPassword;
    boolean ischecked;
    boolean isschecked;
    String typeUser;

    private FirebaseAuth firebaseAuth;
    ProgressDialog progressBar;
    ArrayList<String> Genre=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Email= (EditText) findViewById(R.id.signupEmail);
        Password=(EditText)findViewById(R.id.signupPasswaord);
        Horror=(CheckBox)findViewById(R.id.horrorbtn);
        Comedy=(CheckBox)findViewById(R.id.comedybtn);
        Fantasy=(CheckBox)findViewById(R.id.fantasybtn);
        Scifi=(CheckBox)findViewById(R.id.scibtn);
        Registerr=findViewById(R.id.signupbtn);

        Basic=(RadioButton)findViewById(R.id.basicbtn);
        Premium=(RadioButton)findViewById(R.id.premiumbtn);
        User = (RadioGroup) findViewById(R.id.RadioGroup);


        ischecked=false;
        Basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ischecked==true) {
                    ischecked=false;
                    Basic.setChecked(false);
                }
                else {
                    ischecked=true;
                    Basic.setChecked(true);
                }
            }
        });
        isschecked=false;
        Premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isschecked==true) {
                    isschecked=false;
                    Premium.setChecked(false);
                }
                else {
                    isschecked=true;
                    Premium.setChecked(true);
                }
            }
        });


        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=new ProgressDialog(this);







        if(Basic.isChecked())
        {
            typeUser="Basic";
        }
        else if(Premium.isChecked())
        {
            typeUser="Premium";
        }







        Registerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp;
                if(Horror.isChecked())
                {
                    temp="Horror";
                    Genre.add(temp);
                }
                if(Comedy.isChecked())
                {
                    temp="Comedy";
                    Genre.add(temp);
                }
                if(Scifi.isChecked())
                {
                    temp="Scifi";
                    Genre.add(temp);
                }
                if(Fantasy.isChecked())
                {
                    temp="Fantasy";
                    Genre.add(temp);
                }
                if(RegisterUser()) {

                    progressBar.setMessage("Registering in Progress....");
                    progressBar.show();

                    firebaseAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.dismiss();
                                if(typeUser.equals("Basic")) {

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("BasicUsers");

                                    BasicUser u1 = new BasicUser();
                                    u1.Email = Email.getText().toString();
                                    u1.Password = Password.getText().toString();
                                    u1.FavGenre.addAll(Genre);

                                    String autogeneratedKey=myRef.push().getKey();
                                    myRef.child(autogeneratedKey).setValue(u1);



                                    sendEmailVerification();
                                }
                                else if(typeUser.equals("Premium"))
                                {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("PremiunUsers");
                                    PremiumUser u1 = new PremiumUser();
                                    u1.Email = Email.getText().toString();
                                    u1.Password = Password.getText().toString();
                                    u1.FavGenre.addAll(Genre);
                                    myRef.setValue(u1);
                                    sendEmailVerification();
                                }
                                else
                                {
                                    progressBar.dismiss();
                                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                progressBar.dismiss();
                                Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public boolean RegisterUser()
    {
        mEmail=Email.getText().toString();
        mPassword=Password.getText().toString();
        if(TextUtils.isEmpty(mEmail))
        {
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return false ;
        }
        if(TextUtils.isEmpty(mPassword))
        {
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return false ;
        }
        if(!checkString(mPassword))
        {
            Toast.makeText(this,"Password must have A-Z and a-z and 0-9",Toast.LENGTH_SHORT).show();
            return false ;
        }
        if(mPassword.length()<6)
        {
            Toast.makeText(this,"length of Password must be greater than 5",Toast.LENGTH_SHORT).show();
            return false ;
        }

        return true;
    }
    private  boolean checkString(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }
    public void sendEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        Intent i = new Intent(SignUp.this, MainActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Problem With Internet Connectivity", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
