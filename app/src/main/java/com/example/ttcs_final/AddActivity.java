package com.example.ttcs_final;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.ttcs_final.DAO.SQLHelper;
import com.example.ttcs_final.Model.CongViec;
import com.example.ttcs_final.Model.User;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    public Spinner sp;
    private EditText eTitle, eContent, eDate;
    private Button btUpdate, btCancel, btSedate;
    private RadioButton btChung, btRieng;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("User");
        initView();
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btSedate.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }
    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.tvTitle);
        eContent = findViewById(R.id.tvContent);
        eDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btCancel);
        btSedate = findViewById(R.id.btSedate);
        btRieng = findViewById(R.id.rRieng);
        btChung = findViewById(R.id.rChung);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,getResources().getStringArray(R.array.status)));
    }

    @Override
    public void onClick(View view) {
        if(view == btSedate){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m > 8) {
                        date = d+"/"+(m+1)+"/"+y;

                    }else{
                        date = d+"/0"+(m+1)+"/"+y;
                    }
                    if(d <= 9) {
                        date = "0" + date;
                    }
                    eDate.setText(date);
                }
            },year, month, day);
            dialog.show();
        }
        if(view==btCancel) {
            finish();
        }
        if(view == btUpdate) {
            String t = eTitle.getText().toString();//name
            String p = eContent.getText().toString();//content
            String c = sp.getSelectedItem().toString();
            String d = eDate.getText().toString();
            int select = 0;
            if(btRieng.isChecked()) {
                select = 1;
            }
            if(btChung.isChecked()) {
                select = 0;
            }
            if(!t.isEmpty()) {
                CongViec i = new CongViec(t,p, d, c, select);
                SQLHelper db = new SQLHelper(this);
                db.addItem(i);
                finish();

            }
        }
    }
}