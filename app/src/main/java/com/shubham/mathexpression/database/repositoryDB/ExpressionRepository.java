package com.shubham.mathexpression.database.repositoryDB;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.shubham.mathexpression.database.roomDBAbstract.ExpressionDatabase;
import com.shubham.mathexpression.models.ResultModel;

import java.util.List;

public class ExpressionRepository {
    private static final String DB_NAME = "ExpressionData";
    private ExpressionDatabase expressionDatabase;
    private LiveData<List<ResultModel>> resultModelList;
    private Context context;

    public ExpressionRepository(Context context) {
        expressionDatabase = Room.databaseBuilder(context, ExpressionDatabase.class, DB_NAME).allowMainThreadQueries().build();
        resultModelList = expressionDatabase.expressionDBDao().getAll();
        this.context = context;
    }

    public void insertCharacterData(final ResultModel resultModel) {
        try {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    expressionDatabase.expressionDBDao().insertData(resultModel);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllExpression() {
        expressionDatabase.expressionDBDao().deleteData();
    }

    public LiveData<List<ResultModel>> getAllExpressions() {
        return resultModelList;
    }
}
