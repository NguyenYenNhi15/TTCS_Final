package com.example.ttcs_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ttcs_final.DAO.SQLHelper;
import com.example.ttcs_final.Model.User;

public class ChangePassWordActivity extends AppCompatActivity {
    private SQLHelper db;
    User user = new User();
    private EditText CurrenPass, NewPass,ConFirmPass;
    private Button btn_change, btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word);
        db = new SQLHelper(this);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("User");
        CurrenPass = findViewById(R.id.pasword);
        NewPass = findViewById(R.id.change_password);
        ConFirmPass = findViewById(R.id.change_password_confirm);
        btn_change = findViewById(R.id.btUpdate);
        btn_back = findViewById(R.id.btBack);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = String.valueOf(CurrenPass.getText()).trim();
                String Newpass = String.valueOf(NewPass.getText()).trim();
                String NewpassCon = String.valueOf(ConFirmPass.getText()).trim();
                if(!pass.equalsIgnoreCase(user.getPassword())){
                    Toast.makeText(ChangePassWordActivity.this, "Sai mật khẩu, hãy nhập lại.", Toast.LENGTH_SHORT).show();
                }else if(!Newpass.equalsIgnoreCase(NewpassCon)) {
                    Toast.makeText(ChangePassWordActivity.this, "Mật khẩu mới khác nhau hãy nhập lại", Toast.LENGTH_SHORT).show();
                }else if(pass.equalsIgnoreCase(NewpassCon)) {
                    Toast.makeText(ChangePassWordActivity.this, "Hãy nhập mật khẩu mới khác mật khẩu cũ.", Toast.LENGTH_SHORT).show();
                }else{
                    db.changePassword(user.getId(), Newpass);
                    Toast.makeText(ChangePassWordActivity.this, "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}