package mts.animals.configStarter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

@ConfigurationProperties
public class AnimalConfigurationProperties {

    @Value("#{'${animal.cat.names}'.split(',')}")
    private List<String> catNames;
    @Value("#{'${animal.dog.names}'.split(',')}")
    private List<String> dogNames;
    @Value("#{'${animal.shark.names}'.split(',')}")
    private List<String> sharkNames;
    @Value("#{'${animal.tiger.names}'.split(',')}")
    private List<String> tigerNames;

    public List<String> getCatNames() {
        return Collections.unmodifiableList(catNames);
    }

    public List<String> getDogNames() {
        return Collections.unmodifiableList(dogNames);
    }

    public List<String> getSharkNames() {
        return Collections.unmodifiableList(sharkNames);
    }

    public List<String> getTigerNames() {
        return Collections.unmodifiableList(tigerNames);
    }

}