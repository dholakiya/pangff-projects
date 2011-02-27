package com.pangff;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class Shakespeare {
	public static Map<Integer,File>  articleMap = new HashMap<Integer,File>();
	
	public static File currentFilePath =null;
	
	public static int buttonId = -1;
	
	public static File currentReadFile=null;
	
	public static File readHistoryFile=null;
	
	public static  String readmeContent = "使用说明：\n"
		+"1、点击“选择浏览目录”按钮，打开文件夹浏览对话框；\n"
		+"2、点击要选择的目录（注意，若有该目录有下级目录点击会进入该目录列表）点击确定，之后该目录下的txt文件名会自动加载到     左侧列表，点击文件名称，右侧显示其中内容；\n"
		+"3、在进入含有子目录的目录下时，点击返回按钮返回上级目录\n";
  
}
