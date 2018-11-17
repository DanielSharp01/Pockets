package utils;

import javafx.scene.paint.Color;

public class ColorUtils {
    /**
     * Converts a hex color string to a JavaFX color
     * @param hex #RRGGBB
     * @return JavaFX color
     */
    public static Color fromHex(String hex)
    {
        return Color.rgb(Integer.valueOf(hex.substring(1, 3), 16), Integer.valueOf(hex.substring(3, 5), 16), Integer.valueOf(hex.substring(5, 7), 16));
    }

    /**
     * Converts a JavaFX color to a hex color string
     * @param color JavaFX color
     * @return #RRGGBB
     */
    public static String toHex(Color color)
    {
        return "#" + String.format("%02X", (int)(color.getRed() * 255)) + String.format("%02X", (int)(color.getGreen() * 255)) + String.format("%02X", (int)(color.getBlue() * 255));
    }
}
