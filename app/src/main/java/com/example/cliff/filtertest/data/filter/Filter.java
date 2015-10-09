package com.example.cliff.filtertest.data.filter;

import java.util.ArrayList;

/**
 * Created by Cliff on 10/4/2015.
 */
public abstract class Filter<T> {
    // cw: Consider the situation where we want to execute multiple filters.
    // Repeat calls?
    // Easy to implement, but that might get verbose.
    public abstract ArrayList<T> evaluate(ArrayList<T> list);

}
