package com.github.pinmacaroon.dchookspigot.util;

public class TimeConverter {

    /**
     * <a href="https://bukkit.org/threads/how-can-i-convert-minecraft-long-time-to-real-hours-and-minutes.122912/">https://bukkit.org/threads/how-can-i-convert-minecraft-long-time-to-real-hours-and-minutes.122912/</a>
     * @param time {@link Long}
     * @return {@link String}
     */
    public static String timeOfDayToHoursMinutes2(long time) {
        return String.format("%02d:%02d", (int) ((Math.floor(time / 1000.0) + 8) % 24), (int) Math.floor((time % 1000) / 1000.0 * 60));
    }
}
