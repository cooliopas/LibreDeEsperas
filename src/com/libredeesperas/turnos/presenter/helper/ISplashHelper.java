package com.uxor.turnos.presenter.helper;

import java.util.HashMap;

import android.content.Context;

public interface ISplashHelper {
	
	/*
	 * Actualiza el gcm code en el servidor
	 * @return HashMap
	 * @see com.uxor.turnos.presenter.helper.ISplashHelper#updateGcmCodeServer()
	 */
	Boolean updateGcmCodeServer(Context context, Integer userId, String gcmCode);
}
