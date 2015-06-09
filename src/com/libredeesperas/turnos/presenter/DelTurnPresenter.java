package com.uxor.turnos.presenter;

import java.math.BigDecimal;
import java.util.HashMap;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.uxor.turnos.domain.Turno;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.model.ITurnosModel;
import com.uxor.turnos.model.TurnosModel;
import com.uxor.turnos.presenter.helper.ApplicationHelper;
import com.uxor.turnos.presenter.helper.GcmHelper;
import com.uxor.turnos.presenter.helper.IApplicationHelper;
import com.uxor.turnos.presenter.helper.IGcmHelper;
import com.uxor.turnos.presenter.helper.ITurnosHelper;
import com.uxor.turnos.presenter.helper.TurnosHelper;
import com.uxor.turnos.util.TaskWSDelServerTurn;
import com.uxor.turnos.util.TaskWSNewServerTurn;
import com.uxor.turnos.util.Turnos;
import com.uxor.turnos.view.IAddTurnView;
import com.uxor.turnos.view.IDelTurnView;

public class DelTurnPresenter {
	private IDelTurnView delTurnView;
	private ITurnosModel turnosModel;
	private IGcmHelper gcmHelper;
	private IApplicationHelper applicationHelper;
	private ITurnosHelper turnosHelper;

	public DelTurnPresenter(IDelTurnView delTurnView) {
		this(delTurnView, new TurnosModel(), GcmHelper.getInstance(),
				ApplicationHelper.getInstance(), TurnosHelper.getInstance());
	}

	public DelTurnPresenter(IDelTurnView delTurnView, ITurnosModel turnosModel,
			IGcmHelper gcmHelper, IApplicationHelper applicationHelper,
			ITurnosHelper turnosHelper) {
		this.delTurnView = delTurnView;
		this.turnosModel = turnosModel;
		this.gcmHelper = gcmHelper;
		this.applicationHelper = applicationHelper;
		this.turnosHelper = turnosHelper;
	}

	// /*
	// * Devuelve el listado de servicios para cargar en el combo
	// */
	// public void retrieveServicesCbo() throws UxorException {
	//
	// // Actualizo la grilla de sucursales
	// this.addTurnView.setVcServicesCbo(this.turnosModel
	// .retrieveServicesCbo());
	// }

	public void delTurn(Long turnId) throws UxorException {

		// Check if Internet Connection present
		if (!gcmHelper.isConnectingToInternet()) {

			applicationHelper
					.showAlertDialog(
							delTurnView.getContext(),
							"Error en la conexión de internet",
							"Por favor, contecte internet para poder sincronizar el turno...",
							false);

			// Cambia el estado del turno a "Sin sincronizar (SIN)"
			// this.turnosModel.updateTurnStatus(turnId,
			// Turnos.TURN_STATUS_SIN);
		} else {

			// Llamar tarea asincronica para agregar el turno en el servidor
			TaskWSDelServerTurn taskWSDelServerTurn = new TaskWSDelServerTurn(
					this);
			taskWSDelServerTurn.execute(turnId);
		}
	}

	public HashMap delServerTurn(Long turnId) throws UxorException {
		// Obtengo el turno
		Turno turn = this.turnosModel.getTurn(turnId);

		try
		{
			// Llamo al webservice Cancelar Turnos
			HashMap hmServerTurn = turnosHelper.delServerTurn(
					delTurnView.getContext(), turn.getServerTurnId());
			Boolean result = (Boolean) hmServerTurn.get("result");

			if (result) {
				// Deshabilito el turno de la base local del celular
				this.turnosModel.updateTurnStatus(new BigDecimal(turnId), "DES");
			}

			return hmServerTurn;
		} catch (Exception e) {
			Log.e("DelTurnPresenter", e.getMessage());
			throw new UxorException("Se produjo un error",e);
		}
	}

	public IDelTurnView getDelTurnView() {
		return delTurnView;
	}

}
