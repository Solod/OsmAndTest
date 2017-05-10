package ua.com.solodilov.evgen.osmandtest.dm;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.Observable;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadService implements DownloadInterface {
    private long enqueue;
    private DownloadManager dm;
    private BroadcastReceiver receiver;
    private DownloadObservable observable;

    @Override
    public void onStart(Context context, DownloadObservable observable) {
        this.observable = observable;
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    Log.d("DownLoadID: ", " -- " + downloadId);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(enqueue);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {

                            DownloadService.this.observable.notifyAllObservers(downloadId);

                        }
                    }
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);

        context.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onStop(Context context) {
        context.unregisterReceiver(receiver);
        observable.unregisterAll();
    }

    @Override
    public void startDownload(Context context, DownloadObserver downloadObserver, Uri uri) {
        Log.d("suka", "startDownload");
        dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse("http://www.vogella.de/img/lars/LarsVogelArticle7.png"));
        request.setTitle("My File: " + uri);
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //request.setDestinationUri(Uri.parse("file://" + folderName + "/myfile.mp3"));
        enqueue = dm.enqueue(request);
        downloadObserver.setId(enqueue);
        observable.registerObserver(downloadObserver);
        Log.d("id: ", "enqueue: " + enqueue);
    }


}
