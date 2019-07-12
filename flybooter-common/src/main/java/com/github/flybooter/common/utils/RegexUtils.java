package com.github.flybooter.common.utils;

import com.github.flybooter.common.constants.RegexConstants;
import org.apache.commons.lang3.StringUtils;

import static com.github.flybooter.common.constants.RegexConstants.*;


public class RegexUtils {

    public static boolean isName(String nameString) {
        if (StringUtils.isEmpty(StringUtils.deleteWhitespace(nameString))) {
            return false;
        }
        if (RegexConstants.DIGITAL.matcher(nameString).matches()) {
            return false;
        }
        nameString = nameString.replace("·", "");
        return RegexConstants.NAME.matcher(nameString).matches();
    }

    public static boolean isZero(String amountString) {
        return RegexConstants.ZERO_PATTERN.matcher(amountString).matches();
    }

    public static boolean isDigitalOrPrice(String valueStr) {
        if (StringUtils.isNotEmpty(valueStr) && !valueStr.contains(".")) {
            return false;
        }
        return RegexConstants.AMOUNT_OR_DIGITAL.matcher(valueStr).matches();
    }

    public static boolean isScientific(String valueStr) {
        if (StringUtils.isEmpty(valueStr)) {
            return false;
        }
        return RegexConstants.SCIENTIFIC.matcher(valueStr).matches();
    }

    public static boolean isIpAddress(String ip) {
        return RegexConstants.IP_PATTERN.matcher(ip).matches();
    }

    public static boolean isValidMobilePhone(String mobile) {
        return !StringUtils.isEmpty(mobile) && mobile.matches(REGEX_MOBILE);
    }

    public static boolean isNumber(String numberString) {
        String regex = "^(-?[1-9«]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        return numberString.matches(regex);
    }

    public static boolean isEmail(String value) {
        return value.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
    }

    public static boolean isChinese(String value) {
        return value.matches(REGEX_CHINESE);
    }

    public static boolean isMobile(String value) {
        return value.matches(REGEX_MOBILE);
    }

    public static boolean isDate(String value) {
        return value.matches(REGEX_DATE);
    }

    public static boolean isMobile2(String value) {
        return value.matches(REGEX_MOBILE2);
    }

    public static boolean isIdCard(String value) {
        return value.matches(REGEX_ID_CARD);
    }


    public static boolean isLetter(String value) {
        return value.matches("^[A-Za-z]+");
    }
}
