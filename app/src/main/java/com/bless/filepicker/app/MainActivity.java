package com.bless.filepicker.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rratchet.filepicker.FilePicker;
import com.rratchet.filepicker.utils.Constant;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "FilePicker";
    private RadioGroup mRgIconType;
    private RadioGroup mRgBackArrawType;
    private int mIconType;
    private int mBackArrawType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }


    private void initView() {
        mRgIconType = (RadioGroup) findViewById(R.id.rg_iconstyle);
        mRgBackArrawType = (RadioGroup) findViewById(R.id.rg_backarrawstyle);
    }

    private void initListener() {
        mRgIconType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_yellow:
                        mIconType = Constant.ICON_STYLE_YELLOW;
                        break;
                    case R.id.radio_green:
                        mIconType = Constant.ICON_STYLE_GREEN;
                        break;
                    case R.id.radio_blue:
                        mIconType = Constant.ICON_STYLE_BLUE;
                        break;
                }
            }
        });
        mRgBackArrawType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.arrawback_styleone:
                        mBackArrawType = Constant.BACKICON_STYLEONE;
                        break;
                    case R.id.arrawback_styletwo:
                        mBackArrawType = Constant.BACKICON_STYLETWO;
                        break;
                    case R.id.arrawback_stylethree:
                        mBackArrawType = Constant.BACKICON_STYLETHREE;
                        break;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void openFromActivity(View view) {
        new FilePicker()
                .withActivity(this)
                .withRequestCode(Consant.REQUESTCODE_FROM_ACTIVITY)
                .withTitle("文件选择")
                .withIconStyle(mIconType)
                .withBackIcon(mBackArrawType)
                .withNotFoundBooks("至少选择一个文件")
                //.withFileFilter(new String[]{"txt", "png", "docx"})
                .start();
    }

    public void openFragmentActivity(View view) {
        startActivity(new Intent(this, FragmentActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Consant.REQUESTCODE_FROM_ACTIVITY) {
                List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
                //for (String s : list) {
                //    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                //}
                Toast.makeText(getApplicationContext(), "选中了" + list.size() + "个文件", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
