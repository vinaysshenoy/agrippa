package com.vinaysshenoy.agrippa.sample.stages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinaysshenoy.agrippa.WizardStage;
import com.vinaysshenoy.agrippa.sample.R;

/**
 * Created by vinaysshenoy on 30/10/16.
 */

public class Stage3 extends WizardStage {

    private static final String TAG = "Stage3";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wizard_stage_3, container, false);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void onStageShown(@NonNull Bundle wizardContext) {

    }

    @Override
    public void onStageHidden(@NonNull Bundle wizardContext) {

    }

    @Override
    public void onBindContext(@NonNull Bundle wizardContext) {

    }
}
