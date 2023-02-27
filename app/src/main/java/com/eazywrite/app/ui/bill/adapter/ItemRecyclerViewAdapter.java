package com.eazywrite.app.ui.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eazywrite.app.R;
import com.eazywrite.app.ui.bill.fragment.InputViewModel;

import java.util.ArrayList;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {
    private ArrayList<InputViewModel> mList;
    private Context  mContext;

    public ItemRecyclerViewAdapter(ArrayList<InputViewModel> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InputViewModel inputViewModel = mList.get(position);
        holder.icon.setImageResource(inputViewModel.getBean().getValue().getImageId());
        holder.name.setText(inputViewModel.getBean().getValue().getName());
        holder.beiZhu.setText(inputViewModel.getBeiZhu().getValue());
        holder.money.setText(inputViewModel.getMoneyCount().getValue());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView beiZhu;
        TextView time;
        TextView money;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            beiZhu = itemView.findViewById(R.id.beiZhu);
            time = itemView.findViewById(R.id.time);
            money = itemView.findViewById(R.id.money);
            name = itemView.findViewById(R.id.name);
        }
    }
}
