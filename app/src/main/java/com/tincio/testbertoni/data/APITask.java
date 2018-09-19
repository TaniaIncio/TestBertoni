package com.tincio.testbertoni.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tania on 9/18/18.
 */

public class APITask {

    protected TaskDbHelper mTaskDBHelper = null;
    //protected StreamDbHelper mStreamDBHelper = null;
    String TAG = getClass().getSimpleName();
    // Callback
    private APICallback mCallback;

    public APITask(TaskDbHelper mDBHelper,
                        APICallback mCallback) {
        this.mTaskDBHelper = mDBHelper;
        //this.mStreamDBHelper = mStreamDBHelper;
        this.mCallback = mCallback;
    }


    public int insertTask(Task task) {
        SQLiteDatabase db = mTaskDBHelper.getWritableDatabase();

        db.beginTransaction();
        int i = 0;
                try {
                    // All parsed, insert into DB
                    mTaskDBHelper.insertTask(db,"", task.description,task.status);
                    db.setTransactionSuccessful();
                    mCallback.onNewTaskState(true);
                } catch (android.database.sqlite.SQLiteConstraintException e) {
                    // ignore duplicate locations
                    mCallback.onNewTaskState(false);
                } finally {
                    db.endTransaction();
                    return i;

                }
    }

    public int deleteTask(Task task) {
        SQLiteDatabase db = mTaskDBHelper.getWritableDatabase();

        db.beginTransaction();
        int i = 0;
        try {
            // All parsed, insert into DB
            mTaskDBHelper.deleteTask(db,String.valueOf(task.getId()));
            db.setTransactionSuccessful();
            mCallback.onNewTaskState(true);
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            // ignore duplicate locations
            mCallback.onNewTaskState(false);
        } finally {
            db.endTransaction();
            return i;

        }
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = mTaskDBHelper.getWritableDatabase();

        db.beginTransaction();
        int i = 0;
        try {
            // All parsed, insert into DB
            mTaskDBHelper.updateTask(db,task);
            db.setTransactionSuccessful();
            mCallback.onNewTaskState(true);
        } catch (android.database.sqlite.SQLiteConstraintException e) {
            // ignore duplicate locations
            mCallback.onNewTaskState(false);
        } finally {
            db.endTransaction();
            return i;

        }
    }

    public interface APICallback {

        void onNewTaskState(boolean isOk);
    }
}


