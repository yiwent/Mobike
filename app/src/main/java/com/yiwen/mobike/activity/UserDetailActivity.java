package com.yiwen.mobike.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yiwen.mobike.R;
import com.yiwen.mobike.views.MySettingView;
import com.yiwen.mobike.views.TabTitleView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailActivity extends AppCompatActivity {

    private static final int IMAGE_PICKER = 1;
    @BindView(R.id.title_use_detail)
    TabTitleView  mTitleUseDetail;
    @BindView(R.id.tv_head)
    MySettingView mTvHead;
    @BindView(R.id.tv_nick_name)
    MySettingView mTvNickName;
    @BindView(R.id.tv_name)
    MySettingView mTvName;
    @BindView(R.id.tv_is_realname)
    MySettingView mTvIsRealname;
    @BindView(R.id.tv_myphone)
    MySettingView mTvMyphone;
    @BindView(R.id.tv_weixin)
    MySettingView mTvWeixin;
    @BindView(R.id.tv_qq)
    MySettingView mTvQq;
    @BindView(R.id.tv_school)
    MySettingView mTvSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
    }

    private void initData() {
        initImagePicker();
    }

    private void initEvent() {
        mTitleUseDetail.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                UserDetailActivity.this.finish();
            }
        });
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        //  imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(400);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(400);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(400);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(400);//保存文件的高度。单位像素
    }

    @OnClick({ R.id.tv_head, R.id.tv_nick_name, R.id.tv_name, R.id.tv_is_realname, R.id.tv_myphone, R.id.tv_weixin, R.id.tv_qq, R.id.tv_school})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_head:
                Go2Imagepicker();
                break;
            case R.id.tv_nick_name:
                Go2ModifyNickNameActivity();
                break;
            case R.id.tv_name:
                break;
            case R.id.tv_is_realname:
                break;
            case R.id.tv_myphone:
                Go2MobileNumActivity();
                break;
            case R.id.tv_weixin:
                break;
            case R.id.tv_qq:
                break;
            case R.id.tv_school:
                break;
        }
    }

    private void Go2Imagepicker() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        //        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
        startActivityForResult(intent, IMAGE_PICKER);

    }

    private void Go2MobileNumActivity() {
        Intent intent = new Intent(UserDetailActivity.this, MobileNumActivity.class);
        startActivity(intent);
    }

    private void Go2ModifyNickNameActivity() {
        Intent intent = new Intent(UserDetailActivity.this, ModifyNickNameActivity.class);
        intent.putExtra("nick_name", mTvNickName.getRigtTvText().toString());
        startActivityForResult(intent, 2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "requestCode: " + requestCode);
        switch (requestCode) {
            case 1:
                uploadImage(requestCode, resultCode, data);
                break;
            case 2:
                upDateNickname(requestCode, resultCode, data);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            default:
                break;
        }

    }

    private void upDateNickname(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK==resultCode&&data!=null){
            String nickName=data.getStringExtra("nick_name");
            mTvNickName.setRigtTvText(nickName.toString());
        }
    }

    private void uploadImage(int requestCode, int resultCode, Intent data) {
        Log.d("uploadImage", "uploadImage: " + requestCode);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1) {
                ArrayList<ImageItem> images = null;
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem imageItem = images.get(0);
                Glide.with(UserDetailActivity.this).load(imageItem.path).into(mTvHead.getRigtImageView());
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
                    .error(R.mipmap.default_image)           //设置错误图片
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
