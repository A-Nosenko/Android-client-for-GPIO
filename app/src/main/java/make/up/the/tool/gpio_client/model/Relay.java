package make.up.the.tool.gpio_client.model;

/**
 * @author Anatolii Nosenko
 * @version 07 May 2018
 */
public class Relay {
    private final String technicalName;
    private String customName;
    private boolean enabled;

    public Relay(String technicalName, String customName, boolean enabled) {
        this.technicalName = technicalName;
        this.customName = customName;
        this.enabled = enabled;
    }


    public String getTechnicalName() {
        return technicalName;
    }

    public String getCustomName() {
        return customName;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
