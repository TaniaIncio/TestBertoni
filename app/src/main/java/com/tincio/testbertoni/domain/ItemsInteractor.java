package com.tincio.testbertoni.domain;

import android.content.Context;
import android.os.Handler;

import com.tincio.testbertoni.data.APITask;
import com.tincio.testbertoni.data.Task;
import com.tincio.testbertoni.data.TaskDbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tania on 9/18/18.
 */

public class ItemsInteractor implements APITask.APICallback{

    private APITask mApiProcessor;
    TaskDbHelper mDbHelper;
    MainCallback callback;

    public ItemsInteractor(Context context, MainCallback callback){
        mDbHelper = TaskDbHelper.getInstance(context);
        mApiProcessor = new APITask(mDbHelper, this);
        this.callback = callback;
    }

    @Override
    public void onNewTaskState(boolean isOk) {
        this.callback.onResponse(isOk);
    }

    public interface OnFinishedListener {
        void onFinished(List<Task> items);
    }

    /*public void findItems(final OnFinishedListener listener) {
        new Handler().postDelayed(() -> listener.onFinished(createArrayList()), 2000);
    }
*/

    public void insertItem(Task task){
        mApiProcessor.insertTask(task);
    }

    public void deleteItem(Task task){
        mApiProcessor.deleteTask(task);
    }

    public void updateItem(Task task){
        mApiProcessor.updateTask(task);
    }
}

