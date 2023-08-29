/*
 * Copyright (C) 2018-2020 Roundworks Technologies Private Limited, info@alveo.fit
 *
 * This file is part of alveofit.
 *
 *  alveofit can not be copied and/or distributed without the express
 * permission of Roundworks Technologies Private Limited
 *
 */

package com.shubham.mathexpression.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shubham.mathexpression.models.DataModel;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {
    /**
     * converter for Urls list field
     * @param list
     * @return
     */
    @TypeConverter
    public String fromDataList(List<DataModel> list) {
        if (list == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DataModel>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }

    @TypeConverter
    public List<DataModel> toDataList(String string) {
        if (string == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DataModel>>() {}.getType();
        List<DataModel> list = gson.fromJson(string, type);
        return list;
    }
}