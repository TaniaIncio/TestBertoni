package com.tincio.testbertoni.data;

import android.database.Cursor;

/**
 * Created by tania on 9/18/18.
 */

public class TaskCursor extends CursorHelper<Task> implements TaskContract {

    public TaskCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Returns the {@link Task} object of the position of the current
     * cursor position. If the cursor points at an empty position, a
     * {@link Task} object with undefined values is returned. (The
     * calling method should verify that the given Cursor is at a valid
     * position.
     */
    protected Task getParsedObject() {
        return TaskDbHelper.getCursorTask(mCursor);
    }

    /**
     * Returns true if there are no Tasks left.
     */
    public boolean isFinished() {
        return mCursor.isAfterLast();
    }

    /**
     * Returns true if the Checks whether the departure time of the current
     * position is in the past.
     */


    public boolean moveToNext() {
        return mCursor.moveToNext();
    }

}
