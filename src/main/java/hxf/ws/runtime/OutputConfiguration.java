package hxf.ws.runtime;

/**
 * Created by kumuhammad on 2/24/2015.
 */
public class OutputConfiguration {
    private boolean customOutput;
    private boolean dynamic;
    private boolean prettyOutput;
    private String customAdapterClass;

    public OutputConfiguration(boolean customOutput, boolean dynamic, boolean prettyOutput, String customAdapterClass) {
        this.customOutput = customOutput;
        this.dynamic = dynamic;
        this.prettyOutput = prettyOutput;
        this.customAdapterClass = customAdapterClass;
    }

    public OutputConfiguration() {

    }

    public boolean isCustomOutput() {
        return customOutput;
    }

    public void setCustomOutput(boolean customOutput) {
        this.customOutput = customOutput;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public boolean isPrettyOutput() {
        return prettyOutput;
    }

    public void setPrettyOutput(boolean prettyOutput) {
        this.prettyOutput = prettyOutput;
    }

    public String getCustomAdapterClass() {
        return customAdapterClass;
    }

    public void setCustomAdapterClass(String customAdapterClass) {
        this.customAdapterClass = customAdapterClass;
    }
}
