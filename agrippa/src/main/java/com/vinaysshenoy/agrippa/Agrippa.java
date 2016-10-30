package com.vinaysshenoy.agrippa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.vinaysshenoy.agrippa.view.widget.AgrippaPagerAdapter;
import com.vinaysshenoy.agrippa.view.widget.AgrippaViewPager;

/**
 * Created by vinaysshenoy on 29/10/16.
 */

public final class Agrippa {

    @NonNull
    private final AgrippaViewPager viewPager;

    private AgrippaPagerAdapter agrippaPagerAdapter;

    private WizardStage curWizardStage;

    private Bundle wizardContext;

    private Agrippa(Builder builder, ViewGroup addTo) {
        this.viewPager = new AgrippaViewPager(builder.context);
        viewPager.setLayoutParams(builder.layoutParams);

        if (addTo != null) {
            addTo.addView(viewPager);
        }

        if(builder.savedInstanceState != null) {
            wizardContext = builder.savedInstanceState.getBundle("wizard_context");
        }
        if (wizardContext == null) {
            wizardContext = new Bundle();
        }
    }

    public void saveState(Bundle outState) {
        outState.putBundle("wizard_context", wizardContext);
    }

    public View view() {
        return viewPager;
    }

    public static final class Builder {

        final Context context;

        Bundle savedInstanceState;

        ViewGroup.LayoutParams layoutParams;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setSavedInstanceState(Bundle savedInstanceState) {
            this.savedInstanceState = savedInstanceState;
            return this;
        }

        public Builder setLayoutParams(ViewGroup.LayoutParams layoutParams) {
            this.layoutParams = layoutParams;
            return this;
        }

        public Agrippa create(@Nullable ViewGroup addTo) {
            return new Agrippa(this, addTo);
        }
    }
}
