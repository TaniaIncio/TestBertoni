package com.tincio.testbertoni.data;

/**
 * Created by tania on 9/18/18.
 */

interface TaskContract {
    String TABLE_NAME = "tasks";

    // Fields

    /**
     * SQLite primary key
     **/
    String COLUMN_NAME_ID = "_id";

    /**
     * Identifier
     */
    String COLUMN_NAME_IDENTIFIER = "identifier";

    String COLUMN_NAME_DESCRIPTION = "description";
    String COLUMN_NAME_STATUS = "status";



    // Data types
    String TEXT_TYPE = " TEXT";
    String COMMA_SEP = ",";

    // SQL statements
    String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + TABLE_NAME + " (" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"

         //   + COLUMN_NAME_IDENTIFIER + TEXT_TYPE + " UNIQUE " + COMMA_SEP

            + COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP
            + COLUMN_NAME_STATUS + TEXT_TYPE
            + " )";

    String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
