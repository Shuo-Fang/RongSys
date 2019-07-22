package com.ruoyi.broadserver.server.handle.impl;

import com.ruoyi.broadserver.global.GlobalInfo;
import com.ruoyi.broadserver.global.ProtocolsToClient;
import com.ruoyi.broadserver.server.MinaCastHandler;
import com.ruoyi.broadserver.server.handle.DefaultCommand;
import org.apache.mina.core.session.IoSession;
import com.ruoyi.broad.utils.bConvert;

//获取终端IP（对系统可读可写）并为流媒体心跳包
public class ClientHeart_IP extends DefaultCommand {

	public ClientHeart_IP(IoSession session, byte[] content) {
		super(session, content);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] execute() {
		// TODO Auto-generated method stub
		byte[] data = bConvert.subBytes(content, 5, 15);
		String info = new String(data);

		String command = save(info)?"1":"0";//保存信息
		
		datainfo = info;
		loggersession();//插入日志
		return returnBytes(ProtocolsToClient.IPCHANGE, command, "nothin");//暂时默认为无流媒体需求
	}
	@Override
	public boolean save(Object obj) {
		// TODO Auto-generated method stub
		session.setAttribute(MinaCastHandler.CLIENTINFO, obj);
		GlobalInfo.putClientToMap(session);
		return true;
	}

	@Override
	public Object get(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}