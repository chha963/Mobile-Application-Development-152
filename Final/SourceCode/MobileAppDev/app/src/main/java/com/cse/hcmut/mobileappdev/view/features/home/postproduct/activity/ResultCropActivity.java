package com.cse.hcmut.mobileappdev.view.features.home.postproduct.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;

import java.io.IOException;

/**
 * Created by dinhn on 5/4/2016.
 */
public class ResultCropActivity extends MyActivity {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static String EXTRA_TOOLBAR_COLOR
            = "com.cse.hcmut.mobileappdev.view.features.home.postproduct.activity.ResultCropActivity.toolbar_color";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static void startWithUriForResult(@NonNull Fragment fragment, @NonNull Uri uri, int toolbarColor, int requestCode) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, ResultCropActivity.class);
            intent.setData(uri);
            intent.putExtra(EXTRA_TOOLBAR_COLOR, toolbarColor);
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    public static void startWithUriForResult(@NonNull Activity activity, @NonNull Uri uri, int requestCode) {
        if (activity != null) {
            Intent intent = new Intent(activity, ResultCropActivity.class);
            intent.setData(uri);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.activity_result_crop;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Uri data = intent.getData();
//        ((ImageView) findViewById(R.id.imageViewPreview_ResultCropActivity)).setImageURI(data);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data);
            ((ImageView) findViewById(R.id.imageViewPreview_ResultCropActivity)).setImageBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int toolbarColor = intent.getIntExtra(EXTRA_TOOLBAR_COLOR, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ResultCropActivity);
        toolbar.setBackgroundColor(toolbarColor);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.description_cropped_image));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_confirm_change_image) {
            setResult(Activity.RESULT_OK, getIntent());
            finish();
        } else if (item.getItemId() == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED, getIntent());
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    /**
//     * Callback received when a permissions request has been completed.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    saveCroppedImage();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//
//    private void saveCroppedImage() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    getString(R.string.permission_write_storage_rationale),
//                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
//        } else {
//            Uri imageUri = getIntent().getData();
//            if (imageUri != null && imageUri.getScheme().equals("file")) {
//                try {
//                    copyFileToDownloads(getIntent().getData());
//                } catch (Exception e) {
//                    Toast.makeText(ResultActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.e(TAG, imageUri.toString(), e);
//                }
//            } else {
//                Toast.makeText(ResultActivity.this, getString(R.string.toast_unexpected_error), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void copyFileToDownloads(Uri croppedFileUri) throws Exception {
//        String downloadsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//        String filename = String.format("%d_%s", Calendar.getInstance().getTimeInMillis(), croppedFileUri.getLastPathSegment());
//
//        File saveFile = new File(downloadsDirectoryPath, filename);
//
//        FileInputStream inStream = new FileInputStream(new File(croppedFileUri.getPath()));
//        FileOutputStream outStream = new FileOutputStream(saveFile);
//        FileChannel inChannel = inStream.getChannel();
//        FileChannel outChannel = outStream.getChannel();
//        inChannel.transferTo(0, inChannel.size(), outChannel);
//        inStream.close();
//        outStream.close();
//
//        showNotification(saveFile);
//    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
