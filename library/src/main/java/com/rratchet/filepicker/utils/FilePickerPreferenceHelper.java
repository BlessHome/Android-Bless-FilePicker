package com.rratchet.filepicker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Set;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      18-3-7
 * 版本:      V1.0
 * 描述:      description
 *
 * </pre>
 */

public class FilePickerPreferenceHelper extends PreferencesHelper {

    private static final String PREF_NAME = "file_picker.pref";

    private static final String KEY_HISTORICAL_PATH = "KEY_HISTORICAL_PATH";

    /**
     * 实例化
     *
     * @param context the context
     */
    public FilePickerPreferenceHelper(Context context) {
        super(context, PREF_NAME);
    }

    public void saveHistoricalPath(String path) {
        putString(KEY_HISTORICAL_PATH, path);
    }

    public String obtainHistoricalPath() {
        return getString(KEY_HISTORICAL_PATH);
    }

}

abstract class PreferencesHelper {

    private SharedPreferences mSharedPreferences;

    /**
     * 实例化
     *
     * @param context the context
     * @param name    the name
     */
    protected PreferencesHelper(Context context, String name) {
        mSharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * Put boolean.
     *
     * @param key   the key
     * @param value the value
     */
    protected void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * Put float.
     *
     * @param key   the key
     * @param value the value
     */
    protected void putFloat(String key, float value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * Put int.
     *
     * @param key   the key
     * @param value the value
     */
    protected void putInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Put long.
     *
     * @param key   the key
     * @param value the value
     */
    protected void putLong(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * Put string.
     *
     * @param key   the key
     * @param value the value
     */
    protected void putString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Put string set.
     *
     * @param key   the key
     * @param value the value
     */
    protected void putStringSet(String key, Set<String> value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            editor.putStringSet(key, value);
        }
        editor.commit();
    }

    /**
     * Gets boolean.
     *
     * @param key the key
     * @return the boolean
     */
    protected boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * Gets float.
     *
     * @param key the key
     * @return the float
     */
    protected float getFloat(String key) {
        return mSharedPreferences.getFloat(key, -1.0f);
    }

    /**
     * Gets int.
     *
     * @param key the key
     * @return the int
     */
    protected int getInt(String key) {
        return mSharedPreferences.getInt(key, -1);
    }

    /**
     * Gets long.
     *
     * @param key the key
     * @return the long
     */
    protected long getLong(String key) {
        return mSharedPreferences.getLong(key, -1L);
    }

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    protected String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    /**
     * Gets string set.
     *
     * @param key the key
     * @return the string set
     */
    protected Set<String> getStringSet(String key) {
        return mSharedPreferences.getStringSet(key, null);
    }
}
