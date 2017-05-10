package ua.com.solodilov.evgen.osmandtest.dm;


import android.database.Observable;

import java.util.ListIterator;

/**
 * Created by jack on 10.05.17.
 */

public class DownloadObservable extends Observable<DownloadObserver> {

    public void notifyAllObservers(long id){
        ListIterator<DownloadObserver> iterator = mObservers.listIterator();
        while (iterator.hasNext()) {
            iterator.next().update(id);
        }
    }
}
