package com.example.app_foodstore.Activity;


import static com.example.app_foodstore.Constant.ConstantVariable.IMG_URL;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.app_foodstore.Model.response.UserRes;
import com.example.app_foodstore.R;
import com.example.app_foodstore.Utils.ImageUtils;
import com.example.app_foodstore.ViewModel.UserViewModel;

import java.io.IOException;

import okhttp3.MultipartBody;

public class EditPersonalInfoActivity extends AppCompatActivity {
    ImageButton btnChooseImage;
    AppCompatButton btnSave;
    ImageView imgAvatar;
    Uri mUri;
    UserRes user;
    EditText etName, etPhone, etBio;
    TextView tvEmail;
    UserViewModel userViewModel;
    String fullname, email, phone;
    MultipartBody.Part avatarUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal__info);

        AnhXa();
    }

    private void AnhXa() {
        initViewModel();
        getUser();
        setupUser();
        setupBtnChooseImage();
        setUpBtnSave();
    }

    private void initViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void setUpBtnSave() {
        btnSave = findViewById(R.id.edit_PI_btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isEnough = gatherInformation();
                if (isEnough) {
                    userViewModel.updateUserProfile(fullname, email, phone, avatarUri);
                }
                else {
                    Toast.makeText(EditPersonalInfoActivity.this, "Please fill all the information!", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean gatherInformation() {
                fullname = etName.getText().toString();
                email = tvEmail.getText().toString();
                phone = etPhone.getText().toString();
                if (mUri != null)
                {
                    avatarUri = ImageUtils.toMultipartBody(EditPersonalInfoActivity.this, mUri, "avatar");
                }
                return !fullname.isEmpty() && !email.isEmpty() && phone.length() == 10 && avatarUri != null;
            }
        });
    }

    private void setupBtnChooseImage() {
        btnChooseImage = findViewById(R.id.edit_PI_btnChooseImage);
        btnChooseImage.setOnClickListener(v -> CheckPermission());
    }

    private void setupUser() {
        imgAvatar = findViewById(R.id.edit_PI_avatar);
        Glide.with(this).load(IMG_URL + user.getProfile_image()).into(imgAvatar);
        etName = findViewById(R.id.edit_PI_et_Name);
        etName.setText(user.getFullname());
        tvEmail = findViewById(R.id.edit_PI_tv_email);
        tvEmail.setText(user.getEmail());
        etPhone = findViewById(R.id.edit_PI_et_phone);
        etPhone.setText(user.getPhone_number());
        etBio = findViewById(R.id.edit_PI_et_bio);
        //etBio.setText(user.getBio());
    }

    private void getUser() {
        user = (UserRes) getIntent().getSerializableExtra("user");
    }

    public static final int MY_REQUEST_CODE=100;
    public static String[] storge_permissions = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 = {
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO
    };
    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storge_permissions_33;
        } else {
            p = storge_permissions;
        }
        return p;
    }
    private void CheckPermission() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            //ActivityCompat.requestPermissions(UploadImageActivity.this,permissions(),MY_REQUEST_CODE);
            //hoặc
            requestPermissions(permissions(),MY_REQUEST_CODE);
        }
    }
    //Hàm xử lý lấy ảnh
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e("logd",  "onActivityResult");
                    if(result.getResultCode()== Activity.RESULT_OK){
                        //request code
                        Intent data = result.getData();
                        if(data==null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imgAvatar.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
}
