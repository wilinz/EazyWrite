package com.eazywrite.app.ui.bill.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.eazywrite.app.R;

public class MyDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        getDialog().getWindow().setWindowAnimations(R.style.dialogAnim);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        View view = inflater.inflate(R.layout.dialog_fragment,container);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
        window.setAttributes(windowParams);

    }
}
