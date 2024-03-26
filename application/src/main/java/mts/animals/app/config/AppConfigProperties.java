package mts.animals.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * @author Vladislav Gruzdov
 */
@PropertySource("classpath:application.yaml")
@ConfigurationProperties(prefix = "application")
public class AppConfigProperties {

    private Map<String, String> sys;
    private Map<String, String> core;

    public boolean getLogDebugData() {
        return Boolean.parseBoolean(sys.getOrDefault("logDebugData", ""));
    }

    public int getAnimalCount() {
        return Integer.parseInt(core.getOrDefault("animalCount", "0"));
    }

    public Map<String, String> getSys() {
        return sys;
    }

    public void setSys(Map<String, String> sys) {
        this.sys = sys;
    }

    public Map<String, String> getCore() {
        return core;
    }

    public void setCore(Map<String, String> core) {
        this.core = core;
    }

}
