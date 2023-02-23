package com.eazywrite.app.ui.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eazywrite.app.R;
import com.eazywrite.app.data.model.OutputBean;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<OutputBean> beans;
    private ArrayList<OutputBean> coloredBeans;
    private Context mContext;
    private GridLayoutManager layoutManager;

    public RecycleViewAdapter(ArrayList<OutputBean> beans, Context context, ArrayList<OutputBean> coloredBeans
            , GridLayoutManager layoutManager) {
        super();
        this.beans = beans;
        this.mContext = context;
        this.coloredBeans = coloredBeans;
        this.layoutManager = layoutManager;
    }
    boolean isClick = false;
    int prePosition;

    public void resume() {
        ImageView icon = (ImageView) layoutManager.findViewByPosition(prePosition).findViewById(R.id.icon);
        icon.setImageResource(beans.get(prePosition).getImageId());
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder view = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
        view.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if(!isClick){
                    int position  = view.getAdapterPosition();
                    view.icon.setImageResource(coloredBeans.get(position).getImageId());
                    prePosition = position;
                    isClick = true;
                }else{
                    int position  = view.getAdapterPosition();
                    ImageView icon = (ImageView) layoutManager.findViewByPosition(prePosition).findViewById(R.id.icon);
                    icon.setImageResource(beans.get(prePosition).getImageId());
                    view.icon.setImageResource(coloredBeans.get(position).getImageId());
                    prePosition = position;
                }


            }
        });
        return view;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.icon.setImageResource(beans.get(position).getImageId());
        holder.name.setText(beans.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
        }
    }
}
