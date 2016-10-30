package com.vinaysshenoy.agrippa;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.vinaysshenoy.agrippa.view.widget.AgrippaPagerAdapter;
import com.vinaysshenoy.agrippa.view.widget.AgrippaViewPager;

import java.util.List;

/**
 * Created by vinaysshenoy on 29/10/16.
 */

public final class Agrippa {

    private final AgrippaViewPager agrippaPager;

    private final Handler handler;

    private final OnWizardCompleteListener listener;

    private AgrippaPagerAdapter agrippaPagerAdapter;

    private WizardStage curWizardStage;

    private Bundle wizardContext;

    private final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (curWizardStage != null) {
                bindWizardContext(curWizardStage, wizardContext);
                curWizardStage.onStageHidden(wizardContext);
            }

            curWizardStage = currentStage();
            curWizardStage.onStageShown(wizardContext);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private Agrippa(Builder builder, OnWizardCompleteListener onWizardCompleteListener, ViewGroup addTo) {
        this.agrippaPager = new AgrippaViewPager(builder.context);
        agrippaPager.setId(R.id.agrippa_pager);
        agrippaPager.setLayoutParams(builder.layoutParams);

        if (builder.savedInstanceState != null) {
            wizardContext = builder.savedInstanceState.getBundle("agp_wizard_context");
        }
        if (wizardContext == null) {
            wizardContext = new Bundle();
        }

        this.listener = onWizardCompleteListener;
        agrippaPagerAdapter = new AgrippaPagerAdapter(builder.context, builder.fragmentManager);
        agrippaPager.setAdapter(agrippaPagerAdapter);
        setWizardStages(builder.stages);

        handler = new Handler(Looper.getMainLooper());
        if (addTo != null) {
            addTo.addView(agrippaPager);
        }
    }

    private static void bindWizardContext(WizardStage wizardStage, Bundle wizardContext) {
        wizardStage.onBindContext(wizardContext);
    }

    public void saveState(Bundle outState) {
        outState.putBundle("agp_wizard_context", wizardContext);
    }

    public void onRestoreState() {
        agrippaPager.addOnPageChangeListener(pageChangeListener);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageChangeListener.onPageSelected(agrippaPager.getCurrentItem());
            }
        }, 100L);
    }

    public View view() {
        return agrippaPager;
    }

    private void setWizardStages(@NonNull List<String> stages) {
        curWizardStage = null;
        wizardContext.clear();
        agrippaPagerAdapter.setPages(stages);
        if (stages.size() > 0) {
            agrippaPager.setCurrentItem(0, false);
        }
    }

    public int stageCount() {
        return agrippaPagerAdapter.getCount();
    }

    public int currentStageIndex() {
        return agrippaPager.getCurrentItem();
    }

    public boolean canGoForward() {
        return agrippaPager.getCurrentItem() < agrippaPagerAdapter.getCount();
    }

    public boolean isOnLastStage() {
        return currentStageIndex() == stageCount() - 1;
    }

    public boolean isOnFirstStage() {
        return currentStageIndex() == 0;
    }

    public WizardStage currentStage() {
        return agrippaPagerAdapter.stageForIndex(agrippaPager.getCurrentItem());
    }

    public boolean canGoBack() {
        return agrippaPager.getCurrentItem() > 0 && agrippaPagerAdapter.getCount() > 0;
    }

    public void back() {
        if (canGoBack()) {
            agrippaPager.setCurrentItem(agrippaPager.getCurrentItem() - 1, true);
        }
    }

    public void forward() {
        if (canGoForward()) {

            final WizardStage currentStage = currentStage();
            if (currentStage.isValid()) {
                if (isOnLastStage()) {
                    bindWizardContext(currentStage, wizardContext);
                    onComplete();
                } else {
                    agrippaPager.setCurrentItem(agrippaPager.getCurrentItem() + 1, true);
                }
            } else {
                currentStage.showErrorMessage();
            }
        }
    }

    private void onComplete() {
        listener.onComplete(wizardContext);
    }

    public interface OnWizardCompleteListener {

        void onComplete(Bundle wizardContext);
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

        public Agrippa create(@NonNull OnWizardCompleteListener onWizardCompleteListener, @Nullable ViewGroup addTo) {

            if (stages == null || stages.isEmpty()) {
                throw new IllegalStateException("Stages cannot be null or empty. Has setStages() been called?");
            }

            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }

            return new Agrippa(this, onWizardCompleteListener, addTo);
        }
    }
}
