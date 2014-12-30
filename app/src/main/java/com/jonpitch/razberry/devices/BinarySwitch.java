package com.jonpitch.razberry.devices;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcel;

import com.google.gson.Gson;
import com.jonpitch.razberry.api.RazberryApi;
import com.jonpitch.razberry.api.RazberryService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BinarySwitch extends Device {

    public BinarySwitch(int id, String name, boolean isOn, int level) {
        super(id, name, isOn, level);
        setId(id);
        setName(name);
        setIsOn(isOn);
        setLevel(isOn ? 100 : 0);

        Gson gson = new Gson();
        mSerializedDevice = gson.toJson(this);
    }

    public void on(Context context, Callback<Void> callback) {
        ToggleTask task = new ToggleTask(context, this, callback);
        task.execute();
    }

    public void off(Context context, Callback<Void> callback) {
        ToggleTask task = new ToggleTask(context, this, callback);
        task.execute();
    }

    private class ToggleTask extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private Device mDevice;
        private Callback<Void> mCallback;

        public ToggleTask(Context context, Device device, Callback<Void> callback) {
            mContext = context;
            mDevice = device;
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final boolean oldState = mDevice.getIsOn();

            RazberryService api = RazberryApi.getApi(mContext);
            api.toggleBinarySwitch(
                    mDevice.getId(),
                    oldState ? 0 : 255,
                    new Callback<Void>() {
                        @Override
                        public void success(Void aVoid, Response response) {
                            mDevice.setIsOn(!oldState);
                            mCallback.success(aVoid, response);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            mCallback.failure(error);
                        }
                    });
            return null;
        }
    }

    // parcelable

    public static final Creator<BinarySwitch> CREATOR = new Creator<BinarySwitch>() {

        @Override
        public BinarySwitch createFromParcel(Parcel parcel) {
            return new BinarySwitch(parcel);
        }

        @Override
        public BinarySwitch[] newArray(int size) {
            return new BinarySwitch[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(mSerializedDevice);
    }

    private BinarySwitch(Parcel in) {
        super(in);
    }
}
