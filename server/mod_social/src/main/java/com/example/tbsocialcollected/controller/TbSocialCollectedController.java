package com.example.tbsocialcollected.controller;
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
import com.example.tbsocialcollected.bean.TbSocialCollected;
import com.example.tbsocialcollected.service.TbSocialCollectedService;

/**
 * controller
 */
@Controller
public class TbSocialCollectedController  {


	@Resource
	private TbSocialCollectedService tbSocialCollectedService;
	/**
	 * 跳转到列表页面
	 */
	@RequestMapping("tbSocialCollected/gotoList")
	public String gotoList(TbSocialCollected tbSocialCollected,HttpServletRequest request,HttpServletResponse response){
		return "example/tb_social_collected_list";
	}


	/**
	 * 跳转到详情页面
	 */
	@RequestMapping("tbSocialCollected/gotoDetail")
	public String gotoDetail(TbSocialCollected tbSocialCollected,HttpServletRequest request,HttpServletResponse response){
		if(tbSocialCollected.getId()!=null){
			 request.setAttribute("tb_social_collected",tbSocialCollectedService.selectByPrimaryKey(tbSocialCollected));
		}else {
			request.setAttribute("tb_social_collected",tbSocialCollected);
		}
		return "example/tb_social_collected_detail";

	}


	/**
	 * 删除-
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialCollected/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(TbSocialCollected tbSocialCollected,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialCollectedService.deleteByPrimaryKey(tbSocialCollected));
	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialCollected/save")
	public DataRes save(TbSocialCollected tbSocialCollected,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialCollected.getId() != null && tbSocialCollected.getId() !=0) {
			tbSocialCollectedService.update(tbSocialCollected);
		} else {
			tbSocialCollectedService.insert(tbSocialCollected);
		}
		return DataRes.success(tbSocialCollected.getId());

	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialCollected/insert")
	public DataRes insert(@RequestBody TbSocialCollected tbSocialCollected,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialCollected.getId() != null && tbSocialCollected.getId() !=0) {
			tbSocialCollectedService.update(tbSocialCollected);
		} else {
			tbSocialCollectedService.insert(tbSocialCollected);
		}
		return DataRes.success(tbSocialCollected.getId());

	}


	/**
	 * 根据主键查询->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialCollected/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(TbSocialCollected tbSocialCollected,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialCollectedService.selectByPrimaryKey(tbSocialCollected));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialCollected/selectByCondition")
	public DataRes selectByCondition(TbSocialCollected tbSocialCollected,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialCollectedService.selectByCondition(tbSocialCollected));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialCollected/selectAllByPaging")
	public DataRes selectAllByPaging(TbSocialCollected tbSocialCollected,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialCollectedService.selectAllByPaging(tbSocialCollected));
	}


	/**
	 * 导出报表->
	 */
	@RequestMapping("tbSocialCollected/export")
	public void export(TbSocialCollected tbSocialCollected,HttpServletRequest request,HttpServletResponse response){
		List<TbSocialCollected> list= tbSocialCollectedService.selectAll(tbSocialCollected);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("outId", "关联id");
		header.put("userId", "用户");
		header.put("dataType", "数据类型");
		header.put("createTime_", "创建时间");
		ExcelUtils.exportExcel("收藏记录",header,list,response,request);

	}


}
