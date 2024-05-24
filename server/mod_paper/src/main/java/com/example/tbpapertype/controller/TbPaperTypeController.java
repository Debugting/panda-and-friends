package com.example.tbpapertype.controller;
import javax.annotation.Resource;
import com.example.common.annotation.Pass;
import com.example.common.utils.ExcelUtils;
import com.example.sysuser.bean.SysUser;
import com.example.utils.UserUtils;
import org.springframework.beans.factory.annotation.Value;;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.common.bean.DataRes;
import java.util.*;
import org.springframework.stereotype.Controller;
import com.example.tbpapertype.bean.TbPaperType;
import com.example.tbpapertype.service.TbPaperTypeService;

/**
 * controller
 */
@Controller
public class TbPaperTypeController  {


	@Value("${rootUser}")
	private String rootUser;
	@Resource
	private TbPaperTypeService tbPaperTypeService;
	/**
	 * 跳转到列表页面
	 */
	@RequestMapping("tbPaperType/gotoList")
	public String gotoList(TbPaperType tbPaperType,HttpServletRequest request,HttpServletResponse response){
		return "example/tb_paper_type_list";
	}


	/**
	 * 跳转到详情页面
	 */
	@RequestMapping("tbPaperType/gotoDetail")
	public String gotoDetail(TbPaperType tbPaperType,HttpServletRequest request,HttpServletResponse response){
		if(tbPaperType.getId()!=null){
			 request.setAttribute("tb_paper_type",tbPaperTypeService.selectByPrimaryKey(tbPaperType));
		}else {
			request.setAttribute("tb_paper_type",tbPaperType);
		}
		return "example/tb_paper_type_detail";

	}


	/**
	 * 删除-
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaperType/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(TbPaperType tbPaperType,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbPaperTypeService.deleteByPrimaryKey(tbPaperType));
	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaperType/save")
	public DataRes save(TbPaperType tbPaperType,HttpServletRequest request,HttpServletResponse response){
		if (tbPaperType.getId() != null && tbPaperType.getId() !=0) {
			tbPaperTypeService.update(tbPaperType);} else {
		SysUser sysUser = UserUtils.getUser(request.getSession());
		if (sysUser != null && !sysUser.getAct().equals(rootUser)) {
			tbPaperType.setSysUserId(sysUser.getId());}
			tbPaperTypeService.insert(tbPaperType);}
		return DataRes.success(tbPaperType.getId());

	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaperType/insert")
	public DataRes insert(@RequestBody TbPaperType tbPaperType,HttpServletRequest request,HttpServletResponse response){
		if (tbPaperType.getId() != null && tbPaperType.getId() !=0) {
			tbPaperTypeService.update(tbPaperType);
		} else {
			tbPaperTypeService.insert(tbPaperType);
		}
		return DataRes.success(tbPaperType.getId());

	}


	/**
	 * 根据主键查询->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaperType/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(TbPaperType tbPaperType,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbPaperTypeService.selectByPrimaryKey(tbPaperType));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaperType/selectByCondition")
	public DataRes selectByCondition(TbPaperType tbPaperType,HttpServletRequest request,HttpServletResponse response){
		SysUser sysUser = UserUtils.getUser(request.getSession());
		if (sysUser != null && !sysUser.getAct().equals(rootUser)) {tbPaperType.setSysUserId(sysUser.getId());}
		return DataRes.success(tbPaperTypeService.selectByCondition(tbPaperType));

	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaperType/selectAllByPaging")
	public DataRes selectAllByPaging(TbPaperType tbPaperType,HttpServletRequest request,HttpServletResponse response){
		SysUser sysUser = UserUtils.getUser(request.getSession());
		if (sysUser != null && !sysUser.getAct().equals(rootUser)) {tbPaperType.setSysUserId(sysUser.getId());}
		return DataRes.success(tbPaperTypeService.selectAllByPaging(tbPaperType));

	}


	/**
	 * 导出报表->
	 */
	@RequestMapping("tbPaperType/export")
	public void export(TbPaperType tbPaperType,HttpServletRequest request,HttpServletResponse response){
		List<TbPaperType> list= tbPaperTypeService.selectAll(tbPaperType);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("name", "名称");
		header.put("dataType", "类型");
		header.put("status_", "{\"name\":\"状态\",\"0\":\"禁用\",\"1\":\"可用\"}");
		ExcelUtils.exportExcel("帖子类型",header,list,response,request);

	}


}
