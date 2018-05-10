package com.isoftston.issuser.conchapp.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corelibs.utils.IMEUtil;
import com.isoftston.issuser.conchapp.R;

import java.math.BigInteger;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 输入栏控件
 * Created by issuser on 2018/4/11.
 */

public class InputView extends LinearLayout {

    @Bind(R.id.tv_type)
    TextView tv_type;
    @Bind(R.id.rl_all)
    RelativeLayout rl_all;
    @Bind(R.id.et_input)
    EditText et_input;
    //    @Bind(R.id.tv_write)
//    TextView tv_write;
    @Bind(R.id.tv_result)
    TextView tv_result;
    @Bind(R.id.ll_alter)
    LinearLayout ll_alter;
    @Bind(R.id.ll_right)
    LinearLayout ll_right;

    public InputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public InputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InputView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_input, this);
        ButterKnife.bind(this, this);

        ll_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_input.setVisibility(VISIBLE);
                ll_right.setVisibility(GONE);
                tv_type.setTextColor(getResources().getColor(R.color.white));
                rl_all.setBackgroundDrawable(getResources().getDrawable(R.drawable.ll_input_selector_bg));
                et_input.setFocusable(true);
                et_input.setFocusableInTouchMode(true);
                et_input.requestFocus();

            }
        });

//        tv_write.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                et_input.setVisibility(VISIBLE);
//                tv_write.setVisibility(GONE);
//                tv_type.setTextColor(getResources().getColor(R.color.white));
//                rl_all.setBackground(getResources().getDrawable(R.drawable.ll_input_selector_bg));
//                et_input.setFocusable(true);
//                et_input.setFocusableInTouchMode(true);
//                et_input.requestFocus();
//            }
//        });
//        tv_result.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                et_input.setVisibility(VISIBLE);
//                tv_result.setVisibility(GONE);
//                tv_type.setTextColor(getResources().getColor(R.color.white));
//                rl_all.setBackground(getResources().getDrawable(R.drawable.ll_input_selector_bg));
//                et_input.setFocusable(true);
//                et_input.setFocusableInTouchMode(true);
//                et_input.requestFocus();
//            }
//        });

        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(et_input.getText().toString())) {
                        tv_result.setText(et_input.getText().toString());
                    } else {
                        tv_result.setText("");
                    }
                    et_input.setVisibility(GONE);
                    ll_right.setVisibility(VISIBLE);
                    rl_all.setBackgroundDrawable(null);
                    tv_type.setTextColor(getResources().getColor(R.color.black));
                    IMEUtil.closeIME(et_input, getContext());
                    return true;
                }
                return false;
            }
        });
        et_input.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if (!TextUtils.isEmpty(et_input.getText().toString())) {
                        tv_result.setText(et_input.getText().toString());
                    } else {
                        tv_result.setText("");
                    }
                    et_input.setVisibility(GONE);
                    ll_right.setVisibility(VISIBLE);
                    rl_all.setBackgroundDrawable(null);
                    tv_type.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
    }


    public void setInputText(String title, Integer inputType) {
        tv_type.setText(title);
        if (inputType != null) {
            et_input.setInputType(inputType);
        }
    }

    /**
     * 获取填写结果
     *
     * @return
     */
    public String getContent() {
        if (!TextUtils.isEmpty(et_input.getText().toString().trim())) {
            return tv_result.getText().toString();
        } else {
            return et_input.getText().toString();
        }
    }
}
