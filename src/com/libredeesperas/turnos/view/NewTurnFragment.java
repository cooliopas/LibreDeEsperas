package com.uxor.turnos.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uxor.turnos.R;
import com.uxor.turnos.domain.exception.UxorException;
import com.uxor.turnos.presenter.OneTurnPresenter;
import com.uxor.turnos.presenter.helper.ApplicationHelper;

public class NewTurnFragment extends Fragment {
	private static final String LOGTAG="newTurnFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Cargo el titulo en la Action Bar
        ((MainActivity)getActivity()).setTituloSeccion(getResources().getString(R.string.fragment_new_turn));
       	
		return inflater.inflate(R.layout.fragment_new_turn, container, false);		
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);
        
        //Cargo la fuente en el label de new turn
      	Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Light.ttf");
      	Typeface robotoThin = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Thin.ttf");
      	Typeface robotoBlack = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Black.ttf");
      	Typeface robotoCondensedLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoCondensed-Light.ttf");
      	Typeface robotoCondensedBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoCondensed-Bold.ttf");
      	
      	TextView txtLblNoActiveTurnsView = (TextView) getActivity().findViewById(R.id.txtLblNoActiveTurns);
      	txtLblNoActiveTurnsView.setTypeface(robotoCondensedLight);
      	
      	TextView txtLblDescriptionNewTurnView = (TextView) getActivity().findViewById(R.id.txtLblDescriptionNewTurn);
      	txtLblDescriptionNewTurnView.setTypeface(robotoLight);
      	
      	//OnClick
      	TextView btnAcceptNewTurn = (TextView) getActivity().findViewById(R.id.btnAcceptNewTurn);
      	
      	btnAcceptNewTurn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		       	String fragmentName = "fragment2";
		       	Fragment fragment = new Fragment2();
		        FragmentManager manager = getActivity().getSupportFragmentManager();
		        FragmentTransaction fragmentManager = manager.beginTransaction();
				fragmentManager.replace(R.id.content_frame, fragment, fragmentName);
				fragmentManager.commit();
				
			}
		});
    }
	
	public void updateView(){
        //Formateo el texto de la vista
        formatTextView();
	}
	
	private void formatTextView(){
		
        //Cargo la fuente en el label de new turn
      	Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Light.ttf");
      	Typeface robotoCondensedLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/RobotoCondensed-Light.ttf");
      	
      	TextView txtLblNoActiveTurnsView = (TextView) getActivity().findViewById(R.id.txtLblNoActiveTurns);
      	txtLblNoActiveTurnsView.setTypeface(robotoCondensedLight);
      	
      	TextView txtLblDescriptionNewTurnView = (TextView) getActivity().findViewById(R.id.txtLblDescriptionNewTurn);
      	txtLblDescriptionNewTurnView.setTypeface(robotoLight);
      	
      	//OnClick
      	TextView btnAcceptNewTurn = (TextView) getActivity().findViewById(R.id.btnAcceptNewTurn);
      	btnAcceptNewTurn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		       	String fragmentName = "fragment2";
		       	Fragment fragment = new Fragment2();

		        FragmentManager manager = getActivity().getSupportFragmentManager();
		        FragmentTransaction fragmentManager = manager.beginTransaction();
		        //manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				//fragmentManager.addToBackStack(null);
				fragmentManager.replace(R.id.content_frame, fragment, fragmentName);
				//fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				fragmentManager.commit();
				
			}
		});
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		 
		String tituloActionBar = getResources().getString(R.string.fragment_new_turn);
        setFormatTitleActionBar(tituloActionBar);
	}
	
	private  void setFormatTitleActionBar(String title){
	    
		((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
		((ActionBarActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

	    LayoutInflater inflator = (LayoutInflater)((ActionBarActivity)getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v = inflator.inflate(R.layout.title_view, null);
	    
	    //Se utiliza un textview personalizado para cambiar el tipo de letra.
	    ((TextView)v.findViewById(R.id.title_action_bar)).setText(title);
	    
	    //assign the view to the actionbar
	    ((ActionBarActivity)getActivity()).getSupportActionBar().setCustomView(v);
	}
	

}
