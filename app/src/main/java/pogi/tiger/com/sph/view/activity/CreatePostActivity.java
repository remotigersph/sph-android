package pogi.tiger.com.sph.view.activity;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.databinding.ActivityCreatePostBinding;
import pogi.tiger.com.sph.viewmodel.activity.CreatePostActivityViewModel;

public class CreatePostActivity extends AppCompatActivity {

    public static final String KEY_URI = "uri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreatePostBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(this), R.layout.activity_create_post, null, false);

        Uri photoUri = getIntent().getExtras().getParcelable(KEY_URI);

        binding.setViewModel(new CreatePostActivityViewModel(photoUri));
        setContentView(binding.getRoot());
    }
}
