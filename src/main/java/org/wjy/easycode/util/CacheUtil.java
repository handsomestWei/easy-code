package org.wjy.easycode.util;

import java.lang.ref.SoftReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 轻量的定时内存缓存，可以避免引入过多依赖如redis中间件等
 * 
 * @author weijiayu
 * @date 2023/8/24 16:23
 */
public class CacheUtil {

    public static ConcurrentMap<String, SoftReference<Object>> CacheMap = new ConcurrentHashMap<>();

    /**
     * 缓存kv，单位秒
     * 
     * @author weijiayu
     * @date 2023/8/24 16:27
     * @param key
     * @param value
     * @param second
     * @return void
     */
    public static void cacheSecond(String key, Object value, int second) {
        SoftReference<Object> sf = new SoftReference<Object>(value);
        CacheMap.put(key, sf);
        Timer timer = new Timer();
        timer.schedule(new DeleteTask(key, timer), second * 1000);
    }

    public static Boolean containsKey(String key) {
        return CacheMap.containsKey(key);
    }

    private static class DeleteTask extends TimerTask {
        private String key;
        private Timer timer;

        public DeleteTask(String key, Timer timer) {
            this.key = key;
            this.timer = timer;
        }

        @Override
        public void run() {
            try {
                CacheMap.remove(key);
                timer.cancel();
                if (null != timer) {
                    timer = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
