package ua.com.solodilov.evgen.osmandtest.dm;

import android.content.Context;
import android.net.Uri;


public interface DownloadInterface {
    void onStart(Context context, DownloadObservable observable);
    void onStop(Context context);
    void startDownload(Context context, DownloadObserver downloadObserver, Uri uri);
}
