package ua.com.solodilov.evgen.osmandtest.dm;


import android.util.Log;

import java.util.Observable;

import ua.com.solodilov.evgen.osmandtest.models.Region;

/**
 * Created by jack on 10.05.17.
 */

public class DownloadObserver {

    private long id;
    private Region region;

    public void update(long id) {
        if (this.id == id) {
            Log.d("suka", "*******************"+region);

        }
        Log.d(this.getClass().getSimpleName(), "id: " + this.id+" region: "+this.region);
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setId(long id) {
        this.id = id;
    }
}
