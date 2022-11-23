package com.example.oecd;

public class EmergencyModel {

    String ApFid,ApName,ApOfficeNumber,ApContactNumber,ApAddress,ApState,
            ApWorkingDay,ApWorkingHour,ApDescription,ApImage;

    public EmergencyModel() {
    }

    public EmergencyModel(String apFid, String apName, String apOfficeNumber, String apContactNumber, String apAddress,
                          String apState, String apWorkingDay, String apWorkingHour, String apImage, String apDescription) {
        this.ApFid = apFid;
        this.ApName = apName;
        this.ApOfficeNumber = apOfficeNumber;
        this.ApContactNumber = apContactNumber;
        this.ApAddress = apAddress;
        this.ApState = apState;
        this.ApWorkingDay = apWorkingDay;
        this.ApWorkingHour = apWorkingHour;
        this.ApImage = apImage;
        this.ApDescription = apDescription;
    }

    public String getApDescription() {
        return ApDescription;
    }

    public void setApDescription(String apDescription) {
        ApDescription = apDescription;
    }

    public String getApFid() {
        return ApFid;
    }

    public void setApFid(String apFid) {
        ApFid = apFid;
    }

    public String getApName() {
        return ApName;
    }

    public void setApName(String apName) {
        ApName = apName;
    }

    public String getApOfficeNumber() {
        return ApOfficeNumber;
    }

    public void setApOfficeNumber(String apOfficeNumber) {
        ApOfficeNumber = apOfficeNumber;
    }

    public String getApContactNumber() {
        return ApContactNumber;
    }

    public void setApContactNumber(String apContactNumber) {
        ApContactNumber = apContactNumber;
    }

    public String getApAddress() {
        return ApAddress;
    }

    public void setApAddress(String apAddress) {
        ApAddress = apAddress;
    }

    public String getApState() {
        return ApState;
    }

    public void setApState(String apState) {
        ApState = apState;
    }

    public String getApWorkingDay() {
        return ApWorkingDay;
    }

    public void setApWorkingDay(String apWorkingDay) {
        ApWorkingDay = apWorkingDay;
    }

    public String getApWorkingHour() {
        return ApWorkingHour;
    }

    public void setApWorkingHour(String apWorkingHour) {
        ApWorkingHour = apWorkingHour;
    }

    public String getApImage() {
        return ApImage;
    }

    public void setApImage(String apImage) {
        ApImage = apImage;
    }
}
