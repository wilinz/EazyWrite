package com.eazywrite.app.ui.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eazywrite.app.R;
import com.eazywrite.app.ui.bill.fragment.OutputBean;
import com.eazywrite.app.ui.bill.AddBillContentActivity;
import com.eazywrite.app.ui.bill.fragment.MyDialogFragment;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<OutputBean> beans;
    private ArrayList<OutputBean> coloredBeans;
    private Context mContext;
    private GridLayoutManager layoutManager;
    private FragmentManager fragmentManager;
    private AddBillContentActivity  mActivity;

    public RecycleViewAdapter(ArrayList<OutputBean> beans, Context context, ArrayList<OutputBean> coloredBeans
            , GridLayoutManager layoutManager, FragmentManager fragmentManager
            ,AddBillContentActivity addBillContentActivity) {
        super();
        this.beans = beans;
        this.mContext = context;
        this.coloredBeans = coloredBeans;
        this.layoutManager = layoutManager;
        this.fragmentManager = fragmentManager;
        this.mActivity = addBillContentActivity;
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

                showDialog();


            }
        });
        return view;
    }

    private void showDialog() {
        DialogFragment dialogFragment = new MyDialogFragment(mActivity,fragmentManager);
        dialogFragment.show(fragmentManager,"DialogFragment");
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
