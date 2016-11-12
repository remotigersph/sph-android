package pogi.tiger.com.sph.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.component.SPHActivity;
import pogi.tiger.com.sph.databinding.ActivityCreatePostBinding;
import pogi.tiger.com.sph.viewmodel.activity.CreatePostActivityViewModel;

public class CreatePostActivity extends SPHActivity {

    public static final String KEY_URI = "uri";
    public static final byte REQUEST_CODE_PERMISSIONS = 0x01;
    CreatePostActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreatePostBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(this), R.layout.activity_create_post, null, false);

        Uri photoUri = getIntent().getExtras().getParcelable(KEY_URI);
        viewModel = new CreatePostActivityViewModel(this, photoUri);
        binding.setViewModel(viewModel);
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // check for permissions
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_PERMISSIONS);
        }
        else {
            viewModel.init();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.destroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_PERMISSIONS) {
            for(int grantResult:grantResults) {
                if(grantResult == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Location is needed to create a post", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
            viewModel.init();
        }
    }
}
