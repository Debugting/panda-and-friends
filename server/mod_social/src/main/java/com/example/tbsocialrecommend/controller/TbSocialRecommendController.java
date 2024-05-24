package com.example.tbsocialrecommend.controller;
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
import com.example.tbsocialrecommend.bean.TbSocialRecommend;
import com.example.tbsocialrecommend.service.TbSocialRecommendService;

/**
 * controller
 */
@Controller
public class TbSocialRecommendController  {


	@Resource
	private TbSocialRecommendService tbSocialRecommendService;
	/**
	 * 跳转到列表页面
	 */
	@RequestMapping("tbSocialRecommend/gotoList")
	public String gotoList(TbSocialRecommend tbSocialRecommend,HttpServletRequest request,HttpServletResponse response){
		return "example/tb_social_recommend_list";
	}


	/**
	 * 跳转到详情页面
	 */
	@RequestMapping("tbSocialRecommend/gotoDetail")
	public String gotoDetail(TbSocialRecommend tbSocialRecommend,HttpServletRequest request,HttpServletResponse response){
		if(tbSocialRecommend.getId()!=null){
			 request.setAttribute("tb_social_recommend",tbSocialRecommendService.selectByPrimaryKey(tbSocialRecommend));
		}else {
			request.setAttribute("tb_social_recommend",tbSocialRecommend);
		}
		return "example/tb_social_recommend_detail";

	}


	/**
	 * 删除-
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialRecommend/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(TbSocialRecommend tbSocialRecommend,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialRecommendService.deleteByPrimaryKey(tbSocialRecommend));
	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialRecommend/save")
	public DataRes save(TbSocialRecommend tbSocialRecommend,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialRecommend.getId() != null && tbSocialRecommend.getId() !=0) {
			tbSocialRecommendService.update(tbSocialRecommend);
		} else {
			tbSocialRecommendService.insert(tbSocialRecommend);
		}
		return DataRes.success(tbSocialRecommend.getId());

	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialRecommend/insert")
	public DataRes insert(@RequestBody TbSocialRecommend tbSocialRecommend,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialRecommend.getId() != null && tbSocialRecommend.getId() !=0) {
			tbSocialRecommendService.update(tbSocialRecommend);
		} else {
			tbSocialRecommendService.insert(tbSocialRecommend);
		}
		return DataRes.success(tbSocialRecommend.getId());

	}


	/**
	 * 根据主键查询->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialRecommend/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(TbSocialRecommend tbSocialRecommend,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialRecommendService.selectByPrimaryKey(tbSocialRecommend));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialRecommend/selectByCondition")
	public DataRes selectByCondition(TbSocialRecommend tbSocialRecommend,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialRecommendService.selectByCondition(tbSocialRecommend));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialRecommend/selectAllByPaging")
	public DataRes selectAllByPaging(TbSocialRecommend tbSocialRecommend,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialRecommendService.selectAllByPaging(tbSocialRecommend));
	}


	/**
	 * 导出报表->
	 */
	@RequestMapping("tbSocialRecommend/export")
	public void export(TbSocialRecommend tbSocialRecommend,HttpServletRequest request,HttpServletResponse response){
		List<TbSocialRecommend> list= tbSocialRecommendService.selectAll(tbSocialRecommend);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("outId", "关联id");
		header.put("userId", "用户");
		header.put("dataType", "数据类型");
		header.put("score", "推荐得分");
		ExcelUtils.exportExcel("推荐数据",header,list,response,request);

	}


}
