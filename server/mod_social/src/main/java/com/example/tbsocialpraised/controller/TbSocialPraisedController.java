package com.example.tbsocialpraised.controller;
import javax.annotation.Resource;
import com.example.common.annotation.Pass;
import com.example.common.utils.ExcelUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.common.bean.DataRes;
import java.util.*;
import org.springframework.stereotype.Controller;
import com.example.tbsocialpraised.bean.TbSocialPraised;
import com.example.tbsocialpraised.service.TbSocialPraisedService;

/**
 * controller
 */
@Controller
public class TbSocialPraisedController  {


	@Resource
	private TbSocialPraisedService tbSocialPraisedService;
	/**
	 * 跳转到列表页面
	 */
	@RequestMapping("tbSocialPraised/gotoList")
	public String gotoList(TbSocialPraised tbSocialPraised,HttpServletRequest request,HttpServletResponse response){
		return "example/tb_social_praised_list";
	}


	/**
	 * 跳转到详情页面
	 */
	@RequestMapping("tbSocialPraised/gotoDetail")
	public String gotoDetail(TbSocialPraised tbSocialPraised,HttpServletRequest request,HttpServletResponse response){
		if(tbSocialPraised.getId()!=null){
			 request.setAttribute("tb_social_praised",tbSocialPraisedService.selectByPrimaryKey(tbSocialPraised));
		}else {
			request.setAttribute("tb_social_praised",tbSocialPraised);
		}
		return "example/tb_social_praised_detail";

	}


	/**
	 * 删除-
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialPraised/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(TbSocialPraised tbSocialPraised,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialPraisedService.deleteByPrimaryKey(tbSocialPraised));
	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialPraised/save")
	public DataRes save(TbSocialPraised tbSocialPraised,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialPraised.getId() != null && tbSocialPraised.getId() !=0) {
			tbSocialPraisedService.update(tbSocialPraised);
		} else {
			tbSocialPraisedService.insert(tbSocialPraised);
		}
		return DataRes.success(tbSocialPraised.getId());

	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialPraised/insert")
	public DataRes insert(@RequestBody TbSocialPraised tbSocialPraised,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialPraised.getId() != null && tbSocialPraised.getId() !=0) {
			tbSocialPraisedService.update(tbSocialPraised);
		} else {
			tbSocialPraisedService.insert(tbSocialPraised);
		}
		return DataRes.success(tbSocialPraised.getId());

	}


	/**
	 * 根据主键查询->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialPraised/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(TbSocialPraised tbSocialPraised,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialPraisedService.selectByPrimaryKey(tbSocialPraised));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialPraised/selectByCondition")
	public DataRes selectByCondition(TbSocialPraised tbSocialPraised,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialPraisedService.selectByCondition(tbSocialPraised));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialPraised/selectAllByPaging")
	public DataRes selectAllByPaging(TbSocialPraised tbSocialPraised,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialPraisedService.selectAllByPaging(tbSocialPraised));
	}


	/**
	 * 导出报表->
	 */
	@RequestMapping("tbSocialPraised/export")
	public void export(TbSocialPraised tbSocialPraised,HttpServletRequest request,HttpServletResponse response){
		List<TbSocialPraised> list= tbSocialPraisedService.selectAll(tbSocialPraised);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("outId", "关联id");
		header.put("userId", "用户");
		header.put("dataType", "数据类型");
		header.put("createTime_", "创建时间");
		ExcelUtils.exportExcel("点赞记录",header,list,response,request);

	}


}
