package com.shubham.mathexpression.activity;

import static com.shubham.mathexpression.network.Urls.APP_BASE_URL;
import static com.shubham.mathexpression.utils.AppConstants.SIMPLE_DATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shubham.mathexpression.R;
import com.shubham.mathexpression.database.repositoryDB.ExpressionRepository;
import com.shubham.mathexpression.models.DataModel;
import com.shubham.mathexpression.models.ResultModel;
import com.shubham.mathexpression.network.APIClient;
import com.shubham.mathexpression.network.APIInterface;
import com.shubham.mathexpression.utils.AppCommonMethods;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtExpression;
    private AppCompatButton btnEvaluate;
    private AppCompatButton btnSave;
    private TextView tvResults;
    private ProgressBar pbLoading;
    private List<DataModel> dataModelList = new ArrayList<>();
    private ResultModel resultModel = new ResultModel();
    private ExpressionRepository expressionRepository;
    private int id = 0;
    private Activity activity;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        initController();
    }

    /**
     * asking user to grant permission so that database can be saved on device itself
     */
    private void getPermission() {
        if (Build.VERSION.SDK_INT < 23 ||
                ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            Log.e("Permission Granted", "true");
        } else {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    /**
     * initializing all
     */
    private void initController() {
        activity = MainActivity.this;
        expressionRepository = new ExpressionRepository(activity);
        edtExpression = findViewById(R.id.edt_Expression);
        btnEvaluate = findViewById(R.id.btn_Evaluate);
        btnSave = findViewById(R.id.btn_save);
        tvResults = findViewById(R.id.tv_results);
        pbLoading = findViewById(R.id.pb_loading);
        btnEvaluate.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        edtExpression.requestFocus();
    }


    /**
     * onClick listener for buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_Evaluate) {
            breakExpression();
        }
        if (i == R.id.btn_save) {
            expressionRepository.insertCharacterData(resultModel);
            Toast.makeText(activity, "Data inserted Successfully", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * initializing app bar menu item
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * assigning action on app bar menu item
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_history) {
            startActivity(new Intent(activity, HistoryActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * here I split the user input on the basis of next line
     * next, for each expression API will called and same expression is pass to the DataModel to save the details
     */
    private void breakExpression() {
        tvResults.setText("");
        dataModelList = new ArrayList<>();
        resultModel = new ResultModel();
        id = 0;
        try {
            String[] expression = edtExpression.getText().toString().split("\n");
            int length = expression.length;
            Log.e("lenght", length+"");
            for (int i = 0 ; i < expression.length ; i++) {
                if (!expression[i].equals("")) {
                    String encodedItem = URLEncoder.encode(expression[i], "utf-8");
                    evaluateResult(expression[i], encodedItem);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * here, API is called without waiting response from server side
     * showText will be pass on the next function
     * expression will be pass as parameter to the API
     * @param showText
     * @param expression
     */
    private void evaluateResult(String showText, String expression) {
        APIInterface apiInterface = APIClient.getClient(activity).create(APIInterface.class);
        pbLoading.setVisibility(View.VISIBLE);
        Call<String> call = apiInterface.callGetCharactersList(APP_BASE_URL, expression);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    pbLoading.setVisibility(View.GONE);
                    try {
                        Log.e("strJsonOfBody", new Gson().toJson(response.body()));
                        setViewToResult(showText, new Gson().toJson(response.body()));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                call.cancel();
            }
        });
    }

    /**
     * response will be shown on the screen
     * expression will be stored along with result string
     * @param expression
     * @param result
     */
    private void setViewToResult(String expression, String result) {
        DataModel dataModel = new DataModel();
        dataModel.setResultId(id++);
        dataModel.setResult(expression + " = " + result);
        dataModelList.add(dataModel);
        resultModel.setDateTime(AppCommonMethods.getDate(SIMPLE_DATE));
        resultModel.setDataModels(dataModelList);
        Log.e("resulto", new Gson().toJson(resultModel));
        tvResults.append(id + ") " + expression + " => " + result + "\n");
    }
}