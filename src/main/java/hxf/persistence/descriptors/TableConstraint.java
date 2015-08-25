package hxf.persistence.descriptors;

import java.util.List;

/**
 * Created by kumait on 8/25/2015.
 */
public abstract class TableConstraint {
    private Table table;
    private String name;
    private List<String> columns;
}
