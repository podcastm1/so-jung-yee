package org.galebar.sogungyee.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by CodeRi on 2015. 7. 25..
 */
public class PreferenceHelper {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public PreferenceHelper(String prefName, Context context) {
        pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public boolean save() {
        editor.apply();
        return editor.commit();
    }
}
