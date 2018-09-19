package com.tincio.testbertoni.data;

/**
 * Created by tania on 9/18/18.
 */


import android.database.Cursor;

/**
 * Encapsulates a cursor and provides some handy helper calls.
 */
public abstract class CursorHelper<T> {

    Cursor mCursor;

    CursorHelper(Cursor cursor) {
        mCursor = cursor;
        // reset position of cursor
        mCursor.moveToFirst();
    }

    public boolean isLast() {
        return mCursor.isLast();
    }

    public boolean hasNext() {
        return !mCursor.isAfterLast();
    }

    public boolean isFirst() {
        return mCursor.isFirst();
    }

    /**
     * Returns the current object of the encapsulated cursor.
     */
    public T getCurrent() {
        if (mCursor.isAfterLast()) {
            return null; // at end - no more objects
        } else {
            return getParsedObject();
        }
    }

    /**
     * Returns the next object of the encapsulated cursor without moving
     * it.
     */
    public T getPeekNext() {
        mCursor.moveToNext();
        T object;

        if (mCursor.isAfterLast()) {
            object = null; // at end - no more objects
        } else {
            object = getParsedObject();
        }

        mCursor.moveToPrevious();
        return object;
    }

    /**
     * Moves the cursor to the next position and returns its Object.
     */
    public T getNext() {
        if (mCursor.move(1)) {
            // moved cursor to next position, extract object
            return getParsedObject();
        } else {
            // could not move the cursor, return null as error
            return null;
        }
    }

    public T getPrevious() {
        // verify that there is a previous object
        if (!mCursor.moveToPrevious()) {
            return null;
        }

        T object = getParsedObject();
        mCursor.moveToNext();
        // return previous position
        return object;

    }

    public boolean moveToNext() {
        return mCursor.moveToNext();
    }

    protected abstract T getParsedObject();


}
