package org.cs3219.project.peerprep.common.utils;

import org.cs3219.project.peerprep.common.constant.SymbolConstants;

public class StringUtils {
    public static String convertSpecialCharFromJavaToHtml(String input) {
        return input.replace(SymbolConstants.JAVA_ENTER_SYMBOL, SymbolConstants.HTML_ENTER_SYMBOL)
                .replace(SymbolConstants.JAVA_TAB_SYMBOL, SymbolConstants.HTML_TAB_SYMBOL);
    }
}
