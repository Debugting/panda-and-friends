package com.example.tbsocialbanner.controller;
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
import com.example.tbsocialbanner.bean.TbSocialBanner;
import com.example.tbsocialbanner.service.TbSocialBannerService;

/**
 * controller
 */
@Controller
public class TbSocialBannerController  {


	@Resource
	private TbSocialBannerService tbSocialBannerService;
	/**
	 * 跳转到列表页面
	 */
	@RequestMapping("tbSocialBanner/gotoList")
	public String gotoList(TbSocialBanner tbSocialBanner,HttpServletRequest request,HttpServletResponse response){
		return "example/tb_social_banner_list";
	}


	/**
	 * 跳转到详情页面
	 */
	@RequestMapping("tbSocialBanner/gotoDetail")
	public String gotoDetail(TbSocialBanner tbSocialBanner,HttpServletRequest request,HttpServletResponse response){
		if(tbSocialBanner.getId()!=null){
			 request.setAttribute("tb_social_banner",tbSocialBannerService.selectByPrimaryKey(tbSocialBanner));
		}else {
			request.setAttribute("tb_social_banner",tbSocialBanner);
		}
		return "example/tb_social_banner_detail";

	}


	/**
	 * 删除-
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialBanner/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(TbSocialBanner tbSocialBanner,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialBannerService.deleteByPrimaryKey(tbSocialBanner));
	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialBanner/save")
	public DataRes save(TbSocialBanner tbSocialBanner,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialBanner.getId() != null && tbSocialBanner.getId() !=0) {
			tbSocialBannerService.update(tbSocialBanner);
		} else {
			tbSocialBannerService.insert(tbSocialBanner);
		}
		return DataRes.success(tbSocialBanner.getId());

	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialBanner/insert")
	public DataRes insert(@RequestBody TbSocialBanner tbSocialBanner,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialBanner.getId() != null && tbSocialBanner.getId() !=0) {
			tbSocialBannerService.update(tbSocialBanner);
		} else {
			tbSocialBannerService.insert(tbSocialBanner);
		}
		return DataRes.success(tbSocialBanner.getId());

	}


	/**
	 * 根据主键查询->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialBanner/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(TbSocialBanner tbSocialBanner,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialBannerService.selectByPrimaryKey(tbSocialBanner));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialBanner/selectByCondition")
	public DataRes selectByCondition(TbSocialBanner tbSocialBanner,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialBannerService.selectByCondition(tbSocialBanner));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialBanner/selectAllByPaging")
	public DataRes selectAllByPaging(TbSocialBanner tbSocialBanner,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialBannerService.selectAllByPaging(tbSocialBanner));
	}


	/**
	 * 导出报表->
	 */
	@RequestMapping("tbSocialBanner/export")
	public void export(TbSocialBanner tbSocialBanner,HttpServletRequest request,HttpServletResponse response){
		List<TbSocialBanner> list= tbSocialBannerService.selectAll(tbSocialBanner);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("imgUrl", "图片");
		header.put("content", "详情");
		header.put("dataType", "数据类型");
		header.put("createTime_", "创建时间");
		ExcelUtils.exportExcel("轮播图",header,list,response,request);

	}


}
