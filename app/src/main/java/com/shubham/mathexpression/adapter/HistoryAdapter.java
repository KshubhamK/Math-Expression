package com.shubham.mathexpression.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.mathexpression.R;
import com.shubham.mathexpression.models.DataModel;
import com.shubham.mathexpression.models.ResultModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<ResultModel> resultModelList;
    private Activity activity;

    /**
     * constructor is called
     * @param resultModelList
     * @param activity
     */
    public HistoryAdapter(List<ResultModel> resultModelList, Activity activity) {
        this.resultModelList = resultModelList;
        this.activity = activity;
    }

    /**
     * view holder created for recycler view
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDateTime;
        private TextView tvResults;
        public MyViewHolder(View view) {
            super(view);
            tvDateTime = view.findViewById(R.id.tv_date_time);
            tvResults = view.findViewById(R.id.tv_results);
        }
    }

    /**
     * MyViewHolder attached to xml
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rcv_history_item, parent, false);

        return new HistoryAdapter.MyViewHolder(itemView);
    }

    /**
     * data attached in onBindViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ResultModel resultModel = resultModelList.get(position);
        if (resultModel != null) {
            holder.tvDateTime.setText(resultModel.getDateTime());
            for (DataModel dataModel : resultModel.getDataModels()) {
                holder.tvResults.append(dataModel.getResult() + "\n");
            }
        }
    }

    /**
     * list size get called
     * @return
     */
    @Override
    public int getItemCount() {
        return resultModelList == null ? 0 : resultModelList.size();
    }
}
