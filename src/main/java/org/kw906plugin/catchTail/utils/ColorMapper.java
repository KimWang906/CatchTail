package org.kw906plugin.catchTail.utils;

import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashMap;

public class ColorMapper {

    private final HashMap<Integer, String> colorNameMap = new HashMap<>();
    private final HashMap<Integer, NamedTextColor> colorMap = new HashMap<>();
    private final HashMap<String, String> nextColorMap = new HashMap<>();

    public String getColorName(int id) {
        return colorNameMap.get(id);
    }

    public NamedTextColor getColor(int id) {
        return colorMap.get(id);
    }

    public String getNextColor(String colorName) {
        return nextColorMap.get(colorName);
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

        nextColorMap.put("주황", "빨강");
        nextColorMap.put("노랑", "주황");
        nextColorMap.put("초록", "노랑");
        nextColorMap.put("청록", "초록");
        nextColorMap.put("파랑", "청록");
        nextColorMap.put("남", "파랑");
        nextColorMap.put("보라", "남");
        nextColorMap.put("갈", "보라");
        nextColorMap.put("검은", "갈");
        nextColorMap.put("회백", "검은");
        nextColorMap.put("흰", "회백");
        nextColorMap.put("핑크", "흰");
        nextColorMap.put("빨강", "핑크");
    }
}
