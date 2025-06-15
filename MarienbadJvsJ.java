/**
 * Programme permettant de jouer au jeu de Marienbad avec deux joueurs réels
 * @author V. Hazevis
 */
class MarienbadJvsJ{
	
/*######################## PROGRAMME PRINCIPALE ######################*/	
	/**
     * Méthode principale pour exécuter le jeu.
     * Elle gère l'alternance entre les joueurs et détermine le gagnant.
     */
	void principal(){
		boolean continuation = true;
		afficherMenu();
		int choix = SimpleInput.getInt("Veuillez choisir une option : ");

		if (choix == 1) {
			String nomJ1 = saisieNom(1);
			String nomJ2 = saisieNom(2);
			int nbrLigne = saisieLigne();
			int [] nbrAlumette = createTab(nbrLigne);
			boolean j1Gagne = false;
			
			while (sommeTab(nbrAlumette) > 0 && continuation){
				// Tour du joueur 1
				affichageJeu(nbrAlumette);
				System.out.println("\u001B[32m" + "Au tour de " + nomJ1 + "\u001B[0m");
				jeu(nbrAlumette);
				
				if (sommeTab(nbrAlumette) == 0 && continuation) {
					j1Gagne = true;
					continuation = false; // Le joueur 1 a gagné
				} else {
					// tour de joueur 2
					affichageJeu(nbrAlumette);
					System.out.println("\u001B[32m" + "Au tour de " + nomJ2 + "\u001B[0m");
					jeu(nbrAlumette);
				}
			}
			
			// Affichage du gagnant
			affichageJeu(nbrAlumette);
			if (j1Gagne) {
				System.out.println("\u001B[32m" + "Le gagnant est : " + nomJ1 + "\u001B[0m");
			} else {
				System.out.println("\u001B[32m" + "Le gagnant est : " + nomJ2 + "\u001B[0m");
			}
		}
		else if (choix == 2){
			testCreateTab();
			System.out.println();
			testSommeTab();
		}
		else {
			System.out.println("\u001B[31m" + "Merci d'avoir joué !" + "\u001B[0m");
			continuation = false; // Quitte le jeu
		}
    }
	


/*################### MÉTHODES D'AFFICHAGE #########################*/
	/**
	 * affiche un menu pour le jeu
	 */
    void afficherMenu() {
        System.out.println("\u001B[34m" + "===========================" + "\u001B[0m");
        System.out.println("\u001B[32m" + "    Bienvenue au Jeu de   " + "\u001B[0m");
        System.out.println("\u001B[32m" + "        Marienbad         " + "\u001B[0m");
        System.out.println("\u001B[36m" + " vous etes dans la version " + "\u001B[0m");
        System.out.println("\u001B[36m" + "     joueur vs joueur " + "\u001B[0m");
        System.out.println("\u001B[34m" + "===========================" + "\u001B[0m");
        System.out.println("\u001B[33m" + "1. Commencer une partie" + "\u001B[0m");
        System.out.println("\u001B[33m" + "2. Lancer les tests unitaires" + "\u001B[0m");
        System.out.println("\u001B[33m" + "3. Quitter" + "\u001B[0m");
        System.out.println("\u001B[34m" + "===========================" + "\u001B[0m");
    }

    /**
     * Gère l'affichage du jeu dans le terminal.
     * @param nbrAlumette Le tableau du nombre d'allumettes par ligne.
     */
    void affichageJeu(int[] nbrAlumette) {
		System.out.println("\u001B[36m" + "État du jeu : " + "\u001B[0m");
		System.out.println("\u001B[34m" + "===========================" + "\u001B[0m");

		for (int i = 0; i < nbrAlumette.length; i++) {
			if (i<=9){
				System.out.print(' ');
			}
			System.out.print("\u001B[33m" + i + " : " + "\u001B[0m"); // Indice de la ligne
			for (int j = 0; j < nbrAlumette[i]; j++) {
				System.out.print("\u001B[32m" + "| " + "\u001B[0m"); // Allumettes en vert
			}
			// Afficher le nombre d'allumettes restantes
			System.out.println("\u001B[35m" + " " + nbrAlumette[i] + "\u001B[0m");
		}
		System.out.println("\u001B[34m" + "===========================" + "\u001B[0m");
	}
	
	/**
     * Affiche les éléments d'un tableau au format texte.
     * @param t Le tableau à afficher.
     */
    void displayTab(int[] t) {
		if (t.length == 0){
			System.out.print("{}");
		} else {
			System.out.print("{");
			for (int i = 0; i < t.length - 1; i++) {
				System.out.print(t[i] + ",");
			}
			System.out.print(t[t.length - 1] + "}");
		}
    }



/*################### DECLARATION DES METHODES #######################*/

