package hxf.ws.runtime;

/**
 * Created by kumait on 12/3/14.
 */
public class RuntimeInfo {
    private Class interfaceCls;
    private Class classCls;

    public RuntimeInfo(Class interfaceCls, Class classCls) {
        this.interfaceCls = interfaceCls;
        this.classCls = classCls;
    }

    public Class getInterfaceCls() {
        return interfaceCls;
    }

    public Class getClassCls() {
        return classCls;
    }
}
