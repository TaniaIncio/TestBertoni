package com.tincio.testbertoni.presentation;

import android.content.Context;

import com.tincio.testbertoni.data.Task;

import java.util.List;

/**
 * Created by tania on 9/18/18.
 */

public interface MainView {
    void showProgress();

    void hideProgress();

    void setItems(List<Task> items);

    void refresh();

    Context getContext();
}
