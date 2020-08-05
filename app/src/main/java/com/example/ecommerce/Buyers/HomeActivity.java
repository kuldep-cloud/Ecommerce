package com.example.ecommerce.Buyers;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecommerce.MainActivity;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.R;
import com.example.ecommerce.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;

import com.example.ecommerce.Admin.AdminMaintainProductsActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference ProductRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Paper.init(this);

        /*type=getIntent().getExtras().get("com.example.ecommerce.Admin").toString();*/

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            type=getIntent().getExtras().get("com/example/ecommerce/Admin").toString();
        }


        ProductRef= FirebaseDatabase.getInstance().getReference().child("Products");


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                            if(!type.equals("com/example/ecommerce/Admin"))
                            {
                                Intent intent=new Intent(HomeActivity.this, CartActivity.class);
                                startActivity(intent);

                            }
                }
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_cart, R.id.nav_Search, R.id.nav_category,
                R.id.nav_settings, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int menuId = destination.getId();
                switch (menuId) {

                    case R.id.nav_cart:
                        if(!type.equals("com/example/ecommerce/Admin"))
                        {


                        }
                       /* Intent intent3=new Intent(HomeActivity.this,CartActivity.class);
                        startActivity(intent3);*/
                        break;
                    case R.id.nav_Search:
                        if(!type.equals("com/example/ecommerce/Admin"))
                        {
                            Intent intent3=new Intent(HomeActivity.this, SearchProductsActivity.class);
                            startActivity(intent3);
                        }
                        break;
                    case R.id.nav_category:

                        break;
                    case R.id.nav_settings:
                        if(!type.equals("com/example/ecommerce/Admin"))
                        {
                            Intent intent=new Intent(HomeActivity.this, SettingsActivity.class);
                            startActivity(intent);
                        }

                        break;
                    case R.id.nav_logout:
                        if(!type.equals("com/example/ecommerce/Admin"))
                        {
                            Paper.book().destroy();
                            Intent intent2=new Intent(HomeActivity.this, MainActivity.class);
                            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent2);
                            Toast.makeText(HomeActivity.this, "logout succesfully", Toast.LENGTH_SHORT).show();
                        }
                        break;


                }
            }
        });




        View  headerView = navigationView.getHeaderView(0);
        TextView userNameTextView= headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView=headerView.findViewById(R.id.user_profile_image);


        if(!type.equals("com/example/ecommerce/Admin"))
        {
            userNameTextView.setText(Prevalent.currentonlineUser.getName());
            Picasso.get().load(Prevalent.currentonlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products>options=new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductRef, Products.class)
                .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                holder.txtproductName.setText(model.getPname());
                holder.txtproductDescription.setText(model.getDescription());
                holder.txtProductPrice.setText("Price = "+ model.getPrice()+" $");
                Picasso.get().load(model.getImage()).into(holder.imageView);



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(type.equals("com/example/ecommerce/Admin"))
                        {
                            Intent intent=new Intent(HomeActivity.this, AdminMaintainProductsActivity.class);
                            intent.putExtra("pid",model.getPid());
                            startActivity(intent);

                        }
                        else
                        {
                            Intent intent4=new Intent(HomeActivity.this, ProductDetailsActivity.class);
                            intent4.putExtra("pid",model.getPid());
                            startActivity(intent4);

                        }

                    }
                });


            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                ProductViewHolder holder=new ProductViewHolder(view);
                return  holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}
