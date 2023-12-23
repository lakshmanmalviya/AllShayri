package com.example.allshayri.Modals;

public class UserFeedModal {
    String userMsg,userName,userId;

    public UserFeedModal() {
    }

    public UserFeedModal(String userMsg, String userName, String userId) {
        this.userMsg = userMsg;
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
