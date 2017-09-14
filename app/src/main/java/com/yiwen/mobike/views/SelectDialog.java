package com.yiwen.mobike.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yiwen.mobike.R;


public class SelectDialog extends Dialog implements View.OnClickListener {
    private TextView                confirm;
    private IDialogOnclickInterface dialogOnclickInterface;
    private Context                 context;

    public SelectDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);

        confirm = (TextView) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //        dialogOnclickInterface = (IDialogOnclickInterface) context;
        switch (v.getId()) {
            //            case R.id.confirm:
            ////                dialogOnclickInterface.confirmOnclick();
            ////                turnGPSOn(getContext());
            //                Intent intent = new Intent(
            //                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            //                context.startActivity(intent);
            //              cancel();
            //                break;

            default:
                break;
        }
    }

    public interface IDialogOnclickInterface {
        void confirmOnclick();

    }
}
