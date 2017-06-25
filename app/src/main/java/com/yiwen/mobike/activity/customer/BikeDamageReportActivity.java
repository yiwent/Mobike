package com.yiwen.mobike.activity.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.adapter.BaseAdapter;
import com.yiwen.mobike.adapter.BikeDamageAdapter;
import com.yiwen.mobike.bean.BikeDamageData;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.qrcode.CaptureActivity;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.TabTitleView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import dmax.dialog.SpotsDialog;

public class BikeDamageReportActivity extends AppCompatActivity {

    private static final int REQS_IMAGE_PICKER = 1;
    private static final int REQEST_CARNUB     =2 ;
    @BindView(R.id.title_bike_damage)
    TabTitleView mTitleBikeDamage;
    @BindView(R.id.id_tv_carnub)
    TextView     mTvCarnub;
    @BindView(R.id.lo_bike_damage)
    LinearLayout mLoBikeDamage;
    @BindView(R.id.recycle_bike_damage)
    RecyclerView mRecyclerview;
    @BindView(R.id.id_iv_pick)
    ImageView    mIvPick;
    @BindView(R.id.et_bike_damage)
    EditText     mEtBikeDamage;
    @BindView(R.id.id_tv_desc_nub)
    TextView     mTvDescNub;
    @BindView(R.id.id_bt_query)
    Button       mBtQuery;
    private BmobFile    bmobFile;
    private SpotsDialog mDialog;
    private BikeDamageData mData;
    private MyUser mMyUser;
    private BikeDamageAdapter mAdapter;
    private String[] chargetext = {"车太重了，骑不动", "二维码脱落",
            "把套歪了", "车铃丢了", "踏板坏了", "龙头歪斜", "刹车失灵", "其他"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_damage_report);
        ButterKnife.bind(this);
        iniView();
        initImagePick();
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

    private void iniView() {
        mTitleBikeDamage.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mMyUser = MyApplication.getInstance().getUser();
        if (mMyUser == null)
            finish();
        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra("carNub"))) {
            mTvCarnub.setText(intent.getStringExtra("carNub"));
        }
        final List<String> data = new ArrayList<>();
        for (int i = 0; i < chargetext.length; i++) {
            data.add(chargetext[i]);
        }
        mAdapter = new BikeDamageAdapter(this, data);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                mAdapter.selectItem(position);
            }
        });
        mData = new BikeDamageData();
        mDialog = new SpotsDialog(this);
        mEtBikeDamage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length=140-s.length();
                mTvDescNub.setText(length+"/140");
                if (s.length()>0&&mTvDescNub.getText().toString()!="扫描二维码或者手动输入"){
                    mBtQuery.setBackgroundResource(R.color.red);
                    mBtQuery.setClickable(true);
                }else {
                    mBtQuery.setBackgroundResource(R.color.bg_color);
                    mBtQuery.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static Intent getMyIntent(Context context, String carNub) {
        Intent intent = new Intent(context, BikeDamageReportActivity.class);
        intent.putExtra("carNub", carNub);
        return intent;
    }

    @OnClick({R.id.lo_bike_damage, R.id.id_iv_pick, R.id.id_bt_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lo_bike_damage:
            case R.id.lo_report_unlock:
                startActivityForResult(CaptureActivity.
                        getMyIntent(BikeDamageReportActivity.this, true), REQEST_CARNUB);
                break;
            case R.id.id_iv_pick:
                Go2Imagepicker();
                break;
            case R.id.id_bt_query:
                toQuery();
                break;
        }
    }

    private void toQuery() {
        mBtQuery.setClickable(false);
        if (mTvCarnub.getText().toString()=="扫描二维码或者手动输入") {
            ToastUtils.show(BikeDamageReportActivity.this, "请输入单车编号");
            return;
        }
        if (TextUtils.isEmpty(mEtBikeDamage.getText().toString())) {
            ToastUtils.show(BikeDamageReportActivity.this, "请输入描述信息");
            return;
        }
        mDialog.show();
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                   saveDatial();
                } else {
                    Logger.d(e);
                    mBtQuery.setClickable(true);
                    ToastUtils.show(BikeDamageReportActivity.this, "图片上传失败");
                }
                if (mDialog.isShowing())
                    mDialog.dismiss();

            }
        });
    }
    private void saveDatial() {
        mData.setMyUser(mMyUser);
        mData.setCarNub(mTvCarnub.getText().toString());
        mData.setDesc(mEtBikeDamage.getText().toString());
        mData.setCarPic(bmobFile);
        mData.setType(mAdapter.getType());
        mData.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtils.show(BikeDamageReportActivity.this, "提交成功");
                    finish();
                } else {

                    Logger.d(e);
                    ToastUtils.show(BikeDamageReportActivity.this, "提交失败");
                }
                mBtQuery.setClickable(true);
                if (mDialog.isShowing())
                    mDialog.dismiss();
            }
        });
    }

    private void Go2Imagepicker() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        //        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
        startActivityForResult(intent, REQS_IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQS_IMAGE_PICKER:
                uploadImage(requestCode, resultCode, data);
                break;
            case REQEST_CARNUB:
                if (resultCode == RESULT_OK) {
                    mTvCarnub.setText(data.getStringExtra("result"));
                }
                break;
        }
    }

    private void uploadImage(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQS_IMAGE_PICKER) {
                ArrayList<ImageItem> images = null;
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem imageItem = images.get(0);
                Glide.with(BikeDamageReportActivity.this).load(imageItem.path).into(mIvPick);
               // if (bmobFile == null)
                bmobFile = new BmobFile(new File(imageItem.path));
                bmobFile.setUrl(imageItem.path);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
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
