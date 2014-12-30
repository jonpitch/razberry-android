package com.jonpitch.razberry.devices;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public abstract class Device implements IDevice, Parcelable {

    protected int id;
    protected String name;
    protected boolean isOn;
    protected int level;

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

    //

    protected String mSerializedDevice;

    protected Device(int id, String name, boolean isOn, int level) {
        this.id = id;
        this.name = name;
        this.isOn = isOn;
        this.level = level;

        Gson gson = new Gson();
        mSerializedDevice = gson.toJson(this);
    }

    protected Device(Parcel source) {
        mSerializedDevice = source.readString();

        // get the basics
        Gson gson = new Gson();
        Device d = gson.fromJson(mSerializedDevice, Device.class);
        this.setId(d.getId());
        this.setName(d.getName());
        this.setIsOn(d.getIsOn());
        this.setLevel(d.getLevel());
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSerializedDevice);
    }

}
