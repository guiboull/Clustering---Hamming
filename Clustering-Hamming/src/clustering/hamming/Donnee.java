package clustering.hamming;

public class Donnee {
	public int nom;
	public String couleur;
	public int noyau;
	public int flagelle;
	public String membrane;

	public Donnee(int n, String co, int no, int fl, String me) {
		nom = n;
		couleur = co;
		noyau = no;
		flagelle = fl;
		membrane = me;
	}

	public int getNom() {
		return nom;
	}

	public void setNom(int nom) {
		this.nom = nom;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public int getNoyau() {
		return noyau;
	}

	public void setNoyau(int noyau) {
		this.noyau = noyau;
	}

	public int getFlagelle() {
		return flagelle;
	}

	public void setFlagelle(int flagelle) {
		this.flagelle = flagelle;
	}

	public String getMembrane() {
		return membrane;
	}

	public void setMembrane(String membrane) {
		this.membrane = membrane;
	}
}