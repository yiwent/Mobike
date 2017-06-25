package com.yiwen.mobike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.google.gson.reflect.TypeToken;
import com.yiwen.mobike.R;
import com.yiwen.mobike.adapter.PoiHostoryAdapter;
import com.yiwen.mobike.adapter.PoiSuggestionAdapter;
import com.yiwen.mobike.adapter.decoration.DividerItemDecoration;
import com.yiwen.mobike.provider.PoiObject;
import com.yiwen.mobike.provider.ProviderUtil;
import com.yiwen.mobike.utils.JSONUtil;
import com.yiwen.mobike.utils.MyLocationManager;
import com.yiwen.mobike.utils.NavUtil;
import com.yiwen.mobike.utils.PreferencesUtils;
import com.yiwen.mobike.views.ClearEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwen.mobike.R.id.lo_first_address;

public class ActionSearchActivity extends AppCompatActivity implements
        OnGetSuggestionResultListener, PoiSuggestionAdapter.OnItemClickListener
        , PoiHostoryAdapter.OnHistoryItemClickListener {
    @BindView(R.id.et_action_search)
    ClearEditText mEtActionSearch;
    @BindView(R.id.tv_search_cancel)
    TextView      mTvSearchCancel;
    @BindView(R.id.tv_actionsech_mylotion)
    TextView      mTvActionsechMylotion;
    @BindView(R.id.recyclerview_poi_history)
    RecyclerView  recyclerview_poi_history;
    @BindView(R.id.recyclerview_poi)
    RecyclerView  recyclerview_poi;
    //保存地址
    private static final String FIRST_ADDRESS   = "address";
    private static final String SENCOND_ADDRESS = "address1";
    @BindView(R.id.id_iv_star)
    ImageView      mStar;
    @BindView(lo_first_address)
    RelativeLayout mLoFirstAddress;
    @BindView(R.id.id_iv_star1)
    ImageView      mStar1;
    @BindView(R.id.lo_second_address)
    RelativeLayout mLoSecondAddress;
    @BindView(R.id.address)
    TextView       mAddress;
    @BindView(R.id.address1)
    TextView       mAddress1;
    @BindView(R.id.district)
    TextView       mDistrict;
    @BindView(R.id.district1)
    TextView       mDistrict1;
    private PoiObject first, second;
    //保存地址 end
    private BDLocation                            mBDLocation;
    private List<SuggestionResult.SuggestionInfo> suggestionInfoList;
    private SuggestionSearch mSuggestionSearch = null;
    private PoiSuggestionAdapter sugAdapter;
    private boolean firstSetAdapter = true;
    private PoiHostoryAdapter poiHostoryAdapter;
    private ProviderUtil      providerUtil;
    private String            currentAddress, end;
    private LatLng startLL, endLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_search);
        ButterKnife.bind(this);
        intiView();
        initData();
        initEvent();

    }

    private void initEvent() {

    }

    private void initData() {
    }

    private void intiView() {
        //        Intent intent = getIntent();
        //        mBDLocation = intent.getParcelableExtra("mylotion");
        //想使用内置导航，必须初始化导航， NavUtil.initNavi(this);
        NavUtil.initNavi(this);
        currentAddress = MyLocationManager.getInstance().getAddress();
        startLL = MyLocationManager.getInstance().getCurrentLL();
        mTvActionsechMylotion.setText(currentAddress);
        providerUtil = new ProviderUtil(this);
        recyclerview_poi.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_poi.addItemDecoration(new DividerItemDecoration(ActionSearchActivity.this, LinearLayoutManager.VERTICAL));
        recyclerview_poi_history.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_poi_history.addItemDecoration(new DividerItemDecoration(ActionSearchActivity.this, LinearLayoutManager.VERTICAL));
        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        mEtActionSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    return;
                }
                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(s.toString()).city("深圳"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initAddress();
    }

    private void initAddress() {
        if (PreferencesUtils.getString(this, FIRST_ADDRESS, null) != null) {
            PoiObject poiObject = JSONUtil.fromJson(PreferencesUtils.
                    getString(this, FIRST_ADDRESS, null), new TypeToken<PoiObject>() {
            }.getType());
            first=poiObject;
            mAddress.setText(poiObject.address);
            mDistrict.setText(poiObject.district);
        } else {
            mLoFirstAddress.setVisibility(View.GONE);
        }
        if (PreferencesUtils.getString(this, SENCOND_ADDRESS, null) != null) {
            PoiObject poiObject = JSONUtil.fromJson(PreferencesUtils.
                    getString(this, SENCOND_ADDRESS, null), new TypeToken<PoiObject>() {
            }.getType());
            second=poiObject;
            mAddress1.setText(poiObject.address);
            mDistrict1.setText(poiObject.district);
        } else {
            mLoSecondAddress.setVisibility(View.GONE);
        }
        showHistoryPOI();
    }

    @OnClick({R.id.tv_search_cancel, R.id.lo_first_address, R.id.lo_second_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_cancel:
                ActionSearchActivity.this.finish();
                break;
            case R.id.lo_first_address:
                if (first != null)
                    getLocation(first);
                break;
            case R.id.lo_second_address:
                if (second != null)
                    getLocation(second);
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (recyclerview_poi_history.getVisibility() == View.VISIBLE) {
                recyclerview_poi_history.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        recyclerview_poi_history.setVisibility(View.GONE);
        suggestionInfoList = res.getAllSuggestions();
        if (firstSetAdapter) {
            String from = "detination";//isStartPoi == true ? "start" : "detination";
            sugAdapter = new PoiSuggestionAdapter(this, suggestionInfoList, from);
            recyclerview_poi.setAdapter(sugAdapter);
            sugAdapter.setOnClickListener(this);
            firstSetAdapter = false;
        } else {
            sugAdapter.changeData(suggestionInfoList);
        }
    }

    @Override
    public void onHistoryItemClick(View v, int position, PoiObject info) {
        end = info.address;
        getLocation(info);
        // getLocation(new LatLng(Double.parseDouble(info.lattitude), Double.parseDouble(info.longitude)));
    }

    @Override
    public void onItemClick(View v, int position, String flag, SuggestionResult.SuggestionInfo info) {
        end = info.district;
        providerUtil.addData(info.key, info.district, info.pt.latitude + "", info.pt.longitude + "");

        //  getLocation(info.pt);
    }

    private void getLocation(PoiObject info) {
        Intent resultIntent = new Intent();
        //   resultIntent.putExtra("LatLng",pt);
        resultIntent.putExtra("PoiObject", info);
        setResult(RESULT_OK, resultIntent);
        finish();
        //        endLL=pt;
        //        if (startLL != null && endLL != null) {
        //            NavUtil.showChoiceNaviWayDialog(this, startLL, pt, currentAddress, end);
        //        }
    }


    private void showHistoryPOI() {
        try {
            recyclerview_poi_history.setVisibility(View.VISIBLE);
            List<PoiObject> poiItems = providerUtil.getData();
            poiHostoryAdapter = new PoiHostoryAdapter(ActionSearchActivity.this, poiItems);
            recyclerview_poi_history.setAdapter(poiHostoryAdapter);
            poiHostoryAdapter.setOnClickListener(this);
        } catch (Exception e) {
            Log.d("gaolei", e.getMessage());
        }
    }
}
