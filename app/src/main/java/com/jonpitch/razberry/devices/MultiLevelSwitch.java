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

public class MultiLevelSwitch extends Device {

    public MultiLevelSwitch(int id, String name, boolean isOn, int level) {
        super(id, name, isOn, level);
        setId(id);
        setName(name);
        setIsOn(isOn);
        setLevel(level);

        Gson gson = new Gson();
        mSerializedDevice = gson.toJson(this);
    }

    public void on(Context context, Callback<Void> callback) {
        MultiLevelTask task = new MultiLevelTask(context, this, callback);
        task.execute(255);
    }

    public void off(Context context, Callback<Void> callback) {
        MultiLevelTask task = new MultiLevelTask(context, this, callback);
        task.execute(0);
    }

    public void setLevel(Context context, int level, Callback<Void> callback) {
        MultiLevelTask task = new MultiLevelTask(context, this, callback);
        task.execute(level);
    }

    private class MultiLevelTask extends AsyncTask<Integer, Void, Void> {

        private Context mContext;
        private Device mDevice;
        private Callback<Void> mCallback;

        public MultiLevelTask(Context context, Device device, Callback<Void> callback) {
            mContext = context;
            mDevice = device;
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Integer... params) {
            final int state = params[0];
            RazberryService api = RazberryApi.getApi(mContext);

            api.toggleMultiLevelSwitch(mDevice.getId(), state, new Callback<Void>() {
                @Override
                public void success(Void aVoid, Response response) {
                    mDevice.setIsOn(state == 0 ? false : true);
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

    public static final Creator<MultiLevelSwitch> CREATOR = new Creator<MultiLevelSwitch>() {

        @Override
        public MultiLevelSwitch createFromParcel(Parcel parcel) {
            return new MultiLevelSwitch(parcel);
        }

        @Override
        public MultiLevelSwitch[] newArray(int size) {
            return new MultiLevelSwitch[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(mSerializedDevice);
    }

    private MultiLevelSwitch(Parcel in) {
        super(in);
    }
}
