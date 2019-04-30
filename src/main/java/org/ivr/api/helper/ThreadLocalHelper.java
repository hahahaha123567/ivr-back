package org.ivr.api.helper;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/12
 **/
public class ThreadLocalHelper {

    private static ThreadLocal<Integer> REQUEST_ID_THREAD_LOCAL = new ThreadLocal<>();

    public static Integer getRequestId() {
        return REQUEST_ID_THREAD_LOCAL.get();
    }

    public static void setRequestId(Integer requestId) {
        REQUEST_ID_THREAD_LOCAL.set(requestId);
    }

    public static void removeRequestId() {
        REQUEST_ID_THREAD_LOCAL.remove();
    }
}
