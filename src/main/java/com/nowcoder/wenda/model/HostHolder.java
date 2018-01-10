package com.nowcoder.wenda.model;

import org.springframework.stereotype.Component;

/**
 * Created by nowcoder on 2016/7/3.
 */
@Component
public class HostHolder {
    //threadlocal保证每个线程都有一个users拷贝
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
