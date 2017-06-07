package com.yiwen.mobike.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-05-05
 * Time: 19:43
 * FIXME
 */
public class CommonUtils {


    /**
     * 判断电话号码是否有效
     *
     * @param phoneNumber
     * @return true 有效 / false 无效
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {

        boolean isValid = false;

        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 判断邮箱地址是否有效
     *
     * @param email
     * @return true 有效 / false 无效
     */
    public static boolean isEmailValid(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }


    // 判断网络是否连接
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }

        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                int l = info.length;
                for (int i = 0; i < l; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否使用中文
     *
     * @param Context
     * @return
     */
    private boolean isZh(Context Context) {
        Locale locale = Context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    /**
     * 分段显示不同颜色的文本
     *
     * @param username 用户名
     * @param color1   颜色1
     * @param comment  评论内容
     * @param color2   颜色2
     * @return
     */
    public static String textToHtml(String username, String color1, String comment, String color2) {

        StringBuffer html = new StringBuffer();
        html.append("<font color = ");
        html.append("'" + color1 + "'>");
        html.append(username + ":  ");
        html.append("</font>");

        html.append("<font color = ");
        html.append("'" + color2 + "'>");
        html.append(comment);
        html.append("</font>");

        CharSequence text = Html.fromHtml(html.toString());

        return text.toString();
    }


}
