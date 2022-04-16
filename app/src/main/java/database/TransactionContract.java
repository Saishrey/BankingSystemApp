package database;

import android.provider.BaseColumns;

public class TransactionContract {
    private TransactionContract() { }

    public static final class TransactionEntry implements BaseColumns {
        /**Name of database table for transactions*/
        public final static String TABLE_NAME = "transaction_record";

        /**Table Fields*/
        public final static String TRANSACTION_ID = BaseColumns._ID;
        public final static String COLUMN_FROM_ACCOUNT_NO = "from_account";
        public final static String COLUMN_TO_ACCOUNT_NO = "to_account";
        public final static String COLUMN_AMOUNT = "amount";
        public final static String COLUMN_STATUS = "status";
        public final static String COLUMN_DATE_TIME = "date_and_time";
    }
}
