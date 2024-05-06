package baseDonnes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import mesClasses.Client;

public class ConnectionDB {

	private Connection conn;

	public ConnectionDB() throws Exception {
		super();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ReservationClient", "root", "");

		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery("SELECT * FROM Client");

		// ResultSetMetaData rsmd= res.getMetaData();

		while (res.next()) {
			
			System.out.println(res.getInt("id")+" "+res.getString("nom"));
		}
		
	}
	 public List<Client> getClients() throws Exception {
	        List<Client> clients = new ArrayList<>();

	        Statement stmt = conn.createStatement();
	        ResultSet res = stmt.executeQuery("SELECT * FROM Client");

	        while (res.next()) {
	            //int id = res.getInt("id");
	            String nom = res.getString("nom");
	            // Créer un objet Client pour chaque enregistrement et l'ajouter à la liste
	            clients.add(new Client(nom));
	        }

	        return clients;
	    }
	
}
