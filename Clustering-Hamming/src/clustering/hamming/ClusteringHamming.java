package clustering.hamming;

import java.util.ArrayList;

/** Auteur : Guillaume Boulet 
    Date : 02/01/2018
    Contexte : Mini-projet IA */

public class ClusteringHamming {
	/** Nombre d'éléments. */
	int size = 10;
	/** Nombre de facteurs par élément. */
	int parametres = 4;
	/** Tableau des éléments et de leurs facteurs. */
	public Donnee[] tableauDonnee;
	/** Tableau de Hamming à double entrée. */
	public int[][] tableauHamming;
	public ArrayList<Donnee> clusterA1;
	public ArrayList<Donnee> clusterB1;
	public ArrayList<Donnee> clusterA2;
	public ArrayList<Donnee> clusterB2;

	public ClusteringHamming() {
		tableauDonnee = new Donnee[size];
		tableauHamming = new int[size][size];
		clusterA1 = new ArrayList<>();
		clusterB1 = new ArrayList<>();
		clusterA2 = new ArrayList<>();
		clusterB2 = new ArrayList<>();
	}

	/** Calcul de la distance de Hamming de chaque exemples du tableau. */
	public int Hamming(Donnee d1, Donnee d2) {
		int h = 0;
		// On compare les couleurs (claire ou fonce)
		if (!d1.couleur.equals(d2.couleur)) {
			h++;
		}
		// On compare les noyaux (1 ou 2)
		if (d1.noyau != d2.noyau) {
			h++;
		}
		// On compare les flagelles (1 ou 2)
		if (d1.flagelle != d2.flagelle) {
			h++;
		}
		// On compare les membranes (fine ou épaisse)
		if (!d1.membrane.equals(d2.membrane)) {
			h++;
		}
		return h;
	}

