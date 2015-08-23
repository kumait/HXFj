package hxf.ws.runtime;

/**
 * Created by kumait on 12/3/14.
 */
public class InterfaceConfiguration {
    private String name;
    private String description;
    private RuntimeInfo runtimeInfo;

    public InterfaceConfiguration(String name, String description, RuntimeInfo runtimeInfo) {
        this.name = name;
        this.description = description;
        this.runtimeInfo = runtimeInfo;
    }

    public InterfaceConfiguration(String name, RuntimeInfo runtimeInfo) {
        this(name, null, runtimeInfo);
    }

    public InterfaceConfiguration() {
        this(null, null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RuntimeInfo getRuntimeInfo() {
        return runtimeInfo;
    }

    public void setRuntimeInfo(RuntimeInfo runtimeInfo) {
        this.runtimeInfo = runtimeInfo;
    }
}
