package com.rratchet.filepicker.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      2017/6/23
 * 版本:      V1.0
 * 描述:      综合过滤器
 *
 * </pre>
 */

public class CompositeFilter implements FileFilter, Serializable {

    private ArrayList<FileFilter> mFilters;

    public CompositeFilter(ArrayList<FileFilter> filters) {
        mFilters = filters;
    }

    @Override
    public boolean accept(File file) {
        for (FileFilter filter : mFilters) {
            if (!filter.accept(file)) {
                return false;
            }
        }
        return true;
    }
}
