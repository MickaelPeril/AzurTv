package com.azurtv.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.azurtv.R;

public class JeuFragment extends Fragment {
 
	private Activity activity = null;
	private Button sendButton = null;
	private EditText nameEditText = null;
	private EditText emailEditText = null;
	
	@Override
	public void	 onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	 
        View view = inflater.inflate(R.layout.jeu_layout, container, false);

        // recuperation des elements graphiques depuis le XML
        nameEditText = (EditText)view.findViewById(R.id.nameEditText);
        emailEditText = (EditText)view.findViewById(R.id.emailEditText);
        sendButton = (Button)view.findViewById(R.id.sendButton);
        
        // definition d'une action sur le click du bouton
        sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String	name = nameEditText.getText().toString();
				String	email = emailEditText.getText().toString();
				
				// verification si le champ name n'est pas vide et affichage d'un message d'erreur si c'est le cas
				if (name == null || name.isEmpty())
					Toast.makeText(activity, getString(R.string.enter_name_error), Toast.LENGTH_LONG).show();
				// meme chose avec le champs de l'email
				else if (email == null || email.isEmpty())
					Toast.makeText(activity, getString(R.string.entrer_email_error), Toast.LENGTH_LONG).show();
				// verification du format de l'email
				else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() == false)
					Toast.makeText(activity, getString(R.string.email_not_valid), Toast.LENGTH_LONG).show();
				else {
					// si les champs sont bien remplis, on affiche un message de remerciement
					nameEditText.setText(null);
					emailEditText.setText(null);
					Toast.makeText(activity, getString(R.string.after_send_message), Toast.LENGTH_LONG).show();
				}
			}
		});
        
        return view;
    }
 
}
