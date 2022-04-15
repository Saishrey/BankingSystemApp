package database;

import android.provider.BaseColumns;

public class UserContract {

    private UserContract() { }

    public static final class UserEntry implements BaseColumns {
        /** Name of database table for users */
        public final static String TABLE_NAME = "user";

        /** Table Fields */
        public final static String USER_ID = BaseColumns._ID;
        public final static String COLUMN_USER_NAME ="name";
        public final static String COLUMN_GENDER = "gender";
        public final static String COLUMN_USER_ACCOUNT_NUMBER ="accountNo";
        public final static String COLUMN_USER_EMAIL ="email";
        public final static String COLUMN_USER_IFSC_CODE ="ifscCode";
        public final static String COLUMN_USER_CONTACT ="contact";
        public final static String COLUMN_USER_ACCOUNT_BALANCE ="balance";
    }
}
