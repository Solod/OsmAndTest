package ua.com.solodilov.evgen.osmandtest.interfaces;

import android.net.Uri;

import java.net.URL;


public interface OnDownloadListener {
    void startDownLoad(URL url);
    int getProgressDownload();
}
