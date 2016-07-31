package com.alban42.properties;

/**
 * Created by alban on 23/07/2016.
 *
 * @author alban
 */
public enum PropertiesEnumTest implements IProperties {

    TEST1("Test1", "Comment 1."), TEST2("Test2", "Comment 2."), TEST3("Test3", "Comment 3.");

    private String defaultValue;
    private String comment;

    PropertiesEnumTest(final String defaultValue, final String comment) {
        this.defaultValue = defaultValue;
        this.comment = comment;
    }

    @Override
    public String defaultValue() {
        return defaultValue;
    }

    @Override
    public String getComment() {
        return comment;
    }
}
