package com.example.tbsocialcomment.controller;
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
import com.example.tbsocialcomment.bean.TbSocialComment;
import com.example.tbsocialcomment.service.TbSocialCommentService;

/**
 * controller
 */
@Controller
public class TbSocialCommentController  {


	@Resource
	private TbSocialCommentService tbSocialCommentService;
	/**
	 * 跳转到列表页面
	 */
	@RequestMapping("tbSocialComment/gotoList")
	public String gotoList(TbSocialComment tbSocialComment,HttpServletRequest request,HttpServletResponse response){
		return "example/tb_social_comment_list";
	}


	/**
	 * 跳转到详情页面
	 */
	@RequestMapping("tbSocialComment/gotoDetail")
	public String gotoDetail(TbSocialComment tbSocialComment,HttpServletRequest request,HttpServletResponse response){
		if(tbSocialComment.getId()!=null){
			 request.setAttribute("tb_social_comment",tbSocialCommentService.selectByPrimaryKey(tbSocialComment));
		}else {
			request.setAttribute("tb_social_comment",tbSocialComment);
		}
		return "example/tb_social_comment_detail";

	}


	/**
	 * 删除-
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialComment/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(TbSocialComment tbSocialComment,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialCommentService.deleteByPrimaryKey(tbSocialComment));
	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialComment/save")
	public DataRes save(TbSocialComment tbSocialComment,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialComment.getId() != null && tbSocialComment.getId() !=0) {
			tbSocialCommentService.update(tbSocialComment);
		} else {
			tbSocialCommentService.insert(tbSocialComment);
		}
		return DataRes.success(tbSocialComment.getId());

	}


	/**
	 *  保存 (主键为空则增加否则修改)-> 
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialComment/insert")
	public DataRes insert(@RequestBody TbSocialComment tbSocialComment,HttpServletRequest request,HttpServletResponse response){
		if (tbSocialComment.getId() != null && tbSocialComment.getId() !=0) {
			tbSocialCommentService.update(tbSocialComment);
		} else {
			tbSocialCommentService.insert(tbSocialComment);
		}
		return DataRes.success(tbSocialComment.getId());

	}


	/**
	 * 根据主键查询->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialComment/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(TbSocialComment tbSocialComment,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialCommentService.selectByPrimaryKey(tbSocialComment));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialComment/selectByCondition")
	public DataRes selectByCondition(TbSocialComment tbSocialComment,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialCommentService.selectByCondition(tbSocialComment));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)
	 */
	@ResponseBody
	@Pass
	@RequestMapping("tbSocialComment/selectAllByPaging")
	public DataRes selectAllByPaging(TbSocialComment tbSocialComment,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(tbSocialCommentService.selectAllByPaging(tbSocialComment));
	}


	/**
	 * 导出报表->
	 */
	@RequestMapping("tbSocialComment/export")
	public void export(TbSocialComment tbSocialComment,HttpServletRequest request,HttpServletResponse response){
		List<TbSocialComment> list= tbSocialCommentService.selectAll(tbSocialComment);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("outId", "关联id");
		header.put("userId", "用户");
		header.put("replyUserId", "回复用户");
		header.put("word", "内容");
		header.put("dataType", "数据类型");
		header.put("createTime_", "创建时间");
		ExcelUtils.exportExcel("用户评论",header,list,response,request);

	}


}
