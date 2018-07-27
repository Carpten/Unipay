package com.chuangjiangx.unipay.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chuangjiangx.unipay.R;

public class DialogBuild {

    private View mContentView;

    private AlertDialog mDialog;


    public DialogBuild(Context context) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null);
        mDialog = new AlertDialog.Builder(mContentView.getContext()).setView(mContentView).create();
        mContentView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public DialogBuild setCloseOn(boolean show) {
        mContentView.findViewById(R.id.iv_close).setVisibility(show ? View.VISIBLE : View.GONE);
        mDialog.setCancelable(show);
        return this;
    }

    public DialogBuild setTitle(String title) {
        TextView textView = mContentView.findViewById(R.id.tv_title);
        textView.setText(title);
        return this;
    }

    public DialogBuild setContentText(String contentText) {
        TextView textView = mContentView.findViewById(R.id.tv_content);
        textView.setText(contentText);
        return this;
    }

    public DialogBuild setPositiveText(String positiveText) {
        Button button = mContentView.findViewById(R.id.btn_positive);
        button.setText(positiveText);
        return this;
    }

    public DialogBuild onPositive(final View.OnClickListener onClickListener) {
        Button button = mContentView.findViewById(R.id.btn_positive);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
                dismiss();
            }
        });
        return this;
    }

    public AlertDialog show() {
        mDialog.show();
        return mDialog;
    }

    private void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
