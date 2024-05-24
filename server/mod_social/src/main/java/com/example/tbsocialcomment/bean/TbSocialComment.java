package com.example.tbsocialcomment.bean;
import com.example.common.bean.Page;
import com.example.common.utils.DateUtils;
import java.util.Date;
import java.math.BigDecimal;
import com.example.common.utils.MyStringUtils;


/**
 *用户评论 bean
 */
public class TbSocialComment extends Page  {


	/**
	 * id
	 */
	private Integer id;
	/**
	 * 关联id
	 */
	private Integer outId;
	/**
	 * 用户
	 */
	private Integer userId;
	/**
	 * 回复用户
	 */
	private Integer replyUserId;
	/**
	 * 内容
	 */
	private String word;
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 创建时间
	 */
	private Date createTime;
	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id = id;
	}


	public Integer getOutId(){
		return outId;
	}


	public void setOutId(Integer outId){
		this.outId = outId;
	}


	public Integer getUserId(){
		return userId;
	}


	public void setUserId(Integer userId){
		this.userId = userId;
	}


	public Integer getReplyUserId(){
		return replyUserId;
	}


	public void setReplyUserId(Integer replyUserId){
		this.replyUserId = replyUserId;
	}


	public String getWord(){
		return word;
	}


	public void setWord(String word){
		this.word = word;
	}


	public String getDataType(){
		return dataType;
	}


	public void setDataType(String dataType){
		this.dataType = dataType;
	}


	public String getCreateTime_(){
		return DateUtils.formatDateTime(createTime);
	}


	public Date getCreateTime(){
		return createTime;
	}


	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}


}
