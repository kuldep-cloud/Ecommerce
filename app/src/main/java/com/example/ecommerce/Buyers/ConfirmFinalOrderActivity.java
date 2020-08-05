package com.example.ecommerce.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText nameEdittext,phoneEdittext,addressEdittext,cityEdittext;
    private Button confirmButton;
    private String totalAmount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirm_final_order);

        totalAmount=getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = $ "+totalAmount, Toast.LENGTH_SHORT).show();
        nameEdittext=findViewById(R.id.shippment_name);
        phoneEdittext=findViewById(R.id.shippment_phone_number);
        addressEdittext=findViewById(R.id.shippment_address);
        cityEdittext=findViewById(R.id.shippment_city);
        confirmButton=findViewById(R.id.confirm_final_order_btn);



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });

    }

    private void Check() {

        if(TextUtils.isEmpty(nameEdittext.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Your Full Name.", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(phoneEdittext.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Your Phone Number.", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(addressEdittext.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Your Address.", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(cityEdittext.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Your city name.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ConfirmOrder();
        }

    }

    private void ConfirmOrder() {

        final String saveCurrentDate,saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentonlineUser.getPhone());
        HashMap<String,Object> ordersMap=new HashMap<>();
        ordersMap.put("totalAmount",totalAmount);
        ordersMap.put("name",nameEdittext.getText().toString());
        ordersMap.put("phone",phoneEdittext.getText().toString());
        ordersMap.put("address",addressEdittext.getText().toString());
        ordersMap.put("city",cityEdittext.getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("state","not shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentonlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Your Final Order Has Been Placed Successfully.", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });
                }
            }
        });
    }
}
