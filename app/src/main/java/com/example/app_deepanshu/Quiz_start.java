package com.example.app_deepanshu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.app_deepanshu.api.Api;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Quiz_start extends AppCompatActivity {

    RecyclerView recyclerView;
    SubjectAdapter adapter;
    List<Subject> subjectList=new ArrayList<>();
    List<Subject> subjects;
    ProgressBar progressBar;
  //  FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
     //   floatingActionButton = findViewById(R.id.fab);
        recyclerView=findViewById(R.id.recyclerView1);
//
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
//                Bundle b = new Bundle();
//                b.putString("flag", "subject");
//                intent.putExtras(b);
//                startActivity(intent);
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Subject>> call = api.getSubjects();
        call.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {

                subjects = response.body();
                for(Subject s: subjects) {
                    subjectList.add(
                            new Subject(
                                    s.getTitle(),
                                    s.getStd(),
                                    s.getId()
                            )
                    );
                }
                recyclerView = findViewById(R.id.recyclerView1);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Quiz_start.this));


                adapter = new SubjectAdapter(Quiz_start.this,subjectList);
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }




}
