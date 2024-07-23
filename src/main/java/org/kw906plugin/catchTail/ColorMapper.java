package org.kw906plugin.catchTail;

import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashMap;

public class ColorMapper {

    private final HashMap<Integer, String> colorNameMap = new HashMap<>();
    private final HashMap<Integer, NamedTextColor> colorIdMap = new HashMap<>();

    public String getColorName(int id) {
        return colorNameMap.get(id);
    }

    public NamedTextColor getColorId(int id) {
        return colorIdMap.get(id);
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

        colorIdMap.put(0, NamedTextColor.RED);
        colorIdMap.put(1, NamedTextColor.GOLD);
        colorIdMap.put(2, NamedTextColor.YELLOW);
        colorIdMap.put(3, NamedTextColor.GREEN);
        colorIdMap.put(4, NamedTextColor.DARK_AQUA);
        colorIdMap.put(5, NamedTextColor.AQUA);
        colorIdMap.put(6, NamedTextColor.BLUE);
        colorIdMap.put(7, NamedTextColor.DARK_PURPLE);
        colorIdMap.put(8, NamedTextColor.DARK_RED);
        colorIdMap.put(9, NamedTextColor.BLACK);
        colorIdMap.put(10, NamedTextColor.GRAY);
        colorIdMap.put(11, NamedTextColor.WHITE);
        colorIdMap.put(12, NamedTextColor.LIGHT_PURPLE);
    }
}
