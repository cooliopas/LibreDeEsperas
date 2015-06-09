package com.uxor.turnos.presenter.helper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;


public interface ITurnosHelper {
	HashMap retrieveInfoTurns(List<String> serverIds);
	HashMap newServerTurn(Context context, BigDecimal clientServiceId, String gcmId, Timestamp turnDate, String turnNumber, Integer userId) throws UxorException;
	HashMap retrieveTurnsNotifications(Context context, String gcmCode) throws UxorException;
	HashMap delServerTurn(Context context, BigDecimal serverTurnId) throws UxorException;
	HashMap getServiceCongestion(Context context, BigDecimal serviceId) throws UxorException;
	HashMap retrieveServerNotifications(Context context, String gcmCode) throws UxorException;

	
}
