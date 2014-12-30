package com.jonpitch.razberry.ui.device;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jonpitch.razberry.R;
import com.jonpitch.razberry.devices.Device;

import java.util.List;

import butterknife.ButterKnife;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private List<Device> mData;
    private Context mContext;

    public DeviceAdapter(Context context, List<Device> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_device, parent, false);
        DeviceViewHolder vh = new DeviceViewHolder(v);
        ButterKnife.inject(vh, v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        // get device
        final Device device = mData.get(position);
        holder.draw(mContext, device);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
