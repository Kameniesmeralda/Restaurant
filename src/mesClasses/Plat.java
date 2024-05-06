package mesClasses;

public class Plat implements Article {
	private String nom;
	private int prix;
	
	public Plat(int prix, String nom) {
		super();
		this.nom = nom;
		this.prix = prix;
	}

	@Override
	public String toString() {
		return nom + " : " + prix +"€";
	}

	@Override
	public int getPrix() {
		return prix;
	}
}
