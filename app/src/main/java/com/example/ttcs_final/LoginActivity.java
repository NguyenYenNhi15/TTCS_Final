package com.example.ttcs_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ttcs_final.DAO.SQLHelper;
import com.example.ttcs_final.Model.User;
import com.example.ttcs_final.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SQLHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sqlHelper = new SQLHelper(this);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString().trim();
                String password = binding.loginPassword.getText().toString().trim();

                if(email.equals("")||password.equals(""))
                    Toast.makeText(LoginActivity.this, "Hãy nhập tất cả các trường", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = sqlHelper.checkEmailPassword(email, password);
                    if(checkCredentials == true){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        User user = sqlHelper.getUserByEmailAndPassword(email, password);
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("USER_OBJECT", user);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Thông tin tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}