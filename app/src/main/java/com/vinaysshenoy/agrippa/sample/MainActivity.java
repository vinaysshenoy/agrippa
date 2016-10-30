package com.vinaysshenoy.agrippa.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.vinaysshenoy.agrippa.Agrippa;
import com.vinaysshenoy.agrippa.sample.stages.Stage1;
import com.vinaysshenoy.agrippa.sample.stages.Stage2;
import com.vinaysshenoy.agrippa.sample.stages.Stage3;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Agrippa agrippa;

    private Button prevButton;

    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        prevButton = (Button) findViewById(R.id.btn_prev);
        nextButton = (Button) findViewById(R.id.btn_next);

        final FrameLayout container = (FrameLayout) findViewById(R.id.frame_wizard);

        agrippa = new Agrippa.Builder(this)
                .setSavedInstanceState(savedInstanceState)
                .setStages(Arrays.asList(
                        Stage1.class.getName(),
                        Stage2.class.getName(),
                        Stage3.class.getName()
                ))
                .create(new Agrippa.OnWizardCompleteListener() {
                    @Override
                    public void onComplete(Bundle wizardContext) {
                        Log.d(TAG, "Wizard Complete!");
                    }
                }, container);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agrippa.back();
                updateUi();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agrippa.forward();
                updateUi();
            }
        });
        updateUi();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        agrippa.saveState(outState);
    }

    private void updateUi() {

        prevButton.setEnabled(!agrippa.isOnFirstStage());
        nextButton.setText(agrippa.isOnLastStage() ? R.string.finish : R.string.next);
    }
}
