package com.yiwen.mobike.activity.usercenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.activity.login.CampusVerifyActivity;
import com.yiwen.mobike.activity.login.SsoUserInfoActivity;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.MySettingView;
import com.yiwen.mobike.views.TabTitleView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

public class UserDetailActivity extends AppCompatActivity {

    private static final int    REQS_IMAGE_PICKER = 1;
    private static final int    REQS_NICKNAME     = 2;
    private static final String TAG               = "UserDetailActivity";
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
    private MyUser mMyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        mMyUser = MyApplication.getInstance().getUser();
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        if (mMyUser != null) {
            if (mMyUser.getPicUser() != null) {
                Glide.with(UserDetailActivity.this)
                        .load(mMyUser.getPicUser().getUrl())
                        .error(getResources().getDrawable(R.drawable.avatar_default_login))
                        .into(mTvHead.getRigtImageView());
            }
            mTvNickName.setRigtTvText(mMyUser.getNickName());
            String phone = mMyUser.getMobilePhoneNumber();
            if (phone.length() == 11)
                mTvMyphone.setRigtTvText(phone.substring(0, 3) + "****" + phone.substring(7, 11));

            if (!TextUtils.isEmpty(mMyUser.getMyName())) {
                mTvName.setRigtTvText(mMyUser.getMyName());
                mTvName.setShowIndicator(false);
                mTvName.setClickable(false);
                mTvIsRealname.setRigtTvText("已认证");
                mTvIsRealname.setClickable(false);
            } else {
                mTvName.setRigtTvText("请实名认证");
                mTvName.setClickable(true);
                mTvName.setRigtTvColor(getResources().getColor(R.color.red));
                mTvName.setShowIndicator(true);
                mTvIsRealname.setRigtTvText("未认证");
            }

            if (!TextUtils.isEmpty(mMyUser.getWeixin())) {
                mTvWeixin.setRigtTvText(mMyUser.getWeixin());
            } else {
                mTvWeixin.setRigtTvText("未绑定");
            }
            if (!TextUtils.isEmpty(mMyUser.getQq())) {
                mTvQq.setRigtTvText(mMyUser.getQq());
            } else {
                mTvQq.setRigtTvText("未绑定");
            }
            if (!TextUtils.isEmpty(mMyUser.getSchool())) {
                mTvSchool.setRigtTvText(mMyUser.getSchool());
                mTvSchool.setRigtTvColor(getResources().getColor(R.color.colorPrimary));
            } else {
                mTvSchool.setRigtTvText("未认证");
                mTvSchool.setRigtTvColor(getResources().getColor(R.color.red));
            }
        }
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

    @OnClick({R.id.tv_head, R.id.tv_nick_name, R.id.tv_name, R.id.tv_is_realname, R.id.tv_myphone, R.id.tv_weixin, R.id.tv_qq, R.id.tv_school})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_head:
                Go2Imagepicker();
                break;
            case R.id.tv_nick_name:
                Go2ModifyNickNameActivity();
                break;
            case R.id.tv_name:
                // TODO: 2017/6/21
                break;
            case R.id.tv_is_realname:
                // TODO: 2017/6/21
                break;
            case R.id.tv_myphone:
                Go2MobileNumActivity();
                break;
            case R.id.tv_weixin:
                Go2BindOrDisbindWx();
                break;
            case R.id.tv_qq:
                Go2BindOrDisbindQQ();
                break;
            case R.id.tv_school:
                Go2BindOrDisbindSC();
                break;
        }
    }

    private void Go2BindOrDisbindSC() {
        // TODO: 2017/6/21
        Intent intent = new Intent(UserDetailActivity.this, CampusVerifyActivity.class);
        startActivity(intent);
    }

    private void Go2BindOrDisbindQQ() {
        // TODO: 2017/6/21
    }

    private void Go2BindOrDisbindWx() {
        Intent intent = new Intent(UserDetailActivity.this, SsoUserInfoActivity.class);
        startActivity(intent);
    }

    private void Go2Imagepicker() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        //        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
        startActivityForResult(intent, REQS_IMAGE_PICKER);

    }

    private void Go2MobileNumActivity() {
        Intent intent = new Intent(UserDetailActivity.this, MobileNumActivity.class);
        startActivity(intent);
    }

    private void Go2ModifyNickNameActivity() {
        Intent intent = new Intent(UserDetailActivity.this, ModifyNickNameActivity.class);
        intent.putExtra("nick_name", mTvNickName.getRigtTvText().toString());
        startActivityForResult(intent, REQS_NICKNAME);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "requestCode: " + requestCode);
        switch (requestCode) {
            case REQS_IMAGE_PICKER:
                uploadImage(requestCode, resultCode, data);
                break;
            case REQS_NICKNAME:
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
        if (RESULT_OK == resultCode && data != null) {
            String nickName = data.getStringExtra("nick_name");
            if (nickName != null) {
                MyUser NMyUser = new MyUser();
                NMyUser.setNickName(nickName);
                mMyUser.setNickName(nickName);
                MyApplication.getInstance().upDataUser(mMyUser, NMyUser);
                mTvNickName.setRigtTvText(nickName);
            }
        }
    }

    private void uploadImage(int requestCode, int resultCode, Intent data) {
        Log.d("uploadImage", "uploadImage: " + requestCode);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQS_IMAGE_PICKER) {
                ArrayList<ImageItem> images = null;
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem imageItem = images.get(0);
                final BmobFile bmobFile = new BmobFile(new File(imageItem.path));
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            MyUser NMyUser = new MyUser();
                            NMyUser.setPicUser(bmobFile);
                            mMyUser.setPicUser(bmobFile);
                            MyApplication.getInstance().upDataUser(mMyUser, NMyUser);
                            Glide.with(UserDetailActivity.this).load(bmobFile.getUrl()).into(mTvHead.getRigtImageView());
                        } else {
                            ToastUtils.show(UserDetailActivity.this, "上传失败");
                        }
                    }
                });

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
