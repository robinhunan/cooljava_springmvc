package com.service.tool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.tool.CodeGeneratorMapper;
import com.model.base.AjaxResult;
import com.model.tool.TableFields;
import com.model.tool.TemplateParams;
import com.quantity.Global;
import com.util.codeGenerator.MapToBeanUtils;
import com.util.codeGenerator.PbUtils;
import com.util.codeGenerator.TemplateHelp;

import freemarker.template.TemplateException;

/**
 * 
 * @功能说明：代码生成器
 */
@Service("CodeGeneratorService")
@Transactional
public class CodeGeneratorService  {
	
	@Resource
	private CodeGeneratorMapper codeDao;// 系统菜单
	
	private String database =  Global.getConfig("database");
	
	/**
	 * 获取所有的表名称
	 * 
	 * @return
	 */
	public List<TemplateParams> getTablesList() throws Exception{
		List<TemplateParams> list = new ArrayList<TemplateParams>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("database", database);
			params.put("dbName",  Global.getConfig("jdbc.type"));//数据库
			List<Map<String, Object>> maps =  codeDao.getTablesList(params);
			MapToBeanUtils utils = new MapToBeanUtils<TemplateParams>();
			list = utils.ListMapToJavaBean(maps, TemplateParams.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 根据表名称获取字段集合
	 * 
	 * @param tableName
	 * @return
	 */
	public List<TableFields> getFieldList(String tableName) throws Exception{
		List<Map> maps = getListMap(tableName);
		MapToBeanUtils utils = new MapToBeanUtils<TableFields>();
		List<TableFields> list = utils.ListMapToJavaBean(maps, TableFields.class);
		return list;
	}
	
	/**
	 * 获取列数据
	 * 
	 * @param tableName
	 * @return
	 */
	private List<Map> getListMap(String tableName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table_name", tableName);// 表名称
		params.put("database", database);// 数据库
		params.put("dbName",  Global.getConfig("jdbc.type"));//数据库类型
		List<Map> maps = codeDao.getListMap(params);
		return maps;
	}
	
	/**
	 * 生成代码
	 * 
	 * @param templateParams
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public AjaxResult createCode(TemplateParams templateParams, HttpSession session, HttpServletRequest request) throws Exception{
		AjaxResult result = new AjaxResult();
		result.setCode("0");//生成成功
		//生成模板文件目录
		String jspFilePath = templateParams.getClassPath() + "/src/main/webapp/" + templateParams.getClassName() + "/";
		jspFilePath += templateParams.getClassName();
		
		// 1、生成Model代码
		creatModel(templateParams, request);
		
		 //2.生成Mapper代码
		creatMapper(templateParams,request);
		 
		//3.生成Service代码
		creatService(templateParams,request);
		
		// 4.生成Controller类
		creatController(templateParams, request);
		
		// 5.生成list.jsp
		createListJsp(templateParams, request);
		
		// 6.生成form.jsp
		createFormJsp(templateParams, request);
		
		// 7.生成list.js
		createListJs(templateParams, request);
		
		//8.生成Mapper.xml
		createXml(templateParams, request);
		
		return result;
	}
	
	/**
	 * 生成JavaBean代码
	 * 
	 * @param templateParams
	 * @throws TemplateException
	 * @throws IOException
	 */
	private void creatModel(TemplateParams templateParams, HttpServletRequest request) throws Exception {
		List<Map> list = getListMap(templateParams.getTableName());
		List<Map<String, Object>> clList = new ArrayList<Map<String, Object>>();
		// List<Map> isNullList =
		// getIsNullAble(templateParams.getTableName().toUpperCase());

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				// 遍历list
				Map<String, Object> map = list.get(i);


				// 遍历map
				for (String key : map.keySet()) {
					// 列名称
					if (key.equals("COLUMNNAME")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get(key).toString());// 列名称，首字母小写，去下划线
						oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线

						// 自动判断大小写
						if (map.get(key).toString().substring(1, 2).equals("_")) {
							oMap.put("UpUmnName", reStr);// 列名称，首字母小写，去下划线
						} else {
							oMap.put("UpUmnName", PbUtils.fristStrToUpperCase(reStr));// 列名称，首字母大写，去下划线
						}

					}

					// 注释
					if (key.equals("COLUMNCOMMENT")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
						oMap.put("columnComment", map.get(key) == null ? reStr : map.get(key));// 注释
					}

					// 列类型
					if (key.equals("COLUMNTYPE")) {
						String columnType = map.get("COLUMNTYPE").toString();// 列类型
						String columnName = map.get("COLUMNNAME").toString();// 列名称
						String IsNullAble = map.get("ISNULLABLE").toString();// 是否为空

						String dataLength="10";
						if (map.containsKey("DATALENGTH") && map.get("DATALENGTH") !=null) {
							 dataLength = map.get("DATALENGTH").toString();// 长度
						}
						String cloums_top = "";

						// 判断是否为主键
						if ((columnName.toUpperCase()).equals(templateParams.getPkColumn().toUpperCase())) {
							cloums_top += "@Id ";
						}
						if (columnType.contains("timestamp")) {
							oMap.put("cloumsType", "Timestamp");
						}
						if (columnType.contains("long") || columnType.contains("interval") || columnType.contains("blob") || columnType.contains("varchar") || columnType.contains("char") || columnType.contains("text")) {
							oMap.put("cloumsType", "String");
						}
						if (columnType.contains("decimal") || columnType.contains("float") || columnType.contains("double") || columnType.contains("integer") ) {
							oMap.put("cloumsType", "Long");
						}
						if (columnType.contains("date") || columnType.contains("datetime")) {
							oMap.put("cloumsType", "Date");
							cloums_top += " @Temporal(TemporalType.TIMESTAMP) ";
						}
						if (columnType.contains("Integer")||columnType.contains("int")) {
							oMap.put("cloumsType", "int");
						}
                        if (columnType.contains("NUMBER") ||columnType.contains("INTEGER")) {
                            oMap.put("cloumsType", "int");
                        }
                        if (columnType.contains("NVARCHAR2") || columnType.contains("NCHAR")||columnType.contains("VARCHAR2")) {
                            oMap.put("cloumsType", "String");
                        }
                        if (columnType.contains("DATE")){
                            oMap.put("cloumsType", "Date");
                            cloums_top += " @Temporal(TemporalType.TIMESTAMP) ";
                        }
                        if (columnType.contains("NCLOB")|| columnType.contains("CLOB")) {
                            oMap.put("cloumsType", "String");
                        }
                        if (columnType.contains("NUMBER") && map.get("DATASCALE") !=null&& !map.get("DATASCALE").toString().equals(String.valueOf(0))) {
                            oMap.put("cloumsType", "Double");
                        }
						cloums_top += "@Column( name = \"" + columnName + "\" ";
						if (columnName.equals(templateParams.getPkColumn().toUpperCase())) {
							cloums_top += ", unique = true ";
						}

						
						if (IsNullAble.equals("NO")) {
							cloums_top += " ,nullable = false ";
						}

						// 判断是否唯一性约束
						// if (isNullList.contains(key.equals("COLUMNTYPE"))) {
						// cloums_top += " , unique = true ";
						// }

						// 长度
						if (!columnType.contains("decimal")) {
							cloums_top += " , length = " + dataLength + " ";
						} else {
							// NUMBER 类型才有
							cloums_top += " , precision = " + dataLength + ", scale = 0 ";
						}

						cloums_top += " )";

						oMap.put("cloums_top", cloums_top);

					}

				}
				clList.add(oMap);// 添加到集合
			}
		}

