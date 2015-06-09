package com.uxor.turnos.view;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.uxor.turnos.domain.Menu;
import com.uxor.turnos.domain.exception.UxorException;

public interface IAboutUsView {
	public void setSocialMediaList(List<com.uxor.turnos.domain.ItemMedia> socialMediaList) throws UxorException;
	public Context getContext();

}
