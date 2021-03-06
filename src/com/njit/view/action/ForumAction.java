package com.njit.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.njit.base.BaseAction;
import com.njit.domain.Forum;
import com.njit.domain.PageBean;
import com.njit.domain.Topic;
import com.njit.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum>{

	private	 int viewType=0;
	private int orderBy=0;
	private boolean asc=false;
	public String list() throws Exception {
		List<Forum> forumList=fs.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	
	
	/** 显示单个版块（主题列表） */
	public String show() throws Exception {
		// 准备数据：forum
		Forum forum = fs.getById(model.getId());
		ActionContext.getContext().put("forum", forum);

		// // 准备数据：topicList
		// List<Topic> topicList = topicService.findByForum(forum);
		// ActionContext.getContext().put("topicList", topicList);

		// // 准备分页信息 v1
		// PageBean pageBean = topicService.getPageBeanByForum(pageNum, pageSize, forum);
		// ActionContext.getContext().getValueStack().push(pageBean);

		// // 准备分页信息 v2
		// String hql = "FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC";
		// List<Object> parameters = new ArrayList<Object>();
		// parameters.add(forum);
		// PageBean pageBean = replyService.getPageBean(pageNum, pageSize, hql, parameters);
		// ActionContext.getContext().getValueStack().push(pageBean);

		// // 准备分页信息 v3
		// String hql = "FROM Topic t WHERE t.forum=? ";
		// List<Object> parameters = new ArrayList<Object>();
		// parameters.add(forum);
		//
		// if (viewType == 1) { // 1 表示只看精华帖
		// hql += " AND t.type=? ";
		// parameters.add(Topic.TYPE_BEST);
		// }
		//
		// if (orderBy == 1) { // 1 表示只按最后更新时间排序
		// hql += " ORDER BY t.lastUpdateTime " + (asc ? "ASC" : "DESC");
		// } else if (orderBy == 2) { // 2 表示只按主题发表时间排序
		// hql += " ORDER BY t.postTime " + (asc ? "ASC" : "DESC");
		// } else if (orderBy == 3) { // 3 表示只按回复数量排序
		// hql += " ORDER BY t.replyCount " + (asc ? "ASC" : "DESC");
		// } else { // 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
		// hql += " ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC";
		// }
		// 
		// PageBean pageBean = replyService.getPageBean(pageNum, pageSize, hql, parameters);
		// ActionContext.getContext().getValueStack().push(pageBean);

		// 准备分页信息 ，最终版
		new QueryHelper(Topic.class, "t")//
				// 过滤条件
				.addCondition("t.forum=?", forum)//
				.addCondition((viewType == 1), "t.type=?", Topic.TYPE_BEST) // 1 表示只看精华帖
				// 排序条件
				.addOrderProperty((orderBy == 1), "t.lastUpdateTime", asc) // 1 表示只按最后更新时间排序
				.addOrderProperty((orderBy == 2), "t.postTime", asc) // 2 表示只按主题发表时间排序
				.addOrderProperty((orderBy == 3), "t.replyCount", asc) // 3 表示只按回复数量排序
				.addOrderProperty((orderBy == 0), "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)//
				.addOrderProperty((orderBy == 0), "t.lastUpdateTime", false) // 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
				.preparePageBean(ts, pageNum, pageSize);

		return "show";
	}


	public int getViewType() {
		return viewType;
	}


	public void setViewType(int viewType) {
		this.viewType = viewType;
	}


	public int getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(int orderType) {
		this.orderBy = orderType;
	}


	public boolean isAsc() {
		return asc;
	}


	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	
	
	
	
}
