package com.jonpitch.razberry.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.jonpitch.razberry.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public abstract class BaseActivity extends ActionBarActivity {

    // views
    @Optional @InjectView(R.id.toolbar) Toolbar toolbar;

    // method to specify layout
    protected abstract int getLayoutResource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.inject(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
}
