package com.example.tbpapertype.bean;
import com.example.common.bean.Page;
import com.example.common.utils.DateUtils;
import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import com.example.common.utils.MyStringUtils;


/**
 *帖子类型 bean
 */
public class TbPaperType extends Page  {


	/**
	 * id
	 */
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private String dataType;
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


	public String getName(){
		return name;
	}


	public void setName(String name){
		this.name = name;
	}


	public String getDataType(){
		return dataType;
	}


	public void setDataType(String dataType){
		this.dataType = dataType;
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
