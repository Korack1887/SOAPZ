package com.example.SOAPZ.entity;

import java.util.Arrays;

public enum Genre {
    MYSTERY, COMEDY, FANTASY, FICTION, DRAMA, HORROR;

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }
}
