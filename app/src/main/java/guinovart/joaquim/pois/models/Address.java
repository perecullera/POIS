package guinovart.joaquim.pois.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by perecullera on 18/8/15.
 */
public class Address implements Parcelable {
    public String street;
    public String number;
    public String city;

    public Address(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
        dest.writeString(number);
        dest.writeString(city);
    }
    protected Address(Parcel in){
        this.street = in.readString();
        this.number = in.readString();
        this.city = in.readString();

    }
    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
