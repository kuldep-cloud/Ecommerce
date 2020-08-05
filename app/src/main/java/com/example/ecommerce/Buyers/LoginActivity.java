package com.example.ecommerce.Buyers;

import com.example.ecommerce.Admin.AdminCategoryActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

public class LoginActivity extends AppCompatActivity {
    private EditText Inputnumber,InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink,NotAdminLink,forgetPasswordLink;

    private  String parentDbName ="Users";
    private CheckBox ChkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton=findViewById(R.id.login_btn);
        Inputnumber=findViewById(R.id.login_phone_number_input);
        InputPassword=findViewById(R.id.login_password_input);
        loadingBar=new ProgressDialog(this);
        ChkBoxRememberMe=findViewById(R.id.remember_me_chkb);
        AdminLink=findViewById(R.id.admin_panel_link);
        NotAdminLink=findViewById(R.id.not_admin_panel_link);
        forgetPasswordLink=findViewById(R.id.forget_password);
        Paper.init(this);

        forgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check","login");
                startActivity(intent);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();

            }


        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName="Admins";

            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });

    }


    private void LoginUser() {
        String phone=Inputnumber.getText().toString();
        String password=InputPassword.getText().toString();
        if(TextUtils.isEmpty(phone))
        {
            Inputnumber.setError("please write your phone number!");
        }
        else if(TextUtils.isEmpty(password)) {
            InputPassword.setError("Please Write password!");
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait,  while we are Checking for credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AllowAccesstoAccount(phone,password);
        }
    }

    private void AllowAccesstoAccount(final String phone, final String password) {
        if(ChkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users usersData=dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if(usersData.getPhone().equals(phone))
                    {
                        if(usersData.getPassword().equals(password))
                        {
                            if(parentDbName.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in successfully... ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "logged in successfully... ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalent.currentonlineUser = usersData;
                                startActivity(intent);
                            }


                        }
                        else{
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Pasword is incorrect.", Toast.LENGTH_SHORT).show();
                    }

                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account with this "+ phone +" number do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "you need to create a new Account. ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
