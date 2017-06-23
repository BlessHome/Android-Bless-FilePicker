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

    /**
     * 绑定Activity
     *
     * @param activity
     * @return
     */
    public FilePicker withActivity(Activity activity) {
        this.mActivity = activity;
        return this;
    }

    /**
     * 绑定Fragment
     *
     * @param fragment
     * @return
     */
    public FilePicker withFragment(Fragment fragment) {
        this.mFragment = fragment;
        return this;
    }

    /**
     * 绑定v4包Fragment
     *
     * @param supportFragment
     * @return
     */
    public FilePicker withSupportFragment(android.support.v4.app.Fragment supportFragment) {
        this.mSupportFragment = supportFragment;
        return this;
    }


    /**
     * 设置主标题
     *
     * @param title
     * @return
     */
    public FilePicker withTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 设置辩题颜色
     *
     * @param color
     * @return
     */
    public FilePicker withTitleColor(String color) {
        this.mTitleColor = color;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color
     * @return
     */
    public FilePicker withBackgroundColor(String color) {
        this.mBackgroundColor = color;
        return this;
    }

    /**
     * 请求码
     *
     * @param requestCode
     * @return
     */
    public FilePicker withRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    /**
     * 设置返回图标
     *
     * @param backStyle
     * @return
     */
    public FilePicker withBackIcon(int backStyle) {
        this.mBackStyle = 0;//默认样式
        this.mBackStyle = backStyle;
        return this;
    }

    /**
     * 设置选择模式，默认为true,多选；false为单选
     *
     * @param isMutily
     * @return
     */
    public FilePicker withMutilyMode(boolean isMutily) {
        this.mMutilyMode = isMutily;
        return this;
    }

    /**
     * 设置多选时按钮文字
     *
     * @param text
     * @return
     */
    public FilePicker withAddText(String text) {
        this.mAddText = text;
        return this;
    }

    /**
     * 设置文件夹图标风格
     *
     * @param style
     * @return
     */
    public FilePicker withIconStyle(int style) {
        this.mIconStyle = style;
        return this;
    }

    public FilePicker withFileFilter(String[] arrs) {
        this.mFileTypes = arrs;
        return this;
    }

    /**
     * 没有选中文件时的提示信息
     *
     * @param notFoundFiles
     * @return
     */
    public FilePicker withNotFoundBooks(String notFoundFiles) {
        this.mNotFoundFiles = notFoundFiles;
        return this;
    }

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
        Bundle bundle = new Bundle();
        bundle.putSerializable("param", paramEntity);
        return bundle;
    }
}
