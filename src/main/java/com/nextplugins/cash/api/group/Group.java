package com.nextplugins.cash.api.group;

import com.nextplugins.cash.util.text.ColorUtil;
import lombok.Getter;
import lombok.val;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
@Getter
public class Group {

    private final String prefix;
    private final String suffix;

    public Group() {
        prefix = "";
        suffix = "";
    }

    public Group(String prefix, String suffix) {
        val coloredPrefix = ColorUtil.colored(prefix);
        if (coloredPrefix.endsWith("  ")) this.prefix = coloredPrefix.replace("  ", " ");
        else if (!coloredPrefix.endsWith(" ")) this.prefix = coloredPrefix + " ";
        else this.prefix = coloredPrefix;

        val coloredSuffix = ColorUtil.colored(suffix);
        if (!coloredSuffix.startsWith(" ")) this.suffix = " " + coloredSuffix;
        else this.suffix = coloredSuffix;
    }
}
