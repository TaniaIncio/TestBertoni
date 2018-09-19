package com.tincio.testbertoni.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tania on 9/18/18.
 */

public class TaskDbHelper extends SQLiteOpenHelper implements
    TaskContract {

        public static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "Task.db";

        private static TaskDbHelper sInstance = null;

    private TaskDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Access to Singleton object of this class. Creates a new instance if it
         * has not been created yet.
         */
    public static TaskDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TaskDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void reinitialise() {
        SQLiteDatabase db = getWritableDatabase();
        // delete all entries
        db.execSQL(SQL_DELETE_ENTRIES);

        onCreate(db);

        db.close();
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void emptyTaskTable() {
        getWritableDatabase().delete(TABLE_NAME, null, null);
    }

    public int getVersion() {
        return getReadableDatabase().getVersion();
    }

    /**
     * Expects a writeable {@link SQLiteDatabase} - used for batch commits.
     */
    public void insertTask(SQLiteDatabase db, String id, String description, String status) {

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_DESCRIPTION, description);
        cv.put(COLUMN_NAME_STATUS, status);
        // TODO: verify whether the db parameter is needed - can we just get
        // another writeable handle on the db (even if the transaction is
        // started on a different one?)
        db.insertOrThrow(TABLE_NAME, null, cv);
    }

    Cursor getAllTaskCursor() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY "
                + COLUMN_NAME_DESCRIPTION, null);
    }

    /**
     * Returns the Task with the given _id.
     */
    public Task getTask(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_NAME_ID + " = " + id, null);
        c.moveToFirst();
        Task d = getCursorTask(c);
        c.close();

        return d;
    }

    /**
     * Helper method that converts the cursor to a Task object.
     */
    static Task getCursorTask(Cursor mCursor) {

        Task d = new Task();

        d.id = mCursor.getInt(mCursor
                .getColumnIndex(TaskContract.COLUMN_NAME_ID));
        d.description = mCursor.getString(mCursor
                .getColumnIndex(TaskContract.COLUMN_NAME_DESCRIPTION));
        d.status = mCursor.getString(mCursor
                .getColumnIndex(TaskContract.COLUMN_NAME_STATUS));


        return d;
    }

    public void deleteTask(SQLiteDatabase db, String id) {
        db.delete(TABLE_NAME, "_ID=?", new String[]{id});
    }

    public void updateTask(SQLiteDatabase db, Task task) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_STATUS, task.getStatus().equals("1") ? "0" : "1");
        db.update(TABLE_NAME, cv, "_ID = ?",new String[] { String.valueOf(task.getId()) });
    }

}
