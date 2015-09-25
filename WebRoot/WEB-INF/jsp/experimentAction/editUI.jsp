<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>实验预约设置</title>
	<%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<script type="text/javascript">
	$(function () {
		// alert() ;
		$('.expTime').each(function(){
			var old = $(this).text() ;
			var week = old.split('w')[0] ;
			var day = old.split('w')[1] ;
			var num = old.split('w')[2] ;
			var result = '第'+week+'周,周 '+day+' ,'+(num*2-1)+'、'+(num*2)+'节' ;
			 $(this).text(result) ;
		});
	});
</script>
<body>

<!-- 标题显示 --> 
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 实验预约信息
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id=MainArea>

  <s:form action="exp_edit">
        <s:hidden name="id"></s:hidden>
        
        <div class="ItemBlock_Title1"><!-- 信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 部门信息 </DIV>  -->
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                   
                     <tr><td>实验项目名称</td>
                      <td>
                        	<s:textfield  name="project.name"  readonly="true" cssClass="InputStyle"/> *</td>
                    </tr>
                    <tr><td>实验课程</td>
                      <td>
                        <s:textfield  name="project.curriculum.name"  readonly="true" cssClass="InputStyle"/> *</td>
                    </tr>
                    <tr><td>实验教师</td>
                      <td>
                        	<s:textfield  name="user.name"  readonly="true" cssClass="InputStyle"/> *</td>
                    </tr>
                      <tr><td>实验时间</td>
                    <td><s:textfield  name="expDiaplayTime"  readonly="true" cssClass="InputStyle"/> *</td>
                    </tr>
                    <tr><td>实验学期</td>
                    <td><s:textfield  name="expTerm"  readonly="true" cssClass="InputStyle"/> *</td>
                    </tr>
                     <tr><td>实验机房</td>
                        <td><s:select name="labId" cssClass="SelectStyle"
                        		list="#labList" listKey="id" listValue="name"
                        		headerKey="0" headerValue="==请选择机房=="
                        	/>
                        </td>
                    </tr>
					 <tr><td>实验班级</td>
                       <td>
                        	<s:textfield  name="dept.name" readonly="true" cssClass="InputStyle"/> *</td>
                    </tr>
                    <tr><td>实验项目说明</td>
                        <td><s:textarea name="description" cssClass="TextareaStyle"></s:textarea></td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input type="image" src="${pageContext.request.contextPath}/style/images/save.png"/>
            <a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/style/images/goBack.png"/></a>
        </div>
    </s:form>
</div>

<div class="Description">
	说明：<br />
	1，查看自己的课表。<br />
	2，如有特殊情况，可与其他教师进行调课。<br />
	3，调整实验，直接删除后重新添加。
</div>

</body>
</html>
