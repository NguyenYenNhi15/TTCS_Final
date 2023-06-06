package com.example.ttcs_final.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcs_final.Adapter.RecycleViewAdapter;
import com.example.ttcs_final.AddActivity;
import com.example.ttcs_final.DAO.SQLHelper;
import com.example.ttcs_final.Model.CongViec;
import com.example.ttcs_final.Model.User;
import com.example.ttcs_final.R;
import com.example.ttcs_final.UpdateDeleteActivity;
import java.util.List;
public class FragmentThongTin extends Fragment implements RecycleViewAdapter.ItemListener{
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private SQLHelper db;
    private Button btAdd;
    private User user = new User();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongtin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclrView);
        //    Tablayout:
        btAdd = view.findViewById(R.id.buttonAdd);
        adapter = new RecycleViewAdapter();
        db = new SQLHelper((getContext()));
        List<CongViec> list = db.getAllCV();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        //    Tablayout:
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        CongViec congViec = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("congViec", congViec);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<CongViec>  list = db.getAllCV();
        adapter.setList(list);
    }
}

