package com.example.tbuser.bean;
import com.example.common.bean.Page;
import com.example.common.utils.DateUtils;
import java.util.Date;
import java.math.BigDecimal;
import com.example.common.utils.MyStringUtils;


/**
 *用户 bean
 */
public class TbUser extends Page  {


	/**
	 * id
	 */
	private Integer id;
	/**
	 * 账号
	 */
	private String act;
	/**
	 * 密码
	 */
	private String pwd;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * {"name":"性别","1":"男","0":"女"}
	 */
	private Integer sex;
	/**
	 * 出生日期
	 */
	private Date birthday;
	/**
	 * 籍贯
	 */
	private String province;
	/**
	 * 密保问题
	 */
	private String question;
	/**
	 * 密保答案
	 */
	private String answer;
	/**
	 * 图片
	 */
	private String imgUrl;
	/**
	 * {"name":"类型","1":"类型1","2":"类型2","3":"类型3"}
	 */
	private Integer userType;
	/**
	 * {"name":"状态","1":"可用","0":"禁用"}
	 */
	private Integer status;
	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id = id;
	}


	public String getAct(){
		return act;
	}


	public void setAct(String act){
		this.act = act;
	}


	public String getPwd(){
		return pwd;
	}


	public void setPwd(String pwd){
		this.pwd = pwd;
	}


	public String getName(){
		return name;
	}


	public void setName(String name){
		this.name = name;
	}


	public String getSex_(){
		if(MyStringUtils.isEmpty(sex)) {
			 return "";
		}else if(sex.toString().equals("1")) {
			return "男";
		}else if(sex.toString().equals("0")) {
			return "女";
		}
		return "";

	}


	public Integer getSex(){
		return sex;
	}


	public void setSex(Integer sex){
		this.sex = sex;
	}


	public String getBirthday_(){
		return DateUtils.formatDateTime(birthday);
	}


	public Date getBirthday(){
		return birthday;
	}


	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}


	public String getProvince(){
		return province;
	}


	public void setProvince(String province){
		this.province = province;
	}


	public String getQuestion(){
		return question;
	}


	public void setQuestion(String question){
		this.question = question;
	}


	public String getAnswer(){
		return answer;
	}


	public void setAnswer(String answer){
		this.answer = answer;
	}


	public String getImgUrl(){
		return imgUrl;
	}


	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}


	public String getUserType_(){
		if(MyStringUtils.isEmpty(userType)) {
			 return "";
		}else if(userType.toString().equals("1")) {
			return "类型1";
		}else if(userType.toString().equals("2")) {
			return "类型2";
		}else if(userType.toString().equals("3")) {
			return "类型3";
		}
		return "";

	}


	public Integer getUserType(){
		return userType;
	}


	public void setUserType(Integer userType){
		this.userType = userType;
	}


	public String getStatus_(){
		if(MyStringUtils.isEmpty(status)) {
			 return "";
		}else if(status.toString().equals("1")) {
			return "可用";
		}else if(status.toString().equals("0")) {
			return "禁用";
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
