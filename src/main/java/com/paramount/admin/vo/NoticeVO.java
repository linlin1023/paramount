package com.paramount.admin.vo;

import com.paramount.admin.domain.Notice;
import com.paramount.admin.domain.User;

import java.io.Serializable;
import java.util.List;

/**
 */
public class NoticeVO implements Serializable{

    private static final long serialVersionUID = -938974811148699054L;
    private Notice notice;

    private List<User> users;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
