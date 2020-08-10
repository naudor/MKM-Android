package com.MKMAndroid.classes;

/**
 * Created by Arnau on 24/03/2014.
 */
public class Condition {
    private String description;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Condition (String description, String code){
        this.description = description;
        this.code = code;
    }

}
