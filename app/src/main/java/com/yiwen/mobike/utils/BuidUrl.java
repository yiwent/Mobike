package com.yiwen.mobike.utils;

//import com.baidu.middleware.GeoRange;

import com.loopj.android.http.RequestParams;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class BuidUrl {
    public static       String base  = "m.mobike.com/app/pages";//a
    public static final String https = "https";//b
    public static final String useid = "1909139792283648233927";//test

    /**
     * 刚骑完首页红包
     *
     * @return
     */
    public static String GetIndex()//A
    {
        return getUrl("/red_packet/index.html");
    }

    public static String GetEggShell()//B
    {
        RequestParams requestParms = new RequestParams();
        //    requestParms.put("time", RideManager.getUrl().x());
        //    requestParms.put("free", 60 * RideManager.getUrl().y());
        return getHelp("/EggShell.html", requestParms);
    }

    public static String getBase()//getUrl
    {
        return "m2.mobike.com/app/pages";
    }

    /**
     * 红包问题
     * @param p
     * @param t
     * @return
     */
    public static String getRedPacketProblem(int p, int t)//getUrl
    {
        RequestParams requestParms = new RequestParams();
        requestParms.put("p", p);
        requestParms.put("t", t);
        return getHelp("/redPacketProblem.html", requestParms);
    }

    public static String getUrl(String addUrl)//getUrl
    {
        return getUrl(addUrl, null);
    }

    public static String getRidingTrack(String orderid, int country)//getUrl
    {
        RequestParams requestParms = new RequestParams();
        requestParms.put("userid", useid); // q.getUrl().d()
        requestParms.put("orderid", orderid);
        requestParms.put("lang", Locale.getDefault().getLanguage());
        requestParms.put("country", country);
        return getUrl("/ridingtrack/index.html", requestParms);
    }

    public static String getUrl(String addUrl, RequestParams paramRequestParams)//getUrl
    {
        if (paramRequestParams != null)
            try {
                paramRequestParams = new RequestParams();
                //if (GeoRange.inCHINA());
                for (String str = "0"; ; str = "1") {
                    paramRequestParams.add("countryid", str);
                    paramRequestParams.add("belongid", "0");//String.valueOf(q.getUrl().g().id)
                    return new URL("https", getBase(), addUrl + "?" + paramRequestParams).toString();
                }
            } catch (MalformedURLException localMalformedURLException) {
            }
        return "https://" + getBase() + addUrl;
    }

    /**
     * 关于我们
     *
     * @return
     */
    public static String getAboutus()//b
    {
        RequestParams requestParms = new RequestParams();
        requestParms.put("version", "4.4.1");//MyApplication.getUrl
        requestParms.put("lang", Locale.getDefault().getLanguage());
        return getUrl("/aboutus/index.html", requestParms);
    }

    public static String getUrls(String add)//b
    {
        return getUrl(add);
    }

    public static String getHelp(String addurl, RequestParams requestParams)//b
    {
        return getUrl("/help/" + Locale.getDefault().getLanguage() + addurl, requestParams);
    }

    /**
     * 有惊喜，开箱上瘾
     *
     * @return
     */
    public static String getTreasue()//c
    {
        return getUrl("/treasure_hunt/index.html");
    }

    /**
     * 有邀请码的下载
     *
     * @param invitationCode
     * @return
     */
    public static String getDownload(String invitationCode)//c
    {
        RequestParams requestParms = new RequestParams();
        requestParms.add("invitationCode", invitationCode);
        return getUrl("/download/index.html", requestParms);
    }

    /**
     * 充值说明
     *
     * @return
     */
    public static String getChargeExplanation()//d
    {
        return getHelp("/ChargeExplanation.html", null);
    }

    public static String getOrder(String orderid)//d
    {
        RequestParams requestParms = new RequestParams();
        requestParms.put("lang", Locale.getDefault().getLanguage());
        requestParms.put("userid", useid); // q.getUrl().d()
        requestParms.put("orderid", orderid);
        // requestParms.put("citycode", l.getUrl().e());
        return getUrl("/eggshell_bike_result/index.html", requestParms);
    }

    /**
     * 充值协议
     *
     * @return
     */
    public static String getChargeProtocal()//e
    {
        return getHelp("/ChargeProtocal.html", null);
    }

    /**
     * 膜拜单车租赁服务协议
     *
     * @return
     */
    public static String getProtocol()//f
    {
        return getHelp("/protocol.html", null);
    }

    /**
     * 注册与登陆
     *
     * @return
     */
    public static String getAccountSign()//g
    {
        return getHelp("/AccountSign.html", null);
    }

    /**
     * 预约与开锁
     *
     * @return
     */
    public static String getUnlockReservation()//h
    {
        return getHelp("/UnlockReservation.html", null);
    }

    /**
     * 车费与押金
     *
     * @return
     */
    public static String getFareDeposit()//i
    {
        return getHelp("/FareDeposit.html", null);
    }

    /**
     * 关于膜拜
     *
     * @return
     */
    public static String getAboutMobike()//j
    {
        return getHelp("/AboutMobike.html", null);
    }

    /**
     * 还车相关
     *
     * @return
     */
    public static String getTheCarGuide()//k
    {
        return getHelp("/TheCarGuide.html", null);
    }

    /**
     * 关于膜拜
     *
     * @return
     */
    public static String getAboutCycling()//l
    {
        return getHelp("/AboutCycling.html", null);
    }

    /**
     * 红包车攻略
     *
     * @return
     */
    public static String getRedPacketCapturing()//m
    {
        RequestParams requestParms = new RequestParams();
        //   requestParms.put("time", RideManager.getUrl().x());
        //   requestParms.put("free", 60 * RideManager.getUrl().y());
        return getHelp("/redPacketCapturing.html", requestParms);
    }

    /**
     * 财富捕获
     *
     * @return
     */
    public static String getTreasureHuntCapturing()//n
    {
        RequestParams requestParms = new RequestParams();
        //   requestParms.put("time", RideManager.getUrl().x());
        //   requestParms.put("free", 60 * RideManager.getUrl().y());
        return getHelp("/treasureHuntCapturing.html", requestParms);
    }

    /**
     * 膜拜信用分
     *
     * @return
     */
    public static String getMobikeCredit()//o
    {
        return getHelp("/MobikeCredit.html", null);
    }

    /**
     * 押金说明
     *
     * @return
     */
    public static String getDepositInstruction()//p
    {
        return getHelp("/DepositInstruction.html", null);
    }

    /**
     * 优惠劵说明
     *
     * @return
     */
    public static String getCouponWithParams()//q
    {
        RequestParams requestParms = new RequestParams();
        requestParms.put("userid", useid);//q.getUrl().d()
        //    requestParms.put("citycode", l.getUrl().e());
        requestParms.put("lang", Locale.getDefault().getLanguage());
        //   requestParms.put("currency", q.getUrl().j().id);
        return getUrl("/coupon/index.html", requestParms);
    }

    /**
     * 优惠劵说明
     *
     * @return
     */
    public static String getCoupon()//r
    {
        return getHelp("/coupon.html", null);
    }

    /**
     * 信用积分
     *
     * @return
     */
    public static String getCredit()//s
    {
        RequestParams requestParms = new RequestParams();
        requestParms.put("userid", useid);//q.getUrl().d()
        requestParms.put("times", System.currentTimeMillis());
        requestParms.put("lang", Locale.getDefault().getLanguage());
        return getUrl("/credit/index.html", requestParms);
    }

    /**
     * 意见反馈
     *
     * @return
     */
    public static String getFeedback()//t
    {
        RequestParams requestParms = new RequestParams();
        requestParms.put("userid", useid);//q.getUrl().d()
        requestParms.put("lang", Locale.getDefault().getLanguage());
        return getUrl("/feedback/index.html", requestParms);
    }

    /**
     * 押金退款查找订单号
     *
     * @return
     */
    public static String getQueryPayBack()//u
    {
        return getHelp("/QueryPayBack.html", null);
    }

    /**
     * 退押金说明
     *
     * @return
     */
    public static String getPayBackExplanation()//v
    {
        return getHelp("/PayBackExplanation.html", null);
    }

    /**
     * 在哪还车
     *
     * @return
     */
    public static String getBikeDock()//w
    {
        return getHelp("/BikeDock.html", null);
    }

    /**
     * 找不到车
     *
     * @return
     */
    public static String getHelpFindCar()//x
    {
        return getHelp("/HelpFindCar.html", null);
    }

    public static String getProgrestatus()//y
    {
        RequestParams requestParms = new RequestParams();
        requestParms.put("userid", useid);//q.getUrl().d()
        return getUrl("/progrestatus/index.html", requestParms);
    }

    /**
     * 下载地址
     *
     * @return
     */
    public static String getDownload() {//z
        return getUrl("/download/index.html");
    }

    //    public static abstract interface getUrl {
    //        public static final String a = "m.mobike.com/app/pages";
    //        public static final String b = "staging-m.mobike.com/app/pages";
    //        public static final String c = "integration-m.mobike.com/app/pages";
    //        public static final String d = "canary-m.mobike.com/app/pages";
    //    }
    //
    //    public static abstract interface b {
    //        public static final String A = "/treasure_hunt/index.html";
    //        public static final String B = "/treasureHuntCapturing.html";
    //        public static final String C = "/eggshell_bike_result/index.html";
    //        public static final String D = "/EggShell.html";
    //        public static final String a = "/aboutus/index.html";
    //        public static final String b = "/protocol.html";
    //        public static final String c = "/ChargeExplanation.html";
    //        public static final String d = "/ChargeProtocal.html";
    //        public static final String e = "/AccountSign.html";
    //        public static final String f = "/ridingtrack/index.html";
    //        public static final String g = "/credit/index.html";
    //        public static final String h = "/BikeDock.html";
    //        public static final String i = "/HelpFindCar.html";
    //        public static final String j = "/PayBackExplanation.html";
    //        public static final String k = "/QueryPayBack.html";
    //        public static final String l = "/DepositInstruction.html";
    //        public static final String m = "/UnlockReservation.html";
    //        public static final String n = "/FareDeposit.html";
    //        public static final String o = "/AboutMobike.html";
    //        public static final String p = "/TheCarGuide.html";
    //        public static final String q = "/AboutCycling.html";
    //        public static final String r = "/MobikeCredit.html";
    //        public static final String s = "/feedback/index.html";
    //        public static final String t = "/progrestatus/index.html";
    //        public static final String u = "/coupon/index.html";
    //        public static final String v = "/coupon.html";
    //        public static final String w = "/download/index.html";
    //        public static final String x = "/redPacketProblem.html";
    //        public static final String y = "/redPacketCapturing.html";
    //        public static final String z = "/red_packet/index.html";
    //    }
}