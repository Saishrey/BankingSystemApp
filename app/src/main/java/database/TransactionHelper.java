package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import Adapters.TransactionListAdapter;
import UI.AmountInput;
import UI.TransactionActivity;
import UI.TransactionsFragment;
import UI.UsersFragment;
import database.TransactionContract.TransactionEntry;
import database.UserContract.UserEntry;

public class TransactionHelper extends SQLiteOpenHelper {

    String TABLE_NAME = TransactionEntry.TABLE_NAME;

    /** Name of the database file */
    private static final String DATABASE_NAME = "record.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.*/
    private static final int DATABASE_VERSION = 1;


    public TransactionHelper(TransactionsFragment context) {
        super(context.getActivity(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public TransactionHelper(AmountInput context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the transaction_record table
        String SQL_CREATE_TRANSACTION_TABLE =  "CREATE TABLE " + TransactionEntry.TABLE_NAME + " ("
                + TransactionEntry.TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TransactionEntry.COLUMN_FROM_ACCOUNT_NO + " VARCHAR, "
                + TransactionEntry.COLUMN_TO_ACCOUNT_NO + " VARCHAR, "
                + TransactionEntry.COLUMN_DATE_TIME + " VARCHAR, "
                + TransactionEntry.COLUMN_AMOUNT + " INTEGER, "
                + TransactionEntry.COLUMN_STATUS + " INTEGER, "
                + "FOREIGN KEY (" + TransactionEntry.COLUMN_FROM_ACCOUNT_NO + ") REFERENCES " + UserEntry.TABLE_NAME + "(" + UserEntry.COLUMN_USER_ACCOUNT_NUMBER + "), "
                + "FOREIGN KEY (" + TransactionEntry.COLUMN_TO_ACCOUNT_NO + ") REFERENCES " + UserEntry.TABLE_NAME + "(" + UserEntry.COLUMN_USER_ACCOUNT_NUMBER + "));";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TRANSACTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME);
            onCreate(db);
        }
    }

    public void insertTransferData (String fromAccount, String toAccount, int amount, int status, String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TransactionEntry.COLUMN_FROM_ACCOUNT_NO, fromAccount);
        contentValues.put(TransactionEntry.COLUMN_TO_ACCOUNT_NO, toAccount);
        contentValues.put(TransactionEntry.COLUMN_AMOUNT, amount);
        contentValues.put(TransactionEntry.COLUMN_STATUS, status);
        contentValues.put(TransactionEntry.COLUMN_DATE_TIME, datetime);

        db.insert(TransactionEntry.TABLE_NAME, null, contentValues);

    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TransactionEntry.TABLE_NAME + " ORDER BY " + TransactionEntry.TRANSACTION_ID + " DESC", null);
    }

}
