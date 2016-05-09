package com.cse.hcmut.mobileappdev.base.views.fragment;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.cse.hcmut.mobileappdev.R;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinhn on 4/17/2016.
 */
public abstract class MyBaseDialogFragment extends DialogFragment implements View.OnClickListener {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    /**
     * Interface for receiving the wanted callbacks
     */
    public interface CallbacksListener {

        void onPositiveButtonClicked();

        void onNegativeButtonClicked();

    }

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    /**
     * Returns the layout id associated to the layout used in the fragment
     *
     * @return
     */
    protected abstract int getMessageLayoutId();

    protected abstract String getTitleString();

    protected abstract String getPositiveButtonString();

    protected abstract String getNegativeButtonString();

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final int DEFAULT_CHILD_ANIMATION = 500;

    //private static final int MESSAGE_MIDDLE_ID = R.id.messageFrameLayout_customAlertDialog;

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

    private CallbacksListener mCallbacksListener;

    @BindView(R.id.titleTextView_MyBaseDialogFragment)
    protected TextView mTitleTextView;

    @BindView(R.id.btnOK_MyBaseDialogFragment)
    protected Button mPositiveButton;

    @BindView(R.id.btnCancel_MyBaseDialogFragment)
    protected Button mNegativeButton;

    @BindView(R.id.messageLayout_MyBaseDialogFragment)
    protected ViewStub mMessageLayoutViewStub;

    protected View mMessageLayoutViewInflated;

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
                case R.id.btnOK_MyBaseDialogFragment:
                    mCallbacksListener.onPositiveButtonClicked();
                    dismiss();
                    break;
                case R.id.btnCancel_MyBaseDialogFragment:
                    mCallbacksListener.onNegativeButtonClicked();
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
        int layoutId = getLayoutId();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = getActivity().getLayoutInflater().inflate(layoutId, null);
        if (rootView != null) {
            builder.setView(rootView);
            dialog = builder.create();

            ButterKnife.bind(this, rootView);

            mTitleTextView.setText(getTitleString());
            mMessageLayoutViewStub.setLayoutResource(getMessageLayoutId());
            mMessageLayoutViewInflated = mMessageLayoutViewStub.inflate();
            mPositiveButton.setText(getPositiveButtonString());
            mNegativeButton.setText(getNegativeButtonString());
            mPositiveButton.setOnClickListener(this);
            mNegativeButton.setOnClickListener(this);
        }
        return dialog;
    }


//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = getActivity().getLayoutInflater().inflate(getLayoutId(), container);
//        return v;
//    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }

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

    protected final int getLayoutId() {
        return R.layout.fragment_dialog;
    }

    protected final View getMessageLayoutViewInflated() {
        return mMessageLayoutViewInflated;
    }

    public void setCallbacksListener(CallbacksListener callbacksListener) {
        this.mCallbacksListener = callbacksListener;
    }
}
