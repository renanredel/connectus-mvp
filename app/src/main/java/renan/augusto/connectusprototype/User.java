package renan.augusto.connectusprototype;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("Name")
    private String Name;

    @SerializedName("RegistrationNumber")
    private String RegistrationNumber;

    @SerializedName("UserImage")
    private String UserImage;

    @SerializedName("GoogleAccount")
    private String GoogleAcc;

    @SerializedName("FacebookAccount")
    private String Facebookacc;

    @SerializedName("InstagramAccount")
    private String Instagramacc;

    @SerializedName("SkypeAccount")
    private String Skypeacc;

    public User(String name, String registrationNumber, String userImage, String googleAcc, String facebookacc, String instagramacc, String skypeacc) {
        Name = name;
        RegistrationNumber = registrationNumber;
        UserImage = userImage;
        GoogleAcc = googleAcc;
        Facebookacc = facebookacc;
        Instagramacc = instagramacc;
        Skypeacc = skypeacc;

    }

    public String getUserImage() {
        return UserImage;
    }

    public String getName() {
        return Name;
    }

    public String getRegistrationNumber() {
        return RegistrationNumber;
    }

    public String getGoogleAcc() {
        return GoogleAcc;
    }

    public String getFacebookacc() {
        return Facebookacc;
    }

    public String getInstagramacc() {
        return Instagramacc;
    }

    public String getSkypeacc() {
        return Skypeacc;
    }
}
