package com.example.luxuria;

import android.net.Uri;

public class dataholder {
    private String Name;
    private String email;
    private String userid;
    private Uri profilepic;

    public dataholder(String name, String email, String userid, Uri profilepic) {
        Name = name;
        this.email = email;
        this.userid = userid;
        this.profilepic = profilepic;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Uri getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(Uri profilepic) {
        this.profilepic = profilepic;
    }
}
