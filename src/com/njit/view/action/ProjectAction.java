package com.njit.view.action;


import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.njit.base.BaseAction;
import com.njit.domain.Course;
import com.njit.domain.Curriculum;
import com.njit.domain.Project;
import com.njit.domain.Report;
import com.njit.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ProjectAction extends BaseAction<Project>{
	
	private Long curriculumId;
	/** 列表 */
	public String list() throws Exception {

		List<Curriculum> curriculumList=curriculumService.findAll();
		ActionContext.getContext().put("curriculumList", curriculumList);
		
		new QueryHelper(Project.class, "p")
		.preparePageBean(projectService, pageNum, pageSize);
		            
		return "list";
	}

	/** 列表 */
	public String myList() throws Exception {

		List<Curriculum> curriculumList = curriculumService.findAll();
		ActionContext.getContext().put("curriculumList", curriculumList);
		if (curriculumId == 0) {
			new QueryHelper(Project.class, "p").preparePageBean(projectService,
					pageNum, pageSize);
		} else {
			new QueryHelper(Project.class, "p")
			.addCondition(
					"p.curriculum.id=? ", curriculumId)
					
					.preparePageBean(
					projectService, pageNum, pageSize);
		}
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		projectService.delete(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		
		List<Curriculum> curriculumList=curriculumService.findAll();
		//ActionContext.getContext().getValueStack().push(getCurrentUser());
		ActionContext.getContext().put("curriculumList", curriculumList);
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		model.setCurriculum(curriculumService.getById(curriculumId));
		
		projectService.save(model);
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		Project project=projectService.getById(model.getId());
		//准备数据
		List<Curriculum> curriculumList=curriculumService.findAll();
		ActionContext.getContext().put("curriculumList", curriculumList);
		

		ActionContext.getContext().getValueStack().push(project);
		//回显
		if(project.getCurriculum()!=null)
		{
			curriculumId=project.getCurriculum().getId();
		}
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		Project project=projectService.getById(model.getId());
		project.setName(model.getName());
		project.setProjectNo(model.getProjectNo());
		project.setTotal(model.getTotal());
		project.setCurriculum(curriculumService.getById(curriculumId));
		project.setDescription(model.getDescription());
		projectService.update(project);
		return "toList";
	}

	public Long getCurriculumId() {
		return curriculumId;
	}

	public void setCurriculumId(Long curriculumId) {
		this.curriculumId = curriculumId;
	}

 
	
	
}
