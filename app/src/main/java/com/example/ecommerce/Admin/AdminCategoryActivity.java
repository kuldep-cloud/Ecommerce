package com.example.ecommerce.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecommerce.Buyers.HomeActivity;
import com.example.ecommerce.MainActivity;
import com.example.ecommerce.R;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tshirts,sportsTshirts,femaleDreses,sweaters;
    private ImageView glasses,hatscaps,walletBagsPurses,shoes;
    private ImageView headPhonesHandfree,laptops,watches,mobilesphones;

    private Button LogoutBtn,CheckOrdersBtn,maintainProductsBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn=findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn=findViewById(R.id.check_orders_btn);
        maintainProductsBtn=findViewById(R.id.maintain_btn);

        tshirts=findViewById(R.id.tshirts);
        sportsTshirts=findViewById(R.id.sports_tshirts);
        femaleDreses=findViewById(R.id.female_dresses);
        sweaters=findViewById(R.id.sweater_dresses);
        glasses=findViewById(R.id.glasses);
        hatscaps=findViewById(R.id.hats);
        walletBagsPurses=findViewById(R.id.wallet_purses_bags);
        shoes=findViewById(R.id.shoes);
        headPhonesHandfree=findViewById(R.id.headphones_handfree);
        laptops=findViewById(R.id.laptop_pc);
        watches=findViewById(R.id.wathes);
        mobilesphones=findViewById(R.id.mobile_phones);

        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this, HomeActivity.class);
               intent.putExtra("com/example/ecommerce/Admin", "com/example/ecommerce/Admin");
                startActivity(intent);
                finish();

            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                Toast.makeText(AdminCategoryActivity.this, "com.example.ecommerce.Admin logout Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);


            }
        });


        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","tshirts");
                startActivity(intent);

            }
        });
        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","sportsTshirts");
                startActivity(intent);

            }
        });
        femaleDreses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","femaleDreses");
                startActivity(intent);

            }
        });
        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","sweaters");
                startActivity(intent);

            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","glasses");
                startActivity(intent);

            }
        });
        hatscaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","hatscaps");
                startActivity(intent);

            }
        });
        walletBagsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","walletBagsPurses");
                startActivity(intent);

            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","shoes");
                startActivity(intent);

            }
        });
        headPhonesHandfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","headPhonesHandfree");
                startActivity(intent);

            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","laptops");
                startActivity(intent);

            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","watches");
                startActivity(intent);

            }
        });
        mobilesphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","mobilesphones");
                startActivity(intent);

            }
        });
    }
}
