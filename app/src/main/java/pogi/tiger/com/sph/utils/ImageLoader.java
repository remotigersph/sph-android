package pogi.tiger.com.sph.utils;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Pogi on 03/11/2016.
 */

public class ImageLoader {

    public static void load(final ImageView view, String path) {
        StorageReference reference = FirebaseUtils.createStorageReferenceFromUrl(path);
        if(reference == null) {
            Glide.with(view.getContext())
                    .load(path)
                    .fitCenter()
                    .crossFade()
                    .into(view);
            return;
        }
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(view.getContext())
                        .load(uri)
                        .fitCenter()
                        .crossFade()
                        .into(view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(view, "Failed to load image: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public static void load(final ImageView view, String path, final int width, final int height) {
        StorageReference reference = FirebaseUtils.createStorageReferenceFromUrl(path);
        if(reference == null) {
            Glide.with(view.getContext())
                    .load(path)
                    .override(width, height)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(view);
            return;
        }
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(view.getContext())
                        .load(uri)
                        .override(width, height)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .into(view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(view, "Failed to load image: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
