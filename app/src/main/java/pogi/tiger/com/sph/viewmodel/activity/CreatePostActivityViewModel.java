package pogi.tiger.com.sph.viewmodel.activity;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import pogi.tiger.com.sph.utils.FirebaseUtils;

/**
 * Created by Pogi on 01/11/2016.
 */

public class CreatePostActivityViewModel extends BaseObservable {

    private Uri photoUri;
    private String uploadedPhotoUri;
    private String content;

    public CreatePostActivityViewModel(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    @BindingAdapter({"imageUri"})
    public static void loadImage(ImageView view, Uri uri) {
        Glide.with(view.getContext())
                .loadFromMediaStore(uri)
                .fitCenter()
                .crossFade()
                .into(view);
    }

    public void post(final View view) {
        // upload photo
        UploadTask uploadTask = FirebaseUtils.createUploadTask(photoUri);

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(view.getContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                uploadedPhotoUri = taskSnapshot.getStorage().toString();
                createPost();
            }
        });
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        content = s.toString();
    }

    private void createPost() {
        FirebaseUtils.writeNewPost(uploadedPhotoUri, content);
    }
}
