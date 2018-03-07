package com.rratchet.filepicker.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rratchet.filepicker.R;
import com.rratchet.filepicker.adapter.PathAdapter;
import com.rratchet.filepicker.filter.FileFilter;
import com.rratchet.filepicker.model.ParamEntity;
import com.rratchet.filepicker.utils.Constant;
import com.rratchet.filepicker.utils.FilePickerPreferenceHelper;
import com.rratchet.filepicker.utils.FileUtils;
import com.rratchet.filepicker.widget.EmptyRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

public class FilePickerActivity extends AppCompatActivity {

    private final String TAG = "FilePicker";
    private final int RESULTCODE = 1024;
    private EmptyRecyclerView mRecyclerView;
    private View mEmptyView;
    private TextView mTvPath;
    private TextView mTvBack;
    private Button mBtnAddBook;
    private String mPath;
    private List<File> mListFiles;
    private ArrayList<String> mListNumbers = new ArrayList<String>();//存放选中条目的数据地址
    private PathAdapter mPathAdapter;
    private Toolbar mToolbar;
    private ParamEntity mParamEntity;
    private FileFilter mFilter;

    private FilePickerPreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);
        mParamEntity = (ParamEntity) getIntent().getExtras().getSerializable("param");
        initView();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initToolbar();
        if (!checkSDState()) {
            Toast.makeText(this, R.string.FilePicker_NotFoundPath, Toast.LENGTH_SHORT).show();
            return;
        }

        preferenceHelper = new FilePickerPreferenceHelper(this);
        /*
         * 判断是否记录上次路径
         * 是：从历史读取
         * 否：判断是否有默认路径
         */
        String showPath;
        if (mParamEntity.isSaveHistoricalPath()) {
            showPath = preferenceHelper.obtainHistoricalPath();
        } else {
            showPath = mParamEntity.getDefaultPath();
        }
        if (showPath != null && !"".equals(showPath)) {
            File file = new File(showPath);
            if (file != null && file.exists() && file.isDirectory()) {
                mPath = showPath;
            }
        }

        if (mPath == null || "".equals(mPath)) {
            mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        mTvPath.setText(mPath);
        mFilter = new FileFilter(mParamEntity.getFileTypes());
        mListFiles = getFileList(mPath);
        mPathAdapter = new PathAdapter(mListFiles, this, mFilter, mParamEntity.isMutilyMode());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mPathAdapter.setIconStyle(mParamEntity.getIconStyle());
        mRecyclerView.setAdapter(mPathAdapter);
        mRecyclerView.setEmptyView(mEmptyView);
        initListener();

    }

    @Override
    protected void onDestroy() {
        if (preferenceHelper != null) {
            preferenceHelper.saveHistoricalPath(mPath);
        }
        super.onDestroy();
    }

    /**
     * 更新Toolbar展示
     */
    private void initToolbar() {
        if (mParamEntity.getTitle() != null) {
            mToolbar.setTitle(mParamEntity.getTitle());
        }
        if (mParamEntity.getTitleColor() != null) {
            mToolbar.setTitleTextColor(Color.parseColor(mParamEntity.getTitleColor())); //设置标题颜色
        }
        if (mParamEntity.getBackgroundColor() != null) {
            mToolbar.setBackgroundColor(Color.parseColor(mParamEntity.getBackgroundColor()));
        }
        if (!mParamEntity.isMutilyMode()) {
            mBtnAddBook.setVisibility(View.GONE);
        }
        switch (mParamEntity.getBackIcon()) {
            case Constant.BACKICON_STYLEONE:
                mToolbar.setNavigationIcon(R.mipmap.filepicker_backincostyleone);
                break;
            case Constant.BACKICON_STYLETWO:
                mToolbar.setNavigationIcon(R.mipmap.filepicker_backincostyletwo);
                break;
            case Constant.BACKICON_STYLETHREE:
                //默认风格
                break;
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 添加点击事件处理
     */
    private void initListener() {
        // 返回目录上一级
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPath = new File(mPath).getParent();
                if (tempPath == null) {
                    return;
                }
                mPath = tempPath;
                mListFiles = getFileList(mPath);
                mPathAdapter.setListData(mListFiles);
                mPathAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(0);
                setShowPath(mPath);
                //清除添加集合中数据
                mListNumbers.clear();
                if (mParamEntity.getAddText() != null) {
                    mBtnAddBook.setText(mParamEntity.getAddText());
                } else {
                    mBtnAddBook.setText(R.string.FilePicker_Selected);
                }
            }
        });
        mPathAdapter.setOnItemClickListener(new PathAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                if (mParamEntity.isMutilyMode()) {
                    if (mListFiles.get(position).isDirectory()) {
                        //如果当前是目录，则进入继续查看目录
                        chekInDirectory(position);
                    } else {
                        //如果已经选择则取消，否则添加进来
                        if (mListNumbers.contains(mListFiles.get(position).getAbsolutePath())) {
                            mListNumbers.remove(mListFiles.get(position).getAbsolutePath());
                        } else {
                            mListNumbers.add(mListFiles.get(position).getAbsolutePath());
                        }
                        if (mParamEntity.getAddText() != null) {
                            mBtnAddBook.setText(mParamEntity.getAddText() + "( " + mListNumbers.size() + " )");
                        } else {
                            mBtnAddBook.setText(getString(R.string.FilePicker_Selected) + "( " + mListNumbers.size() + " )");
                        }

                    }
                } else {
                    //单选模式直接返回
                    if (mListFiles.get(position).isDirectory()) {
                        chekInDirectory(position);
                        return;
                    }
                    mListNumbers.add(mListFiles.get(position).getAbsolutePath());
                    chooseDone();
                }

            }
        });

        mBtnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListNumbers.size() < 1) {
                    String info = mParamEntity.getNotFoundFiles();
                    if (TextUtils.isEmpty(info)) {
                        Toast.makeText(FilePickerActivity.this, R.string.FilePicker_NotFoundBooks, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FilePickerActivity.this, info, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //返回
                    chooseDone();
                }
            }
        });
    }


    /**
     * 点击进入目录
     *
     * @param position
     */
    private void chekInDirectory(int position) {
        mPath = mListFiles.get(position).getAbsolutePath();
        setShowPath(mPath);
        //更新数据源
        mListFiles = getFileList(mPath);
        mPathAdapter.setListData(mListFiles);
        mPathAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    /**
     * 完成提交
     */
    private void chooseDone() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("paths", mListNumbers);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    /**
     * 根据地址获取当前地址下的所有目录和文件，并且排序
     *
     * @param path
     * @return List<File>
     */
    private List<File> getFileList(String path) {
        File file = new File(path);
        List<File> list = FileUtils.getFileListByDirPath(path, mFilter);
        return list;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mRecyclerView = (EmptyRecyclerView) findViewById(R.id.recylerview);
        mTvPath = (TextView) findViewById(R.id.tv_path);
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mBtnAddBook = (Button) findViewById(R.id.btn_addbook);
        mEmptyView = findViewById(R.id.empty_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mParamEntity.getAddText() != null) {
            mBtnAddBook.setText(mParamEntity.getAddText());
        }
    }

    /**
     * 检测SD卡是否可用
     */
    private boolean checkSDState() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 显示顶部地址
     *
     * @param path
     */
    private void setShowPath(String path) {
        mTvPath.setText(path);
    }
}
