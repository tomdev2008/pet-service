<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/report/channelDictSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">

		<input type="hidden" name="id" value="${myForm.id }" />
		
		<div class="pageFormContent" layoutH="58">

			<div class="unit">
				<label>渠道名称：</label>
				<input type="text" name="alias" size="30" value="${myForm.alias }" class="required" />
			</div>	
			
			<div class="unit">
				<label>渠道编码：</label>
				<input type="text" name="code" size="30" value="${myForm.code }" class="required"/>
			</div>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
