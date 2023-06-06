package com.example.ttcs_final.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcs_final.Adapter.RecycleViewAdapter;
import com.example.ttcs_final.AddActivity;
import com.example.ttcs_final.ChangePassWordActivity;
import com.example.ttcs_final.DAO.SQLHelper;
import com.example.ttcs_final.LoginActivity;
import com.example.ttcs_final.Model.CongViec;
import com.example.ttcs_final.Model.User;
import com.example.ttcs_final.R;
import com.example.ttcs_final.UpdateDeleteActivity;

import java.util.List;

public class FragmentProfile extends Fragment implements RecycleViewAdapter.ItemListener{
    private ImageView imageView;
    private TextView uName, uEmail;
    private Button changePass, logout;
    private SQLHelper db;
    private User user = new User();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new SQLHelper(getContext());
        imageView = view.findViewById(R.id.profile_picture);
        uName = view.findViewById(R.id.user_name);
        uEmail = view.findViewById(R.id.user_email);
        logout = view.findViewById(R.id.logout_button);
        changePass = view.findViewById(R.id.changepass_button);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePassWordActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        uName.setText("Tên của bạn: "+ user.getName());
        uEmail.setText("Email của bạn: "+user.getEmail());
    }

    @Override
    public void onItemClick(View view, int position) {

    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
