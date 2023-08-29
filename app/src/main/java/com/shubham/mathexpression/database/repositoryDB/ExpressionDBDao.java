package com.shubham.mathexpression.database.repositoryDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.shubham.mathexpression.models.ResultModel;

import java.util.List;

@Dao
public interface ExpressionDBDao {
    @Insert
    Long insertData(ResultModel resultModel);

    @Query("DELETE FROM result_table")
    void deleteData();

    @Query("SELECT * FROM result_table")
    LiveData<List<ResultModel>> getAll();
}