		if (clList != null && clList.size() > 0) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("functionComment", templateParams.getFunctionComment());// 功能说明
			data.put("className", templateParams.getClassName());// 类名称
			data.put("mouldName", templateParams.getMouldName());// 子模块名称
			data.put("cloums", clList);// 属性
			data.put("date", PbUtils.getCurrentDate());// 日期
			// 作者
			if (!PbUtils.isEmpty(templateParams.getAuthor())) {
				data.put("author", templateParams.getAuthor());
			}
			data.put("Table_NAME", templateParams.getTableName());// 表名称
			// 模板名称
			String ftlName = "/Model.ftl";

			// 生成文件的路径和名称
			String fileName = templateParams.getClassPath() + "/src/main/java/com/model/" + templateParams.getMouldName()+"/"+templateParams.getClassName() + ".java";
			TemplateHelp.creatTemplate(data, ftlName, fileName, request);
		}
	}

	/**
	 * 生成Mapper.java类
	 * 
	 * @param templateParams
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void creatMapper(TemplateParams templateParams, HttpServletRequest request) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("className", templateParams.getClassName());// JavaBean名称
		data.put("HiTable", PbUtils.fristStrToUpperCase(PbUtils.strRelplacetoLowerCase(templateParams.getTableName())));// hibbernate
		data.put("mouldName", templateParams.getMouldName());// 子模块名称	
		data.put("functionComment", templateParams.getFunctionComment());// 功能说明
		data.put("classNameToL", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// JavaBean名称-第一个字母小写
		data.put("date", PbUtils.getCurrentDate());// 日期
		// 作者
		if (!PbUtils.isEmpty(templateParams.getAuthor())) {
			data.put("author", templateParams.getAuthor());
		}
		String ftlname = "/Mapper.ftl";
		// 生成文件的路径和名称
		String fileName = templateParams.getClassPath() + "/src/main/java/com/mapper/" + templateParams.getMouldName()+"/"+templateParams.getClassName() + "Mapper.java";
		TemplateHelp.creatTemplate(data, ftlname, fileName, request);
	}

	/**
	 * 生成Service.java类
	 * 
	 * @param templateParams
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void creatService(TemplateParams templateParams, HttpServletRequest request) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("className", templateParams.getClassName());// JavaBean名称
		data.put("HiTable", PbUtils.fristStrToUpperCase(PbUtils.strRelplacetoLowerCase(templateParams.getTableName())));// hibbernate
		data.put("mouldName", templateParams.getMouldName());// 子模块名称	
		data.put("functionComment", templateParams.getFunctionComment());// 功能说明
		data.put("classNameToL", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// JavaBean名称-第一个字母小写
		data.put("date", PbUtils.getCurrentDate());// 日期
		// 作者
		if (!PbUtils.isEmpty(templateParams.getAuthor())) {
			data.put("author", templateParams.getAuthor());
		}
		String ftlname = "/Service.ftl";
		// 生成文件的路径和名称
		String fileName = templateParams.getClassPath() + "/src/main/java/com/service/" + templateParams.getMouldName()+"/"+templateParams.getClassName() + "Service.java";
		TemplateHelp.creatTemplate(data, ftlname, fileName, request);
	}
	
	/**
	 * 生成Action类
	 * 
	 * @param templateParams
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void creatController(TemplateParams templateParams, HttpServletRequest request) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("className", templateParams.getClassName());// JavaBean名称
		data.put("classNameToL", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// JavaBean名称-第一个字母小写
		data.put("functionComment", templateParams.getFunctionComment());// 功能说明
		data.put("mouldName", templateParams.getMouldName());// 子模块名称	
		data.put("date", PbUtils.getCurrentDate());// 日期
		// 自动判断大小写
		if (templateParams.getPkColumn().substring(1, 2).equals("_")) {
			data.put("PkColumn", PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn()));// 主键字段-第一字母小写，去掉下划线，下划线紧跟的字母大写
		} else {
			data.put("PkColumn", PbUtils.fristStrToUpperCase(PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn())));// 列名称，首字母大写，去下划线
		}

		// 作者
		if (!PbUtils.isEmpty(templateParams.getAuthor())) {
			data.put("author", templateParams.getAuthor());
		}
		// 生成文件的路径和名称
		String fileName = templateParams.getClassPath() + "/src/main/java/com/controller/" + templateParams.getMouldName()+"/"+templateParams.getClassName() + "Controller.java";

		// ftl文件名称
		String ftlname = "/controller.ftl";

		TemplateHelp.creatTemplate(data, ftlname, fileName, request);
	}

	/**
	 * 生成List.jsp 代码
	 * 
	 * @param templateParams
	 * @throws TemplateException
	 * @throws IOException
	 */
	public void createListJsp(TemplateParams templateParams, HttpServletRequest request) throws IOException, TemplateException {
		List<Map> list = getListMap(templateParams.getTableName());
		List<Map<String, Object>> clList = new ArrayList<Map<String, Object>>();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				// 遍历list
				Map<String, Object> map = list.get(i);

				// 遍历map
				for (String key : map.keySet()) {

					// 列名称
					if (key.equals("COLUMNNAME")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get(key).toString());// 列名称，首字母小写，去下划线
						// if (up) {
						// oMap.put("columnName",
						// PbUtils.fristStrToUpperCase(reStr));// 列名称，首字母大写，去下划线
						// } else if (listIsLower) {
						// oMap.put("columnName",
						// PbUtils.fristAndSecondStrToLowerCase(reStr));//
						// 列名称，首字母小写,第二个字母小写，去下划线
						// } else {
						// oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线
						// }
						oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线
						oMap.put("UpUmnName", PbUtils.fristStrToUpperCase(reStr));// 列名称，首字母大写，去下划线
					}

					// 注释
					if (key.equals("COLUMNCOMMENT")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
						oMap.put("columnComment", map.get(key) == null ? reStr : map.get(key));// 注释
					}

				}
				clList.add(oMap);// 添加到集合
			}
		}

		if (clList != null && clList.size() > 0) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("classNameToL", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// JavaBean名称-第一个字母小写
			data.put("cloums", clList);// 属性
			data.put("functionComment", templateParams.getFunctionComment());// 功能说明
			//data.put("filePath", filePath);// 文件路径,定义在方法和Id前，例如sys_menuInf_MenuInfList
			data.put("action", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// action名称
			data.put("Action", PbUtils.fristStrToUpperCase(templateParams.getClassName()));// 第一个字母大写
			data.put("pkColumn", templateParams.getPkColumn());// 主键字段
			data.put("sortColumn", templateParams.getSortColumn());// 排序字段
			data.put("pkColumnDel", PbUtils.fristStrToLowerCase(PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn())));// 主键字段
			data.put("pkColumn_f", PbUtils.strRelplacetoLowerCase(templateParams.getSortColumn()));// 主键字段
			data.put("searchFilePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Search");// List页面的searchFilePath属性
			data.put("addFilePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Add");// List页面的addFilePath属性
			data.put("editFilePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Edit");// List页面的editFilePath属性
			String fileName = templateParams.getClassPath() + "/src/main/webapp/WEB-INF/views/" + templateParams.getMouldName()+"/"+ PbUtils.fristStrToLowerCase(templateParams.getClassName()) + "List.jsp";
			TemplateHelp.creatTemplate(data, "/List.ftl", fileName, request);
		}

	}
	
	/**
	 * 创建Form
	 * 
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void createFormJsp( TemplateParams templateParams,  HttpServletRequest request) throws IOException, TemplateException {
		List<Map> list = getListMap(templateParams.getTableName());
		List<Map<String, Object>> clList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				// 遍历list
				Map<String, Object> map = list.get(i);

				// 遍历map
				for (String key : map.keySet()) {

					// 列名称
					if (key.equals("COLUMNNAME")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get(key).toString());// 列名称，首字母小写，去下划线
						oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线
						oMap.put("UpUmnName", reStr);
					}

					// 注释
					if (key.equals("COLUMNCOMMENT")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
						oMap.put("columnComment", map.get(key) == null ? reStr : map.get(key));// 注释
					}

					// 设置列长度,既maxlength
					if (key.equals("COLUMNTYPE")) {
						String columnType = map.get(key).toString();

						if (columnType.contains("varchar") || columnType.contains("char")) {
							// 设置列长度,既maxlength
							if (map.get("DATALENGTH") != null) {
								oMap.put("maxlength", (Integer.parseInt(map.get("DATALENGTH").toString())));
							}

						} else {
							// num类型,长度为2
							oMap.put("maxlength", 2);
						}
					}

					// 是否必填
					if (map.get("ISNULLABLE") != null) {
						oMap.put("IsNullAble", map.get("ISNULLABLE").toString().equals("NO") ? "NO" : "YES");
					}

					// 列类型
					if (key.equals("COLUMNTYPE")) {
						oMap.put("IsDate", "NO");
						String columnType = map.get("COLUMNTYPE").toString();// 列类型
						if (columnType.contains("timestamp") || columnType.contains("date") || columnType.contains("datetime")) {
							oMap.put("IsDate", "YES");
							Map<String, Object> dateMap = new HashMap<String, Object>();
							String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
							dateMap.put("id", reStr);//ID
							datesList.add(dateMap);
						}
					}
				}
				clList.add(oMap);// 添加到集合
			}
		}

		if (clList != null && clList.size() > 0) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("cloums", clList);// 属性
			data.put("classNameToL", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// JavaBean名称-第一个字母小写
			data.put("functionComment", templateParams.getFunctionComment());// 功能说明
			data.put("datesList", datesList);// 时间空间集合
			data.put("action", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// action名称
			//data.put("filePath", filePath);// Id名称，例如sys_menuInf_MenuInfList
			data.put("listPagePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "List");// search页面的listPagePath属性
			//TemplateHelp.creatTemplate(data, ftlName, fileName, request);
			String fileName = templateParams.getClassPath() + "/src/main/webapp/WEB-INF/views/" + templateParams.getMouldName()+"/"+ PbUtils.fristStrToLowerCase(templateParams.getClassName()) + "Form.jsp";
			TemplateHelp.creatTemplate(data, "/Form.ftl", fileName, request);
		}
	}
	
	/**
	 * 生成List.js 代码
	 * 
	 * @param templateParams
	 * @throws TemplateException
	 * @throws IOException
	 */
	public void createListJs(TemplateParams templateParams, HttpServletRequest request) throws IOException, TemplateException {
		List<Map> list = getListMap(templateParams.getTableName());
		List<Map<String, Object>> clList = new ArrayList<Map<String, Object>>();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				// 遍历list
				Map<String, Object> map = list.get(i);

				// 遍历map
				for (String key : map.keySet()) {

					// 列名称
					if (key.equals("COLUMNNAME")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get(key).toString());// 列名称，首字母小写，去下划线
						// if (up) {
						// oMap.put("columnName",
						// PbUtils.fristStrToUpperCase(reStr));// 列名称，首字母大写，去下划线
						// } else if (listIsLower) {
						// oMap.put("columnName",
						// PbUtils.fristAndSecondStrToLowerCase(reStr));//
						// 列名称，首字母小写,第二个字母小写，去下划线
						// } else {
						// oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线
						// }
						oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线
						oMap.put("UpUmnName", PbUtils.fristStrToUpperCase(reStr));// 列名称，首字母大写，去下划线
					}

					// 注释
					if (key.equals("COLUMNCOMMENT")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
						oMap.put("columnComment", map.get(key) == null ? reStr : map.get(key));// 注释
					}

				}
				clList.add(oMap);// 添加到集合
			}
		}

		if (clList != null && clList.size() > 0) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("classNameToL", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// JavaBean名称-第一个字母小写
			data.put("cloums", clList);// 属性
			data.put("functionComment", templateParams.getFunctionComment());// 功能说明
			//data.put("filePath", filePath);// 文件路径,定义在方法和Id前，例如sys_menuInf_MenuInfList
			data.put("action", PbUtils.fristStrToLowerCase(templateParams.getClassName()));// action名称
			data.put("Action", PbUtils.fristStrToUpperCase(templateParams.getClassName()));// 第一个字母大写
			data.put("pkColumn", templateParams.getPkColumn());// 主键字段
			data.put("sortColumn", templateParams.getSortColumn());// 排序字段
			data.put("pkColumnDel", PbUtils.fristStrToLowerCase(PbUtils.strRelplacetoLowerCase(templateParams.getPkColumn())));// 主键字段
			data.put("pkColumn_f", PbUtils.strRelplacetoLowerCase(templateParams.getSortColumn()));// 主键字段
			data.put("searchFilePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Search");// List页面的searchFilePath属性
			data.put("addFilePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Add");// List页面的addFilePath属性
			data.put("editFilePath", "app_" + templateParams.getClassName() + "_" + templateParams.getClassName() + "Edit");// List页面的editFilePath属性
			String fileName = templateParams.getClassPath() + "/src/main/webapp/res/js/cool-js/" + PbUtils.fristStrToLowerCase(templateParams.getClassName()) + "List.js";
			TemplateHelp.creatTemplate(data, "/Js.ftl", fileName, request);
		}

	}
	
	/**
	 * 生成xml代码
	 * 
	 * @param templateParams
	 * @throws TemplateException
	 * @throws IOException
	 */
	private void createXml(TemplateParams templateParams, HttpServletRequest request) throws Exception {
		List<Map> list = getListMap(templateParams.getTableName());
		List<Map<String, Object>> clList = new ArrayList<Map<String, Object>>();
		// List<Map> isNullList =
		// getIsNullAble(templateParams.getTableName().toUpperCase());

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> oMap = new HashMap<String, Object>();
				// 遍历list
				Map<String, Object> map = list.get(i);

				// 遍历map
				for (String key : map.keySet()) {
					// 列名称
					if (key.equals("COLUMNNAME")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get(key).toString());// 列名称，首字母小写，去下划线
						oMap.put("columnNameL", map.get(key).toString());// 列名称，首字母小写，去下划线
						oMap.put("columnName", reStr);// 列名称，首字母小写，去下划线

						// 自动判断大小写
						if (map.get(key).toString().substring(1, 2).equals("_")) {
							oMap.put("UpUmnName", reStr);// 列名称，首字母小写，去下划线
						} else {
							oMap.put("UpUmnName", PbUtils.fristStrToUpperCase(reStr));// 列名称，首字母大写，去下划线
						}

					}

					// 注释
					if (key.equals("COLUMNCOMMENT")) {
						String reStr = PbUtils.strRelplacetoLowerCase(map.get("COLUMNNAME").toString());// 列名称，首字母小写，去下划线
						oMap.put("columnComment", map.get(key) == null ? reStr : map.get(key));// 注释
					}

					// 列类型
					if (key.equals("COLUMNTYPE")) {
						String columnType = map.get("COLUMNTYPE").toString();// 列类型
						String columnName = map.get("COLUMNNAME").toString();// 列名称
						String IsNullAble = map.get("ISNULLABLE").toString();// 是否为空

						
						String dataLength="10";
						if (map.containsKey("DATALENGTH") && map.get("DATALENGTH") !=null) {
							 dataLength = map.get("DATALENGTH").toString();// 长度
						}
						String cloums_top = "";

						// 判断是否为主键
						if ((columnName.toUpperCase()).equals(templateParams.getPkColumn().toUpperCase())) {
							cloums_top += "@Id ";
						}
						if (columnType.contains("timestamp")) {
							oMap.put("cloumsType", "Timestamp");
						}
						if (columnType.contains("long") || columnType.contains("interval") || columnType.contains("blob") || columnType.contains("varchar") || columnType.contains("char")) {
							oMap.put("cloumsType", "String");
						}
						if (columnType.contains("decimal") || columnType.contains("float") || columnType.contains("double") || columnType.contains("integer") || columnType.contains("longtext")) {
							oMap.put("cloumsType", "Long");
						}
						if (columnType.contains("date") || columnType.contains("datetime")) {
							oMap.put("cloumsType", "Date");
							cloums_top += " @Temporal(TemporalType.TIMESTAMP) ";
						}
						if (columnType.contains("int")|| columnType.contains("INTEGER")) {
							oMap.put("cloumsType", "Integer");
						}


						cloums_top += "@Column( name = \"" + columnName + "\" ";
						if (columnName.equals(templateParams.getPkColumn().toUpperCase())) {
							cloums_top += ", unique = true ";
						}
						
						if (IsNullAble.equals("NO")) {
							cloums_top += " ,nullable = false ";
						}

						// 判断是否唯一性约束
						// if (isNullList.contains(key.equals("COLUMNTYPE"))) {
						// cloums_top += " , unique = true ";
						// }

						// 长度
						if (!columnType.contains("decimal")) {
							cloums_top += " , length = " + dataLength + " ";
						} else {
							// NUMBER 类型才有
							cloums_top += " , precision = " + dataLength + ", scale = 0 ";
						}

						cloums_top += " )";

						oMap.put("cloums_top", cloums_top);

					}

				}
				clList.add(oMap);// 添加到集合
			}
		}

		if (clList != null && clList.size() > 0) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("functionComment", templateParams.getFunctionComment());// 功能说明
			data.put("className", templateParams.getClassName());// 类名称
			data.put("mouldName", templateParams.getMouldName());// 子模块名称
			data.put("tableName", templateParams.getTableName());// 表名称
			data.put("cloums", clList);// 属性
			data.put("date", PbUtils.getCurrentDate());// 日期
			// 作者
			if (!PbUtils.isEmpty(templateParams.getAuthor())) {
				data.put("author", templateParams.getAuthor());
			}
			data.put("Table_NAME", templateParams.getTableName());// 表名称
			// 模板名称
			String ftlName = "/Xml.ftl";

			// 生成文件的路径和名称
			String fileName = templateParams.getClassPath() + "/src/main/java/com/mapper/" + templateParams.getMouldName()+"/"+PbUtils.fristStrToLowerCase(templateParams.getClassName()) + "Mapper.xml";
			TemplateHelp.creatTemplate(data, ftlName, fileName, request);
		}
	}

	
}
