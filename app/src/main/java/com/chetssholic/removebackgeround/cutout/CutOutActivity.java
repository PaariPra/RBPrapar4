package com.chetssholic.removebackgeround.cutout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Arrays;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alexvasilkov.gestures.views.interfaces.GestureView;
import com.chetssholic.removebackgeround.R;
import com.chetssholic.removebackgeround.activity.EditActivity;
import com.chetssholic.removebackgeround.adsclass.ShowIntertialads;
import com.chetssholic.removebackgeround.utils.BitmapUtiles;
import com.chetssholic.removebackgeround.utils.TinyDB;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import top.defaults.checkerboarddrawable.CheckerboardDrawable;


public class CutOutActivity extends AppCompatActivity {

    public FrameLayout loadingModal;
    private GestureView gestureView;
    private DrawView drawView;
    private ConstraintLayout manualClearSettingsLayout;
    private static final short MAX_ERASER_SIZE = 150;
    private static final short BORDER_SIZE = 45;
    private static final float MAX_ZOOM = 4F;

    private AdView ad_view;
    ImageView doneButton;

    private void Daabaner() {

        ad_view = findViewById(R.id.ad_view);
        TinyDB tinyDB = new TinyDB(CutOutActivity.this);
        int status = tinyDB.getInt("status");
        if (status == 1) {


            MobileAds.setRequestConfiguration(
                    new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
                            .build()
            );


            AdRequest adRequest = new AdRequest.Builder().build();
            ad_view.setVisibility(View.VISIBLE);
            ad_view.loadAd(adRequest);


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_edit);

        initVIes();
        Daabaner();
        initDAta();


    }

    private void initDAta() {
        FrameLayout drawViewLayout = findViewById(R.id.drawViewLayout);

        int sdk = android.os.Build.VERSION.SDK_INT;

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            drawViewLayout.setBackgroundDrawable(CheckerboardDrawable.create());
        } else {
            drawViewLayout.setBackground(CheckerboardDrawable.create());
        }

        SeekBar strokeBar = findViewById(R.id.strokeBar);
        strokeBar.setMax(MAX_ERASER_SIZE);
        strokeBar.setProgress(50);

        gestureView = findViewById(R.id.gestureView);

        ImageView imageView8 = findViewById(R.id.imageView8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        drawView = findViewById(R.id.drawView);
        drawView.setDrawingCacheEnabled(true);
        drawView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        drawView.setStrokeWidth(strokeBar.getProgress());

        strokeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                drawView.setStrokeWidth(seekBar.getProgress());
            }
        });

        loadingModal = findViewById(R.id.loadingModal);
        loadingModal.setVisibility(INVISIBLE);

        drawView.setLoadingModal(loadingModal);

        manualClearSettingsLayout = findViewById(R.id.manual_clear_settings_layout);

        setUndoRedo();
        initializeActionButtons();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);


        }


        Uri uri = Uri.parse(getIntent().getExtras().getString("imageuri"));


        setDrawViewBitmap(uri);


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = startSaveDrawingTask();


                startActivity(
                        new Intent(CutOutActivity.this,
                                EditActivity.class).putExtra("imageuri", uri.toString())
                                .putExtra("type", getIntent().getExtras().getString("type")));


            }
        });
    }

    private void initVIes() {
        doneButton = findViewById(R.id.doneh);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Uri getExtraSource() {
        return getIntent().hasExtra(CutOut.CUTOUT_EXTRA_SOURCE) ? (Uri) getIntent().getParcelableExtra(CutOut.CUTOUT_EXTRA_SOURCE) : null;
    }


    private Uri startSaveDrawingTask() {

        Bitmap bitmap;
        SaveDrawingTask task = new SaveDrawingTask(this);

        int borderColor;
        if ((borderColor = getIntent().getIntExtra(CutOut.CUTOUT_EXTRA_BORDER_COLOR, -1)) != -1) {
            bitmap = BitmapUtility.getBorderedBitmap(this.drawView.getDrawingCache(), borderColor, BORDER_SIZE);
            task.execute(bitmap);
        } else {
            bitmap = drawView.getDrawingCache();
            task.execute(this.drawView.getDrawingCache());
        }


        Uri uri = BitmapUtiles.saveToCacheAndGetUri(
                bitmap,
                "phortedot",
                CutOutActivity.this
        );

        return uri;


    }


    private void activateGestureView() {
        gestureView.getController().getSettings()
                .setMaxZoom(MAX_ZOOM)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f);
    }

    private void deactivateGestureView() {
        gestureView.getController().getSettings()
                .setPanEnabled(false)
                .setZoomEnabled(false)
                .setDoubleTapEnabled(false);
    }

    private void initializeActionButtons() {
        LinearLayout autoClearButton = findViewById(R.id.auto_clear_button);
        LinearLayout manualClearButton = findViewById(R.id.manual_clear_button);
        LinearLayout zoomButton = findViewById(R.id.zoom_button);

        autoClearButton.setActivated(false);
        autoClearButton.setOnClickListener((buttonView) -> {
            if (!autoClearButton.isActivated()) {
                drawView.setAction(DrawView.DrawViewAction.AUTO_CLEAR);
                manualClearSettingsLayout.setVisibility(INVISIBLE);
                autoClearButton.setActivated(true);
                manualClearButton.setActivated(false);
                zoomButton.setActivated(false);
                deactivateGestureView();
            }
        });

        manualClearButton.setActivated(true);
        drawView.setAction(DrawView.DrawViewAction.MANUAL_CLEAR);
        manualClearButton.setOnClickListener((buttonView) -> {
            if (!manualClearButton.isActivated()) {
                drawView.setAction(DrawView.DrawViewAction.MANUAL_CLEAR);
                manualClearSettingsLayout.setVisibility(VISIBLE);
                manualClearButton.setActivated(true);
                autoClearButton.setActivated(false);
                zoomButton.setActivated(false);
                deactivateGestureView();
            }

        });

        zoomButton.setActivated(false);
        deactivateGestureView();
        zoomButton.setOnClickListener((buttonView) -> {
            if (!zoomButton.isActivated()) {
                drawView.setAction(DrawView.DrawViewAction.ZOOM);
                manualClearSettingsLayout.setVisibility(INVISIBLE);
                zoomButton.setActivated(true);
                manualClearButton.setActivated(false);
                autoClearButton.setActivated(false);
                activateGestureView();
            }

        });
    }

    private void setUndoRedo() {
        LinearLayout undoButton = findViewById(R.id.undo);
        undoButton.setEnabled(false);
        undoButton.setOnClickListener(v -> undo());
        LinearLayout redoButton = findViewById(R.id.redo);
        redoButton.setEnabled(false);
        redoButton.setOnClickListener(v -> redo());

        drawView.setButtons(undoButton, redoButton);
    }

    void exitWithError(Exception e) {
        Intent intent = new Intent();
        intent.putExtra(CutOut.CUTOUT_EXTRA_RESULT, e);
        setResult(CutOut.CUTOUT_ACTIVITY_RESULT_ERROR_CODE, intent);
        finish();
    }

    private void setDrawViewBitmap(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            drawView.setBitmap(bitmap);
        } catch (IOException e) {
            exitWithError(e);
        }
    }


    private void undo() {
        drawView.undo();
    }

    private void redo() {
        drawView.redo();
    }

}