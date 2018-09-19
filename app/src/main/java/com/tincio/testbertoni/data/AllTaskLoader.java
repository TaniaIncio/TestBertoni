package com.tincio.testbertoni.data;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by tania on 9/18/18.
 */

public class AllTaskLoader extends SQLiteCursorLoader {

    public AllTaskLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor getCursor() {
        return TaskDbHelper.getInstance(getContext()).getAllTaskCursor();
    }

}