package com.vinaysshenoy.agrippa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.vinaysshenoy.agrippa.view.widget.AgrippaPagerAdapter;
import com.vinaysshenoy.agrippa.view.widget.AgrippaViewPager;

import java.util.List;

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

        if (builder.savedInstanceState != null) {
            wizardContext = builder.savedInstanceState.getBundle("wizard_context");
        }
        if (wizardContext == null) {
            wizardContext = new Bundle();
        }

        if (addTo != null) {
            addTo.addView(viewPager);
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

        final FragmentManager fragmentManager;

        Bundle savedInstanceState;

        ViewGroup.LayoutParams layoutParams;

        List<String> stages;

        public Builder(@NonNull Context context, @NonNull FragmentManager fragmentManager) {
            this.context = context;
            this.fragmentManager = fragmentManager;
        }

        public Builder(@NonNull FragmentActivity activity) {
            this(activity, activity.getSupportFragmentManager());
        }

        public Builder(@NonNull Fragment fragment) {
            this(fragment.getContext(), fragment.getChildFragmentManager());
        }

        public Builder setSavedInstanceState(@Nullable Bundle savedInstanceState) {
            this.savedInstanceState = savedInstanceState;
            return this;
        }

        public Builder setLayoutParams(@Nullable ViewGroup.LayoutParams layoutParams) {
            this.layoutParams = layoutParams;
            return this;
        }

        public Builder setStages(@NonNull List<String> stages) {
            this.stages = stages;
            return this;
        }

        public Agrippa create(@Nullable ViewGroup addTo) {

            if(stages == null || stages.isEmpty()) {
                throw new IllegalStateException("Stages cannot be null or empty. Has setStages() been called?");
            }

            if(layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }

            return new Agrippa(this, addTo);
        }
    }
}
