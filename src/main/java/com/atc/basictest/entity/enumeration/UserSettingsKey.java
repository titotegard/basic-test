package com.atc.basictest.entity.enumeration;

public enum UserSettingsKey {
    BIOMETRIC_LOGIN("biometric_login", "false"),
    PUSH_NOTIF("push_notification", "false"),
    SMS_NOTIF("sms_notification", "false"),
    SHOW_ONBOARD("show_onboard", "false"),
    WIDGET_ORDER("widget_order", "1,2,3,4,5");

    public final String desc;
    public final String constraint;

    UserSettingsKey(String desc, String constraint) {
        this.desc = desc;
        this.constraint = constraint;
    }

    public static String getDescription(String name) {
        for (UserSettingsKey key : values()) {
            if (name.contains(key.name().toLowerCase())) {
                return key.desc;
            }
        }
        return null;
    }
}
