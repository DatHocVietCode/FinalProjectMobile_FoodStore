package com.example.app_foodstore.Activity;

import static com.example.app_foodstore.APIService.Constant.IMG_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.APIService.Constant;
import com.example.app_foodstore.APIService.User.APIServiceUser;
import com.example.app_foodstore.Model.response.BaseResponse;
import com.example.app_foodstore.Model.response.UserRes;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.UserViewModel;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalInfoActivity extends AppCompatActivity {
    TextView tv_edit;
    UserViewModel userViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        // Khởi tạo UserViewModel thông qua ViewModelProvider
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        loadData();
        AnhXa();
    }

    private void loadData() {
        // Lấy token từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);

        String token = prefs.getString("access_token", "");
        // Gọi ViewModel để lấy dữ liệu người dùng
        userViewModel.getUserProfile(token);

        // Quan sát dữ liệu thay đổi
        userViewModel.getUserProfileLiveData().observe(this, new Observer<UserRes>() {
            @Override
            public void onChanged(UserRes userRes) {
                if (userRes != null) {
                    // Cập nhật UI khi dữ liệu người dùng thay đổi
                    updateUI(userRes);
                } else {
                    Toast.makeText(PersonalInfoActivity.this, "Không lấy được dữ liệu người dùng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI(UserRes userRes) {
        // Cập nhật thông tin người dùng lên giao diện
        TextView tvFullname = findViewById(R.id.personal_fullname);
        TextView tvPhone = findViewById(R.id.personal_phone);
        TextView tvEmail = findViewById(R.id.personal_email);
        TextView tvDob = findViewById(R.id.personal_dob);
        CircleImageView imageView = findViewById(R.id.personal_image);
        TextView tvName = findViewById(R.id.personal_name);

        // Set dữ liệu vào các TextView
        tvFullname.setText(userRes.getFullname());
        tvPhone.setText(userRes.getPhone_number());
        tvEmail.setText(userRes.getEmail());
        tvDob.setText(userRes.getDate_of_birth());
        tvName.setText(userRes.getFullname());

        // Tải ảnh đại diện với Glide
        Glide.with(PersonalInfoActivity.this)
                .load(IMG_URL + userRes.getProfile_image()) // Thay "IMG_URL" bằng URL thực tế của ảnh
                .placeholder(R.drawable.user_avatar_sample)  // Ảnh placeholder khi ảnh chưa tải
                .error(R.drawable.user_avatar_sample)       // Ảnh hiển thị khi có lỗi tải ảnh
                .into(imageView);
    }
    private void AnhXa() {
        tv_edit = findViewById(R.id.PI_tv_edit);
        tv_edit.setOnClickListener(v -> {
            userViewModel.getUserProfileLiveData().observe(this, userRes ->
            {
                Intent intent = new Intent(PersonalInfoActivity.this, EditPersonalInfoActivity.class);
                intent.putExtra("user", userRes);
                startActivity(intent);
            });

        });
    }
}
