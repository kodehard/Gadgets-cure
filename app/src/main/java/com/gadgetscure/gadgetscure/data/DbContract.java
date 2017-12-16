package com.gadgetscure.gadgetscure.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by awasthi's on 12/16/2017.
 */

public final class DbContract {

    private DbContract() {}


    public static final String CONTENT_AUTHORITY = "com.gadgetscure.gadgetscure";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_RECEIPTS = "receipts";

    public static final class DbEntry implements BaseColumns {


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_RECEIPTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECEIPTS;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECEIPTS;


        public final static String TABLE_NAME = "receipts";


        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_REF = "refno";

        public final static String COLUMN_DEVICE = "device";

        public final static String COLUMN_ISSUE = "issue";


        public final static String COLUMN_DATE = "date";



    }
}
