package mts.animals.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Vladislav Gruzdov
 */
@ConfigurationProperties
public class AppConfigProperties {

    @Value("${application.sys.logDebugData}")
    private boolean logDebugData;

    @Value("${application.core.animalCount}")
    private int animalCount;

    public boolean getLogDebugData() {
        return logDebugData;
    }

    public int getAnimalCount() {
        return animalCount;
    }

}
