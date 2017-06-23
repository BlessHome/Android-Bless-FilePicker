package com.rratchet.filepicker.utils;

import java.io.File;
import java.util.Comparator;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      2017/6/23
 * 版本:      V1.0
 * 描述:      文件比较器
 *
 * </pre>
 */

public class FileComparator implements Comparator<File> {

    @Override
    public int compare(File f1, File f2) {
        if(f1 == f2) {
            return 0;
        }
        if(f1.isDirectory() && f2.isFile()) {
            // Show directories above files
            return -1;
        }
        if(f1.isFile() && f2.isDirectory()) {
            // Show files below directories
            return 1;
        }
        // Sort the directories alphabetically
        return f1.getName().compareToIgnoreCase(f2.getName());
    }
}
