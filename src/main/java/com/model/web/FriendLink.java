package com.model.web;

import java.util.Date;

import com.model.page.PageDto;
import com.util.DateUtil;

public class FriendLink extends PageDto{
	private String webname;
	private String alink;
	private String email;
	private String dispos;
	private String content;
	private Date addtime;
	private String timeF;
	
	public String getWebname() {
		return webname;
	}
	public void setWebname(String webname) {
		this.webname = webname;
	}
	public String getAlink() {
		return alink;
	}
	public void setAlink(String alink) {
		this.alink = alink;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDispos() {
		return dispos;
	}
	public void setDispos(String dispos) {
		this.dispos = dispos;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	/**
	 * 时间格式化
	 * @return
	 */
	public String getTimeF() {
	 if(this.getAddtime() != null){
            return DateUtil.Date2Stirng2Second(addtime);
        }
        return "";
	}
	
	public void setTimeF(String timeF) {
		this.timeF = timeF;
	}
	
	
}
