package pogi.tiger.com.sph.viewmodel.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import pogi.tiger.com.sph.BR;
import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.model.Category;
import pogi.tiger.com.sph.model.Post;
import pogi.tiger.com.sph.utils.FirebaseUtils;

/**
 * Created by Pogi on 01/11/2016.
 */

public class CreatePostActivityViewModel extends BaseObservable implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private Post post;
    private Uri photoUri;
    private GoogleApiClient mGoogleApiClient;
    private Activity mActivity;
    private String[] categoryArray;

    LocationRequest mLocationRequest;

    public CreatePostActivityViewModel(Activity activity, Uri photoUri) {
        this.photoUri = photoUri;
        this.mActivity = activity;
        post = new Post();
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }

    public void init() {
        mGoogleApiClient.connect();
        FirebaseUtils.generateCategoriesQuery().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> categoryList = new ArrayList<>();
                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren()) {
                    Category category = categorySnapshot.getValue(Category.class);
                    categoryList.add(category.toString());
                }
                categoryArray = new String[categoryList.size()];
                categoryList.toArray(categoryArray);
                notifyPropertyChanged(BR.categoryArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void destroy() {
        if(mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
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
                post.photoUri = taskSnapshot.getStorage().toString();
                createPost();
            }
        });
    }

    @Bindable
    public String[] getCategoryArray() {
        return categoryArray;
    }

    @Bindable
    public String getLocation() {
        return post.longitude + post.latitude == 0 ?
                "Getting location..." : String.format("%s, %s", post.latitude, post.longitude);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        post.content = s.toString();
    }

    private void createPost() {
        FirebaseUtils.writeNewPost(post);
        mActivity.finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(mActivity, "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(mActivity, "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            post.latitude = location.getLatitude();
            post.longitude = location.getLongitude();
            notifyPropertyChanged(BR.location);
        }
        else {
            Toast.makeText(mActivity, "No location found, please turn on GPS", Toast.LENGTH_SHORT).show();
        }
    }

    public AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                post.category = categoryArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }
}
