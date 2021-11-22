package com.activeminders.onlinegymapp.Models;

public class Machines {

    String machinename,machineimage,quantity,key;

    public Machines(String machinename, String machineimage, String quantity, String key) {
        this.machinename = machinename;
        this.machineimage = machineimage;
        this.quantity = quantity;
        this.key = key;
    }

    public Machines() {
    }

    public String getMachinename() {
        return machinename;
    }

    public void setMachinename(String machinename) {
        this.machinename = machinename;
    }

    public String getMachineimage() {
        return machineimage;
    }

    public void setMachineimage(String machineimage) {
        this.machineimage = machineimage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
