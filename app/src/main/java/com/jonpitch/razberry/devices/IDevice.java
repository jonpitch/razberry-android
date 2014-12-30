package com.jonpitch.razberry.devices;

import android.content.Context;
import retrofit.Callback;

public interface IDevice {

    /**
     * Turn the device on
     * @param context
     * @param callback
     */
    public void on(Context context, Callback<Void> callback);

    /**
     * Turn the device off
     * @param context
     * @param callback
     */
    public void off(Context context, Callback<Void> callback);
}
