package com.jonpitch.razberry.ui.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jonpitch.razberry.R;
import com.jonpitch.razberry.devices.BinarySwitch;
import com.jonpitch.razberry.devices.Device;
import com.jonpitch.razberry.devices.MultiLevelSwitch;
import com.jonpitch.razberry.DeviceActivity;

import org.parceler.Parcels;

import butterknife.InjectView;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.deviceName) TextView mDeviceName;
    @InjectView(R.id.deviceLevel) TextView mDeviceLevel;
    @InjectView(R.id.card_device) LinearLayout mCardLayout;

    public DeviceViewHolder(View v) {
        super(v);
    }

    public void draw(Context context, Device device) {
        setDeviceName(device);
        setDeviceLevel(device);
        setOnClickListener(context, device);
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

    public void setOnClickListener(final Context context, final Device d) {
        mCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context, DeviceActivity.class);
                Bundle extras = new Bundle();
                extras.putParcelable(DeviceActivity.PARCEL, Parcels.wrap(d));
                detail.putExtras(extras);
                context.startActivity(detail);
            }
        });
    }
}
