package com.controller.tool;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.base.AjaxResult;
import com.model.tool.TableFields;
import com.model.tool.TemplateParams;
import com.service.tool.CodeGeneratorService;
import com.service.user.UserService;

/**
 * 
 * @功能说明：代码生成器,生成Action、JavaBean、Dao、Service类和jsp模板文件
 */
@Controller
@RequestMapping("/codeGenerator")
public class CodeGeneratorController {
	
	private Logger logger = Logger.getLogger(CodeGeneratorController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CodeGeneratorService codeGeneratorService;
	

	@RequestMapping("/form")
	public String form(HttpServletRequest request, Model model){
		try {
			//获取所有数据库表下拉框数据
			List<TemplateParams> listParams = codeGeneratorService.getTablesList();
			model.addAttribute("tableList", listParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "views/tool/codeGenerator";
	}
	
	/**
	 * 获取所有数据库表下拉框数据
	 */
	@RequestMapping("/getTablesList")
	@ResponseBody
	public List<TemplateParams> getTablesList() {
		List<TemplateParams> listParams = null;
		try {
			listParams = codeGeneratorService.getTablesList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return listParams;
	}

	/**
	 * 根据表名称获取字段集合
	 */
	@RequestMapping("/getFieldList")
	@ResponseBody
	public List<TableFields> getFieldList(TemplateParams templateParams) {
		List<TableFields> tableFields = null;
		try {
			tableFields = codeGeneratorService.getFieldList(templateParams.getTableName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return tableFields;
	}

	/**
	 * 生成代码
	 */
	@RequestMapping("/createCode")
	@ResponseBody
	public AjaxResult createCode(TemplateParams templateParams, HttpSession session, HttpServletRequest request) {
		AjaxResult result = new AjaxResult();
		result.setCode("1");
		try {
			result = codeGeneratorService.createCode(templateParams, session, request);
			result.setMsg("代码生成成功");
			result.setCode("0");
		} catch (Exception e) {
			result.setMsg("代码生成失败");
			e.printStackTrace();
		}
		return result;
	}
	
}
