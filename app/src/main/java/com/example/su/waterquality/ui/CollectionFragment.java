package com.example.su.waterquality.ui;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.su.waterquality.R;
import com.example.su.waterquality.adapters.BTDeviceListAdapter;
import com.example.su.waterquality.models.HttpPostRes;
import com.example.su.waterquality.models.WaterData;
import com.example.su.waterquality.interfaces.WaterQualityService;
import com.example.su.waterquality.services.BluetoothConnect;
import com.example.su.waterquality.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.su.waterquality.ui.QueryFragment.HTTP_URL;


/**
 * Created by su on 2017/1/12.
 */

public class CollectionFragment extends Fragment implements View.OnClickListener, Callback<HttpPostRes> {

    private static final int REQUEST_ENABLE_BT = 1;

    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    public static final String TAG = "MainActivity";

    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothDevice device = null;
    private BTDeviceListAdapter mBTDeviceListAdapter;
    private ListView mDeviceListview;
    private BluetoothConnect mBluetoothConnect = null;
    private String mConnectedDeviceName = null;
    private AlertDialog mAlertDialog;
    private EditText et_location;
    private EditText et_Temp;
    private EditText et_NTU;
    private Button bt_stop;
    private Button bt_upload;
    private FloatingActionButton fab;
    private Double temp;
    private Double ntu;
    private Double ph;
    private Integer type;
    private String name;
    private Double latitude = 0.0;
    private Double longitude = 0.0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        initBluetooth();
        initViews(view);
        initListeners();
        initLocation();
        return view;
    }

    private void initLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }else{
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 0,locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度
            }
        }
    }

    private void initViews(View view) {
        et_location= (EditText) view.findViewById(R.id.value_location);
        et_Temp= (EditText) view.findViewById(R.id.value_temp);
        et_NTU= (EditText) view.findViewById(R.id.value_ntu);
        bt_stop = (Button) view.findViewById(R.id.stop_collect);
        bt_upload = (Button) view.findViewById(R.id.upload_info);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

    }

    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 注册设备被发现时的广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);
        // 注册搜寻结束的广播
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(mReceiver, filter);
        if (mBluetoothAdapter == null) {
            // 设备不支持蓝牙
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("警告");
            dialog.setMessage("未发现本机蓝牙设备，请检测后重试！");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else if (mBluetoothConnect == null) {
                setupConnect();
            }
        }
    }

    private void initListeners() {
        bt_stop.setOnClickListener(this);
        bt_upload.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    private void setupConnect() {

        mBluetoothConnect = new BluetoothConnect(getActivity(), mHandler);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (!mBluetoothAdapter.isDiscovering()) {
                    int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                    mBluetoothAdapter.startDiscovery();
                    showDialog();
                } else {
                    Toast.makeText(getActivity(), "正在搜寻蓝牙设备，请等待……", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.stop_collect:
                stopCollect();
                Toast.makeText(getActivity(),"蓝牙连接已断开",Toast.LENGTH_LONG).show();
                break;
            case R.id.upload_info:
                uploadInfo();
                break;
        }
    }

    private void uploadInfo() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(HTTP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WaterQualityService service=retrofit.create(WaterQualityService.class);
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String data_str=sdf.format(date);
        WaterData waterdata=new WaterData(type,name,longitude,latitude,data_str,temp,ntu,ph);
        Call<HttpPostRes> call = service.postWaterData(waterdata);
        call.enqueue(this);
    }

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                mBTDeviceListAdapter.addDevice(device);
                mBTDeviceListAdapter.notifyDataSetChanged();
            }
        }
    };

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater layout = LayoutInflater.from(getActivity());
        View dialogView = layout.inflate(R.layout.device_list, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        dialog.setTitle("请选择您的水质检测设备");
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mBluetoothAdapter.cancelDiscovery();
            }
        });
        mBTDeviceListAdapter = new BTDeviceListAdapter(getActivity().getLayoutInflater());
        mDeviceListview = (ListView) dialogView.findViewById(R.id.list_device);
        mDeviceListview.setAdapter(mBTDeviceListAdapter);
        mDeviceListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final BluetoothDevice device = mBTDeviceListAdapter.getDevice(position);
                if (device == null) return;
                Intent intent = new Intent(getActivity(), WaterQualityActivity.class);
                intent.putExtra(EXTRA_DEVICE_ADDRESS, device.getAddress());
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }
                connectDevice(intent);
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog = dialog.show();
    }




    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mBluetoothConnect.connect(device, true);


    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothConnect.STATE_CONNECTED:
                            setStatus(mConnectedDeviceName);
                            break;
                        case BluetoothConnect.STATE_CONNECTING:
                            setStatus("connecting");
                            break;
                        case BluetoothConnect.STATE_LISTEN:

                            break;
                        case BluetoothConnect.STATE_NONE:
                            setStatus("not_connected");
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Log.d(TAG, readMessage);
                    String[] data = readMessage.split("\r\n");
                    try {
                        temp=Double.parseDouble(data[data.length - 2]);
                        ntu=Double.parseDouble(data[data.length - 1]);
                        et_Temp.setText(temp.toString());
                        et_NTU.setText(ntu.toString());
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);

                    Toast.makeText(getActivity(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();

                    break;
                case Constants.MESSAGE_TOAST:

                    Toast.makeText(getActivity(), msg.getData().getString(Constants.TOAST),
                            Toast.LENGTH_SHORT).show();
                    mBluetoothConnect.connect(device, true);
                    break;
            }
        }
    };

    private void setStatus(CharSequence subTitle) {
        Log.d("log", subTitle.toString());
    }


    private void stopCollect() {
        if (mBluetoothConnect != null) {
            mBluetoothConnect.stop();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBluetoothConnect != null) {
            mBluetoothConnect.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mBluetoothConnect != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mBluetoothConnect.getState() == BluetoothConnect.STATE_NONE) {
                // Start the Bluetooth chat services
                mBluetoothConnect.start();
            }
        }
    }


    @Override
    public void onResponse(Call<HttpPostRes> call, Response<HttpPostRes> response) {
        byte status=response.body().getStatus();
        if(status==1){
            Toast.makeText(getActivity(),"上传成功",Toast.LENGTH_SHORT).show();
        }else if(status==0){
            Toast.makeText(getActivity(),"上传失败，请重试",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onFailure(Call<HttpPostRes> call, Throwable t) {
        Toast.makeText(getActivity(),"上传失败，请重试",Toast.LENGTH_SHORT).show();
    }



}
