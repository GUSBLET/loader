package com.source.loader.technical.unique.string.customizer;

public class UniqueStringCustomizer {
    public  static String capitalizeRecord(String name) {
        if (name != null && !name.isEmpty())
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
