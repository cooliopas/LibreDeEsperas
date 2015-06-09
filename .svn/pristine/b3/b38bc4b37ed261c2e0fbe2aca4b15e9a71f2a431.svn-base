package com.uxor.turnos.view;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.view.dto.LstTurnDto;

public interface IOneTurnView {

	public void setLstTurnDto(LstTurnDto lstTurnDto) throws UxorException;
	public Context getContext();
	public void startView(Intent pIntent);
	public BigDecimal getTurnBefore();
	public void setTurnBefore(BigDecimal turnBefore);
	public BigDecimal getWaitingTime();
	public void setWaitingTime(BigDecimal waitingTime);
	public LstTurnDto getLstTurnDto();
	public String getTurnNumber();
	public void setTurnNumber(String turnNumber);
	public String getServiceTypeDesc();
	public void setServiceTypeDesc(String serviceTypeDesc);
	public String getClientName();
	public void setClientName(String clientName);
	public String getBranchAddress();
	public void setBranchAddress(String branchAddress);
	public void updateView();
	public void setImgLogoClient(String logoPath);	
}
