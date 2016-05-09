package com.cse.hcmut.mobileappdev.view.features.home.postproduct.dialog;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.cse.hcmut.mobileappdev.R;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinhn on 5/4/2016.
 */
public class ChooseAddOrRemoveImageDialogSingleFragment extends DialogFragment implements View.OnClickListener {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    /**
     * Interface for receiving the wanted callbacks
     */
    public interface CallbacksListener {

        void onUploadImageClicked();

        void onRemoveImageClicked();
    }

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final int DEFAULT_CHILD_ANIMATION = 500;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ChooseAddOrRemoveImageDialogSingleFragment newInstance() {

        Bundle args = new Bundle();

        ChooseAddOrRemoveImageDialogSingleFragment fragment = new ChooseAddOrRemoveImageDialogSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

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

    CallbacksListener mCallbacksListener;

    @BindView(R.id.uploadImageLayout_AddRemoveImageDialogSingleFragment)
    View mUploadView;

    @BindView(R.id.removeImageLayout_AddRemoveImageDialogSingleFragment)
    View mRemoveView;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onClick(View v) {
        if (mCallbacksListener != null) {
            switch (v.getId()) {
                case R.id.uploadImageLayout_AddRemoveImageDialogSingleFragment:
                    mCallbacksListener.onUploadImageClicked();
                    dismiss();
                    break;
                case R.id.removeImageLayout_AddRemoveImageDialogSingleFragment:
                    mCallbacksListener.onRemoveImageClicked();
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }


    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.single_fragment_add_remove_image_dialog, null);
        if (rootView != null) {
            builder.setView(rootView);
            dialog = builder.create();
            ButterKnife.bind(this, rootView);

            Bundle arguments = getArguments();
            if (arguments != null) {
                setupWidgets();
            }
//            mCloseButton.setOnClickListener(this);
            mUploadView.setOnClickListener(this);
            mRemoveView.setOnClickListener(this);
        }
        return dialog;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        final Fragment parent = getParentFragment();

        Animation fragmentAnimation = null;

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
        return fragmentAnimation;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacksListener = null;
    }

    private void setupWidgets() {
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public void setCallbacksListener(CallbacksListener callbacksListener) {
        mCallbacksListener = callbacksListener;
    }

}
