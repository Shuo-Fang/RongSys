package com.ruoyi.broadserver.server.handle.impl;

import com.ruoyi.broad.utils.bConvert;
import com.ruoyi.broadserver.global.ProtocolsToClient;
import com.ruoyi.broadserver.server.handle.DefaultCommand;
import org.apache.mina.core.session.IoSession;


//获取基站信息
public class ReadBS extends DefaultCommand {

	public ReadBS(IoSession session, byte[] content) {
		super(session, content);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] execute() {
		byte[] data = bConvert.subBytes(content, 5, 11);
		String info = new String(data);
		String[] infos = info.split(",");

		String command = save(infos)?"1":"0";//保存信息
		
		datainfo = info;
		loggersession();//插入日志
	
		return returnBytes(ProtocolsToClient.CELLPOS, command, null);
	}

	@Override
	public boolean save(Object obj) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object get(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}