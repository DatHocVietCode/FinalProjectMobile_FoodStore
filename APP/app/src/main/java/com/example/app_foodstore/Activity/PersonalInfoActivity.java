package com.example.app_foodstore.Activity;


import static com.example.app_foodstore.Constant.ConstantVariable.IMG_URL;

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
import com.example.app_foodstore.Utils.UserUtils;
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

        AnhXa();
    }

    private void loadData() {
        // Gọi ViewModel để lấy dữ liệu người dùng
        userViewModel.getUserProfile(UserUtils.getAccessToken(this));
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
        // Set dữ liệu vào các TextView với kiểm tra null/rỗng
        tvFullname.setText(safeText(userRes.getFullname()));
        tvPhone.setText(safeText(userRes.getPhone_number()));
        tvEmail.setText(safeText(userRes.getEmail()));
        tvDob.setText(safeText(userRes.getDate_of_birth()));
        tvName.setText(safeText(userRes.getFullname()));

        // Kiểm tra profile_image trước khi tải ảnh
        String profileImage = userRes.getProfile_image();
        if (profileImage != null && !profileImage.trim().isEmpty()) {
            Glide.with(PersonalInfoActivity.this)
                    .load(IMG_URL + profileImage)
                    .placeholder(R.drawable.user_avatar_sample)
                    .error(R.drawable.baseline_account_circle_24)
                    .into(imageView);
        } else {
            // Ảnh mặc định nếu chưa có avatar
            imageView.setImageResource(R.drawable.baseline_account_circle_24);
        }
    }
    // Helper: nếu chuỗi null hoặc rỗng → trả về "Chưa cập nhật"
    String safeText(String input) {
        return (input != null && !input.trim().isEmpty()) ? input : "Unknown";
    }
    private void AnhXa() {
        initViewModel();
        loadData();
        setUpTvEdit();
    }

    private void initViewModel() {
        // Khởi tạo UserViewModel thông qua ViewModelProvider
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void setUpTvEdit() {
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
