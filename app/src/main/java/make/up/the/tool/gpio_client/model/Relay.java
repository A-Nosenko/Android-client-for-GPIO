package make.up.the.tool.gpio_client.model;

/**
 * @author Anatolii Nosenko
 * @version 07 May 2018
 */
public class Relay {
    private int id;
    private final String technicalName;
    private String customName;
    private boolean enabled;

    public Relay(int id, String technicalName, String customName, boolean enabled) {
        this.id = id;
        this.technicalName = technicalName;
        this.customName = customName;
        this.enabled = enabled;
    }
}
