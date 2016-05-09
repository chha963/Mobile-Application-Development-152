package com.cse.hcmut.mobileappdev.base.views.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.activity.MyActivity;
import com.cse.hcmut.mobileappdev.utils.FragmentUtils;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dinhn on 3/7/2016.
 */
public abstract class MyBaseSingleFragment extends Fragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    /**
     * Returns the layout id associated to the layout used in the fragment
     *
     * @return
     */
    protected abstract int getLayoutId();

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final int DEFAULT_CHILD_ANIMATION = 500;

    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    private static long getNextAnimationDuration(Fragment fragment, long defValue) {

        try {
            // Attempt to get the resource ID of the next animation that will be applied to the
            // given fragment
            Field nextAnimField = Fragment.class.getDeclaredField("mNextAnim");
            nextAnimField.setAccessible(true);

            int nextAnimResource = nextAnimField.getInt(fragment);
            Animation nextAnim = AnimationUtils.loadAnimation(fragment.getActivity(), nextAnimResource);

            long duration = (nextAnim == null) ? defValue : nextAnim.getDuration();

            return duration;

        } catch (NoSuchFieldException | IllegalAccessException | Resources.NotFoundException e) {
            // if can not be loaded, use default duration
            return defValue;
        }

    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    // Use this variable to check time between 2 click
    public long mLastClickTime = 0;

    private AlertDialog mAlertDialog;

    Unbinder mUnbinder = null;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        View view = inflater.inflate(layoutId, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        // Close keyboard if existed
        Activity activity = getActivity();
        if (activity != null) {
            View view = activity.getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if ((view != null) && (imm != null)) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initActionBar(true, "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
//        ButterKnife.unbind(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        Animation fragmentAnimation = null;

        // If we are popping all back stack, not show animation
        if (FragmentUtils.sDisableFragmentAnimations) {
            fragmentAnimation = new Animation() {
            };
            fragmentAnimation.setDuration(0);
        } else {

            final Fragment parent = getParentFragment();


            // Apply only if this is a child fragment, and the parent is being removed
            if (!enter && parent != null) {
                Animation doNothingAnim = new AlphaAnimation(1, 1);
                doNothingAnim.setDuration(
                        getNextAnimationDuration(
                                parent,
                                DEFAULT_CHILD_ANIMATION
                        )
                );
                fragmentAnimation = doNothingAnim;
            } else {
                fragmentAnimation = super.onCreateAnimation(transit, enter, nextAnim);
            }
        }
        return fragmentAnimation;
    }

    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        final Activity activity = getActivity();
        if (activity != null) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{permission}, requestCode);
                            }
                        }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        }
    }

    /**
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param title                         - dialog title
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        mAlertDialog = builder.show();
    }

    protected void initActionBar(boolean showHomeButton, String title) {
        Activity currentActivity = getActivity();
        if (currentActivity != null && currentActivity instanceof MyActivity) {
            ActionBar supportActionBar = ((MyActivity) currentActivity).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeButtonEnabled(showHomeButton);
                supportActionBar.setTitle(title);
            }
        }
    }

    protected void showActionbar(boolean isShown) {
        Activity currentActivity = getActivity();
        if (currentActivity != null) {
            MyActivity myActivity = (MyActivity) currentActivity;
            if (myActivity != null) {
                ActionBar supportActionBar = myActivity.getSupportActionBar();
                if (supportActionBar != null) {
                    if (isShown) {
                        supportActionBar.show();
                    } else {
                        supportActionBar.hide();
                    }
                }
            }
        }
    }
}