	/**
	 * demande le nom de l'utilisateur
	 * @param numeroJoueur, le numéro du joueur
	 * @return nom, un string contenant le nom du joueur
	 */
	String saisieNom(int numeroJoueur) {
        String nom;
        System.out.print("Nom du joueur " + numeroJoueur + " : ");
        do {
            nom = SimpleInput.getString("");
        } while (nom == ""); // S'assure qu'un nom valide est saisi
        return nom;
    }
	
	/**
	 * demande un numéro de ligne entre 2 et 15 au joueur
	 * @return le nombre de lignes donné par le joueur 
	 */
    int saisieLigne() {
        int nbrLigne;
        do {
            nbrLigne = SimpleInput.getInt("Entrez le nombre de lignes d'allumettes souhaité, entre 2 et 15 : ");
        } while (nbrLigne < 2 || nbrLigne > 15); // Valide la saisie
        return nbrLigne;
    }
	
	/**
     * Crée le tableau du jeu avec un tableau conteant le nombre d'allumettes par ligne.
     * @param nbrLigne Le nombre de lignes souhaité.
     * @return Le tableau des allumettes initialisé.
     */
    int[] createTab(int nbrLigne) {
        int[] result = new int[nbrLigne];
        for (int i = 0; i < nbrLigne; i++) {
            result[i] = 2 * i + 1; // Ajoute 2 allumettes par ligne supplémentaire
        }
        return result;
    }
	
	/**
     * Calcule la somme des éléments d'un tableau.
     * @param tab Le tableau dont il faut calculer la somme.
     * @return La somme des éléments.
     */
    int sommeTab(int[] tab) {
        int result = 0;
        for (int valeur : tab) {
            result += valeur; // Cumul des valeurs
        }
        return result;
    }
	
	
	/**
     * Gère le retrait des allumettes par le joueur.
     * @param nbrAlumette Le tableau de jeu.
     */
    void jeu(int[] nbrAlumette) {
        int ligne;
        int alumette;
        
        // Saisit une ligne valide où il y a encore des allumettes
        do {
            ligne = SimpleInput.getInt("Dans quelle ligne voulez-vous enlever des allumettes ?"); 
        } while (ligne < 0 || ligne >= nbrAlumette.length || nbrAlumette[ligne] == 0);
        
        // Saisit un nombre d'allumettes valide à retirer
        do {
            alumette = SimpleInput.getInt("Combien d'allumettes voulez-vous enlever ?"); 
        } while (alumette <= 0 || alumette > nbrAlumette[ligne]);
        
        // Met à jour le tableau d'allumettes
        nbrAlumette[ligne] -= alumette;
    }
    
    /*#######################DECLARATION DES METHODES DE TESTS UNITAIRE####################*/
    
    /**
	* Teste la méthode sommeTab()
	*/
	void testSommeTab () {
		System.out.println ("\u001B[33m" +"*** testSommeTab() ***"+ "\u001B[0m");
		int [] t1 = {1,2,3};
		int [] t2 = {};
		int [] t3 = {1};
		testCasSommeTab(t1, 6);
		testCasSommeTab(t2, 0);
		testCasSommeTab(t3, 1);
	}
	/**
	* teste un appel de testSommeTab()
	* @param tab un tableau d'entiers 
	* @param result résultat attendu
	**/
	void testCasSommeTab (int [] tab, int result) {
		System.out.print ("sommeTab (");
		displayTab(tab);
		System.out.print(") \t= " + result + "\t : ");
		int resExec = sommeTab(tab);
		if (resExec == result){
			System.out.println ("\u001B[32m" +"OK"+ "\u001B[0m");
		} else {
			System.err.println ("ERREUR");
		}
	}
	
	    /**
	* Teste la méthode createTab()
	*/
	void testCreateTab () {
		System.out.println ("\u001B[33m" +"*** testCreateTab() ***"+ "\u001B[0m");
		int [] t1 = {1, 3};
		int [] t2 = {1,3,5,7,9,11};
		int [] t3 = {1,3,5,7,9,11,13,15,17,19,21,23,25,27,29};
		testCasCreateTab(t1, 2);
		testCasCreateTab(t2, 6);
		testCasCreateTab(t3, 15);
	}
	/**
	* teste un appel de testCreateTab()
	* @param nbrLigne le nombre de lignes donné pour tester createTab
	* @param result résultat attendu
	**/
	void testCasCreateTab (int [] result, int nbrLigne) {
		System.out.print ("createTab (" + nbrLigne + ") \t= ");
		displayTab(result);
		System.out.print("\t : ");
		int [] resExec = createTab(nbrLigne);
		boolean egaux = true;
		
		if (result.length == resExec.length){
			for (int i = 0; i<result.length; i++){
				if (result[i] != resExec[i]){
					egaux = false;
				}
			}
		} else {
			egaux = false;
		}
		
		if (egaux){
			System.out.println ("\u001B[32m" +"OK"+ "\u001B[0m");
		} else {
			System.err.println ("ERREUR");
		}
	}
}
