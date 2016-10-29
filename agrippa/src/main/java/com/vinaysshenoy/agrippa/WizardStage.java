package com.vinaysshenoy.agrippa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by vinaysshenoy on 04/10/16.
 */

public abstract class WizardStage extends Fragment {

    public abstract boolean isValid();

    public abstract void showErrorMessage();

    public abstract void onStageShown(@NonNull Bundle wizardContext);

    public abstract void onStageHidden(@NonNull Bundle wizardContext);

    public abstract void onBindContext(@NonNull Bundle wizardContext);
}
