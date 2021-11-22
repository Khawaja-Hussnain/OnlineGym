package com.activeminders.onlinegymapp.Models;

public class TrainerMemberships {

    public String membername,memberid,gymid,gymname,membership,membershipkey,membershipdate;

    public TrainerMemberships(String membername, String memberid, String gymid,
                              String gymname, String membership, String membershipkey, String membershipdate) {
        this.membername = membername;
        this.memberid = memberid;
        this.gymid = gymid;
        this.gymname = gymname;
        this.membership = membership;
        this.membershipkey = membershipkey;
        this.membershipdate = membershipdate;
    }

    public TrainerMemberships() {
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getGymid() {
        return gymid;
    }

    public void setGymid(String gymid) {
        this.gymid = gymid;
    }

    public String getGymname() {
        return gymname;
    }

    public void setGymname(String gymname) {
        this.gymname = gymname;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getMembershipkey() {
        return membershipkey;
    }

    public void setMembershipkey(String membershipkey) {
        this.membershipkey = membershipkey;
    }

    public String getMembershipdate() {
        return membershipdate;
    }

    public void setMembershipdate(String membershipdate) {
        this.membershipdate = membershipdate;
    }
}
