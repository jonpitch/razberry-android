package com.jonpitch.razberry.api;

import android.content.Context;

import retrofit.RestAdapter;

public class RazberryApi {

    /**
     * Get an instance of the ZService.
     * @param context
     * @return ZService
     */
    public static RazberryService getApi(Context context) {
        // TODO get user specified url
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //String host = preferences.getString(SettingsActivity.CONTROLLER_IP, "192.168.1.1");
        String host = "192.168.1.21";

        // configure service
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://" + host + ":8083")
                .build();

        return restAdapter.create(RazberryService.class);
    }
}
