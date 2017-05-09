package ua.com.solodilov.evgen.osmandtest.interfaces;

public interface RepositoryItem {
    /**
     *Command to start data loading
     * @param nodes - Array of nodes to go through and get a sheet
     */
    void loadRegionList(String nodes);

    void registerObserver(ObserverRepository o);

    void removeObserver();

    void notifyObserver();
}
