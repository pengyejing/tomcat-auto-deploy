package com.lsj.tad.client;

import java.io.File;

import com.lsj.http.util.HttpMimeParams;
import com.lsj.http.util.HttpParams;
import com.lsj.tad.Conf;
import com.lsj.util.FileUtil;
import com.lsj.util.ZipUtil;

public class App {
	
	public static void main(String[] args){
		
		//1).初始化配置文件
		//Conf conf = new Conf(new String[]{"-proot123", "-atrans"});
		Conf conf = new Conf(args);
		ZipUtil zipUtil = new ZipUtil();
		
		//2).压缩文件
		System.out.println("compressing...");
		try{
			zipUtil.Compress(conf.getWebappPath(), "webapp.zip");
			zipUtil.Compress(conf.getWorkPath(), "work.zip");
		}catch(Exception e){
			//错误，退出
			System.out.println("Interrupt, compress error...");
			System.exit(1);
		}
		System.out.println("compress finish");
		
		//3).部署(添加MIME，发送并等待响应)
		System.out.println("deploying...");
		HttpParams params = new HttpMimeParams()
				.put("webapp", new File("webapp.zip"))
				.put("work", new File("work.zip"))
				.put("appname", conf.getAppName())
				.put("password", conf.getPassword());
		try {
			String strResponse = params.Send(conf.getRemote());
			System.out.println(strResponse);
		} catch (Exception e) {
			//错误
			System.out.println("Interrupt, deploy error...");
		}finally{
			FileUtil.DeleteFile(new File("webapp.zip"));
			FileUtil.DeleteFile(new File("work.zip"));
		}
	}
}
