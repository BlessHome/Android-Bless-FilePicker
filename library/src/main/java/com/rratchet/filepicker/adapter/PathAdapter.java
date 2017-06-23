package com.rratchet.filepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rratchet.filepicker.R;
import com.rratchet.filepicker.filter.FileFilter;
import com.rratchet.filepicker.utils.Constant;
import com.rratchet.filepicker.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * <pre>
 *
 * 作者:      ASLai(gdcpljh@126.com).
 * 日期:      2017/6/23
 * 版本:      V1.0
 * 描述:      路径适配器
 *
 * </pre>
 */

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.PathViewHolder> {

    private final String TAG = "FilePicker";
    public OnItemClickListener onItemClickListener;
    private List<File> mListData;
    private Context mContext;
    private FileFilter mFileFilter;
    private boolean[] mCheckedFlags;
    private boolean mMultiMode;
    private int mIconStyle;

    public PathAdapter(List<File> mListData, Context mContext, FileFilter mFileFilter, boolean mMultiMode) {
        this.mListData = mListData;
        this.mContext = mContext;
        this.mFileFilter = mFileFilter;
        this.mMultiMode = mMultiMode;
        mCheckedFlags = new boolean[mListData.size()];
    }

    @Override
    public PathViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.filepicker_listitem, null);
        PathViewHolder pathViewHolder = new PathViewHolder(view);
        return pathViewHolder;
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @Override
    public void onBindViewHolder(final PathViewHolder holder, final int position) {
        final File file = mListData.get(position);
        if (file.isFile()) {
            updateFileIconStyle(holder.itemPathTypeImageView);
            holder.itemPathNameTextView.setText(file.getName());
            holder.itemPathDetailTextView.setText(mContext.getString(R.string.FilePicker_FileSize) + " " + FileUtils.getReadableFileSize(file.length()));
            holder.itemPathChooseCheckBox.setVisibility(View.VISIBLE);
        } else {
            updateFloaderIconStyle(holder.itemPathTypeImageView);
            holder.itemPathNameTextView.setText(file.getName());
            File[] files = file.listFiles(mFileFilter);
            if (files == null) {
                holder.itemPathDetailTextView.setText("0 " + mContext.getString(R.string.FilePicker_LItem));
            } else {
                holder.itemPathDetailTextView.setText(files.length + " " + mContext.getString(R.string.FilePicker_LItem));
            }
            holder.itemPathChooseCheckBox.setVisibility(View.GONE);
        }
        if (!mMultiMode) {
            holder.itemPathChooseCheckBox.setVisibility(View.GONE);
        }
        holder.itemPathRootRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file.isFile()) {
                    holder.itemPathChooseCheckBox.setChecked(!holder.itemPathChooseCheckBox.isChecked());
                }
                onItemClickListener.click(position);
            }
        });
        holder.itemPathChooseCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同步复选框和外部布局点击的处理
                onItemClickListener.click(position);
            }
        });
        holder.itemPathChooseCheckBox.setOnCheckedChangeListener(null);//先设置一次CheckBox的选中监听器，传入参数null
        holder.itemPathChooseCheckBox.setChecked(mCheckedFlags[position]);//用数组中的值设置CheckBox的选中状态
        //再设置一次CheckBox的选中监听器，当CheckBox的选中状态发生改变时，把改变后的状态储存在数组中
        holder.itemPathChooseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCheckedFlags[position] = b;
            }
        });
    }

    private void updateFloaderIconStyle(ImageView imageView) {
        switch (mIconStyle) {
            case Constant.ICON_STYLE_BLUE:
                imageView.setBackgroundResource(R.mipmap.filepicker_folder_style_blue);
                break;
            case Constant.ICON_STYLE_GREEN:
                imageView.setBackgroundResource(R.mipmap.filepicker_folder_style_green);
                break;
            case Constant.ICON_STYLE_YELLOW:
                imageView.setBackgroundResource(R.mipmap.filepicker_folder_style_yellow);
                break;
        }
    }

    private void updateFileIconStyle(ImageView imageView) {
        switch (mIconStyle) {
            case Constant.ICON_STYLE_BLUE:
                imageView.setBackgroundResource(R.mipmap.filepicker_file_style_blue);
                break;
            case Constant.ICON_STYLE_GREEN:
                imageView.setBackgroundResource(R.mipmap.filepicker_file_style_green);
                break;
            case Constant.ICON_STYLE_YELLOW:
                imageView.setBackgroundResource(R.mipmap.filepicker_file_style_yellow);
                break;
        }
    }

    /**
     * 设置监听器
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置数据源
     *
     * @param listData
     */
    public void setListData(List<File> listData) {
        this.mListData = listData;
        mCheckedFlags = new boolean[mListData.size()];
    }

    public void setIconStyle(int iconStyle) {
        this.mIconStyle = iconStyle;
    }

    public interface OnItemClickListener {
        void click(int position);
    }

    public interface OnCancelChoosedListener {
        void cancelChoosed(CheckBox checkBox);
    }

    class PathViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout itemPathRootRelativeLayout;
        private ImageView itemPathTypeImageView;
        private TextView itemPathNameTextView;
        private TextView itemPathDetailTextView;
        private CheckBox itemPathChooseCheckBox;

        public PathViewHolder(View itemView) {
            super(itemView);
            itemPathRootRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_path_root_relativeLayout);
            itemPathTypeImageView = (ImageView) itemView.findViewById(R.id.item_path_type_imageView);
            itemPathNameTextView = (TextView) itemView.findViewById(R.id.item_path_name_textView);
            itemPathDetailTextView = (TextView) itemView.findViewById(R.id.item_path_detail_textView);
            itemPathChooseCheckBox = (CheckBox) itemView.findViewById(R.id.item_path_choose_checkBox);
        }
    }
}
