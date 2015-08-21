package guinovart.joaquim.pois.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by perecullera on 18/8/15.
 */
public class POI implements Parcelable {
    public int id;
    public String title;
    public Address address;
    public Location location;
    public String transport;
    public String email;
    public String description;
    public String phone;

    public POI(){

    }

    public POI(int id, String title, Location loc){
        this.id=id;
        this.title=title;
        this.location = loc;
    }

    public POI(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.address = in.readParcelable(Address.class.getClassLoader());
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.transport = in.readString();
        this.email = in.readString();
        this.description = in.readString();
        this.phone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeParcelable(address, flags);
        dest.writeParcelable(location, flags);
        dest.writeString(transport);
        dest.writeString(email);
        dest.writeString(description);
        dest.writeString(phone);
    }
    public static final Parcelable.Creator<POI> CREATOR = new Parcelable.Creator<POI>() {
        @Override
        public POI createFromParcel(Parcel in) {
            return new POI(in);
        }

        @Override
        public POI[] newArray(int size) {
            return new POI[size];
        }
    };
}
