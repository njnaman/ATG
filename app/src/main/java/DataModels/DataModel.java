package DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class DataModel implements Parcelable {

    private String id;
    private String owner;
    private String secret;
    private String title;
    private String url_s;
    private int farm;
    private int ispublic;
    private int isfriend;
    private int isfamily;
    private int height_s;
    private int width_s;

    public DataModel(String id, String owner, String secret, String tittle, String url_s, int farm, int ispublic, int isfriend, int isfamily, int height_s, int width_s) {
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.title = title;
        this.url_s = url_s;
        this.farm = farm;
        this.ispublic = ispublic;
        this.isfriend = isfriend;
        this.isfamily = isfamily;
        this.height_s = height_s;
        this.width_s = width_s;
    }

    protected DataModel(Parcel in) {
        id = in.readString();
        owner = in.readString();
        secret = in.readString();
        title = in.readString();
        url_s = in.readString();
        farm = in.readInt();
        ispublic = in.readInt();
        isfriend = in.readInt();
        isfamily = in.readInt();
        height_s = in.readInt();
        width_s = in.readInt();
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getTittle() {
        return title;
    }

    public String getUrl_s() {
        return url_s;
    }

    public int getFarm() {
        return farm;
    }

    public int getIspublic() {
        return ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }

    public int getHeight_s() {
        return height_s;
    }

    public int getWidth_s() {
        return width_s;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeString();
        dest.writeString(id);
        dest.writeString(owner);
        dest.writeString(secret);
        dest.writeString(title);
        dest.writeString(url_s);
        dest.writeInt(farm);
        dest.writeInt(ispublic);
        dest.writeInt(isfriend);
        dest.writeInt(isfamily);
        dest.writeInt(height_s);
        dest.writeInt(width_s);
    }
}
