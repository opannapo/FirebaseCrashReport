package exmp.napodev.firebasecrashexample.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by opannapo on 12/3/17.
 */

public class Music implements Parcelable {
    private int id;
    private String gendre;

    public Music() {

    }

    public Music(Parcel in) {
        id = in.readInt();
        gendre = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(gendre);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGendre() {
        return gendre;
    }

    public void setGendre(String gendre) {
        this.gendre = gendre;
    }
}
