package com.github.pinmacaroon.dchookspigot.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * im hella proud of this so it isn't going anywhere >:3
 */
public class CommandParser {

    public static List<Object> parseString(String source) {
        List<String> parsed = new ArrayList<>();
        StringBuilder holder = new StringBuilder();
        boolean capture = false;
        for (char i : source.toCharArray()) {
            if (!(i == '\'' || i == '"' || i == ' ')) {
                holder.append(i);
            } else if (i == '\'' || i == '"') {
                if (capture) {
                    capture = false;
                    parsed.add(holder.toString());
                    holder.setLength(0);
                } else {
                    parsed.add(holder.toString());
                    holder.setLength(0);
                    capture = true;
                }
            } else {
                if (capture) {
                    holder.append(i);
                } else {
                    parsed.add(holder.toString());
                    holder.setLength(0);
                }
            }
        }
        List<Object> scanned = new ArrayList<>();
        parsed.add(holder.toString());
        for (String i : parsed) {
            if (i.isEmpty()) {
                continue;
            }
            try {
                scanned.add(Float.valueOf(i));
            } catch (NumberFormatException e) {
                if (i.equals("true")) {
                    scanned.add(true);
                } else if (i.equals("false")) {
                    scanned.add(false);
                } else {
                    scanned.add(i.strip());
                }
            }
        }
        return scanned;
    }
}
