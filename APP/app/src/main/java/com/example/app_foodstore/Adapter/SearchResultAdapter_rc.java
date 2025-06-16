package com.example.app_foodstore.Adapter;

import static com.example.app_foodstore.Constant.ConstantVariable.IMG_URL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Activity.FoodDetailActivity;
import com.example.app_foodstore.Utils.UserUtils;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.CartViewModel;

import java.util.List;

public class SearchResultAdapter_rc extends RecyclerView.Adapter<SearchResultAdapter_rc.ViewHolder> {
    Context context;
    List<FoodModel> foods;
    CartViewModel cartViewModel;
    public SearchResultAdapter_rc(Context context, List<FoodModel> foods) {
        this.context = context;
        this.foods = foods;
        cartViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(CartViewModel.class);
    }

    @NonNull
    @Override
    public SearchResultAdapter_rc.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.fragment_food_display4, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter_rc.ViewHolder holder, int position) {
        FoodModel food = foods.get(position);
        holder.tv_foodName.setText(food.getName());
        holder.tv_categoryName.setText(food.getCategory_name());
        holder.tv_price.setText(String.valueOf(Math.round(food.getPrice())) + " VND");
        Glide.with(context).load(IMG_URL + food.getThumbnail()).into(holder.imgFood);
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(food);
            }
        });
        holder.layout_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Click on food: " + food.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), FoodDetailActivity.class);
                intent.putExtra("foodModel", food);
                view.getContext().startActivity(intent);
            }
        });
    }
    private void addToCart(FoodModel foodModel) {
        int quantity = 1;
        Long productId = foodModel.getId();
        String token = UserUtils.getAccessToken(context);

        if (token == null || token.isEmpty()) {
            Toast.makeText(context, "Bạn cần đăng nhập để thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("DEBUG", "Token: " + token);
        Log.d("DEBUG", "Product ID: " + productId);
        Log.d("DEBUG", "Quantity: " + quantity);

        cartViewModel.addCartItem(token, productId, quantity).observe((LifecycleOwner) context, success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                Log.e("ERROR", "Failed to add to cart. Token: " + token + ", Product ID: " + productId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        ImageButton btn_add;
        TextView tv_foodName, tv_categoryName, tv_price;
        FrameLayout layout_food;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.fragment_food_display4_imgFood);
            btn_add = itemView.findViewById(R.id.fragment_food_display4_btn_add);
            tv_foodName = itemView.findViewById(R.id.fragment_food_display4_tv_foodName);
            tv_categoryName = itemView.findViewById(R.id.fragment_food_display4_tv_categoryName);
            tv_price = itemView.findViewById(R.id.fragment_food_display4_tv_price);
            layout_food = itemView.findViewById(R.id.fragment_food_display4);
        }
    }
}
