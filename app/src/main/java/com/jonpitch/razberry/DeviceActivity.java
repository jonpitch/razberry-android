package com.jonpitch.razberry;

import android.os.Bundle;

import com.jonpitch.razberry.R;
import com.jonpitch.razberry.devices.Device;
import com.jonpitch.razberry.ui.BaseActivity;
import com.jonpitch.razberry.ui.device.DeviceFragment;

import org.parceler.Parcels;

public class DeviceActivity extends BaseActivity {

    public static final String PARCEL = "deviceParcel";
    private static final String MODEL = "deviceModel";
    public Device model;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_device;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get data from intent or state
        if (savedInstanceState != null) {
            model = Parcels.unwrap(savedInstanceState.getParcelable(MODEL));
        } else {
            model = Parcels.unwrap(getIntent().getExtras().getParcelable(PARCEL));
        }

        // update toolbar
        getSupportActionBar().setTitle(model.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // add fragment
        getFragmentManager().beginTransaction()
                .replace(R.id.device_container, DeviceFragment.newInstance(), DeviceFragment.TAG)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MODEL, Parcels.wrap(model));
        super.onSaveInstanceState(outState);
    }
}
