package com.github.pinmacaroon.dchookspigot.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WaypointParser {
    private static final Pattern WAYPOINT_PATTERN = Pattern.compile(
            //               name    marker   x        y        z        yaw       ?        ?                  dimension
            "^xaero-waypoint:([^:]+):(.{1,2}):(-?\\d+):(-?\\d+):(-?\\d+):(-?\\d+):([^:]+):([^:]+):Internal-(the-nether|overworld|the-end)[-waypoints]?$"
    );

    public static boolean isWaypoint(String text) {
        return WAYPOINT_PATTERN.matcher(text).matches();
    }

    /**
     1. name
     2. marker
     3. xyz
     4. dimension
     */
    public static List<Object> constructWaypointFromString(String text) {
        Matcher matcher = WAYPOINT_PATTERN.matcher(text);
        //noinspection ResultOfMethodCallIgnored
        matcher.matches();
        return List.of(
                matcher.group(1),
                matcher.group(2),
                new int[]{
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(5)),
                        Integer.parseInt(matcher.group(6)),
                },
                friendlyName(matcher.group(9))
        );
    }

    private static String friendlyName(String s){
        switch (s) {
            case "overworld" : return "the overworld";
            case "the-nether" : return "The Nether";
            case "the-end" : return "The End";
            default : return "Narnia";
        }
    }
}
