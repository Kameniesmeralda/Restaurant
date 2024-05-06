package application;


import java.time.LocalTime;

import baseDonnes.ConnectionDB;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import mesClasses.Article;
import mesClasses.Client;
import mesClasses.Plat;
import mesClasses.Reservation;

public class Controller {

    @FXML
    private ComboBox<Client> comboClients;

    @FXML
    private ListView<Article> lvPlats;

    @FXML
    private TextField tfClient;

    @FXML
    private TextField tfPlat;

    @FXML
    private TextField tfPrix;

    @FXML
    private Label tfTotal;
    private LocalTime time=LocalTime.now();
    
	@FXML
	void initialize() throws Exception {
		System.out.println("Démarage");
		//appel de la methode init()
		init();
		ConnectionDB connect= new ConnectionDB();
		//List<Client> client= connect.getClients();
		comboClients.setItems(Client.listeClients);
		
	}
	@FXML
	void init() throws Exception {
		// creation de 2 clients
		Client marius=new Client("Marius");
		Client fanny=new Client("Fanny");
		
	
		comboClients.setItems(Client.listeClients); // liaison de la liste avec la combobox
		comboClients.getSelectionModel().select(marius);// selection du client Larius par défaut

		//création des deux réservations pour maruis et fanny et de leur heure de reservation
		
		
		
		Reservation revMarius= new Reservation(time, marius, 2);
		Reservation revFanny= new Reservation(time, fanny, 1);
		
		// plats de marius
		Plat p1m=new Plat(3,"salade");
		Plat p2m=new Plat(10,"tartare de bœuf");
		
		//plat de fanny
		Plat p=new Plat(18,"salade complete");
		
		//ajout des commands aux reservations
		revMarius.addArticle(p1m);
		revMarius.addArticle(p2m);
		revFanny.addArticle(p);
		
		
		
		//association de la listeArticles de la réservation de Marius à la lvPlats
		lvPlats.setItems(revMarius.listeArticles);
		
		// Modifier le champs tfTotal donnant le total de la commande 
		int montantTotal= revMarius.montantFacture();
		tfTotal.setText("Total="+montantTotal+"€");
	}
	
		@FXML
	    void actionAddClient(ActionEvent event) throws Exception {
		 String nom=tfClient.getText();
		 boolean clientExiste = false;
		 
			for(Client c: Client.listeClients) {
				if (c.getNom().equalsIgnoreCase(nom)) {
					clientExiste=true;
					break;
				}
			}
		 
			if(!clientExiste) {
				Client c= new Client(nom);
				 comboClients.setItems(Client.listeClients); // mise à jour de la liste liée au combobox
				 comboClients.getSelectionModel().select(c);// selectionne le client par défaut
			   
			}else {
				System.out.println("Le client existe déjà");
			}
			
			comboClients.getSelectionModel().selectedIndexProperty().addListener((
					ObservableValue<? extends Number> obsVal, Number oldValue, Number newValue) ->{
					System.out.println(obsVal.getValue());
					
					 // Récupérer le client sélectionné dans la comboClients
				    Client client = comboClients.getSelectionModel().getSelectedItem();
				    
				    if (client != null) { // Vérifier si un client est sélectionné
				        // Chercher la réservation de ce client en appelant la méthode reserveClient()
				        Reservation reservation = client.reserveClient();
				        
				        if (reservation != null) { // Si une réservation existe pour ce client
				            // Remettre essentiellement la liste des articles (plats) réservés du client choisi dans la comboClients
				            lvPlats.setItems(reservation.listeArticles);
				        } else { // Si aucune réservation n'existe pour ce client
				            // Créer une nouvelle réservation avec une liste d'articles vide
				            reservation = new Reservation(LocalTime.now(), client, 0);
				            lvPlats.setItems(reservation.listeArticles);
				        }
				        
				        // Modifier le champs tfTotal pour donner le total de la réservation
				        int montantTotal = reservation.montantFacture();
				        tfTotal.setText("Total=" + montantTotal + "€");
				    }

					});
		
	 }

	    @FXML
	    void actionAddPlat(ActionEvent event) {
	    	
	    	//créer un Plat avec les valeurs saisies dans les champs tfPlat et tfPrix
	    	String nomPlat= tfPlat.getText();
	    	int prix=Integer.parseInt(tfPrix.getText());
	    	
	    	Plat p =new Plat(prix,nomPlat);
	    	
	    	//Récupérer l’instance du client sélectionné dans le comboClient
	    	Client clientCourant=comboClients.getSelectionModel().getSelectedItem();
	    	
	    	//Appeler la méthode reserveClient() du client sélectionné dans la comboClients
	    	Reservation res= new Reservation(time, clientCourant, 2);
	    		res=clientCourant.reserveClient();
	    	
	    	res.addArticle(p); //Ajout du plat créé aux articles de cette réservatio
	    	
	    	lvPlats.setItems(res.listeArticles);//associer la listeArticles de la réservation à la lvPlats.
	    	
	    	int montantTotal=res.montantFacture();
	    	tfTotal.setText("Total="+montantTotal+"€");
	    }

	    @FXML
	    void actionSupprimer(ActionEvent event) {
	    	int indice=lvPlats.getSelectionModel().getSelectedIndex(); // Donne l’index de l’objet de la listeview sélectionne
	    	
	    	Client clientCourant=comboClients.getSelectionModel().getSelectedItem();
	    	Reservation res= new Reservation(time, clientCourant, 2);
    		res=clientCourant.reserveClient();
    		
    		 if (res != null) { // Vérifier si une réservation a été trouvée pour ce client
    			 
    		        // Supprimer de cette réservation l’article (le plat sélectionné) parmi la listeArticles
    		        if (indice >= 0 && indice < res.listeArticles.size()) {
    		            res.listeArticles.remove(indice);
    		            
    		            // Mettre à jour le champs tfTotal
    		            int montantTotal =0;
    		            montantTotal=res.montantFacture();
    		            tfTotal.setText("Total=" + montantTotal + "€");
    		            System.out.println(res.listeArticles);
    		            
    		        
    		            System.out.println("total="+ montantTotal);
    		        } else {
    		            System.out.println("Aucun plat sélectionné à supprimer.");
    		        }
    		    } else {
    		        System.out.println("Aucune réservation trouvée pour ce client.");
    		    }
	    }

	    

}
