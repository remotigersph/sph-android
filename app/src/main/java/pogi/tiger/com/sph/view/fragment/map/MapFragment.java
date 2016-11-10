package pogi.tiger.com.sph.view.fragment.map;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pogi.tiger.com.sph.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 *
 */
public class MapFragment extends Fragment {

    public MapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

}
