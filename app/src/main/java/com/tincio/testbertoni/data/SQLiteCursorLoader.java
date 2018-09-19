package com.tincio.testbertoni.data;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;


/**
 * Created by tania on 9/18/18.
 */

abstract class SQLiteCursorLoader extends AsyncTaskLoader<Cursor> {

    private final ForceLoadContentObserver mObserver;

    private Cursor mCursor;

    /**
     * Returns the Cursor that is to be loaded.
     */
    public abstract Cursor getCursor();

    @Override
    public Cursor loadInBackground() {
        // TODO: check if call to new TaskDBHelper is appropriate here or
        // whether we can do it in the constructor and keep a handle
        Cursor cursor = getCursor();

        if (cursor != null) {
            // Ensure the cursor window is filled
            cursor.getCount();
            registerContentObserver(cursor, mObserver);
        }
        return cursor;
    }

    /**
     * Registers an observer to get notifications from the content provider when
     * the cursor needs to be refreshed.
     */
    private void registerContentObserver(Cursor cursor, ContentObserver observer) {
        cursor.registerContentObserver(observer);
    }

    /* Runs on the UI thread */
    @Override
    public void deliverResult(Cursor cursor) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        Cursor oldCursor = mCursor;
        mCursor = cursor;

        if (isStarted()) {
            super.deliverResult(cursor);
        }

        if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    SQLiteCursorLoader(Context context) {
        super(context);
        mObserver = new ForceLoadContentObserver();
    }

    /**
     * Starts an asynchronous load of the contacts list data. When the result is
     * ready the callbacks will be called on the UI thread. If a previous load
     * has been completed and is still valid the result may be passed to the
     * callbacks immediately.
     * <p>
     * Must be called from the UI thread
     */
    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    /**
     * Must be called from the UI thread
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        mCursor = null;
    }
}