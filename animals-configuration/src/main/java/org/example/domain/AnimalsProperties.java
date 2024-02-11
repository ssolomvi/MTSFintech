package org.example.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "animal")
public class AnimalsProperties {
    private String[] cat;
    private String[] dog;
    private String[] tiger;
    private String[] shark;


    public String[] getCat() {
        return cat;
    }

    public void setCat(String[] cat) {
        this.cat = cat;
    }

    public String[] getDog() {
        return dog;
    }

    public void setDog(String[] dog) {
        this.dog = dog;
    }

    public String[] getTiger() {
        return tiger;
    }

    public void setTiger(String[] tiger) {
        this.tiger = tiger;
    }

    public String[] getShark() {
        return shark;
    }

    public void setShark(String[] shark) {
        this.shark = shark;
    }
    // standard getters and setters
}
