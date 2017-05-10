package ua.com.solodilov.evgen.osmandtest.ui;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;

import ua.com.solodilov.evgen.osmandtest.R;
import ua.com.solodilov.evgen.osmandtest.dm.DownloadObservable;
import ua.com.solodilov.evgen.osmandtest.dm.DownloadObserver;
import ua.com.solodilov.evgen.osmandtest.dm.DownloadService;
import ua.com.solodilov.evgen.osmandtest.interfaces.OnRegionFragment;
import ua.com.solodilov.evgen.osmandtest.models.Region;

public class MainActivity extends AppCompatActivity implements OnRegionFragment {
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private DownloadService downloadService;
    private DownloadObservable downloadObservable;

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

    @Override
    protected void onStart() {
        super.onStart();
        downloadService = new DownloadService();
        downloadObservable = new DownloadObservable();
        downloadService.onStart(this, downloadObservable);
    }

    @Override
    protected void onStop() {
        downloadService.onStop(this);
        super.onStop();
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

    public void startDownload(Uri uri, DownloadObserver downloadObserver) {
        Log.d("suka", "startDownload");
        downloadService.startDownload(this, downloadObserver, uri);
    }

    public void saveRegion(Region region, Uri uri) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putString(region.getName(), uri.toString()));
    }

    //TODO file is available
    public boolean isRegionSaved(Region region) {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getString(region.getName(), null) != null;
    }

    public Uri getUriFromRegion(Region region) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String tmp = sharedPreferences.getString(region.getName(), null);
        if (TextUtils.isEmpty(tmp)) return null;
        return Uri.parse(tmp);
    }

}
