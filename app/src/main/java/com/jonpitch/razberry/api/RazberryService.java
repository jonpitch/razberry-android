package com.jonpitch.razberry.api;

import com.jonpitch.razberry.api.response.DevicesResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface RazberryService {

    /**
     * Get paired ZWave devices
     */
    @GET("/ZAutomation/api/v1/devices?limit=0")
    DevicesResponse getDevices();

    /**
     * Toggle a binary switch.
     * @param deviceId The id of the device to toggle.
     * @param toggle The toggle mode: 255 = on, 0 = off
     * @param callback The callback executed on success
     */
    @POST("/ZWaveAPI/Run/devices%5B{id}%5D.instances%5B0%5D.SwitchBinary.Set({toggle})")
    void toggleBinarySwitch(
            @Path("id") int deviceId,
            @Path("toggle") int toggle,
            Callback<Void> callback
    );

    /**
     * Toggle a multilevel switch
     * @param deviceId The id of the device to toggle.
     * @param level The level to set 0 = off, 100 = full on
     * @param callback The callback executed on success
     */
    @POST("/ZWaveAPI/Run/devices%5B{id}%5D.instances%5B0%5D.SwitchMultilevel.Set({level})")
    void toggleMultiLevelSwitch(
            @Path("id") int deviceId,
            @Path("level") int level,
            Callback<Void> callback
    );
}
