package jp.sayama.sns.gametalk.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Profile {
	
	@Id
    private Long id;
    private String name;
    private String games;
    private String comment;
    private String extra;
    @Column(name = "icon_path")
    private String iconPath;
    private String userName;
    
    public String getIconPath() {
    	//return "/uploads/nukota02.png";
        return iconPath;
    }
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Profile() {
    }

public Profile(Long id, String name, String games, String comment, String extra) {
    this.id = id;
    this.name = name;
    this.games = games;
    this.comment = comment;
    this.extra = extra;
    }
public Profile(Long id, String name, String games, String comment, String extra, String iconPath) {
    this(id, name, games, comment, extra);
    this.iconPath = iconPath;
	}
}