package info.erwandy.favoritemovierecords.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static info.erwandy.favoritemovierecords.database.DatabaseContract.getColumnInt;
import static info.erwandy.favoritemovierecords.database.DatabaseContract.getColumnString;
import static info.erwandy.favoritemovierecords.database.FavoriteColumns.*;

/**
 * Created by Nursing Bank IT Dept on 3/19/2018.
 */

public class MovieItems implements Parcelable {
    private int mov_id;
    private String mov_title;
    private String mov_description;
    private String mov_date;
    private String mov_image;
    private String mov_rate_count;
    private String mov_rate;

    public int getMov_id() {
        return mov_id;
    }

    public void setMov_id(int mov_id) {
        this.mov_id = mov_id;
    }

    public String getMov_title() {
        return mov_title;
    }

    public void setMov_title(String mov_title) {
        this.mov_title = mov_title;
    }

    public String getMov_description() {
        return mov_description;
    }

    public void setMov_description(String mov_description) {
        this.mov_description = mov_description;
    }

    public String getMov_date() {
        return mov_date;
    }

    public void setMov_date(String mov_date) {
        this.mov_date = mov_date;
    }

    public String getMov_image() {
        return mov_image;
    }

    public void setMov_image(String mov_image) {
        this.mov_image = mov_image;
    }

    public String getMov_rate_count() {
        return mov_rate_count;
    }

    public void setMov_rate_count(String mov_rate_count) {
        this.mov_rate_count = mov_rate_count;
    }

    public String getMov_rate() {
        return mov_rate;
    }

    public void setMov_rate(String mov_rate) {
        this.mov_rate = mov_rate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mov_id);
        dest.writeString(this.mov_title);
        dest.writeString(this.mov_description);
        dest.writeString(this.mov_date);
        dest.writeString(this.mov_image);
        dest.writeString(this.mov_rate_count);
        dest.writeString(this.mov_rate);
    }

    public MovieItems() {
    }

    public MovieItems(Cursor cursor){ //Ditambahkan untuk Content Provider purpose
        this.mov_id             = getColumnInt(cursor, _ID);
        this.mov_title          = getColumnString(cursor, COL_TITLE);
        this.mov_image          = getColumnString(cursor, COL_POSTER);
        this.mov_date           = getColumnString(cursor, COL_RELEASE_DATE);
        this.mov_rate           = getColumnString(cursor, COL_RATE);
        this.mov_rate_count     = getColumnString(cursor, COL_RATE_COUNT);
        this.mov_description    = getColumnString(cursor, COL_OVERVIEW);
    }

    protected MovieItems(Parcel in) {
        this.mov_id             = in.readInt();
        this.mov_title          = in.readString();
        this.mov_description    = in.readString();
        this.mov_date           = in.readString();
        this.mov_image          = in.readString();
        this.mov_rate_count     = in.readString();
        this.mov_rate           = in.readString();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}
