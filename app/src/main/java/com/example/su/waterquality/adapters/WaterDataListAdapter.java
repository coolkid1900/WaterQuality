package com.example.su.waterquality.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.su.waterquality.R;
import com.example.su.waterquality.beans.WaterData;

import java.util.Collection;
import java.util.List;

/**
 * Created by su on 2017/1/12.
 */

public class WaterDataListAdapter extends BaseAdapter{
    private Context mContext;
    private List<WaterData> mWaterDataList;

    public WaterDataListAdapter(Context context,List<WaterData> waterDataList) {
        this.mWaterDataList = waterDataList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mWaterDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mWaterDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  viewHolder;
        // General ListView optimization code.
        //用设备名和设备地址填充view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext). inflate(R.layout.waterdata_list_item,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        WaterData waterData = mWaterDataList.get(position);
        viewHolder.tv_name.setText(waterData.getName());
        viewHolder.tv_date.setText(waterData.getDate());
        viewHolder.tv_temperature.setText(String.valueOf(waterData.getTemperature()));
        viewHolder.tv_turbidity.setText(String.valueOf(waterData.getTurbidity()));
        viewHolder.tv_ph.setText(String.valueOf(waterData.getPh()));
        viewHolder.tv_nitrogen.setText(String.valueOf(waterData.getNitrogen()));
        viewHolder.tv_phosphorus.setText(String.valueOf(waterData.getPhosphorus()));
        return convertView;
    }

    public void addAll(Collection<? extends WaterData> collection){
        mWaterDataList.addAll(collection);
        notifyDataSetChanged();
    }

    class ViewHolder{
        TextView tv_name;
        TextView tv_date;
        TextView tv_temperature;
        TextView tv_turbidity;
        TextView tv_ph;
        TextView tv_nitrogen;
        TextView tv_phosphorus;

        public ViewHolder(View convertView) {
            tv_name = (TextView) convertView.findViewById(R.id.name);
            tv_date = (TextView) convertView.findViewById(R.id.date);
            tv_temperature= (TextView) convertView.findViewById(R.id.temperature);
            tv_turbidity= (TextView) convertView.findViewById(R.id.turbidity);
            tv_ph= (TextView) convertView.findViewById(R.id.ph);
            tv_nitrogen= (TextView) convertView.findViewById(R.id.nitrogen);
            tv_phosphorus= (TextView) convertView.findViewById(R.id.phosphorus);
        }
    }
}
