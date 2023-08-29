package com.shubham.mathexpression.database.roomDBAbstract;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.shubham.mathexpression.database.DataConverter;
import com.shubham.mathexpression.database.repositoryDB.ExpressionDBDao;
import com.shubham.mathexpression.models.ResultModel;

@Database(entities = {ResultModel.class}, version = 2, exportSchema = true)
@TypeConverters({DataConverter.class})
public abstract class ExpressionDatabase extends RoomDatabase {
    public abstract ExpressionDBDao expressionDBDao();
}
