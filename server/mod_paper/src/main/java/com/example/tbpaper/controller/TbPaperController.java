package com.example.tbpaper.controller;
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
import com.example.tbpaper.bean.TbPaper;
import com.example.tbpaper.service.TbPaperService;

/**
 * controller
 */
@Controller
public class TbPaperController  {


	@Value("${rootUser}")
	private String rootUser;
	@Resource
	private TbPaperService tbPaperService;
	/**
	 * 跳转到列表页面
	 */
	@RequestMapping("tbPaper/gotoList")
	public String gotoList(TbPaper tbPaper,HttpServletRequest request,HttpServletResponse response){
		return "example/tb_paper_list";
	}


	/**
	 * 跳转到详情页面
	 */
	@RequestMapping("tbPaper/gotoDetail")
	public String gotoDetail(TbPaper tbPaper,HttpServletRequest request,HttpServletResponse response){
		if(tbPaper.getId()!=null){
			 request.setAttribute("tb_paper",tbPaperService.selectByPrimaryKey(tbPaper));
		}else {
			request.setAttribute("tb_paper",tbPaper);
		}
		return "example/tb_paper_detail";

	}


	/**
	 * 删除-
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaper/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(TbPaper tbPaper,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbPaperService.deleteByPrimaryKey(tbPaper));
	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaper/save")
	public DataRes save(TbPaper tbPaper,HttpServletRequest request,HttpServletResponse response){
		if (tbPaper.getId() != null && tbPaper.getId() !=0) {
			tbPaperService.update(tbPaper);} else {
		SysUser sysUser = UserUtils.getUser(request.getSession());
		if (sysUser != null && !sysUser.getAct().equals(rootUser)) {
			tbPaper.setSysUserId(sysUser.getId());}
			tbPaperService.insert(tbPaper);}
		return DataRes.success(tbPaper.getId());

	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaper/insert")
	public DataRes insert(@RequestBody TbPaper tbPaper,HttpServletRequest request,HttpServletResponse response){
		if (tbPaper.getId() != null && tbPaper.getId() !=0) {
			tbPaperService.update(tbPaper);
		} else {
			tbPaperService.insert(tbPaper);
		}
		return DataRes.success(tbPaper.getId());

	}


	/**
	 * 根据主键查询->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaper/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(TbPaper tbPaper,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbPaperService.selectByPrimaryKey(tbPaper));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaper/selectByCondition")
	public DataRes selectByCondition(TbPaper tbPaper,HttpServletRequest request,HttpServletResponse response){
		SysUser sysUser = UserUtils.getUser(request.getSession());
		if (sysUser != null && !sysUser.getAct().equals(rootUser)) {tbPaper.setSysUserId(sysUser.getId());}
		return DataRes.success(tbPaperService.selectByCondition(tbPaper));

	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbPaper/selectAllByPaging")
	public DataRes selectAllByPaging(TbPaper tbPaper,HttpServletRequest request,HttpServletResponse response){
		SysUser sysUser = UserUtils.getUser(request.getSession());
		if (sysUser != null && !sysUser.getAct().equals(rootUser)) {tbPaper.setSysUserId(sysUser.getId());}
		return DataRes.success(tbPaperService.selectAllByPaging(tbPaper));

	}


	/**
	 * 导出报表->
	 */
	@RequestMapping("tbPaper/export")
	public void export(TbPaper tbPaper,HttpServletRequest request,HttpServletResponse response){
		List<TbPaper> list= tbPaperService.selectAll(tbPaper);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("userId", "用户");
		header.put("paperTypeId", "类型");
		header.put("title", "标题");
		header.put("word", "简介");
		header.put("content", "内容");
		header.put("imgUrl", "图片");
		header.put("imgUrl2", "图片");
		header.put("imgUrl3", "图片");
		header.put("createTime", "发布时间");
		header.put("status_", "{\"name\":\"状态\",\"0\":\"禁用\",\"1\":\"可用\"}");
		ExcelUtils.exportExcel("帖子",header,list,response,request);

	}


}
