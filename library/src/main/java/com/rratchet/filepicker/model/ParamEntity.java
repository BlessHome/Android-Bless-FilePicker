package com.rratchet.filepicker.model;

import java.io.Serializable;

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

public class ParamEntity implements Serializable {

    private String title;
    private String titleColor;
    private String backgroundColor;
    private int backIcon;
    private boolean mutilyMode;
    private String addText;
    private int iconStyle;
    private String[] fileTypes;
    private String notFoundFiles;
    private String defaultPath;
    private boolean saveHistoricalPath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isMutilyMode() {
        return mutilyMode;
    }

    public void setMutilyMode(boolean mutilyMode) {
        this.mutilyMode = mutilyMode;
    }

    public int getBackIcon() {
        return backIcon;
    }

    public void setBackIcon(int backIcon) {
        this.backIcon = backIcon;
    }

    public String getAddText() {
        return addText;
    }

    public void setAddText(String addText) {
        this.addText = addText;
    }

    public int getIconStyle() {
        return iconStyle;
    }

    public void setIconStyle(int iconStyle) {
        this.iconStyle = iconStyle;
    }

    public String[] getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(String[] fileTypes) {
        this.fileTypes = fileTypes;
    }

    public String getNotFoundFiles() {
        return notFoundFiles;
    }

    public void setNotFoundFiles(String notFoundFiles) {
        this.notFoundFiles = notFoundFiles;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public boolean isSaveHistoricalPath() {
        return saveHistoricalPath;
    }

    public void setSaveHistoricalPath(boolean saveHistoricalPath) {
        this.saveHistoricalPath = saveHistoricalPath;
    }
}
