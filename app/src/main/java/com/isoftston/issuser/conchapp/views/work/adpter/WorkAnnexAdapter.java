package com.isoftston.issuser.conchapp.views.work.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.bean.FileBean;

import java.util.List;

public class WorkAnnexAdapter extends RecyclerView.Adapter<WorkAnnexAdapter.ViewHolder> {

    private List<FileBean> annexUrl;

    public WorkAnnexAdapter(List<FileBean> annexUrl) {
        this.annexUrl = annexUrl;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_form_annex,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String fileName = annexUrl.get(position).getFileName();
        holder.fileTv.setText(fileName);
        if( mOnItemClickListener!= null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return annexUrl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView fileTv;

        public ViewHolder(View itemView) {
            super(itemView);
            fileTv = itemView.findViewById(R.id.file_name);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onClick( int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}
