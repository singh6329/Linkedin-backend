package com.app.linkedin.posts_comment_service.auth;

public class UserContextHolder {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    static void setCurrentUserId(Long userId)
    {
        threadLocal.set(userId);
    }

    public static Long getCurrentUserId()
    {
        return threadLocal.get();
    }

    static void remove()
    {
        threadLocal.remove();
    }

}
