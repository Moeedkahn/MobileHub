package com.example.lenovo.mobilehub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    Button loginbtn;
    TextView Registertext;
    private FirebaseAuth firebaseAuth;
    String mEmail;
    String mPassword;
    ProgressDialog progressBar;
    EditText Email;
    EditText Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        loginbtn=findViewById(R.id.Loginbtn);
        Email=findViewById(R.id.loginEmail);
        Password=findViewById(R.id.loginPasswaord);
        Registertext=findViewById(R.id.loginRegister);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=new ProgressDialog(this);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    progressBar.setMessage("Processing....");
                    progressBar.show();
                    firebaseAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.dismiss();
                                checkemailverification();
                            } else {
                                progressBar.dismiss();
                                Toast.makeText(getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
        Registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public boolean validate()
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
        return true;
    }
    private void checkemailverification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean Emailflag=firebaseUser.isEmailVerified();
        if(Emailflag){
            finish();
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
        }
        else {
            Toast.makeText(this,"Verify Email First",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
