package com.example.ecommerce.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerce.Interface.ItemClickListner;
import com.example.ecommerce.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtproductName, txtproductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {

        super(itemView);

        txtproductName=itemView.findViewById(R.id.product_name);
        imageView=itemView.findViewById(R.id.product_image);
        txtproductDescription=(TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice=(TextView) itemView.findViewById(R.id.product_price);
    }




    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(), false);

    }
}


