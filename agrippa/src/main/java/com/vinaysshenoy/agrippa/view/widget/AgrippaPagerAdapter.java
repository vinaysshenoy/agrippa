package com.vinaysshenoy.agrippa.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SimpleArrayMap;
import android.view.ViewGroup;

import com.vinaysshenoy.agrippa.WizardStage;

import java.util.List;

/**
 * Created by vinaysshenoy on 04/10/16.
 */

public class AgrippaPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "AgrippaPagerAdapter";

    @NonNull
    private final Context context;
    private List<String> stages;
    private SimpleArrayMap<String, WizardStage> wizardStages;

    public AgrippaPagerAdapter(@NonNull Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
        stages = null;
        wizardStages = new SimpleArrayMap<>((int) (10 * 1.33F));
    }

    public void setPages(@Nullable List<String> stages) {
        this.stages = stages;
        wizardStages.clear();
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        WizardStage result = (WizardStage) super.instantiateItem(container, position);
        final String stageClassName = stages.get(position);
        wizardStages.put(stageClassName, result);
        return result;
    }

    @Override
    public Fragment getItem(int position) {

        final Fragment fragment = Fragment.instantiate(context, stages.get(position));
        if (!(fragment instanceof WizardStage)) {
            throw new IllegalStateException("All stages in the Wizard must extend WizardStage");
        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        wizardStages.remove(stages.get(position));
    }

    public WizardStage stageForIndex(int stageIndex) {
        return wizardStages.get(stages.get(stageIndex));
    }

    @Override
    public int getCount() {
        return stages == null ? 0 : stages.size();
    }
}
