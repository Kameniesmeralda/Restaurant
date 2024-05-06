package mesClasses;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Reservation {
	private LocalTime heureReserv;
	private Client client  ;
	private int nbPlacesReserv ;
	private List<Article> articlesCommandes = new ArrayList<Article> ();
	public ObservableList<Article> listeArticles= FXCollections.observableArrayList();;
	public static ArrayList<Reservation> listeReservations= new ArrayList<Reservation> ();
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Reservation(LocalTime heureReserv, Client client, int nbPlacesReserv) {
		super();
		this.heureReserv = heureReserv;
		this.client = client;
		this.nbPlacesReserv = nbPlacesReserv;
		
		listeReservations.add( this);
	}
	public void addArticle(Article article) {
		articlesCommandes.add(article);
		listeArticles.add((Plat) article);
	}
	public int montantFacture() {
		int facture = 0;
		for(Article a : listeArticles) {
			facture+=a.getPrix();
		}
		return facture;
	}
	public List<Article> getArticlesCommandes() {
		return articlesCommandes;
	}
	public void addCommande(Plat plat, int quantite) {
		for(int i=0;i<quantite;i++) {
			articlesCommandes.add(plat);
		}
	}
	@Override
	public String toString() {
		String ch="";
		ch+= "Reservation de " +client.getNom()+ " ("+ nbPlacesReserv+" , "+ heureReserv.getHour()+":"+
				heureReserv.getMinute()+")\n";
		for(Article a : articlesCommandes) {
			ch+=a+" \n";
		}
		ch+="Total = "+montantFacture()+"\n\n";
		return ch;
	}
	
}
