package com.activeminders.onlinegymapp.Models;

public class Memberships {

   public String accountname,cardno,cvv,memberemail,
            dateofbirth,memberid, membername,
            memberimage,membership,membershipdate,membershipkey,
            gymid,gymname,gymlogo,trainer;

    public Memberships(String accountname, String cardno, String cvv, String memberemail,
                       String dateofbirth, String memberid, String membername, String memberimage,
                       String membership, String membershipdate, String membershipkey,
                       String gymid, String gymname, String gymlogo, String trainer) {
        this.accountname = accountname;
        this.cardno = cardno;
        this.cvv = cvv;
        this.memberemail = memberemail;
        this.dateofbirth = dateofbirth;
        this.memberid = memberid;
        this.membername = membername;
        this.memberimage = memberimage;
        this.membership = membership;
        this.membershipdate = membershipdate;
        this.membershipkey = membershipkey;
        this.gymid = gymid;
        this.gymname = gymname;
        this.gymlogo = gymlogo;
        this.trainer=trainer;
    }

    public Memberships() {
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getMemberemail() {
        return memberemail;
    }

    public void setMemberemail(String memberemail) {
        this.memberemail = memberemail;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getMemberimage() {
        return memberimage;
    }

    public void setMemberimage(String memberimage) {
        this.memberimage = memberimage;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getMembershipdate() {
        return membershipdate;
    }

    public void setMembershipdate(String membershipdate) {
        this.membershipdate = membershipdate;
    }

    public String getMembershipkey() {
        return membershipkey;
    }

    public void setMembershipkey(String membershipkey) {
        this.membershipkey = membershipkey;
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

    public String getGymlogo() {
        return gymlogo;
    }

    public void setGymlogo(String gymlogo) {
        this.gymlogo = gymlogo;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }
}
