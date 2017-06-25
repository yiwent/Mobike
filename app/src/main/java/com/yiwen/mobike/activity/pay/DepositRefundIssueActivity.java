package com.yiwen.mobike.activity.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.customer.CustomerServiceWebActivity;
import com.yiwen.mobike.bean.DepositRefundData;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.utils.BuidUrl;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.ClearEditText;
import com.yiwen.mobike.views.TabTitleView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import dmax.dialog.SpotsDialog;

public class DepositRefundIssueActivity extends AppCompatActivity {

    @BindView(R.id.title_school)
    TabTitleView  mTitleSchool;
    @BindView(R.id.id_tv_weixin)
    TextView      mTvWeixin;
    @BindView(R.id.id_tv_alipay)
    TextView      mTvAlipay;
    @BindView(R.id.id_iv_pick)
    ImageView     mIvPick;
    @BindView(R.id.et_order)
    ClearEditText mEtOrder;
    @BindView(R.id.id_tv_findorder)
    TextView      mTvFindorder;
    @BindView(R.id.id_tv_query)
    TextView      mTvQuery;
    private static final int REQS_IMAGE_PICKER = 1;
    private static       int WEIXINPAY         = 0;
    private static       int ALIPAY            = 1;
    private              int currentpaytype    = WEIXINPAY;

    private BmobFile    bmobFile;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_refund_issue);
        ButterKnife.bind(this);
        initImagePick();
        initEvent();
    }

    private void initEvent() {
        mDialog = new SpotsDialog(this);
        mEtOrder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mTvQuery.setBackgroundResource(R.color.red);
                } else {
                    mTvQuery.setBackgroundResource(R.color.white);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void DismissDialog() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    private void initImagePick() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(400);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(400);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(400);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(400);//保存文件的高度。单位像素
    }


    public static Intent getDepositRefundIntent(Context context) {
        Intent intent = new Intent(context, DepositRefundIssueActivity.class);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQS_IMAGE_PICKER:
                uploadImage(requestCode, resultCode, data);
                break;
        }
    }

    private void uploadImage(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQS_IMAGE_PICKER) {
                ArrayList<ImageItem> images = null;
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem imageItem = images.get(0);
                Glide.with(DepositRefundIssueActivity.this).load(imageItem.path).into(mIvPick);
                if (bmobFile == null)
                    bmobFile = new BmobFile(new File(imageItem.path));
                bmobFile.setUrl(imageItem.path);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.id_tv_weixin, R.id.id_tv_alipay, R.id.id_iv_pick, R.id.id_tv_findorder, R.id.id_tv_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_tv_weixin:
                selectPayType(view);
                break;
            case R.id.id_tv_alipay:
                selectPayType(view);
                break;
            case R.id.id_iv_pick:
                Go2Imagepicker();
                break;
            case R.id.id_tv_findorder:
                tofindorder();
                break;
            case R.id.id_tv_query:
                toQuery();
                break;
        }
    }

    private void toQuery() {
        if (bmobFile == null) {
            ToastUtils.show(this, "请上传订单截图");
            return;
        }
        if (TextUtils.isEmpty(mEtOrder.getText().toString())) {
            ToastUtils.show(this, "请填写订单号");
            return;
        }
        mDialog.show();
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                   uploadData();
                } else {
                    ToastUtils.show(DepositRefundIssueActivity.this, "上传失败");
                }
                DismissDialog();
            }
        });
    }

    private void uploadData() {
        MyUser myUser= MyApplication.getInstance().getUser();
        DepositRefundData mRefundData=new DepositRefundData();
        mRefundData.setMyUser(myUser);
        mRefundData.setOrderNub(mEtOrder.getText().toString());
        mRefundData.setOrderPic(bmobFile);
        mRefundData.setPaytype(currentpaytype+"");
        mRefundData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtils.show(DepositRefundIssueActivity.this, "提交成功，客服会尽快处理，请勿重复提交");
                    DepositRefundIssueActivity.this.finish();
                } else {
                    ToastUtils.show(DepositRefundIssueActivity.this, "提交失败");
                }
                DismissDialog();
            }
        });
    }

    private void Go2Imagepicker() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        //        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
        startActivityForResult(intent, REQS_IMAGE_PICKER);
    }

    private void selectPayType(View view) {
        mTvWeixin.setBackgroundResource(R.color.bg_color);
        mTvAlipay.setBackgroundResource(R.color.bg_color);
        mTvWeixin.setTextColor(getResources().getColor(R.color.black));
        mTvAlipay.setTextColor(getResources().getColor(R.color.black));
        mTvWeixin.setClickable(true);
        mTvAlipay.setClickable(true);
        TextView tv = (TextView) view;
        tv.setBackgroundResource(R.color.red);
        tv.setClickable(false);
        tv.setTextColor(getResources().getColor(R.color.white));
        currentpaytype = Integer.valueOf((String) tv.getTag());
    }

    private void tofindorder() {
        Intent intent = CustomerServiceWebActivity.getCustomerServiceIntent(
                DepositRefundIssueActivity.this, "客户服务", BuidUrl.getQueryPayBack());
        startActivity(intent);
    }

    public class GlideImageLoader implements ImageLoader {
        @Override
        public void displayImage(Activity activity, String path, ImageView imageView,
                                 int width, int height) {
            Glide.with(activity)//
                    .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(getResources().getDrawable(R.mipmap.default_image))           //设置错误图片
                    .placeholder(R.mipmap.default_image)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .into(imageView);
        }

        @Override
        public void clearMemoryCache() {
            //这里是清除缓存的方法,根据需要自己实现
        }
    }
}
