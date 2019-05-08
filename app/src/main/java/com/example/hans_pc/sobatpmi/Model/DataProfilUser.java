package com.example.hans_pc.sobatpmi.Model;

public class DataProfilUser {

    String id_user;
    String display_name;
    String email_user;
    String date_join;

    public DataProfilUser(String id_user, String display_name, String email_user, String date_join) {
        this.id_user = id_user;
        this.display_name = display_name;
        this.email_user = email_user;
        this.date_join = date_join;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getDate_join() {
        return date_join;
    }

    public void setDate_join(String date_join) {
        this.date_join = date_join;
    }
}
