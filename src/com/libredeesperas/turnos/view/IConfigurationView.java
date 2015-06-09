package com.uxor.turnos.view;

import java.util.List;

import android.content.Context;

import com.uxor.turnos.domain.Town;
import com.uxor.turnos.domain.exception.UxorException;


public interface IConfigurationView {

	public void setVcTownsCbo(List<Town> vcTownsCbo) throws UxorException;

	public Context getContext();
}
