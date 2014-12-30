package com.jonpitch.razberry;

import android.os.Bundle;

import com.jonpitch.razberry.api.response.DevicesResponse;
import com.jonpitch.razberry.ui.BaseActivity;
import com.jonpitch.razberry.ui.device.DevicesFragment;

public class DevicesActivity extends BaseActivity implements DevicesFragment.DevicesCallback {

    // The underlying model - devices found on host
    public DevicesResponse model;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_devices;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDevicesFound(DevicesResponse response) {
        model = response;
    }
}
