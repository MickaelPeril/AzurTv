package com.azurtv.network;

// interface permettant de transmettre les resultats d'une requete internet a une autre partie.
public interface OnNetworkRequestResult {
	public void		onSuccess(String result);
	public void		onError(int errorCode, String message);
}
