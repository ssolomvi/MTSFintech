package mts.animals.config_starter.config;

import mts.animals.config_starter.exceptions.AnimalConfigurationStarterIllegalArgument;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "animal")
public class AnimalConfigurationProperties {

    private Map<String, List<String>> names;

    public Map<String, List<String>> getNames() {
        return Collections.unmodifiableMap(names);
    }

    public void setNames(Map<String, List<String>> names) {
        if (names == null) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalConfigurationProperties::setNames: param" +
                    " names should not be null");
        }
        this.names = names;
    }

    public List<String> getCatNames() {
        return findNames("cat");
    }

    public List<String> getDogNames() {
        return findNames("dog");
    }

    public List<String> getSharkNames() {
        return findNames("shark");
    }

    public List<String> getTigerNames() {
        return findNames("tiger");
    }

    private List<String> findNames(String typeName) {
        if (typeName == null) {
            throw new AnimalConfigurationStarterIllegalArgument("AnimalConfigurationProperties::findNames: param" +
                    " typeName should not be null");
        }

        var result = names.getOrDefault(typeName, Collections.emptyList());
        //this object comparison is an exception because we are comparing a reference to a constant
        if (Collections.<String>emptyList() == result) {
            return result;
        }

        return Collections.unmodifiableList(result);
    }

}