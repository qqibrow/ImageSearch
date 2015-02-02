package com.lniu.gridimagesearch.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.lniu.gridimagesearch.R;

/**
 * Created by lniu on 2/1/15.
 */
public class SettingsDialog extends DialogFragment {
    private EditText mEditText;

    public SettingsDialog() {
        // Empty constructor required for DialogFragment
    }

    public static SettingsDialog newInstance(String title) {
        SettingsDialog frag = new SettingsDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_settings, container);
        getDialog().setTitle("Advanced Filters");
        return view;
    }
}
