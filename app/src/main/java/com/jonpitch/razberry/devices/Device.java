package com.jonpitch.razberry.devices;

import android.content.Context;

import org.parceler.Parcel;

import retrofit.Callback;

@Parcel
public class Device implements IDevice {

    int id;
    String name;
    boolean isOn;
    int level;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public boolean getIsOn() { return this.isOn; }

    public void setIsOn(boolean isOn) { this.isOn = isOn; }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    // required
    public Device() { }

    public Device(int id, String name, boolean isOn, int level) {
        this.id = id;
        this.name = name;
        this.isOn = isOn;
        this.level = level;
    }

    @Override
    public void on(Context context, Callback<Void> callback) {

    }

    @Override
    public void off(Context context, Callback<Void> callback) {

    }
}
