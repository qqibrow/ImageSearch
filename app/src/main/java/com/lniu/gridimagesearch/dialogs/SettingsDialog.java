package com.lniu.gridimagesearch.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lniu.gridimagesearch.R;
import com.lniu.gridimagesearch.models.Settings;

/**
 * Created by lniu on 2/1/15.
 */
public class SettingsDialog extends DialogFragment {
    private Spinner spnerColor;
    private Spinner spnerType;
    private Spinner spnerSize;
    private EditText etSite;

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
        spnerColor = (Spinner) view.findViewById(R.id.spnerColor);
        spnerType = (Spinner) view.findViewById(R.id.spnerType);
        spnerSize = (Spinner) view.findViewById(R.id.spnerSize);
        etSite = (EditText)view.findViewById(R.id.etSite);
        getDialog().setTitle("Advanced Filters");
        return view;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getActivity(), "onDestroy called", Toast.LENGTH_SHORT).show();

        // Get on the inputs
        String color = spnerColor.getSelectedItem().toString();
        String type = spnerType.getSelectedItem().toString();
        String size = spnerSize.getSelectedItem().toString();
        String site = etSite.getText().toString();
        Settings.Instance().SetColor(color).SetSize(size).SetType(type).SetSite(site);
        super.onDestroy();
    }
}
