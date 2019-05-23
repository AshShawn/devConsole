package com.hll.test.common.page;

public class Page{
    
    public static int pageSize = 20;
    
	public static final String KEY = "page";
	private int showCount = 10; //每页显示记录数
	private int showTag = 5;  //分页数字标签显示数量
	private int totalPage;		//总页数
	private int totalResult;	//总记录数
	private int currentPage;	//当前页
	private int currentResult;	//当前记录起始索引
	private boolean entityOrField;	//true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private String pageStr;		//最终页面显示的底部翻页导航，详细见：getPageStr();
	private String formId;
	
	public Page(int currentPage) {
        this.currentPage = currentPage;
        setShowCount(pageSize);
    }
    public Page() {
        setShowCount(pageSize);
    }
    public String getFormId() {
		return formId==null?"":formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	public int getShowTag() {
		return showTag;
	}
	public void setShowTag(int showTag) {
		this.showTag = showTag;
	}
	
	public int getTotalPage() {
		if(totalResult%showCount==0)
			totalPage = totalResult/showCount;
		else
			totalPage = totalResult/showCount+1;
		return totalPage;
	}

	public int getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	public int getCurrentPage() {
		if(currentPage<=0)
			currentPage = 1;
		if(currentPage>getTotalPage())
			currentPage = getTotalPage();
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getPageStr() {
		StringBuffer sb = new StringBuffer();
		if(totalResult>0){
			sb.append("	<ul id=\"page_str\">\n");
		
			int startTag = this.getCurrentPage()-this.showTag/2;
			int endTag = this.getCurrentPage()+this.showTag/2;
			
			while(startTag<1 && endTag<this.getTotalPage()){
				endTag++;
				startTag++;
			}
			while(endTag>this.getTotalPage() && startTag > 1){
				endTag--;
				startTag--;
			}
			if(startTag<1){
				startTag = 1;
			}
			if(endTag>this.getTotalPage()){
				endTag = this.getTotalPage();
			}
			
			if(currentPage==1){
				sb.append("	<li class=\"pageinfo\">上页</li>\n");
			}else{
				sb.append("	<li><a href=\"javascript:void(0);\" onclick=\"nextPage("+(currentPage-1)+")\">上页</a></li>\n");
				if(startTag > 1){	
					sb.append("	<li><a href=\"javascript:void(0);\" onclick=\"nextPage(1)\">1</a></li>\n");
					sb.append("<li>...</li>\n");
				}
			}
			
			
			for(int i=startTag; i<=totalPage && i<=endTag; i++){
				if(currentPage==i)
					sb.append("<li class=\"current\">"+i+"</li>\n");
				else
					sb.append("	<li><a href=\"javascript:void(0);\" onclick=\"nextPage("+i+")\">"+i+"</a></li>\n");
			}
			if(currentPage==totalPage){
				sb.append("	<li class=\"pageinfo\">下页</li>\n");
			}else{
				if(endTag < totalPage){
					sb.append("<li>...</li>\n");
					sb.append("	<li><a href=\"javascript:void(0);\" onclick=\"nextPage("+totalPage+")\">"+totalPage+"</a></li>\n");
				}
				sb.append("	<li><a href=\"javascript:void(0);\" onclick=\"nextPage("+(currentPage+1)+")\">下页</a></li>\n");
			}
//			sb.append("	<li class=\"pageinfo\">第"+currentPage+"页</li>\n");
//			sb.append("	<li class=\"pageinfo\">共"+totalPage+"页</li>\n");
			sb.append("</ul>\n");
			sb.append("<script type=\"text/javascript\">\n");
			sb.append("function nextPage(page){ \n");
			sb.append("var _form = document.getElementById(\""+formId+"\");\n");
			sb.append("	if(_form != 'undefined' && _form != null && _form != ''){\n");
			sb.append("		var url = _form.action;\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		_form.action = url+page;\n");
			sb.append("		_form.submit();\n");
			sb.append("     return false; \n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location.href;\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		document.location = url + page;\n");
			sb.append("	}\n");
			sb.append("}\n");
			sb.append("</script>\n");
		}
		pageStr = sb.toString();
		return pageStr;
	}

	public int getShowCount() {
		return showCount;
	}
	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}
	public int getCurrentResult() {
		currentResult = (getCurrentPage()-1)*getShowCount();
		if(currentResult<0)
			currentResult = 0;
		return currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}
	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

}
	
