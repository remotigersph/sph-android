package pogi.tiger.com.sph.viewmodel.dialog;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Toast;
import pogi.tiger.com.sph.view.dialog.ActionChooserDialogFragment;

/**
 * Created by Pogi on 31/10/2016.
 */

public class ActionChooserDialogFragmentViewModel extends BaseObservable {

    private static DialogFragment fragment;

    public ActionChooserDialogFragmentViewModel(DialogFragment fragment) {
        this.fragment = fragment;
    }

    public void showCamera(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        fragment.startActivityForResult(intent, ActionChooserDialogFragment.REQUEST_CAMERA);
    }

    public void showGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Show only images, no videos or anything else
        intent.setType("image/*");
        fragment.startActivityForResult(intent, ActionChooserDialogFragment.REQUEST_GALLERY);
    }
}
