package com.example.oced_v1;

public class EmergencyModal {

    String ApFid,ApName,ApOfficeNumber,ApContactNumber,ApAddress,ApState
            ,ApWorkingDay,ApWorkingHour,ApEmail,ApImage;

    public EmergencyModal() {
    }

    public EmergencyModal(String apFid, String apName, String apOfficeNumber, String apContactNumber, String apAddress, String apState, String apWorkingDay, String apWorkingHour, String apEmail, String apImage) {
        ApFid = apFid;
        ApName = apName;
        ApOfficeNumber = apOfficeNumber;
        ApContactNumber = apContactNumber;
        ApAddress = apAddress;
        ApState = apState;
        ApWorkingDay = apWorkingDay;
        ApWorkingHour = apWorkingHour;
        ApEmail = apEmail;
        ApImage = apImage;
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

    public String getApEmail() {
        return ApEmail;
    }

    public void setApEmail(String apEmail) {
        ApEmail = apEmail;
    }

    public String getApImage() {
        return ApImage;
    }

    public void setApImage(String apImage) {
        ApImage = apImage;
    }
}
