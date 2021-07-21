package com.example.hellopeople;

public class User {
    private String name;
    private String password;
    private String ip;
    private String country;
    private String country_code;
    private String region;
    private String region_code;
    private String city;
    private String timeZone;
    private String org;
    private boolean isMobile;


    // Contructor for Login
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Contructor Complete
    public User(String name, String password, String ip, String country, String country_code,
                String region, String region_code, String city, String timeZone, String org,
                boolean isMobile) {
        this.name = name;
        this.password = password;
        this.ip = ip;
        this.country = country;
        this.country_code = country_code;
        this.region = region;
        this.region_code = region_code;
        this.city = city;
        this.timeZone = timeZone;
        this.org = org;
        this.isMobile = isMobile;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
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
}
