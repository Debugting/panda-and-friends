package com.example.tbsocialbanner.bean;
import com.example.common.bean.Page;
import com.example.common.utils.DateUtils;
import java.util.Date;
import java.math.BigDecimal;
import com.example.common.utils.MyStringUtils;


/**
 *轮播图 bean
 */
public class TbSocialBanner extends Page  {


	/**
	 * id
	 */
	private Integer id;
	/**
	 * 图片
	 */
	private String imgUrl;
	/**
	 * 详情
	 */
	private String content;
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


	public String getImgUrl(){
		return imgUrl;
	}


	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}


	public String getContent(){
		return content;
	}


	public void setContent(String content){
		this.content = content;
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
