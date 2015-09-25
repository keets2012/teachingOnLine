package com.njit.view.action;


import java.io.BufferedReader;
import java.io.File;
import com.njit.util.DocConverter;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.management.Query;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njit.base.BaseAction;
import com.njit.domain.Course;
import com.njit.domain.Curriculum;
import com.njit.domain.Department;
import com.njit.domain.Experiment;
import com.njit.domain.Project;
import com.njit.domain.Report;
import com.njit.domain.Share;
import com.njit.domain.Task;
import com.njit.domain.User;
import com.njit.util.QueryHelper;
import com.njit.util.ResponseUtil;
import com.njit.util.qqq;
import com.njit.util.gson.DeptGsonAdapter;
import com.njit.util.gson.ExperimentGsonAdapter;
import com.njit.util.gson.ProjectGsonAdapter;
import com.njit.util.gson.TaskGsonAdapter;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ReportAction extends BaseAction<Report>{
	private static Gson gson = new GsonBuilder()
	.registerTypeAdapter(Experiment.class, new ExperimentGsonAdapter())
	.registerTypeAdapter(Project.class, new ProjectGsonAdapter())
	.registerTypeAdapter(Task.class, new TaskGsonAdapter())
	.registerTypeAdapter(Department.class, new DeptGsonAdapter())
	.create() ;	
	public static final String UPLOAD_DIRECTORY = "upload";
	//protected HttpServletResponse response;
	private static final String ZIP_NAME = "batch_download_archives.zip";
	private Long curriculumId;
	private Long taskId;
	private Long courseId;
	private Long departmentId;
	private Long reportId;
	 /** 代表上传的文件内容的对象 */  
    private File upload;  
    
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Report.class);
    /** Struts2约定的代表上传的文件的名 */  
    private String uploadFileName;  
    /** Struts2约定的代表上传文件的内容类型(MIME) */  
    private String uploadContentType;  
    private File fileUrl; //存放文件的文件名
    
	private static final long serialVersionUID = 1232131L;
	private String fileName;//此属性为接受前台界面传过来的属性名
	private int downloadModel;
	private String[] downloadFile;
	/**
	 * Single file download model
	 */
	public static final int SINGLE_MODEL = 0;

	/**
	 * Multiple files download model
	 */
	public static final int MULTIPLE_MODEL = 1;
	
	/** 列表 */
	public String list() throws Exception {
		List<Curriculum> curriculumList=curriculumService.findAll();
		ActionContext.getContext().put("curriculumList", curriculumList);
		new QueryHelper(Report.class, "r")
		.addCondition("r.user.id=? ", getCurrentUser().getId())
		.preparePageBean(reportService, pageNum, pageSize);
		return "list";
	}
	/** 列表 */
	public String listPart() throws Exception {
		System.out.println(curriculumId+"===========");
		List<Curriculum> curriculumList=curriculumService.findAll();
		ActionContext.getContext().put("curriculumList", curriculumList);
		if(curriculumId==0)
		{
			new QueryHelper(Report.class, "r")
			.addCondition("r.user.id=? ", getCurrentUser().getId())
			.preparePageBean(reportService, pageNum, pageSize);
		}else{
			System.out.println(curriculumId+"===========");
			new QueryHelper(Report.class, "r")
			.addCondition("r.user.id=? ", getCurrentUser().getId())
			.addCondition(true, "r.task.course.curriculum.id=? ", curriculumId)
			.preparePageBean(reportService, pageNum, pageSize);
		}
		
		return "list";
	}
	
	/** 报告统计 */
	public String count() throws Exception {
	
		List<Course> courseList=cs.findByUser();
		List<Department> departmentList=ds.findAllMyClass();
		ActionContext.getContext().put("courseList", courseList);
		ActionContext.getContext().put("departmentList", departmentList);
		//Long userId=getCurrentUser().getId();
		new QueryHelper(Report.class, "r")
		.addCondition("r.task.user.id=? ",getCurrentUser().getId())
		.preparePageBean(reportService, pageNum, pageSize);
		return "count";
	}

	/** 报告统计 */
	public String fileRead() throws Exception {

		// 读取request文件
		//fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
		String filename = fileName.substring(fileName.lastIndexOf("\\") + 1,
				fileName.length());
		String converfilename = fileName.replaceAll("\\\\", "/");
		System.out.println("======="+converfilename);
		com.njit.util.DocConverter d=new com.njit.util.DocConverter(converfilename);
        d.conver();
        String swf=d.getswfPath();
       // swf=new String(swf.getBytes("ISO-8859-1"), "UTF-8");
         System.out.println("000000"+d.getswfPath());
         Report report=reportService.getById(reportId);
        ActionContext.getContext().getValueStack().push(report);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute("swfpath", swf);
		return "view";
	}
	
	/** 报告统计 */
	public String fileReadNext() throws Exception {

		// 读取request文件
		//fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
		String filename = fileName.substring(fileName.lastIndexOf("\\") + 1,
				fileName.length());
		
		String converfilename = fileName.replaceAll("\\\\", "/");
		System.out.println("======="+converfilename);
		com.njit.util.DocConverter d=new com.njit.util.DocConverter(converfilename);
        d.conver();
        String swf=d.getswfPath();
       // swf=new String(swf.getBytes("ISO-8859-1"), "UTF-8");
         System.out.println("000000"+d.getswfPath());
         Report report=reportService.getById(reportId);
        ActionContext.getContext().getValueStack().push(report);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute("swfpath", swf);
		return "view";
	}
	//得分
	public String mark() throws Exception {
		Report report=reportService.getById(model.getId());
		report.setScore(model.getScore());		
		reportService.update(report);
		
		List<Course> courseList=cs.findByUser();
		List<Department> departmentList=ds.findAllMyClass();
		ActionContext.getContext().put("courseList", courseList);
		ActionContext.getContext().put("departmentList", departmentList);
		//Long userId=getCurrentUser().getId();
		new QueryHelper(Report.class, "r")
		.addCondition("r.task.user.id=? ",getCurrentUser().getId())
		.preparePageBean(reportService, pageNum, pageSize);
		return "count";
	}
	/** 列表 */
	public String myCount() throws Exception {
		List<Course> courseList=cs.findByUser();
		ActionContext.getContext().put("courseList", courseList);
		List<Task> taskList=taskService.findByUser();
		List<Department> departmentList=ds.findAllMyClass();

		ActionContext.getContext().put("taskList", taskList);
		ActionContext.getContext().put("departmentList", departmentList);
		if(courseId!=0&&taskId!=0&&departmentId!=0)
		{
			new QueryHelper(Report.class, "r")
			.addCondition("r.task.course.id=? ", courseId)
			.addCondition(true,"r.task.id=? ", taskId)
			.addCondition(true,"r.user.department.id=? ", departmentId)
			.addCondition(true,"r.task.user.id=? ",getCurrentUser().getId())
			.preparePageBean(reportService, pageNum, pageSize);
		}
		if(courseId==0&&taskId!=0&&departmentId!=0)
		{
			new QueryHelper(Report.class, "r")
			.addCondition("r.task.course.id=? ", courseId)
			.addCondition(true,"r.task.id=? ", taskId)
			.addCondition(true,"r.user.department.id=? ", departmentId)
			.addCondition(true,"r.task.user.id=? ",getCurrentUser().getId())
			.preparePageBean(reportService, pageNum, pageSize);
		}
		if(courseId==0&&taskId==0&&departmentId!=0)
		{
			new QueryHelper(Report.class, "r")
			.addCondition("r.task.user.id=? ",getCurrentUser().getId())
			.addCondition(true,"r.user.department.id=? ", departmentId)
			.preparePageBean(reportService, pageNum, pageSize);
		}
		if(courseId==0&&taskId==0&&departmentId==0)
		{
			new QueryHelper(Report.class, "r")
			.addCondition("r.task.user.id=? ",getCurrentUser().getId())
			.preparePageBean(reportService, pageNum, pageSize);
		}
		if(courseId!=0&&taskId==0&&departmentId==0)
		{
			new QueryHelper(Report.class, "r")
			.addCondition("r.task.course.id=? ", courseId)
			.addCondition(true,"r.task.user.id=? ",getCurrentUser().getId())
			.preparePageBean(reportService, pageNum, pageSize);
		}
		if(courseId!=0&&taskId!=0&&departmentId==0)
		{
			new QueryHelper(Report.class, "r")
			.addCondition("r.task.course.id=? ", courseId)
			.addCondition(true,"r.task.user.id=? ",getCurrentUser().getId())
			.addCondition(true,"r.task.id=? ", taskId)
			.preparePageBean(reportService, pageNum, pageSize);
		}
		if(courseId!=0&&taskId==0&&departmentId!=0)
		{
			new QueryHelper(Report.class, "r")
			.addCondition("r.task.course.id=? ", courseId)
			.addCondition(true,"r.task.user.id=? ",getCurrentUser().getId())
			.addCondition(true,"r.task.id=? ", taskId)
			.addCondition(true,"r.user.department.id=? ", departmentId)
			.preparePageBean(reportService, pageNum, pageSize);
		}
		if(courseId==0&&taskId!=0&&departmentId==0)
		{
			new QueryHelper(Report.class, "r")
			.addCondition("r.task.id=? ", taskId)
			.addCondition(true,"r.task.user.id=? ",getCurrentUser().getId())
			.preparePageBean(reportService, pageNum, pageSize);
		}
		return "count";
	}
	
	/** 报告名单统计 */
	public String detailCountUI() throws Exception {
	
		List<Course> courseList=cs.findByUser();
		//List<Department> departmentList=ds.findAllMyClass();
		ActionContext.getContext().put("courseList", courseList);
	//	ActionContext.getContext().put("departmentList", departmentList);
		
		return "detaileCount";
	}
	
	/** 报告名单统计 */
	public String detailCount() throws Exception {
	
		System.out.println("---------"+courseId+"  "+departmentId+"   "+taskId);
		List<Course> courseList=cs.findByUser();
		ActionContext.getContext().put("courseList", courseList);
		//获取班级
		Department department=ds.getById(departmentId);
		ActionContext.getContext().put("deptName",department.getName() );
		//获取任务名称
		Task task=taskService.getById(taskId);
		ActionContext.getContext().put("taskName", task.getName());
		//获取课程名称
		Course course=cs.getById(courseId);
		ActionContext.getContext().put("courseName", course.getCurriculum().getName());
		
		
		int deptTotal=us.queryTotal(departmentId);
		ActionContext.getContext().put("deptTotal", deptTotal);
		int subTotal=reportService.querySub(departmentId,taskId);
		ActionContext.getContext().put("subTotal", subTotal);
		int unSubTotal=deptTotal-subTotal;
		ActionContext.getContext().put("unSubTotal", unSubTotal);
		int onTimeSub=reportService.queryOnTimeSub(departmentId,taskId);
		ActionContext.getContext().put("onTimeSub", onTimeSub);
		List<User> subList=reportService.querySubUser(departmentId,taskId);
		
		List<User> unSubList=us.findClassStudent(departmentId,subList);		
		ActionContext.getContext().put("unSubList", unSubList);
		List<Report> subReportList=reportService.findSub(departmentId,taskId);
		
		List<User> outDateSubList=reportService.queryOutDate(subReportList);
		ActionContext.getContext().put("outDateSubList", outDateSubList);
		System.out.println("---------++++++++"+deptTotal+"  "+subTotal+"   "+onTimeSub);
		return "detaileCountShow";
	}
	
	
	public String queryDepts() throws Exception {
		System.out.println("==========++++++++"+courseId);
		courseId=taskService.getById(taskId).getCourse().getId();
		List<Department> departmentList=cs.findByCourse(courseId);

		System.out.println("=========="+departmentList.size());
		String json = gson.toJson(departmentList) ;
		ResponseUtil.write(ServletActionContext.getResponse(), json) ;
		return null;
	}
	
	
	public String queryTask() throws Exception {
		List<Task> taskList=taskService.findByCourse(courseId);
		//System.out.println("=========="+taskList.size());
		String json = gson.toJson(taskList) ;
		ResponseUtil.write(ServletActionContext.getResponse(), json) ;
		return null;
	}
	
	/** 列表 */
	public String myList() throws Exception {
		List<Report> reportList=reportService.findByUser();
		List<Task> allTaskList=taskService.findByStudent();
		List<Task> taskList=taskService.findUnhandle(reportList,allTaskList);
		ActionContext.getContext().put("taskList", taskList);
		
		return "myList";
	}

	/** 删除 */
	public String delete() throws Exception {
		Report report=reportService.getById(model.getId());
		String delpath = report.getFilePath();
		
		System.out.println(model.getId());
		System.out.println("==============>>>>>>>"+delpath);
		try {
			File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		reportService.delete(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		Task task=taskService.getById(taskId);
		ActionContext.getContext().put("courseName", task.getCourse().getCurriculum().getName());
		ActionContext.getContext().put("userName", task.getUser().getName());
		ActionContext.getContext().put("taskName", task.getName());
		ActionContext.getContext().put("thisId", taskId);
		System.out.println("================="+taskId);
		
		return "saveUI";
	}
	public String getRealPath(String pathName) {
		return ServletActionContext.getServletContext().getRealPath(pathName) + "/";
	}
	/** 添加 */
	public String add() throws Exception {
		
		String filePath = ServletActionContext.getServletContext().getRealPath("/uploads/"+getCurrentUser().getLoginName());  		
		if(!new File(filePath).isDirectory()){
			//查看该路径存在与否，遇过不存在，创建路径
			new File(filePath).mkdirs();
		}
		
		System.out.println("文件的名：" + uploadFileName);  
	    System.out.println("===" + upload.getName());  
	    System.out.println("文件的内容类型：" + uploadContentType);  
	        
		fileUrl = new File(filePath+"/"+uploadFileName);
		FileUtils.copyFile(upload, fileUrl);//将文件复制到服务器上指定的路径      
		
		model.setUser(getCurrentUser());
		model.setDescription(model.getDescription());
		model.setName(model.getName());
		model.setFilePath(fileUrl.getAbsolutePath());
		model.setTask(taskService.getById(taskId));
		model.setHandleDate(new Date());
		reportService.save(model);
		
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		Report report=reportService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(report);
		
		return "editUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		Report report=reportService.getById(model.getId());
		report.setName(model.getName());
		report.setDescription(model.getDescription());
		
		reportService.update(report);
		
		return "toList";
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public File getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(File fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileName() {
		return fileName;
	}
    public void setFileName(String fileName) throws Exception {

		/*
		 * 这里面转码的，原因是，前台传过来的形式是ISO-8859格式
		 * ，到后台我们还要转成UTF_8，避免中文乱码等原因
		 */
	 
		this.fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
	}
	
    
/**
 * 此方法对应struts.xml文件中的<param name="inputName">inputStream</param>属性
 * @return
 * @throws Exception
 */
	public InputStream getInputStream() throws Exception {
		/*
		 * 截取前台传过来的超链接地址后的文件名，作为显示用
		 */
		if (downloadModel == MULTIPLE_MODEL) {
			//InputStream fis=null;
			System.out.println("++++++++++++===="+downloadFile.length);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ java.net.URLEncoder.encode(ZIP_NAME, "UTF-8"));
			ZipOutputStream out = new ZipOutputStream(
					response.getOutputStream(), Charset.forName("utf-8"));// Need
																				// JDK_7
			for (int i = 0, length = downloadFile.length; i < length; i++) {
				downloadFile[i] = new String(
						downloadFile[i].getBytes("ISO8859-1"), "UTF-8");
				String downPath=downloadFile[i].substring(
						downloadFile[i].lastIndexOf("\\") + 1, downloadFile[i].length());
				//System.out.println("============="+downPath);
				File download = new File(downloadFile[i]);
				InputStream fis = new FileInputStream(download);
				System.out.println("============="+download.getName());
				ZipEntry entry = new ZipEntry(download.getName());
				out.putNextEntry(entry);
				io(fis, out, 10240);
				out.closeEntry();
				fis.close();
			}

			out.flush();
			out.close();
			LOGGER.info(ZIP_NAME + " create success!");
			return null;
		}else 
		{
			//System.out.println("1===========-------------------");
		String filename = fileName.substring(
				fileName.lastIndexOf("\\") + 1, fileName.length());
		/*
		 * 此处为点击下载后，提示框里显示文件的名字，正好是上面接截取的名字
		 */
		//System.out.println("-------------"+filename);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ java.net.URLEncoder.encode(filename, "UTF-8"));
		/*
		 * 将文件地址转换成文件，然后转换成流,将流返回
		 */
		File file = new File(fileName);
		InputStream is = new FileInputStream(file);
		return is;
		}
	}
	
	
	public static boolean io(InputStream input, OutputStream output,
			int bufferSize) {
		byte[] buf = new byte[bufferSize > 0 ? bufferSize : 2048];
		int len = 0;
		try {
			while ((len = input.read(buf)) != -1) {
				output.write(buf, 0, len);
			}
			output.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 此方法是前台点击下载后的响应方法
	 * @return
	 * @throws  Exception
	 */
	public String filedownload() throws Exception {
		downloadModel=SINGLE_MODEL;
		return "success";
	}
	public String multiFileDownload() throws Exception {
		downloadModel=MULTIPLE_MODEL;
		return "success";
	}
	
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long getCurriculumId() {
		return curriculumId;
	}
	public void setCurriculumId(Long curriculumId) {
		this.curriculumId = curriculumId;
	}
	public int getDownloadModel() {
		return downloadModel;
	}
	public void setDownloadModel(int downloadModel) {
		this.downloadModel = downloadModel;
	}
	public String[] getDownloadFile() {
		return downloadFile;
	}
	public void setDownloadFile(String[] downloadFile) {
		this.downloadFile = downloadFile;
	}
	
	
}
