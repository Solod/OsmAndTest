package ua.com.solodilov.evgen.osmandtest.interfaces;

import java.util.List;

import ua.com.solodilov.evgen.osmandtest.models.Region;

public interface ObserverRepository {
    void update(List<Region> list);
}
