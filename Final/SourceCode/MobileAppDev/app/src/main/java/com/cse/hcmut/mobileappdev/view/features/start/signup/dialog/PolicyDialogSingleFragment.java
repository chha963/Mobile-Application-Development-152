package com.cse.hcmut.mobileappdev.view.features.start.signup.dialog;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.StringDocumentLayout;
import com.bluejamesbond.text.style.TextAlignment;
import com.bluejamesbond.text.util.ArticleBuilder;
import com.cse.hcmut.mobileappdev.R;

import java.lang.reflect.Field;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinhn on 4/30/2016.
 */
public class PolicyDialogSingleFragment extends DialogFragment implements View.OnClickListener {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    /**
     * Interface for receiving the wanted callbacks
     */
    public interface CallbacksListener {

        void onCloseButtonClicked();

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

    public static PolicyDialogSingleFragment newInstance() {

        Bundle args = new Bundle();

        PolicyDialogSingleFragment fragment = new PolicyDialogSingleFragment();
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

    @BindView(R.id.txtViewBriefDescription_PolicyDialogSingleFragment)
    TextView mBriefDescriptionTextView;

    @BindView(R.id.btnClose_MoreDialogSingeFragment)
    Button mCloseButton;

    @BindView(R.id.articleList_PolicyDialogSingleFragment)
    LinearLayout mPolicyContentLayout;

    @BindString(R.string.txt_view_policy_sign_up)
    String mBriefDescriptionTitle;

    @BindString(R.string.policy_content)
    String mPolicyContent;

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
                case R.id.btnClose_MoreDialogSingeFragment:
                    mCallbacksListener.onCloseButtonClicked();
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

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.single_fragment_policy_dialog, null);
        if (rootView != null) {
            builder.setView(rootView);
            dialog = builder.create();
            ButterKnife.bind(this, rootView);
            mCloseButton.setOnClickListener(this);

            setupWidgets();
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

        mBriefDescriptionTextView.setText(mBriefDescriptionTitle);

        String otherDescription = mPolicyContent;
        String doubleNewLine = System.getProperty("line.separator") + System.getProperty("line.separator");
        String formatOtherDescription = otherDescription.replaceAll("[\\n]", doubleNewLine);

        ArticleBuilder ab = new ArticleBuilder();
        ab.append(formatOtherDescription);

        addDocumentView(ab, DocumentView.FORMATTED_TEXT);

    }

    private DocumentView addDocumentView(CharSequence article, int type, boolean rtl) {
        final DocumentView documentView = new DocumentView(getActivity(), type);
        documentView.setFadeInDuration(800);
        documentView.setFadeInAnimationStepDelay(30);
        documentView.setFadeInTween(new DocumentView.ITween() {
            @Override
            public float get(float t, float b, float c, float d) {
                return c * (t /= d) * t * t + b;
            }
        });

        StringDocumentLayout.LayoutParams documentLayoutParams = documentView.getDocumentLayoutParams();
        if (documentLayoutParams != null) {
            documentLayoutParams.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
            documentLayoutParams.setTextTypeface(Typeface.DEFAULT);
            documentLayoutParams.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            documentLayoutParams.setTextAlignment(TextAlignment.JUSTIFIED);
            documentLayoutParams.setInsetPaddingLeft(0f);
            documentLayoutParams.setInsetPaddingRight(0f);
            documentLayoutParams.setInsetPaddingTop(0f);
            documentLayoutParams.setInsetPaddingBottom(0f);
            documentLayoutParams.setLineHeightMultiplier(1.1f);
            documentLayoutParams.setReverse(rtl);
            documentLayoutParams.setDebugging(false);
            documentLayoutParams.setAntialias(true);
            documentView.setText(article);
        }

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.addView(documentView);
        mPolicyContentLayout.addView(linearLayout);

        return documentView;
    }

    private DocumentView addDocumentView(CharSequence article, int type) {
        return addDocumentView(article, type, false);
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public void setCallbacksListener(CallbacksListener callbacksListener) {
        mCallbacksListener = callbacksListener;
    }

}
