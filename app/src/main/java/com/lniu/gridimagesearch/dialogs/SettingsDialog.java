package com.lniu.gridimagesearch.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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
    private Switch on;

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

        on = (Switch)view.findViewById(R.id.switch1);
        boolean filterOn = Settings.Instance().On();
        on.setChecked(filterOn);
        setEnabledWidgets(filterOn);
        getDialog().setTitle("Advanced Filters");

        on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEnabledWidgets(isChecked);
            }
        });
        return view;
    }

    private void setEnabledWidgets(boolean flag) {
        spnerColor.setEnabled(flag);
        spnerType.setEnabled(flag);
        spnerSize.setEnabled(flag);
        etSite.setEnabled(flag);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getActivity(), "onDestroy called", Toast.LENGTH_SHORT).show();

        // Get on the inputs
        String color = spnerColor.getSelectedItem().toString();
        String type = spnerType.getSelectedItem().toString();
        String size = spnerSize.getSelectedItem().toString();
        String site = etSite.getText().toString();
        boolean turnedOn = on.isChecked();
        if(turnedOn) {
            Settings.Instance().TurnOn();
        } else {
            Settings.Instance().TurnOff();
        }
        Settings.Instance().SetColor(color).SetSize(size).SetType(type).SetSite(site);
        super.onDestroy();
    }
}
