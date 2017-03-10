package com.example.su.waterquality.adapters;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.su.waterquality.R;

import java.util.ArrayList;

/**
 * Created by su on 2017/1/12.
 */

public class BTDeviceListAdapter extends BaseAdapter{
    private ArrayList<BluetoothDevice> mLeDevices;
    private LayoutInflater mInflator;

    public BTDeviceListAdapter(LayoutInflater layoutInflater) {
        super();
        mLeDevices = new ArrayList<>();
        mInflator = layoutInflater;
    }

    public void addDevice(BluetoothDevice device) {
        if (!mLeDevices.contains(device)) {
            mLeDevices.add(device);
        }
    }

    public BluetoothDevice getDevice(int position) {
        return mLeDevices.get(position);
    }


    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    //返回每个item显示的view
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        // General ListView optimization code.
        //用设备名和设备地址填充view
        if (view == null) {
            view = mInflator.inflate(R.layout.device_content_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.deviceAddress = (TextView) view.findViewById(R.id.deviceAddr);
            viewHolder.deviceName = (TextView) view.findViewById(R.id.deviceName);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        BluetoothDevice device = mLeDevices.get(i);
        final String deviceName = device.getName();
        if (deviceName != null && deviceName.length() > 0)
            viewHolder.deviceName.setText(deviceName);
        else
            viewHolder.deviceName.setText(R.string.unknown_device);
        viewHolder.deviceAddress.setText(device.getAddress());

        return view;
    }

    class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }

}
