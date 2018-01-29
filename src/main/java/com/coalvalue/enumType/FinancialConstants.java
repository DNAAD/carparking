package com.coalvalue.enumType;

/**
 * Created by Administrator on 2015/10/6.
 */
public enum FinancialConstants {
    DEBIT("DEBIT"), CREDIT("CREDIT");

    private String value;

    FinancialConstants(String value) {
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static FinancialConstants fromValue(String value) {
                for (FinancialConstants f : values()) {
                    if (value == f.getValue()) {
                return f;
            }
        }
        throw new IllegalArgumentException("unknown value given");
    }
}
