package com.activeminders.onlinegymapp.Models;

public class Session {

    String sessiontitle,sessiondays,sessionfee,gymid,gymname,gymlogo,key;

    public Session(String sessiontitle, String sessiondays, String sessionfee, String gymid, String gymname, String gymlogo, String key) {
        this.sessiontitle = sessiontitle;
        this.sessiondays = sessiondays;
        this.sessionfee = sessionfee;
        this.gymid = gymid;
        this.gymname = gymname;
        this.gymlogo = gymlogo;
        this.key = key;
    }

    public Session() {
    }


    public String getSessiontitle() {
        return sessiontitle;
    }

    public void setSessiontitle(String sessiontitle) {
        this.sessiontitle = sessiontitle;
    }

    public String getSessiondays() {
        return sessiondays;
    }

    public void setSessiondays(String sessiondays) {
        this.sessiondays = sessiondays;
    }

    public String getSessionfee() {
        return sessionfee;
    }

    public void setSessionfee(String sessionfee) {
        this.sessionfee = sessionfee;
    }

    public String getGymid() {
        return gymid;
    }

    public void setGymid(String gymid) {
        this.gymid = gymid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGymname() {
        return gymname;
    }

    public void setGymname(String gymname) {
        this.gymname = gymname;
    }

    public String getGymlogo() {
        return gymlogo;
    }

    public void setGymlogo(String gymlogo) {
        this.gymlogo = gymlogo;
    }
}
