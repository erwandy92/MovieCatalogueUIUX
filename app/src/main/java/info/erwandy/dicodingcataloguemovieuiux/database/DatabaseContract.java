package info.erwandy.dicodingcataloguemovieuiux.database;

import android.database.Cursor;
import android.net.Uri;

import static info.erwandy.dicodingcataloguemovieuiux.database.FavoriteColumns.TABLE_NAME;

public class DatabaseContract {

    public static final String AUTHORITY = "info.erwandy.dicodingcataloguemovieuiux";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

    public static Double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble( cursor.getColumnIndex(columnName) );
    }
}
