package com.example.hellopeople.model;

public class Ip {
    private String ip;
    private String country;
    private String country_code;
    private String region;
    private String region_code;
    private String city;
    private String timeZone;
    private String org;
    private boolean isMobile;

    private String message_error;

    // Empty Contructor
    public Ip(){}

    // Contructor For Information IP
    public Ip(String country, String country_code,
                String region, String region_code, String city, String timeZone, String org,
                boolean isMobile) {
        this.country = country;
        this.country_code = country_code;
        this.region = region;
        this.region_code = region_code;
        this.city = city;
        this.timeZone = timeZone;
        this.org = org;
        this.isMobile = isMobile;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public String getCountry() {
        return country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getRegion() {
        return region;
    }

    public String getRegion_code() {
        return region_code;
    }

    public String getCity() {
        return city;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getOrg() {
        return org;
    }

    public boolean isMobile() {
        return isMobile;
    }


    public void setMessage_error(String message_error) {
        this.message_error = message_error;
    }

    public String getMessage_error() {
        return message_error;
    }
}
