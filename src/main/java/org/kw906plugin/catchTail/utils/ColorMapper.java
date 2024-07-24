package org.kw906plugin.catchTail.utils;

import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashMap;

public class ColorMapper {

    private final HashMap<Integer, String> colorNameMap = new HashMap<>();
    private final HashMap<Integer, NamedTextColor> colorMap = new HashMap<>();

    public String getColorName(int id) {
        return colorNameMap.get(id);
    }

    public NamedTextColor getColor(int id) {
        return colorMap.get(id);
    }

    public ColorMapper() {
        colorNameMap.put(0, "빨강");
        colorNameMap.put(1, "주황");
        colorNameMap.put(2, "노랑");
        colorNameMap.put(3, "초록");
        colorNameMap.put(4, "청록");
        colorNameMap.put(5, "파랑");
        colorNameMap.put(6, "남");
        colorNameMap.put(7, "보라");
        colorNameMap.put(8, "갈");
        colorNameMap.put(9, "검은");
        colorNameMap.put(10, "회백");
        colorNameMap.put(11, "흰");
        colorNameMap.put(12, "핑크");

        colorMap.put(0, NamedTextColor.RED);
        colorMap.put(1, NamedTextColor.GOLD);
        colorMap.put(2, NamedTextColor.YELLOW);
        colorMap.put(3, NamedTextColor.GREEN);
        colorMap.put(4, NamedTextColor.DARK_AQUA);
        colorMap.put(5, NamedTextColor.AQUA);
        colorMap.put(6, NamedTextColor.BLUE);
        colorMap.put(7, NamedTextColor.DARK_PURPLE);
        colorMap.put(8, NamedTextColor.DARK_RED);
        colorMap.put(9, NamedTextColor.BLACK);
        colorMap.put(10, NamedTextColor.GRAY);
        colorMap.put(11, NamedTextColor.WHITE);
        colorMap.put(12, NamedTextColor.LIGHT_PURPLE);
    }
}
