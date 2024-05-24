package com.example.tbsocialrecommend.bean;
import com.example.common.bean.Page;
import com.example.common.utils.DateUtils;
import java.util.Date;
import java.math.BigDecimal;
import com.example.common.utils.MyStringUtils;


/**
 *推荐数据 bean
 */
public class TbSocialRecommend extends Page  {


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
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 推荐得分
	 */
	private Integer score;
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


	public String getDataType(){
		return dataType;
	}


	public void setDataType(String dataType){
		this.dataType = dataType;
	}


	public Integer getScore(){
		return score;
	}


	public void setScore(Integer score){
		this.score = score;
	}


}
