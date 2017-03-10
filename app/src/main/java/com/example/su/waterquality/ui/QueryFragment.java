package com.example.su.waterquality.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.su.waterquality.R;
import com.example.su.waterquality.adapters.WaterDataListAdapter;
import com.example.su.waterquality.beans.HttpGetRes;
import com.example.su.waterquality.beans.WaterData;
import com.example.su.waterquality.interfaces.WaterQualityService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by su on 2017/1/12.
 */

public class QueryFragment extends Fragment implements View.OnClickListener, Callback<HttpGetRes> {

    private Button bt_query;
    private ListView mListView;
    private List<WaterData> mWaterDatas;
    private WaterDataListAdapter mWaterDataListAdapter;

    public static final String HTTP_URL="http://115.156.178.105:8080";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_query,container,false);
        initDatas();
        initViews(view);
        initListeners();
        return view;
    }

    private void initDatas() {
        mWaterDatas=new ArrayList<>();
    }


    private void initViews(View view) {
        bt_query= (Button) view.findViewById(R.id.bt_search);
        mListView= (ListView) view.findViewById(R.id.list_waterdata);
        mWaterDataListAdapter=new WaterDataListAdapter(getActivity(),mWaterDatas);
        mListView.setAdapter(mWaterDataListAdapter);
    }

    private void initListeners() {
        bt_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search:
                getWaterData();
                break;
        }
    }

    public void getWaterData() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(HTTP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WaterQualityService service=retrofit.create(WaterQualityService.class);
        Call<HttpGetRes> call = service.getWaterData();
        call.enqueue(this);

    }


    @Override
    public void onResponse(Call<HttpGetRes> call, Response<HttpGetRes> response) {
        mWaterDatas=response.body().getWaterDataList();
//        mWaterDataListAdapter.notifyDataSetChanged();
        mWaterDataListAdapter.addAll(mWaterDatas);
        Toast.makeText(getActivity(),"数据查询成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Call<HttpGetRes> call, Throwable t) {
        Toast.makeText(getActivity(),"连接失败："+call.request().url(),Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }
}