	/** Construction du tableau des distances de Hamming. */
	public void TableauDistanceHamming() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				tableauHamming[i][j] = Hamming(tableauDonnee[i], tableauDonnee[j]);
			}
		}
	}

	/** Initialisation des données de l'exemple. */
	public void Initialisation() {
		tableauDonnee[0] = new Donnee(0, "Claire", 2, 2, "Fine");
		tableauDonnee[1] = new Donnee(1, "Claire", 1, 2, "Fine");
		tableauDonnee[2] = new Donnee(2, "Fonce", 2, 2, "Fine");
		tableauDonnee[3] = new Donnee(3, "Claire", 2, 1, "Fine");
		tableauDonnee[4] = new Donnee(4, "Claire", 2, 2, "Epaisse");
		tableauDonnee[5] = new Donnee(5, "Claire", 1, 1, "Epaisse");
		tableauDonnee[6] = new Donnee(6, "Fonce", 2, 2, "Epaisse");
		tableauDonnee[7] = new Donnee(7, "Fonce", 1, 1, "Fine");
		tableauDonnee[8] = new Donnee(8, "Fonce", 1, 1, "Epaisse");
		tableauDonnee[9] = new Donnee(9, "Fonce", 2, 1, "Epaisse");
	}

	/** Affichage du tableau des distance de Hamming en mode console. */
	public void AffichageDistanceHamming() {
		System.out.println("Tableau des distances de Hamming :\n");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(tableauHamming[i][j] + " ");
			}
			System.out.println();
		}
	}

	/** Classement en 2 cluster en fonction de la distance de Hamming Minimale. */
	public void ClusterDistanceHammingMinimale() {
		int max = parametres;
		int min = 1;
		boolean initial = false;
		ArrayList<Donnee> resteNonPlacer = new ArrayList<>();

		// Création d'un cluster temporaire contenant les données en attendant de les
		// placer dans les clusters
		for (int i = 0; i < size; i++) {
			resteNonPlacer.add(tableauDonnee[i]);
		}

		// Initialisation des 2 clusters
		while (!initial && max <= parametres) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (tableauHamming[i][j] == max && !initial) {
						clusterA1.add(tableauDonnee[i]);
						clusterB1.add(tableauDonnee[j]);
						resteNonPlacer.remove(tableauDonnee[i]);
						resteNonPlacer.remove(tableauDonnee[j]);
						initial = true;
					}
				}
			}
			if (!initial) {
				max--;
			}
		}

		boolean okClusterA = true;
		boolean okClusterB = true;
		int remplissageClusterA = 0;
		int remplissageClusterB = 0;

		// Remplissage des clusters en répartissant les données en fonctions de leurs
		// distances de Hamming
		while (!resteNonPlacer.isEmpty()) {
			ArrayList<Donnee> clusterTemporaire = new ArrayList<>(resteNonPlacer);
			if (okClusterA) {
				for (Donnee aClusterTemporaire : clusterTemporaire) {
					if (tableauHamming[clusterA1.get(remplissageClusterA).getNom()][aClusterTemporaire
							.getNom()] <= min) {
						clusterA1.add(aClusterTemporaire);
						resteNonPlacer.remove(aClusterTemporaire);
					}
				}
				if (remplissageClusterA <= clusterA1.size()) {
					remplissageClusterA = 0;
					okClusterA = false;
				} else {
					remplissageClusterA++;
				}
			} else if (okClusterB) {
				for (Donnee aClusterTemporaire : clusterTemporaire) {
					if (tableauHamming[clusterB1.get(remplissageClusterB).getNom()][aClusterTemporaire
							.getNom()] <= min) {
						clusterB1.add(aClusterTemporaire);
						resteNonPlacer.remove(aClusterTemporaire);
					}
				}
				if (remplissageClusterB <= clusterB1.size()) {
					remplissageClusterB = 0;
					okClusterB = false;
				} else {
					remplissageClusterB++;
				}
			} else {
				min++;
				okClusterA = true;
				okClusterB = true;
			}
		}

		System.out.println("\n\nSéparation en 2 clusters en prenant le classement par distance de Hamming Minimale :");
		// Affichage du cluster A
		System.out.print("\nCluster A : ");
		clusterA1.forEach(aClusterA1 -> System.out.print(aClusterA1.getNom() + " "));
		System.out.println(" ");
		// Affichage du cluster B
		System.out.print("Cluster B : ");
		clusterB1.forEach(aClusterB1 -> System.out.print(aClusterB1.getNom() + " "));
		System.out.println();
	}

	/** Classement en 2 cluster en fonction de la distance de Hamming Maximale. */
	public void ClusterDistanceHammingMaximale() {
		int max = parametres;
		int min = 4;
		boolean initial = false;
		ArrayList<Donnee> resteNonPlacer = new ArrayList<>();

		// Création d'un cluster temporaire contenant les données en attendant de les
		// placer dans les clusters
		for (int i = 0; i < size; i++) {
			resteNonPlacer.add(tableauDonnee[i]);
		}

		// Initialisation des 2 clusters
		while (!initial && max <= parametres) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (tableauHamming[i][j] == max && !initial) {
						clusterA2.add(tableauDonnee[i]);
						clusterB2.add(tableauDonnee[j]);
						resteNonPlacer.remove(tableauDonnee[i]);
						resteNonPlacer.remove(tableauDonnee[j]);
						initial = true;
					}
				}
			}
			if (!initial) {
				max--;
			}
		}

		boolean okClusterA = true;
		boolean okClusterB = true;
		int remplissageClusterA = 0;
		int remplissageClusterB = 0;

		// Remplissage des clusters en répartissant les données en fonctions de leurs
		// distances de Hamming
		while (!resteNonPlacer.isEmpty()) {
			ArrayList<Donnee> clusterTemporaire = new ArrayList<>(resteNonPlacer);
			if (okClusterA) {
				for (Donnee aClusterTemporaire : clusterTemporaire) {
					if (tableauHamming[clusterA2.get(remplissageClusterA).getNom()][aClusterTemporaire
							.getNom()] >= min) {
						clusterB2.add(aClusterTemporaire);
						resteNonPlacer.remove(aClusterTemporaire);
					}
				}
				if (remplissageClusterA <= clusterA2.size()) {
					remplissageClusterA = 0;
					okClusterA = false;
				} else {
					remplissageClusterA++;
				}
			} else if (okClusterB) {
				for (Donnee aClusterTemporaire : clusterTemporaire) {
					if (tableauHamming[clusterB2.get(remplissageClusterB).getNom()][aClusterTemporaire
							.getNom()] >= min) {
						clusterA2.add(aClusterTemporaire);
						resteNonPlacer.remove(aClusterTemporaire);
					}
				}
				if (remplissageClusterB <= clusterB2.size()) {
					remplissageClusterB = 0;
					okClusterB = false;
				} else {
					remplissageClusterB++;
				}
			} else {
				min--;
				okClusterA = true;
				okClusterB = true;
			}
		}

		System.out.println("\n\nSéparation en 2 clusters en prenant le classement par distance de Hamming Maximale :");
		// Affichage du cluster A
		System.out.print("\nCluster A : ");
		clusterA2.forEach(aClusterA2 -> System.out.print(aClusterA2.getNom() + " "));
		System.out.println(" ");
		// Affichage du cluster B
		System.out.print("Cluster B : ");
		clusterB2.forEach(aClusterB2 -> System.out.print(aClusterB2.getNom() + " "));
		System.out.println();
	}

	public static void main(String[] args) {
		ClusteringHamming c = new ClusteringHamming();
		c.Initialisation();
		c.TableauDistanceHamming();
		c.AffichageDistanceHamming();
		c.ClusterDistanceHammingMinimale();
		c.ClusterDistanceHammingMaximale();
	}
}