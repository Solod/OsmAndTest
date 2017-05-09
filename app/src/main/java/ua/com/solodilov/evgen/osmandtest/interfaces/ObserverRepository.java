package ua.com.solodilov.evgen.osmandtest.interfaces;

import java.util.List;

import ua.com.solodilov.evgen.osmandtest.models.Region;

/**
 * Created by jack on 08.05.17.
 */

public interface ObserverRepository {
    void update(List<Region> list);
}
