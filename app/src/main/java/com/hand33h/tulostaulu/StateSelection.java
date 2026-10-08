package com.hand33h.tulostaulu;

/** No default state; accept only a user-provided positive integer identifier. */
public final class StateSelection {
    private StateSelection() {}
    public static boolean valid(String state) {
        return state!=null && state.trim().matches("[1-9][0-9]*");
    }
}
