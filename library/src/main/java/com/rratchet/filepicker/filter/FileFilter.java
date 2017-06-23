package com.rratchet.filepicker.filter;

import java.io.File;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      2017/6/23
 * 版本:      V1.0
 * 描述:      description
 *
 * </pre>
 */

public class FileFilter implements java.io.FileFilter {

    private String[] mTypes;

    public FileFilter(String[] types) {
        this.mTypes = types;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        if (mTypes != null && mTypes.length > 0) {
            for (int i = 0; i < mTypes.length; i++) {
                if (file.getName().endsWith(mTypes[i].toLowerCase()) || file.getName().endsWith(mTypes[i].toUpperCase())) {
                    return true;
                }
            }
        }else {
            return true;
        }
        return false;
    }
}
