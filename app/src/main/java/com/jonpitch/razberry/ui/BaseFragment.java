package com.jonpitch.razberry.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jonpitch.razberry.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public abstract class BaseFragment extends Fragment {

    // specify layout
    protected abstract int getLayoutResource();

    // base views
    @Optional @InjectView(R.id.loading) LinearLayout mLoading;
    @Optional @InjectView(R.id.empty) LinearLayout mEmpty;
    @Optional @InjectView(R.id.empty_message) TextView mEmptyMessage;
    @Optional @InjectView(R.id.content) View mContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.inject(this, v);
        return v;
    }

    /**
     * Show a loading indicator on the fragment
     */
    public void showLoading() {
        mContent.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
    }

    /**
     * Show a message in place of the entire fragment content
     * @param message (optional) the message to show, "server error" otherwise
     */
    public void showMessage(String message) {
        mContent.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
        mEmptyMessage.setText(message == null ? getString(R.string.server_error) : message);
        mEmpty.setVisibility(View.VISIBLE);
    }

    /**
     * Show the fragment content
     */
    public void showContent() {
        mLoading.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mContent.setVisibility(View.VISIBLE);
    }
}
