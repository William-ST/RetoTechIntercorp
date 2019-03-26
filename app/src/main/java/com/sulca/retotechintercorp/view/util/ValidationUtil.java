package com.sulca.retotechintercorp.view.util;

import android.text.TextUtils;

/**
 * Created by everis on 25/03/19.
 */
public class ValidationUtil {

    public static boolean isValidPhoneNumber(String number) {
        return number != null && number.length() == 9 && number.charAt(0) == '9';
    }

    public static boolean isValidField(String field) {
        return !TextUtils.isEmpty(field);
    }

}
