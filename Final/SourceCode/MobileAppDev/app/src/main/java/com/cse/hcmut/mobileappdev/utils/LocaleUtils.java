package com.cse.hcmut.mobileappdev.utils;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.ContextThemeWrapper;

import java.util.Locale;

/**
 * Created by dinhn on 4/25/2016.
 */
public class LocaleUtils {

    private static Locale sLocale;

    public static void setLocale(Locale locale) {
        sLocale = locale;
        if (sLocale != null) {
            Locale.setDefault(sLocale);
        }
    }

    public static void updateConfig(ContextThemeWrapper wrapper) {
        if (sLocale != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Resources res = wrapper.getBaseContext().getResources();
            Configuration configuration = res.getConfiguration();
            configuration.setLocale(sLocale);
            res.updateConfiguration(configuration, res.getDisplayMetrics());

            updateResSystem();
        }
    }

    public static void updateConfig(Application app, Configuration configuration) {
        if (sLocale != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration config = new Configuration(configuration);
            config.locale = sLocale;
            Resources res = app.getBaseContext().getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());

            updateResSystem();
        }
    }

    private static void updateResSystem() {
        Resources resSystem = Resources.getSystem();
        Configuration systemConfig = resSystem.getConfiguration();
        systemConfig.locale = sLocale;
        resSystem.updateConfiguration(systemConfig, resSystem.getDisplayMetrics());
    }

}
