package com.jonpitch.razberry.ui.device;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jonpitch.razberry.R;
import com.jonpitch.razberry.devices.BinarySwitch;
import com.jonpitch.razberry.devices.Device;
import com.jonpitch.razberry.devices.MultiLevelSwitch;

import butterknife.InjectView;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.deviceName) TextView mDeviceName;
    @InjectView(R.id.deviceLevel) TextView mDeviceLevel;

    public DeviceViewHolder(View v) {
        super(v);
    }

    public void draw(Context context, Device device) {
        setDeviceName(device);
        setDeviceLevel(device);
    }

    public void setDeviceName(Device d) {
        mDeviceName.setText(d.getName());
    }

    public void setDeviceLevel(Device d) {
        if (d instanceof BinarySwitch) {
            mDeviceLevel.setText(d.getIsOn() ? "On" : "Off");
        } else if (d instanceof MultiLevelSwitch) {
            int level = d.getLevel();
            if (level > 0) {
                mDeviceLevel.setText("On: " + level);
            } else {
                mDeviceLevel.setText("Off");
            }
        }
    }
}
