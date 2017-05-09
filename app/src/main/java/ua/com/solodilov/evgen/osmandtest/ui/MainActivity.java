package ua.com.solodilov.evgen.osmandtest.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ua.com.solodilov.evgen.osmandtest.R;
import ua.com.solodilov.evgen.osmandtest.interfaces.OnRegionFragment;

public class MainActivity extends AppCompatActivity implements OnRegionFragment {
    private FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            addFirstFragment();
        }
    }

    private void addFirstFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ContinentFragment.instantiate(this, ContinentFragment.class.getName()))
                .commit();
    }

    @Override
    public void applyRegionFragment(String region) {
        Bundle bundle = new Bundle();
        bundle.putString(RegionFragment.KEY_ARGUMENT, region);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegionFragment.instantiate(this, RegionFragment.class.getName(), bundle))
                .addToBackStack("")
                .commit();
    }

}
