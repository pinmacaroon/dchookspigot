package com.github.pinmacaroon.dchookspigot.util;

public class Unformat {
    public static String sanitize(String string){
        return string.replaceAll("§[a-z0-9]", "");
    }
}
