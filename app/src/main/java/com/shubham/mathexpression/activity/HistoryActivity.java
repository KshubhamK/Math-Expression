package com.shubham.mathexpression.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.shubham.mathexpression.R;
import com.shubham.mathexpression.adapter.HistoryAdapter;
import com.shubham.mathexpression.database.repositoryDB.ExpressionRepository;
import com.shubham.mathexpression.models.ResultModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView rvHistory;
    private CardView cvNoRecord;
    private ExpressionRepository expressionRepository;
    private List<ResultModel> resultModelList = new ArrayList<>();
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initController();
    }

    private void initController() {
        activity = HistoryActivity.this;
        expressionRepository = new ExpressionRepository(activity);
        rvHistory = findViewById(R.id.rv_history);
        cvNoRecord = findViewById(R.id.cv_no_record);
        getLiveData();
    }

    private void getLiveData() {
        resultModelList.clear();
        expressionRepository.getAllExpressions().observe(HistoryActivity.this, new Observer<List<ResultModel>>() {
            @Override
            public void onChanged(List<ResultModel> resultModels) {
                for (ResultModel resultModel : resultModels) {
                    resultModelList.add(resultModel);
                    Log.e("character", new Gson().toJson(resultModel));
                }
                if (resultModelList.size() > 0) {
                    rvHistory.setVisibility(View.VISIBLE);
                    cvNoRecord.setVisibility(View.GONE);
                    setViewToHistoryList(resultModelList);
                }
                else {
                    rvHistory.setVisibility(View.GONE);
                    cvNoRecord.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setViewToHistoryList(List<ResultModel> resultModelList) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        rvHistory.setLayoutManager(mLayoutManager);
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        HistoryAdapter historyAdapter = new HistoryAdapter(resultModelList, activity);
        rvHistory.setAdapter(historyAdapter);
    }
}