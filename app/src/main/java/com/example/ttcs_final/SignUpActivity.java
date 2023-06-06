package com.example.ttcs_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ttcs_final.DAO.SQLHelper;
import com.example.ttcs_final.databinding.ActivitySignUpBinding;


public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    SQLHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sqlHelper = new SQLHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.signupName.getText().toString().trim();
                String email = binding.signupEmail.getText().toString().trim();
                String password = binding.signupPassword.getText().toString().trim();
                String confirmPassword = binding.signupConfirm.getText().toString().trim();

                if(email.equals("")||password.equals("")||confirmPassword.equals(""))
                    Toast.makeText(SignUpActivity.this, "Hãy nhập tất cả các trường", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = sqlHelper.checkEmail(email);
                        if(checkUserEmail == false){
                            Boolean insert = sqlHelper.insertData(name, email, password);
                            if(insert == true){
                                Toast.makeText(SignUpActivity.this, "Đăng kí tài khoản thành công!Hãy đăng nhập", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SignUpActivity.this, "Lỗi đăng kí tài khoản!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Tài khoản thật sự đã tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUpActivity.this, "Mật khẩu xác nhận khác nhau!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}