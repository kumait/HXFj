package hxf.ws.runtime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumait on 12/3/14.
 */
public class ServiceConfiguration {
    private String name;
    private String description;
    private List<InterfaceConfiguration> interfaceConfigs;

    public ServiceConfiguration(String name, String description) {
        this.name = name;
        this.description = description;
        this.interfaceConfigs = new ArrayList<InterfaceConfiguration>();
    }

    public ServiceConfiguration() {
        this(null, null);
    }

    public ServiceConfiguration(String name) {
        this(name, null);
    }

    public void addInterfaceConfig(String name, String description, Class interfaceCls, Class classCls) {
        InterfaceConfiguration iconf = new InterfaceConfiguration(name, description, new RuntimeInfo(interfaceCls, classCls));
        interfaceConfigs.add(iconf);
    }

    public void addInterfaceConfig(String name, Class interfaceCls, Class classCls) {
        InterfaceConfiguration iconf = new InterfaceConfiguration(name, null, new RuntimeInfo(interfaceCls, classCls));
        interfaceConfigs.add(iconf);
    }

    public List<InterfaceConfiguration> getInterfaceConfigs() {
        return interfaceConfigs;
    }

    public InterfaceConfiguration getInterfaceConfig(String interfaceName) {
        InterfaceConfiguration ic = null;
        int i = 0;
        while (i < interfaceConfigs.size()) {
            if (interfaceConfigs.get(i).getName().equals(interfaceName)) {
                ic = interfaceConfigs.get(i);
                break;
            }
            i++;
        }
        return ic;
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
}
