package com.example.tbsocialvisited.controller;
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
import com.example.tbsocialvisited.bean.TbSocialVisited;
import com.example.tbsocialvisited.service.TbSocialVisitedService;

/**
 * controller
 */
@Controller
public class TbSocialVisitedController  {


	@Resource
	private TbSocialVisitedService tbSocialVisitedService;
	/**
	 * 跳转到列表页面
	 */
	@RequestMapping("tbSocialVisited/gotoList")
	public String gotoList(TbSocialVisited tbSocialVisited,HttpServletRequest request,HttpServletResponse response){
		return "example/tb_social_visited_list";
	}


	/**
	 * 跳转到详情页面
	 */
	@RequestMapping("tbSocialVisited/gotoDetail")
	public String gotoDetail(TbSocialVisited tbSocialVisited,HttpServletRequest request,HttpServletResponse response){
		if(tbSocialVisited.getId()!=null){
			 request.setAttribute("tb_social_visited",tbSocialVisitedService.selectByPrimaryKey(tbSocialVisited));
		}else {
			request.setAttribute("tb_social_visited",tbSocialVisited);
		}
		return "example/tb_social_visited_detail";

	}


	/**
	 * 删除-
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialVisited/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(TbSocialVisited tbSocialVisited,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialVisitedService.deleteByPrimaryKey(tbSocialVisited));
	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialVisited/save")
	public DataRes save(TbSocialVisited tbSocialVisited,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialVisited.getId() != null && tbSocialVisited.getId() !=0) {
			tbSocialVisitedService.update(tbSocialVisited);
		} else {
			tbSocialVisitedService.insert(tbSocialVisited);
		}
		return DataRes.success(tbSocialVisited.getId());

	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialVisited/insert")
	public DataRes insert(@RequestBody TbSocialVisited tbSocialVisited,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialVisited.getId() != null && tbSocialVisited.getId() !=0) {
			tbSocialVisitedService.update(tbSocialVisited);
		} else {
			tbSocialVisitedService.insert(tbSocialVisited);
		}
		return DataRes.success(tbSocialVisited.getId());

	}


	/**
	 * 根据主键查询->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialVisited/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(TbSocialVisited tbSocialVisited,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialVisitedService.selectByPrimaryKey(tbSocialVisited));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialVisited/selectByCondition")
	public DataRes selectByCondition(TbSocialVisited tbSocialVisited,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialVisitedService.selectByCondition(tbSocialVisited));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialVisited/selectAllByPaging")
	public DataRes selectAllByPaging(TbSocialVisited tbSocialVisited,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialVisitedService.selectAllByPaging(tbSocialVisited));
	}


	/**
	 * 导出报表->
	 */
	@RequestMapping("tbSocialVisited/export")
	public void export(TbSocialVisited tbSocialVisited,HttpServletRequest request,HttpServletResponse response){
		List<TbSocialVisited> list= tbSocialVisitedService.selectAll(tbSocialVisited);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("outId", "关联id");
		header.put("userId", "用户");
		header.put("dataType", "数据类型");
		header.put("createTime_", "创建时间");
		ExcelUtils.exportExcel("浏览记录",header,list,response,request);

	}


}
