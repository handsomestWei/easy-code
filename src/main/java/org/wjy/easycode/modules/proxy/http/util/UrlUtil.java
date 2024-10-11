package org.wjy.easycode.modules.proxy.http.util;

import java.util.HashMap;

/**
 * @author weijiayu
 * @date 2024/1/23 15:26
 */
public class UrlUtil {

    /** /path?k1=v1&&k2=v2 => /path */
    public static String getUrlPath(String url) {
        int paramIndex = url.indexOf("?");
        if (paramIndex >= 0) {
            return url.substring(0, paramIndex);
        }
        return url;
    }

    /** /path?k1=v1&&k2=v2 => [k1=v1, k2=v2] */
    public static HashMap<String, Object> getUrlParamMap(String url) {
        HashMap<String, Object> paramMap = new HashMap<>();
        int paramIndex = url.indexOf("?");
        if (paramIndex < 0) {
            return paramMap;
        }

        String[] kvPairs = url.substring(paramIndex + 1).split("&&");
        for (int i = 0; i < kvPairs.length; i++) {
            String[] kvPair = kvPairs[i].split("=");
            paramMap.put(kvPair[0], kvPair[1]);
        }
        return paramMap;
    }

    /** /path?k1=v1&&k2=v2 => [v1, v2] */
    public static Object[] getUrlParams(String url) {
        HashMap<String, Object> paramMap = getUrlParamMap(url);
        return paramMap.values().toArray();
    }
}
