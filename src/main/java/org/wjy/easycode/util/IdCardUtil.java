package org.wjy.easycode.util;

/**
 * @author weijiayu
 * @date 2022/9/13 14:31
 */
public class IdCardUtil {

    public static String getSex(String idCardNo) {
        try {
            return Integer.parseInt(idCardNo.substring(16, 17)) % 2 == 1 ? "男" : "女";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getBirthDay(String idCardNo) {
        try {
            String birthDay = idCardNo.substring(6, 14);
            return birthDay.substring(0, 4) + "-" + birthDay.substring(4, 6) + "-" + birthDay.substring(6, 8);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        String idCardNo = "450102199906161314";
        System.out.println(getSex(idCardNo));
        System.out.println(getBirthDay(idCardNo));
    }
}
