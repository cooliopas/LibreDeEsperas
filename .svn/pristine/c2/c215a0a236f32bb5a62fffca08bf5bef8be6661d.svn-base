package com.uxor.turnos.presenter.helper;

import java.util.HashMap;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;

import com.uxor.turnos.domain.exception.UxorException;

public interface IGcmHelper {
	boolean isConnectingToInternet();
	boolean checkPlayServices(Activity view);
	String getRegistrationId(Context context, String nombre);
	void saveRegistrationId(Context context, String user, String regId, Long townId, String email);
	HashMap registroServidor(Context context, String usuario, String regId, Long townId, String email);
	String getEmail(Context context) throws UxorException;
	Account getAccount(AccountManager accountManager) throws UxorException;
}
