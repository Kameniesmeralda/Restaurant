package mesClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Client {
	private String nom;
	public static ObservableList<Client> listeClients =FXCollections.observableArrayList();

	public Client(String nom)throws Exception {
		super();
		this.nom = nom;
		
			for(Client c: listeClients) {
				if (this.equals(c)) {
					throw new Exception("Client déjà existant");
				}
			}
		
		listeClients.add(this);
		
		
	}

	@Override
	public String toString() {
		return " "+nom+" ";
	}

	public String getNom() {
		return nom;
	}
	
	public  Reservation reserveClient() {
		for(Reservation res: Reservation.listeReservations) {
			if(this.equals(res.getClient())){
				return res;
			}
		}
		return null; // Renvoyer null si aucune réservation trouvée pour ce client
    
		
	}
}
