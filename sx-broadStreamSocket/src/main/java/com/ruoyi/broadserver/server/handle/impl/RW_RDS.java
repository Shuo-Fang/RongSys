package com.ruoyi.broadserver.server.handle.impl;

import com.ruoyi.broad.domain.Organization;
import com.ruoyi.broad.service.IOrganizationService;
import com.ruoyi.broad.service.impl.OrganizationServiceImpl;
import com.ruoyi.broad.utils.bConvert;
import com.ruoyi.broadserver.global.ProtocolsToClient;
import com.ruoyi.broadserver.server.handle.DefaultCommand;
import org.apache.mina.core.session.IoSession;
import com.ruoyi.framework.util.SpringUtils;
//读取并写入RDS码
public class RW_RDS extends DefaultCommand {
	private IOrganizationService organizationService = (OrganizationServiceImpl) SpringUtils.getBean(OrganizationServiceImpl.class);
	public RW_RDS(IoSession session, byte[] content) {
		super(session, content);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] execute() {
		// TODO Auto-generated method stub
		byte[] data = bConvert.subBytes(content, 5, 14);
		String info = new String(data);
		String[] infos = info.split(",");
		logger.info(info);
		String command = save(infos)?"1":"0";//保存信息
		datainfo = info;
		loggersession();//插入日志
		
		String reinfo = (String)get(info);//获取需要修改的码率频段，后续可进行修改
		if(reinfo != null)
			return returnBytes(ProtocolsToClient.E_FM_RECIVE, command, reinfo);
		else
			return returnBytes(ProtocolsToClient.E_FM_RECIVE, command, info);
	}
	
	@Override
	public boolean save(Object obj) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object get(Object obj) {
		// TODO Auto-generated method stub
		Organization organization  = organizationService.selectRdsByTid((String)session.getAttribute("CLIENTINFO"));
		if(organization != null) {
			String RDS = organization.getRds();
			String fm = organization.getFmfrequency();
			if (RDS != null && fm != null) {
				while (RDS.length() < 8) {
					RDS = "0"+RDS;
				}
				RDS = RDS.substring(0, 8);
				while (fm.length() < 5) {
					fm = "0"+fm;
				}
				fm = fm.substring(0, 5);
				return RDS + "," + fm;
			}
		}
		return null;
	}
}