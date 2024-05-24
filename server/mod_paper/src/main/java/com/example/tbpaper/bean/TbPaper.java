package com.example.tbpaper.bean;
import com.example.common.bean.Page;
import com.example.common.utils.DateUtils;
import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import com.example.common.utils.MyStringUtils;


/**
 *帖子 bean
 */
public class TbPaper extends Page  {


	/**
	 * id
	 */
	private Integer id;
	/**
	 * 用户
	 */
	private Integer userId;
	/**
	 * 类型
	 */
	private Integer paperTypeId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 简介
	 */
	private String word;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 图片
	 */
	private String imgUrl;
	/**
	 * 图片
	 */
	private String imgUrl2;
	/**
	 * 图片
	 */
	private String imgUrl3;
	/**
	 * 发布时间
	 */
	private Timestamp createTime;
	/**
	 * {"name":"状态","0":"禁用","1":"可用"}
	 */
	private Integer status;
	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id = id;
	}


	public Integer getUserId(){
		return userId;
	}


	public void setUserId(Integer userId){
		this.userId = userId;
	}


	public Integer getPaperTypeId(){
		return paperTypeId;
	}


	public void setPaperTypeId(Integer paperTypeId){
		this.paperTypeId = paperTypeId;
	}


	public String getTitle(){
		return title;
	}


	public void setTitle(String title){
		this.title = title;
	}


	public String getWord(){
		return word;
	}


	public void setWord(String word){
		this.word = word;
	}


	public String getContent(){
		return content;
	}


	public void setContent(String content){
		this.content = content;
	}


	public String getImgUrl(){
		return imgUrl;
	}


	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}


	public String getImgUrl2(){
		return imgUrl2;
	}


	public void setImgUrl2(String imgUrl2){
		this.imgUrl2 = imgUrl2;
	}


	public String getImgUrl3(){
		return imgUrl3;
	}


	public void setImgUrl3(String imgUrl3){
		this.imgUrl3 = imgUrl3;
	}


	public String getCreateTime_(){
		return DateUtils.formatDateTime(createTime);
	}


	public Timestamp getCreateTime(){
		return createTime;
	}


	public void setCreateTime(Timestamp createTime){
		this.createTime = createTime;
	}


	public String getStatus_(){
		if(MyStringUtils.isEmpty(status)) {
			 return "";
		}else if(status.toString().equals("0")) {
			return "禁用";
		}else if(status.toString().equals("1")) {
			return "可用";
		}
		return "";

	}


	public Integer getStatus(){
		return status;
	}


	public void setStatus(Integer status){
		this.status = status;
	}


}
