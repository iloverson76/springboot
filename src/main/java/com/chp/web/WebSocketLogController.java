package com.chp.web;

import java.io.IOException;
import java.io.InputStream;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chp.modules.websocket.WebSocketLogThread;

import io.swagger.annotations.Api;
@RestController
@RequestMapping(value = "/tailLog")
@Api(value = "api", description = "websocket使用'tail-f'命令查看日志文件") // Swagger UI 对应api的标题描述
@ServerEndpoint("/tailLog")
public class WebSocketLogController {
	
	private Process process;
	private InputStream inputStream;
	private static final String COMMAND="tail -f /home/soft/tomcat-8.5.29/logs/catalina.out";

	/**
	 * 新的WebSocket请求开启
	 */
	@OnOpen
	public void onOpen(Session session) {
		try {
			// 执行tail -f命令
			process = Runtime.getRuntime().exec(COMMAND);
			inputStream = process.getInputStream();
			
			// 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
			WebSocketLogThread thread = new WebSocketLogThread(inputStream, session);
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * WebSocket请求关闭
	 */
	@OnClose
	public void onClose() {
		try {
			if(inputStream != null)
				inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(process != null)
			process.destroy();
	}
	
	@OnError
	public void onError(Throwable thr) {
		thr.printStackTrace();
	}
}