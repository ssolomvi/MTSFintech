package org.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class Properties {
    private String[] catNames;
    private String[] dogNames;
    private String[] sharkNames;
    private String[] tigerNames;


    public String[] getCatNames() {
        return catNames;
    }

    public void setCatNames(String[] catNames) {
        this.catNames = catNames;
    }

    public String[] getDogNames() {
        return dogNames;
    }

    public void setDogNames(String[] dogNames) {
        this.dogNames = dogNames;
    }

    public String[] getSharkNames() {
        return sharkNames;
    }

    public void setSharkNames(String[] sharkNames) {
        this.sharkNames = sharkNames;
    }

    public String[] getTigerNames() {
        return tigerNames;
    }

    public void setTigerNames(String[] tigerNames) {
        this.tigerNames = tigerNames;
    }
}
