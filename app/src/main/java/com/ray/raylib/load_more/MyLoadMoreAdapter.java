package com.ray.raylib.load_more;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ray.lib.loadmore_recyclerview.LoadingMoreType;
import com.ray.raylib.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Author : hikobe8@github.com
 * Time : 2018/8/22 下午11:57
 * Description :
 */
public class MyLoadMoreAdapter extends com.ray.lib.loadmore_recyclerview.LoadMoreAdapter {

    private List<String> mList = new ArrayList<>();

    public void refreshData(List<String> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
//        setFooterState(LoadingMoreType.TYPE_LOADING);
    }

    public void addData(List<String> list){
        final int preStartIndex = getItemCount();
        mList.addAll(list);
        notifyItemRangeInserted(preStartIndex, list.size());
    }

    @Override
    protected RecyclerView.ViewHolder onCreateNormalViewHolder(@NonNull ViewGroup parent) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    protected void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.bind(mList.get(position));
    }

    @Override
    protected int getNormalItemCount() {
        return mList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        TextView mTvText;

        public MyHolder(View itemView) {
            super(itemView);
            mTvText = itemView.findViewById(R.id.tv_text);
        }

        public void bind(String text) {
            mTvText.setText(text);
        }

    }

}
