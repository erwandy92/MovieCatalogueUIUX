package info.erwandy.dicodingcataloguemovieuiux.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static info.erwandy.dicodingcataloguemovieuiux.database.FavoriteColumns.TABLE_NAME;

public class FavoriteHelper  {

    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private DatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context){
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,
                null,
                values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,
                values,
                _ID +" = ?",
                new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,
                _ID + " = ?",
                new String[]{id});
    }
}
