package info.erwandy.favoritemovierecords.database;

import android.provider.BaseColumns;

public class FavoriteColumns implements BaseColumns {
    public static String TABLE_NAME = "favorite_movie";

    public static String COL_ID             = "_id";
    public static String COL_TITLE          = "title";
    public static String COL_POSTER         = "poster";
    public static String COL_RELEASE_DATE   = "release_date";
    public static String COL_RATE           = "rate";
    public static String COL_RATE_COUNT     = "rate_count";
    public static String COL_OVERVIEW       = "overview";
}
