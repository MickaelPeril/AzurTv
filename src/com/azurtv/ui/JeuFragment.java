package com.azurtv.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.azurtv.R;

public class JeuFragment extends Fragment {

	// Declaration des Attributs
	private Activity activity = null;
	private ImageView sendButton = null;
	private EditText nameEditText, emailEditText, adressEditText, zipEditText,
			cityEditText, answerEditText = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.jeu_layout, container, false);

		// recuperation des elements graphiques depuis le XML
		nameEditText = (EditText) view.findViewById(R.id.nameEditText);
		emailEditText = (EditText) view.findViewById(R.id.emailEditText);
		adressEditText = (EditText) view.findViewById(R.id.adressEditText);
		zipEditText = (EditText) view.findViewById(R.id.zipEditText);
		cityEditText = (EditText) view.findViewById(R.id.cityEditText);
		answerEditText = (EditText) view.findViewById(R.id.answerEditText);
		
		//enleve le focus sur l'edittext name.
		nameEditText.clearFocus();
		
		sendButton = (ImageView) view.findViewById(R.id.sendButton);

		// definition d'une action sur le click du bouton
		sendButton.setOnTouchListener(new ImageView.OnTouchListener() {
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN == event.getAction()) {
					sendButton.setTranslationY(10);
				} else if (MotionEvent.ACTION_UP == event.getAction()) {
					sendButton.setTranslationY(-10);
					//////////////////////////////////////////
					String name = nameEditText.getText().toString();
					String email = emailEditText.getText().toString();
					String adress = adressEditText.getText().toString();
					String zip = zipEditText.getText().toString();
					String city = cityEditText.getText().toString();
					String answer = answerEditText.getText().toString();

					// verification des champs si ils sont valident et non vide et
					// affichage
					// un message d'erreur si c'est le cas
					if (name == null || name.isEmpty())
						Toast.makeText(activity,
								getString(R.string.enter_name_error),
								Toast.LENGTH_LONG).show();

					else if (email == null || email.isEmpty())
						Toast.makeText(activity,
								getString(R.string.entrer_email_error),
								Toast.LENGTH_LONG).show();
					else if (adress == null || adress.isEmpty())
						Toast.makeText(activity,
								getString(R.string.entrer_adress_error),
								Toast.LENGTH_LONG).show();
					else if (zip == null || zip.isEmpty())
						Toast.makeText(activity,
								getString(R.string.entrer_zip_error),
								Toast.LENGTH_LONG).show();
					else if (city == null || city.isEmpty())
						Toast.makeText(activity,
								getString(R.string.entrer_city_error),
								Toast.LENGTH_LONG).show();
					else if (answer == null || answer.isEmpty())
						Toast.makeText(activity,
								getString(R.string.entrer_answer_error),
								Toast.LENGTH_LONG).show();

					else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email)
							.matches() == false)
						Toast.makeText(activity,
								getString(R.string.email_not_valid),
								Toast.LENGTH_LONG).show();
					else {
						// si les champs sont bien remplis, on affiche un message de
						// remerciement
						nameEditText.setText(null);
						emailEditText.setText(null);
						adressEditText.setText(null);
						zipEditText.setText(null);
						cityEditText.setText(null);
						answerEditText.setText(null);

						// Appel de l'application mail
						Intent sendEmail = new Intent(Intent.ACTION_SEND);
						sendEmail.setType("text/plain");

						// Definition du destinataire
						sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL,
								new String[] { "ivan.vujno@gmail.com" });

						// Definition de l'objet
						sendEmail.putExtra(Intent.EXTRA_SUBJECT,
								"Soumission du formulaire");

						// Remplissage du corps du message aves les informations
						// saisie par l'utilisateur dans le formulaire de jeu
						sendEmail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
								" Nom: " + name + "\nEmail: " + email
										+ "\nAdresse: " + adress
										+ "\nCode Postal: " + zip + "\nVille: "
										+ city + "\nRéponse: " + answer);
						startActivity(Intent.createChooser(sendEmail,
								" Envoi email… "));

						// si les champs sont bien remplis, on affiche un message de
						// remerciement
						Toast.makeText(activity,
								getString(R.string.after_send_message),
								Toast.LENGTH_LONG).show();

					}
					////////////////////////////////////////
				}
				return true;
			}
		});
		
		/*sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String name = nameEditText.getText().toString();
				String email = emailEditText.getText().toString();
				String adress = adressEditText.getText().toString();
				String zip = zipEditText.getText().toString();
				String city = cityEditText.getText().toString();
				String answer = answerEditText.getText().toString();

				// verification des champs si ils sont valident et non vide et
				// affichage
				// un message d'erreur si c'est le cas
				if (name == null || name.isEmpty())
					Toast.makeText(activity,
							getString(R.string.enter_name_error),
							Toast.LENGTH_LONG).show();

				else if (email == null || email.isEmpty())
					Toast.makeText(activity,
							getString(R.string.entrer_email_error),
							Toast.LENGTH_LONG).show();
				else if (adress == null || adress.isEmpty())
					Toast.makeText(activity,
							getString(R.string.entrer_adress_error),
							Toast.LENGTH_LONG).show();
				else if (zip == null || zip.isEmpty())
					Toast.makeText(activity,
							getString(R.string.entrer_zip_error),
							Toast.LENGTH_LONG).show();
				else if (city == null || city.isEmpty())
					Toast.makeText(activity,
							getString(R.string.entrer_city_error),
							Toast.LENGTH_LONG).show();
				else if (answer == null || answer.isEmpty())
					Toast.makeText(activity,
							getString(R.string.entrer_answer_error),
							Toast.LENGTH_LONG).show();

				else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email)
						.matches() == false)
					Toast.makeText(activity,
							getString(R.string.email_not_valid),
							Toast.LENGTH_LONG).show();
				else {
					// si les champs sont bien remplis, on affiche un message de
					// remerciement
					nameEditText.setText(null);
					emailEditText.setText(null);
					adressEditText.setText(null);
					zipEditText.setText(null);
					cityEditText.setText(null);
					answerEditText.setText(null);

					// Appel de l'application mail
					Intent sendEmail = new Intent(Intent.ACTION_SEND);
					sendEmail.setType("text/plain");

					// Definition du destinataire
					sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL,
							new String[] { "ivan.vujno@gmail.com" });

					// Definition de l'objet
					sendEmail.putExtra(Intent.EXTRA_SUBJECT,
							"Soumission du formulaire");

					// Remplissage du corps du message aves les informations
					// saisie par l'utilisateur dans le formulaire de jeu
					sendEmail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
							" Nom: " + name + "\nEmail: " + email
									+ "\nAdresse: " + adress
									+ "\nCode Postal: " + zip + "\nVille: "
									+ city + "\nRéponse: " + answer);
					startActivity(Intent.createChooser(sendEmail,
							" Envoi email… "));

					// si les champs sont bien remplis, on affiche un message de
					// remerciement
					Toast.makeText(activity,
							getString(R.string.after_send_message),
							Toast.LENGTH_LONG).show();

				}
			}
		});*/

		return view;
	}

}
