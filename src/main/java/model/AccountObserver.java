package model;

public interface AccountObserver {
    void balanceUpdated(double newBalance);
}