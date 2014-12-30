package com.jonpitch.razberry.ui.device;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jonpitch.razberry.DevicesActivity;
import com.jonpitch.razberry.R;
import com.jonpitch.razberry.api.RazberryApi;
import com.jonpitch.razberry.api.RazberryService;
import com.jonpitch.razberry.api.response.DevicesResponse;
import com.jonpitch.razberry.devices.BinarySwitch;
import com.jonpitch.razberry.devices.Device;
import com.jonpitch.razberry.devices.MultiLevelSwitch;
import com.jonpitch.razberry.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DevicesFragment extends BaseFragment {

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_devices;
    }

    // recycler
    @InjectView(R.id.content) RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    // callback
    private DevicesCallback mCallback;
    public interface DevicesCallback {
        public void onDevicesFound(DevicesResponse response);
    }

    public static DevicesFragment newInstance() {
        DevicesFragment fragment = new DevicesFragment();
        return fragment;
    }

    public DevicesFragment() { }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (DevicesCallback) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DevicesActivity parent = (DevicesActivity) getActivity();

        // setup recycler view
        mLayoutManager = new LinearLayoutManager(parent);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DeviceAdapter(parent, new ArrayList<Device>());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        // always fetch current state of network
        DevicesActivity parent = (DevicesActivity) getActivity();
        DevicesTask task = new DevicesTask(parent);
        task.execute();
    }

    /**
     * Draw the fragment
     */
    public void draw() {
        DevicesActivity parent = (DevicesActivity) getActivity();
        DevicesResponse response = parent.model;

        List<Device> zDevices = new ArrayList<>();
        List<DevicesResponse.Device> devices = response.data.devices;
        for (DevicesResponse.Device d : devices) {
            if (d.deviceType.equals("switchBinary")) {
                boolean on = d.metrics.level.equals("off") ? false : true;
                String[] parts = d.metrics.title.split(" ");
                String[] idParts = parts[1].split(":");
                zDevices.add(new BinarySwitch(
                        Integer.valueOf(idParts[0]),
                        d.metrics.title,
                        on,
                        on ? 100 : 0));
            } else if (d.deviceType.equals("switchMultilevel")) {
                boolean on = d.metrics.level.equals("0") ? false : true;
                String[] parts = d.metrics.title.split(" ");
                String[] idParts = parts[1].split(":");
                zDevices.add(new MultiLevelSwitch(
                        Integer.valueOf(idParts[0]),
                        d.metrics.title,
                        on,
                        Integer.valueOf(d.metrics.level)));
            }
        }

        // update adapter and view
        mAdapter = new DeviceAdapter(parent, zDevices);
        mRecyclerView.swapAdapter(mAdapter, true);

        // show
        showContent();
    }

    /**
     * Fetch devices from host
     */
    private class DevicesTask extends AsyncTask<Void, Void, DevicesResponse> {

        private Context mContext;

        public DevicesTask(Context context) {
            mContext = context;
        }

        @Override
        protected DevicesResponse doInBackground(Void... params) {
            return RazberryApi.getApi(mContext).getDevices();
        }

        @Override
        protected void onPostExecute(DevicesResponse response) {
            if (response != null) {
                mCallback.onDevicesFound(response);
                draw();
            } else {
                // something went wrong
                showMessage(getString(R.string.no_devices_found));
            }
        }

        @Override
        protected void onPreExecute() {
            showLoading();
        }
    }
}
