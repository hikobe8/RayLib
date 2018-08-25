package com.ray.raylib.load_more;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.ray.lib.loadmore_recyclerview.LoadMoreAdapter;
import com.ray.lib.loadmore_recyclerview.LoadMoreRecyclerView;
import com.ray.raylib.R;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreRecyclerActivity extends AppCompatActivity {

    private LoadMoreRecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private MyLoadMoreAdapter mMyLoadMoreAdapter;
    private int mPage = 1;

    private List<String> createData() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i <  10; i++) {
            strings.add("test" + mPage + i);
        }
        mPage ++;
        return strings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_recycler);
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMyLoadMoreAdapter.setDataRefreshing();
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                mPage = 1;
                mMyLoadMoreAdapter.refreshData(createData());
                mMyLoadMoreAdapter.setDataLoaded();
            }
        });
        mRecyclerView = findViewById(R.id.recycler);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMyLoadMoreAdapter = new MyLoadMoreAdapter();
        mMyLoadMoreAdapter.refreshData(createData());
        mRecyclerView.setAdapter(mMyLoadMoreAdapter);
        mRecyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        mMyLoadMoreAdapter.setOnLoadInErrorStateListener(new LoadMoreAdapter.OnLoadInErrorStateListener() {
            @Override
            public void onLoadInErrorState() {
                mMyLoadMoreAdapter.setDataLoading();
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<String> data = createData();
                        mMyLoadMoreAdapter.addData(data);
                        mMyLoadMoreAdapter.setDataLoaded();
                    }
                }, 800);
            }

        });
    }

    private void loadMoreData() {
        Log.e("test", "load more");
        mMyLoadMoreAdapter.setDataLoading();
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                final List<String> data = createData();
                mMyLoadMoreAdapter.addData(data);
                mMyLoadMoreAdapter.setDataLoaded();
                if (mPage == 4) {
                    mMyLoadMoreAdapter.setDataLoadError();
                }
                if (mPage >= 6) {
                    mMyLoadMoreAdapter.setLastPage();
                }

            }
        },500);


    }
}
