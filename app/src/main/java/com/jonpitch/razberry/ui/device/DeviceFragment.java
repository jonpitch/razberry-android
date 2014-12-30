package com.jonpitch.razberry.ui.device;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.jonpitch.razberry.DeviceActivity;
import com.jonpitch.razberry.R;
import com.jonpitch.razberry.devices.BinarySwitch;
import com.jonpitch.razberry.devices.Device;
import com.jonpitch.razberry.devices.MultiLevelSwitch;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DeviceFragment extends Fragment {

    public static final String TAG = "DeviceFragment";

    // binary switch views
    @Optional @InjectView(R.id.binary_toggle) Button mBinaryToggle;

    // multilevel views
    @Optional @InjectView(R.id.multilevel_seekbar) SeekBar mMultilevelSeekbar;

    public DeviceFragment() { }

    public static DeviceFragment newInstance() {
        DeviceFragment fragment = new DeviceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;
        final DeviceActivity parent = (DeviceActivity) getActivity();
        if (parent.model != null && parent.model instanceof BinarySwitch) {
            // inflate and draw view for binary switches
            rootView = inflater.inflate(R.layout.fragment_device_binary, container, false);
            ButterKnife.inject(this, rootView);
            drawBinaryToggle(parent.model.getIsOn());
            mBinaryToggle.setOnClickListener(mBinaryToggleListener);

        } else if (parent.model != null && parent.model instanceof MultiLevelSwitch) {
            // inflate and draw view for multilevel switches
            rootView = inflater.inflate(R.layout.fragment_device_multilevel, container, false);
            ButterKnife.inject(this, rootView);
            mMultilevelSeekbar.setProgress(parent.model.getLevel());
            mMultilevelSeekbar.refreshDrawableState();
            mMultilevelSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                private int mStepSize = 5;
                private int mCurrentProgress = 0;

                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int progress = (Math.round(i / mStepSize)) * mStepSize;

                    // trying to cut down on the network requests a bit
                    if (progress != mCurrentProgress) {
                        seekBar.setProgress(progress);
                        mCurrentProgress = progress;
                        ((MultiLevelSwitch) parent.model).setLevel(parent, mCurrentProgress, new Callback<Void>() {
                            @Override
                            public void success(Void aVoid, Response response) {

                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

        } else {
            // device not supported
            rootView = inflater.inflate(R.layout.fragment_device, container, false);
        }

        return rootView;
    }

    /**
     * Listener for binary toggle
     */
    private View.OnClickListener mBinaryToggleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final DeviceActivity parent = (DeviceActivity) getActivity();
            Device device = parent.model;
            final boolean oldState = device.getIsOn();

            Callback<Void> callback = new Callback<Void>() {
                @Override
                public void success(Void aVoid, Response response) {
                    drawBinaryToggle(!oldState);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast t = Toast.makeText(parent, getString(R.string.communication_error), Toast.LENGTH_SHORT);
                    t.show();
                }
            };

            if (oldState) {
                device.off(parent, callback);
            } else {
                device.on(parent, callback);
            }
        }
    };

    /**
     * Draw the binary toggle button
     * @param toggle current state of toggle
     */
    private void drawBinaryToggle(boolean toggle) {
        mBinaryToggle.setText(toggle ?
                getString(R.string.binary_switch_on) :
                getString(R.string.binary_switch_off));
    }
}
