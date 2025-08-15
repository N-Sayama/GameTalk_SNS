package jp.sayama.sns.gametalk.model;

public class LoginUser {
    private Long userId;   // 必要なら
    private String userName;
    private String password;
    private boolean enabled; // DBがintなら Integer/ int、booleanなら Boolean/ boolean
    
    public LoginUser() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }   // ★ これに合わせて XML は #{userName}
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}