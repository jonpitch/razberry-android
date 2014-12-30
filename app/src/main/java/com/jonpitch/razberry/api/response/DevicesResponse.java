package com.jonpitch.razberry.api.response;

import java.util.List;

public class DevicesResponse {

    public int code;
    public String message;
    public String error;
    public Data data;

    public class Data {
        public boolean structureChanged;
        public long updateTime;
        public List<Device> devices;
    }

    public class Device {
        public String id;
        public Metric metrics;
        public String location;
        public String deviceType;
        public long updateTime;
    }

    public class Metric {
        public String level;
        public String icon;
        public String title;
    }
}
