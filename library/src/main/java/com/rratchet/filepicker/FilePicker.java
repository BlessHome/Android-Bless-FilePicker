package com.rratchet.filepicker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.rratchet.filepicker.model.ParamEntity;
import com.rratchet.filepicker.ui.FilePickerActivity;

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
public class FilePicker {

    private Activity mActivity;
    private Fragment mFragment;
    private android.support.v4.app.Fragment mSupportFragment;
    private String mTitle;
    private String mTitleColor;
    private String mBackgroundColor;
    private int mBackStyle;
    private int mRequestCode;
    private boolean mMutilyMode = true;
    private String mAddText;
    private int mIconStyle;
    private String[] mFileTypes;
    private String mNotFoundFiles;
    private String mDefaultPath;
    private boolean mSaveHistoricalPath;

    /**
     * 绑定Activity
     *
     * @param activity the activity
     * @return file picker
     */
    public FilePicker withActivity(Activity activity) {
        this.mActivity = activity;
        return this;
    }

    /**
     * 绑定Fragment
     *
     * @param fragment the fragment
     * @return file picker
     */
    public FilePicker withFragment(Fragment fragment) {
        this.mFragment = fragment;
        return this;
    }

    /**
     * 绑定v4包Fragment
     *
     * @param supportFragment the support fragment
     * @return file picker
     */
    public FilePicker withSupportFragment(android.support.v4.app.Fragment supportFragment) {
        this.mSupportFragment = supportFragment;
        return this;
    }


    /**
     * 设置主标题
     *
     * @param title the title
     * @return file picker
     */
    public FilePicker withTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 设置辩题颜色
     *
     * @param color the color
     * @return file picker
     */
    public FilePicker withTitleColor(String color) {
        this.mTitleColor = color;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color the color
     * @return file picker
     */
    public FilePicker withBackgroundColor(String color) {
        this.mBackgroundColor = color;
        return this;
    }

    /**
     * 请求码
     *
     * @param requestCode the request code
     * @return file picker
     */
    public FilePicker withRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    /**
     * 设置返回图标
     *
     * @param backStyle the back style
     * @return file picker
     */
    public FilePicker withBackIcon(int backStyle) {
        this.mBackStyle = 0;//默认样式
        this.mBackStyle = backStyle;
        return this;
    }

    /**
     * 设置选择模式，默认为true,多选；false为单选
     *
     * @param isMutily the is mutily
     * @return file picker
     */
    public FilePicker withMutilyMode(boolean isMutily) {
        this.mMutilyMode = isMutily;
        return this;
    }

    /**
     * 设置多选时按钮文字
     *
     * @param text the text
     * @return file picker
     */
    public FilePicker withAddText(String text) {
        this.mAddText = text;
        return this;
    }

    /**
     * 设置文件夹图标风格
     *
     * @param style the style
     * @return file picker
     */
    public FilePicker withIconStyle(int style) {
        this.mIconStyle = style;
        return this;
    }

    /**
     * With file filter file picker.
     *
     * @param arrs the arrs
     * @return the file picker
     */
    public FilePicker withFileFilter(String[] arrs) {
        this.mFileTypes = arrs;
        return this;
    }

    /**
     * 设置默认的路径
     *
     * @param defaultPath the default path
     * @return the file picker
     */
    public FilePicker withDefaultPath(String defaultPath) {
        this.mDefaultPath = defaultPath;
        return this;
    }

    /**
     * 设置是否记录上一次的访问路径
     *
     * @param saveHistoricalPath the save historical path
     * @return file picker
     */
    public FilePicker withSaveHistoricalPath(boolean saveHistoricalPath) {
        this.mSaveHistoricalPath = saveHistoricalPath;
        return this;
    }

    /**
     * 没有选中文件时的提示信息
     *
     * @param notFoundFiles the not found files
     * @return file picker
     */
    public FilePicker withNotFoundBooks(String notFoundFiles) {
        this.mNotFoundFiles = notFoundFiles;
        return this;
    }

    /**
     * Start.
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void start() {
        if (mActivity == null && mFragment == null && mSupportFragment == null) {
            throw new RuntimeException("You must pass Activity or Fragment by withActivity or withFragment or withSupportFragment method");
        }
        Intent intent = initIntent();
        Bundle bundle = getBundle();
        intent.putExtras(bundle);

        if (mActivity != null) {
            mActivity.startActivityForResult(intent, mRequestCode);
        } else if (mFragment != null) {
            mFragment.startActivityForResult(intent, mRequestCode);
        } else {
            mSupportFragment.startActivityForResult(intent, mRequestCode);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private Intent initIntent() {
        Intent intent;
        if (mActivity != null) {
            intent = new Intent(mActivity, FilePickerActivity.class);
        } else if (mFragment != null) {
            intent = new Intent(mFragment.getActivity(), FilePickerActivity.class);
        } else {
            intent = new Intent(mSupportFragment.getActivity(), FilePickerActivity.class);
        }
        return intent;
    }

    @NonNull
    private Bundle getBundle() {
        ParamEntity paramEntity = new ParamEntity();
        paramEntity.setTitle(mTitle);
        paramEntity.setTitleColor(mTitleColor);
        paramEntity.setBackgroundColor(mBackgroundColor);
        paramEntity.setBackIcon(mBackStyle);
        paramEntity.setMutilyMode(mMutilyMode);
        paramEntity.setAddText(mAddText);
        paramEntity.setIconStyle(mIconStyle);
        paramEntity.setFileTypes(mFileTypes);
        paramEntity.setNotFoundFiles(mNotFoundFiles);
        paramEntity.setDefaultPath(mDefaultPath);
        paramEntity.setSaveHistoricalPath(mSaveHistoricalPath);
        Bundle bundle = new Bundle();
        bundle.putSerializable("param", paramEntity);
        return bundle;
    }
}
