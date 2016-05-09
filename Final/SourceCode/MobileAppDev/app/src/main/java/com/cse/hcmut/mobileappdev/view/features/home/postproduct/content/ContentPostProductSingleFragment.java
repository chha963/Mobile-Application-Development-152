package com.cse.hcmut.mobileappdev.view.features.home.postproduct.content;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.adapters.ProductGalleryListPostProductAdapter;
import com.cse.hcmut.mobileappdev.base.list.MyGalleryPostProductBitmapList;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.manager.LogInManager;
import com.cse.hcmut.mobileappdev.models.product.Product;
import com.cse.hcmut.mobileappdev.utils.ColorFilterGenerator;
import com.cse.hcmut.mobileappdev.utils.ResourceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.postproduct.activity.ResultCropActivity;
import com.cse.hcmut.mobileappdev.view.features.home.postproduct.dialog.ChooseAddOrRemoveImageDialogSingleFragment;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dinhn on 5/3/2016.
 */
public class ContentPostProductSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final int NUMBER_OF_LINE_LIMIT_BRIEF_DESCRIPTION = 3;

    private static final String DIALOG_CHOOSE_ADD_OR_REMOVE_IMAGE = "DialogChooseAddOrRemoveImage";

    public static final int REQUEST_SELECT_PICTURE = 0x01;

    public static final int REQUEST_PREVIEW_CROP_PICTURE = 0x02;

    private static final int REQUEST_PRODUCT_GALLERY_ADD_REMOVE_DIALOG = 0x11;

    private static final int REQUEST_PRODUCT_AVATAR_ADD_REMOVE_DIALOG = 0x12;

    private static final int REQUEST_PRODUCT_BANNER_ADD_REMOVE_DIALOG = 0x13;

    private static final int REQUEST_CHOOSE_ADD_OR_REMOVE_IMAGE_DIALOG = 1;

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";

    private static final String ARG_CURRENT_SELECTION_CROP = "arg_current_selection_crop";

    private static final String ARG_PRODUCT = "arg_product";

    private static final String ARG_INDEX_LIST_CURRENT_SELECTION_CROP_UPDATE = "arg_index_list_current_selection_crop_update";

    private static final String CROP_AVATAR_IMAGE = "CropAvatarImage";

    private static final String CROP_BANNER_IMAGE = "CropBannerImage";

    private static final String CROP_GALLERY_IMAGES = "CropGalleryImages";

    private static final int NO_POSITION = -1;

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static ContentPostProductSingleFragment newInstance(Product p) {

        Bundle args = new Bundle();
        args.putInt(ARG_INDEX_LIST_CURRENT_SELECTION_CROP_UPDATE, NO_POSITION);
        args.putParcelable(ARG_PRODUCT, p);

        ContentPostProductSingleFragment fragment = new ContentPostProductSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.userAvatarImageView_ContentPostProductSingleFragment)
    CircleImageView mAvatarImageView;

    @BindView(R.id.productTitleEditText_ContentPostProductSingleFragment)
    EditText mProductTitleEditText;

    @BindView(R.id.productPriceEditText_ContentPostProductSingleFragment)
    EditText mProductPriceEditText;

    @BindView(R.id.briefDescriptionEditText_ContentPostProductSingleFragment)
    EditText mBriefDescriptionEditText;

    @BindView(R.id.fullDescriptionEditText_ContentPostProductSingleFragment)
    EditText mFullDescriptionEditText;

    @BindView(R.id.productAvatarRelativeLayout_ContentPostProductSingleFragment)
    View mProductAvatarView;

    @BindView(R.id.productBannerRelativeLayout_ContentPostProductSingleFragment)
    View mProductBannerView;

    @BindView(R.id.productTitleImageView_ContentPostProductSingleFragment)
    ImageView mProductTitleImageView;

    @BindView(R.id.productPriceImageView_ContentPostProductSingleFragment)
    ImageView mProductPriceImageView;

    @BindView(R.id.productAvatarImageView_ContentPostProductSingleFragment)
    RoundedImageView mProductAvatarImageView;

    @BindView(R.id.productBannerImageView_ContentPostProductSingleFragment)
    RoundedImageView mProductBannerImageView;

    @BindView(R.id.pencilEditProductAvatarImageView_ContentPostProductSingleFragment)
    RoundedImageView mPencilProductAvatarImageView;

    @BindView(R.id.pencilEditBannerAvatarImageView_ContentPostProductSingleFragment)
    RoundedImageView mPencilProductBannerImageView;

    @BindView(R.id.galleryRecyclerView_ContentPostProductSingleFragment)
    RecyclerView mGalleryHorizontalRecyclerView;

    @BindView(R.id.galleryTitleTextView_ContentPostProductSingleFragment)
    TextView mGalleryTitleTextView;

    @BindView(R.id.avatarTitleTextView_ContentPostProductSingleFragment)
    TextView mAvatarTitleTextView;

    @BindView(R.id.bannerTitleTextView_ContentPostProductSingleFragment)
    TextView mBannerTitleTextView;

    @BindDimen(R.dimen.card_view_product_list_grid_layout_margin_right)
    int mMarginRightGalleryItem;

    @BindDimen(R.dimen.post_product_fragment_horizontal_margin)
    int mFirstItemMarginLeft;

    private ProductGalleryListPostProductAdapter mProductGalleryListAdapter;

    private MyGalleryPostProductBitmapList mBitmapsImageList;

    private RoundedImageView mMainCropImageView = null;

    private RoundedImageView mTopEditCropImageView = null;

    @BindString(R.string.crop_label)
    String mCropActivityTitle;

    int mColor = 0;

    int mDottedCardBorderDrawableRes = R.drawable.post_product_dotted_border;

    // Use this to limit edit text
    private int mLastBriefDescriptionCursorPosition = 0;

    private String mBriefDescriptionText = "";

    private Product mProduct = null;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.single_fragment_content_post_product;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.productAvatarRelativeLayout_ContentPostProductSingleFragment)
    void onProductAvatarClick() {

        Bundle args = getArguments();

        if (args != null) {

            if (mProductAvatarImageView != null) {

                removeOldSelectionCropState();
                // save state current selection image
                args.putString(ARG_CURRENT_SELECTION_CROP, CROP_AVATAR_IMAGE);
                Drawable backgroundDrawable = mProductAvatarImageView.getBackground();
                // Got image in (no place holder)
                if (backgroundDrawable == null) {
                    DialogFragment fragment = createProductAvatarDialogFragment();
                    showChooseRemoveImageDialogFragment(fragment);
                } else {
                    pickFromGallery();
                }

            }
        }
    }

    @OnClick(R.id.productBannerRelativeLayout_ContentPostProductSingleFragment)
    void onProductBannerClick() {

        Bundle args = getArguments();

        if (args != null) {

            if (mProductBannerImageView != null) {

                removeOldSelectionCropState();
                args.putString(ARG_CURRENT_SELECTION_CROP, CROP_BANNER_IMAGE);

                Drawable backgroundDrawable = mProductBannerImageView.getBackground();
                // Got image in (no place holder)
                if (backgroundDrawable == null) {
                    DialogFragment fragment = createProductBannerDialogFragment();
                    showChooseRemoveImageDialogFragment(fragment);
                } else {
                    pickFromGallery();
                }

            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mProduct = args.getParcelable(ARG_PRODUCT);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        setupWidgets(activity, mProduct);
        bindWidgetsToListener();
    }

    private void setupWidgets(Context context, Product p) {
        mColor = ResourceUtils.getColorForProductType(getActivity(), p.getType());
        setupColorWidgets(mColor);

        setupAdapters(context);
        setupRecyclerView(context, mGalleryHorizontalRecyclerView);
        mGalleryHorizontalRecyclerView.setAdapter(mProductGalleryListAdapter);

        String iconUrl = LogInManager.get(context).getAccount().getAvatarImageId();

        Glide.with(this)
                .load(iconUrl)
                .centerCrop()
                .dontAnimate()
                .into(mAvatarImageView);

        mProductTitleEditText.setText(p.getName());
        mProductPriceEditText.setText((0 == p.getPrice()) ? "" : String.valueOf(p.getPrice()));
        mBriefDescriptionEditText.setText(p.getBriefDescription());

        mFullDescriptionEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (view.getId() == R.id.fullDescriptionEditText_ContentPostProductSingleFragment) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
        mFullDescriptionEditText.setText(p.getOtherFullDescription());

        Glide.with(this)
                .load(p.getIconId())
                .asBitmap()
                .override(300, 300)
                .centerCrop()
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        loadBitmapToCompoundImageView(resource, mProductAvatarImageView, mPencilProductAvatarImageView);
                    }
                });

        Glide.with(this)
                .load(p.getCatalogueId())
                .asBitmap()
                .override(300, 300)
                .centerCrop()
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        loadBitmapToCompoundImageView(resource, mProductBannerImageView, mPencilProductBannerImageView);
                    }
                });

        ArrayList<String> galleryUrls = p.getGalleryIdList();
        int numberOfImageInGallery = galleryUrls.size();

        // add null to list to ensure that we can insert it to array list
        // because array list can't insert at index > size
        for (int i = 0; i < numberOfImageInGallery; i++) {
            mBitmapsImageList.add(null);
        }

        for (int i = 0; i < numberOfImageInGallery; i++) {
            // index must be constant to inner function
            final int index = i;
            // load synchronous => not sure position was right
            Glide.with(this)
                    .load(galleryUrls.get(i))
                    .asBitmap()
                    .override(300, 300)
                    .centerCrop()
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mBitmapsImageList.set(index, resource);
                            mProductGalleryListAdapter.notifyDataSetChanged();
                        }
                    });
        }


    }

    private void setupRecyclerView(Context context, RecyclerView recyclerView) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        setupLayoutManagerRecyclerView(context, recyclerView);
        setupItemDecorationRecyclerView(recyclerView);

    }

    private void setupLayoutManagerRecyclerView(Context context, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );
    }

    private void setupItemDecorationRecyclerView(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                final int itemPosition = parent.getChildAdapterPosition(view);

                if (itemPosition == RecyclerView.NO_POSITION) {
                    return;
                }

                if (itemPosition == 0) {
                    outRect.set(mFirstItemMarginLeft, 0, mMarginRightGalleryItem, 0);
                } else {
                    outRect.set(0, 0, mMarginRightGalleryItem, 0);
                }
            }
        });
    }

    private void setupAdapters(Context context) {
        if (mBitmapsImageList == null) {
            mBitmapsImageList = new MyGalleryPostProductBitmapList(ProductGalleryListPostProductAdapter.NUMBER_OF_IMAGE_LIMIT);
        }
        mProductGalleryListAdapter = new ProductGalleryListPostProductAdapter(context, mBitmapsImageList);
        mProductGalleryListAdapter.setItemClickListener(new ProductGalleryListPostProductAdapter.OnItemClickListener() {
            @Override
            public void onGalleryItemImageClick(View view, int position) {

                Bundle args = getArguments();

                if (args != null) {
                    removeOldSelectionCropState();
                    args.putString(ARG_CURRENT_SELECTION_CROP, CROP_GALLERY_IMAGES);
                    args.putInt(ARG_INDEX_LIST_CURRENT_SELECTION_CROP_UPDATE, position);

                    DialogFragment fragment = createGalleryDialogFragment();
                    showChooseRemoveImageDialogFragment(fragment);
                }

            }

            @Override
            public void onGalleryItemEmptyPlaceHolderClick(View view, int position) {

                Bundle args = getArguments();

                if (args != null) {
                    removeOldSelectionCropState();
                    args.putString(ARG_CURRENT_SELECTION_CROP, CROP_GALLERY_IMAGES);
                    pickFromGallery();
                }
            }
        });
    }

    private void setupColorWidgets(int color) {
        if (mProductTitleImageView != null) {
            Drawable drawable = mProductTitleImageView.getDrawable();
            ColorFilter colorFilter = ColorFilterGenerator.from(drawable).to(color);
            mProductTitleImageView.setColorFilter(colorFilter);
        }

        if (mProductPriceImageView != null) {
            Drawable drawable = mProductPriceImageView.getDrawable();
            ColorFilter colorFilter = ColorFilterGenerator.from(drawable).to(color);
            mProductPriceImageView.setColorFilter(colorFilter);
        }

        if (mGalleryTitleTextView != null) {
            mGalleryTitleTextView.setTextColor(color);
        }

        if (mAvatarTitleTextView != null) {
            mAvatarTitleTextView.setTextColor(color);
        }

        if (mBannerTitleTextView != null) {
            mBannerTitleTextView.setTextColor(color);
        }
    }

    private void loadBitmapToCompoundImageView(Bitmap bm, ImageView mainImageView, ImageView editTopImageView) {
        if (mainImageView != null && editTopImageView != null) {
            // Disable background place holder
            mainImageView.setBackgroundResource(0);
            // Load image with bitmap
            mainImageView.setImageBitmap(bm);
            // Visible top right edit image
            editTopImageView.setVisibility(View.VISIBLE);
        }
    }

    private void removeImageFromCompoundImageView(ImageView mainImageView, ImageView editTopImageView) {
        if (mainImageView != null && editTopImageView != null) {
            // Set place holder back
            mainImageView.setBackgroundResource(mDottedCardBorderDrawableRes);
            // Remove current image
            mainImageView.setImageDrawable(null);
            // Hide Top Edit Icon
            editTopImageView.setVisibility(View.GONE);
        }
    }

    private void bindWidgetsToListener() {

        mBriefDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mLastBriefDescriptionCursorPosition = mBriefDescriptionEditText.getSelectionStart();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                mBriefDescriptionEditText.removeTextChangedListener(this);

                if (mBriefDescriptionEditText.getLineCount() > NUMBER_OF_LINE_LIMIT_BRIEF_DESCRIPTION) {
                    mBriefDescriptionEditText.setText(mBriefDescriptionText);
                    mBriefDescriptionEditText.setSelection(mLastBriefDescriptionCursorPosition);
                } else {
                    mBriefDescriptionText = mBriefDescriptionEditText.getText().toString();
                }

                mBriefDescriptionEditText.addTextChangedListener(this);
            }
        });
    }

    // Crop methods
    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            this.startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_SELECT_PICTURE) {

                final Uri selectedUri = data.getData();

                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(getActivity(), R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            } else if (requestCode == REQUEST_PREVIEW_CROP_PICTURE) {
                handleCropPreviewInsertResult(data);
            }
        }

        if (resultCode == Activity.RESULT_CANCELED) {
            // handle result cancel from preview crop image activity
        }

        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME + ".png";

        Activity activity = getActivity();

        if (activity != null) {
            UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(activity.getCacheDir(), destinationFileName)));

            uCrop = config(uCrop);

            uCrop.start(activity, this);
        }
    }

    private UCrop config(@NonNull UCrop uCrop) {

        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setHideBottomControls(true);
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.SCALE, UCropActivity.SCALE);

        options.setToolbarColor(mColor);
        options.setToolbarTitle(mCropActivityTitle);
        options.setToolbarWidgetColor(Color.WHITE);

        /*

        Tune everything (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧

        options.setMaxScaleMultiplier(5);
        options.setImageToCropBoundsAnimDuration(666);
        options.setDimmedLayerColor(Color.CYAN);
        options.setOvalDimmedLayer(true);
        options.setShowCropFrame(false);
        options.setCropGridStrokeWidth(20);
        options.setCropGridColor(Color.GREEN);
        options.setCropGridColumnCount(2);
        options.setCropGridRowCount(1);

        // Color palette
        options.setToolbarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));
		options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));

       */

        uCrop = uCrop.withAspectRatio(1f, 1f);
        uCrop = uCrop.withOptions(options);

        return uCrop;
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            ResultCropActivity.startWithUriForResult(this, resultUri, mColor, REQUEST_PREVIEW_CROP_PICTURE);
        } else {
//            Toast.makeText(SampleActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(getActivity(), cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void removeOldSelectionCropState() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.getString(CROP_AVATAR_IMAGE) != null) {
                args.remove(CROP_AVATAR_IMAGE);
            } else if (args.getString(CROP_BANNER_IMAGE) != null) {
                args.remove(CROP_BANNER_IMAGE);
            } else if (args.getString(CROP_GALLERY_IMAGES) != null) {
                args.remove(CROP_GALLERY_IMAGES);
                if (args.getInt(ARG_INDEX_LIST_CURRENT_SELECTION_CROP_UPDATE) != NO_POSITION) {
                    args.putInt(ARG_INDEX_LIST_CURRENT_SELECTION_CROP_UPDATE, NO_POSITION);
                }
            }
        }
    }

    private void handleCropPreviewInsertResult(Intent intent) {
        Bundle args = getArguments();
        if (args != null) {

            if (args.containsKey(ARG_CURRENT_SELECTION_CROP)) {

                String cropSelection = args.getString(ARG_CURRENT_SELECTION_CROP);

                if (cropSelection.equals(CROP_GALLERY_IMAGES)) {
                    loadBitmapAndInsertToListAdapter(intent.getData());
                } else {
                    loadSingleBitmapFromCropResultToSelectedCompoundImageView(cropSelection, intent);
                }

            }

        }
    }

    private void loadBitmapAndInsertToListAdapter(Uri uriImage) {
        try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriImage);
            mBitmapsImageList.add(bm);
            mProductGalleryListAdapter.notifyDataSetChanged();
        } catch (IOException e) {

        }
    }

    private void loadSingleBitmapFromCropResultToSelectedCompoundImageView(String cropSelection, Intent intent) {
        // Get selected main and edit icon image view from lasted activity
        switch (cropSelection) {
            case CROP_AVATAR_IMAGE:
                mMainCropImageView = mProductAvatarImageView;
                mTopEditCropImageView = mPencilProductAvatarImageView;
                break;
            case CROP_BANNER_IMAGE:
                mMainCropImageView = mProductBannerImageView;
                mTopEditCropImageView = mPencilProductBannerImageView;
                break;
            default:
                mMainCropImageView = mTopEditCropImageView = null;
                break;
        }
        // load them
        try {
            Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), intent.getData());
            loadBitmapToCompoundImageView(bm, mMainCropImageView, mTopEditCropImageView);
        } catch (IOException e) {

        }
    }

    private ChooseAddOrRemoveImageDialogSingleFragment createProductAvatarDialogFragment() {

        ChooseAddOrRemoveImageDialogSingleFragment fragment = ChooseAddOrRemoveImageDialogSingleFragment.newInstance();

        fragment.setCallbacksListener(new ChooseAddOrRemoveImageDialogSingleFragment.CallbacksListener() {
            @Override
            public void onUploadImageClicked() {
                pickFromGallery();
            }

            @Override
            public void onRemoveImageClicked() {
                removeImageFromCompoundImageView(mProductAvatarImageView, mPencilProductAvatarImageView);
            }
        });
        fragment.setTargetFragment(ContentPostProductSingleFragment.this, REQUEST_PRODUCT_AVATAR_ADD_REMOVE_DIALOG);

        return fragment;
    }

    private ChooseAddOrRemoveImageDialogSingleFragment createProductBannerDialogFragment() {
        ChooseAddOrRemoveImageDialogSingleFragment fragment = ChooseAddOrRemoveImageDialogSingleFragment.newInstance();

        fragment.setCallbacksListener(new ChooseAddOrRemoveImageDialogSingleFragment.CallbacksListener() {
            @Override
            public void onUploadImageClicked() {
                pickFromGallery();
            }

            @Override
            public void onRemoveImageClicked() {
                removeImageFromCompoundImageView(mProductBannerImageView, mPencilProductBannerImageView);
            }
        });
        fragment.setTargetFragment(ContentPostProductSingleFragment.this, REQUEST_PRODUCT_BANNER_ADD_REMOVE_DIALOG);

        return fragment;
    }

    private ChooseAddOrRemoveImageDialogSingleFragment createGalleryDialogFragment() {
        ChooseAddOrRemoveImageDialogSingleFragment fragment = ChooseAddOrRemoveImageDialogSingleFragment.newInstance();

        fragment.setCallbacksListener(new ChooseAddOrRemoveImageDialogSingleFragment.CallbacksListener() {
            @Override
            public void onUploadImageClicked() {
                pickFromGallery();
            }

            @Override
            public void onRemoveImageClicked() {

                if (mGalleryHorizontalRecyclerView != null) {
                    Bundle args = getArguments();

                    if (args != null) {
                        int posClicked = args.getInt(ARG_INDEX_LIST_CURRENT_SELECTION_CROP_UPDATE);
                        mBitmapsImageList.remove(posClicked);
                        mProductGalleryListAdapter.notifyDataSetChanged();
//                        View rootSelectedView = mGalleryHorizontalRecyclerView.getChildAt(posClicked);
//                        mMainCropImageView = (RoundedImageView)rootSelectedView.findViewById(R.id.mainImageView_GalleryItemPostProductHolder);
//                        mTopEditCropImageView = (RoundedImageView)rootSelectedView.findViewById(R.id.pencilEditImageView_GalleryItemPostProductHolder);
//                        removeImageFromCompoundImageView(mMainCropImageView, mTopEditCropImageView);
                    }

                }

            }
        });
        fragment.setTargetFragment(ContentPostProductSingleFragment.this, REQUEST_PRODUCT_GALLERY_ADD_REMOVE_DIALOG);
        return fragment;
    }

    private void showChooseRemoveImageDialogFragment(DialogFragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragment.show(fragmentManager, DIALOG_CHOOSE_ADD_OR_REMOVE_IMAGE);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
