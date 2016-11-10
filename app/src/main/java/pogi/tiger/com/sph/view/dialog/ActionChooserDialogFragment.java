package pogi.tiger.com.sph.view.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.databinding.DialogFragmentActionChooserBinding;
import pogi.tiger.com.sph.view.activity.CreatePostActivity;
import pogi.tiger.com.sph.viewmodel.dialog.ActionChooserDialogFragmentViewModel;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Pogi on 31/10/2016.
 */
public class ActionChooserDialogFragment extends BottomSheetDialogFragment {

    public static final int REQUEST_GALLERY = 0x01;
    public static final int REQUEST_CAMERA = 0x10;

    public ActionChooserDialogFragment() {
        // Required empty public constructor
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        DialogFragmentActionChooserBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()), R.layout.dialog_fragment_action_chooser, null, false);
        binding.setViewModel(new ActionChooserDialogFragmentViewModel(this));
        dialog.setContentView(binding.getRoot());

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) binding.getRoot().getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GALLERY:
                    if(data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        Intent intent = new Intent(getActivity(), CreatePostActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(CreatePostActivity.KEY_URI, uri);
                        intent.putExtras(bundle);
                        dismiss();
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity(), "No photo uri found", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case REQUEST_CAMERA:
                    dismiss();
                    startActivity(new Intent(getActivity(), CreatePostActivity.class));
                    break;
            }
        }
    }
}
