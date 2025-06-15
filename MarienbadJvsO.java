/**
 * Programme permettant de jouer au jeu de Marienbad avec deux joueurs réels.
 * @author V. Hazevis
 */
class MarienbadJvsO {

    /*######################## PROGRAMME PRINCIPAL ######################*/
    
    /**
     * Méthode principale pour exécuter le jeu.
     * Elle gère l'alternance entre le joueur et l'ordinateur 
     * Elle détermine le gagnant.
     */
    void principal() {
		boolean continuation = true;
		afficherMenu();
		int choix = SimpleInput.getInt("Veuillez choisir une option : ");

		if (choix == 1) {
			String nomJ1 = saisieNom(1); 
			int nbrLigne = saisieLigne();
			int[] nbrAlumette = createTab(nbrLigne); // Création de la table de jeu
			boolean j1Gagne = false; // Indique si le joueur 1 a gagné

			while (sommeTab(nbrAlumette) > 0 && continuation) {
				// Tour du joueur 1
				affichageJeu(nbrAlumette);
				System.out.println("\u001B[32m" + "Au tour de " + nomJ1 + "\u001B[0m");
				jeu(nbrAlumette);
				
				if (sommeTab(nbrAlumette) == 0 && continuation) {
					j1Gagne = true;
					continuation = false; // Le joueur 1 a gagné
				}

				// Tour de l'ordinateur
				affichageJeu(nbrAlumette);
				System.out.println("\u001B[31m" + "Au tour de l'ordinateur" + "\u001B[0m");
				jeuOrdi(nbrAlumette);
			}

			// Affichage du gagnant
			affichageJeu(nbrAlumette);
			if (j1Gagne) {
				System.out.println("\u001B[32m" + "Le gagnant est : " + nomJ1 + "\u001B[0m");
			} else {
				System.out.println("\u001B[32m" + "Le gagnant est : l'ordinateur " + "\u001B[0m");
			}
		} 
		else if (choix == 2){
			testCreateTab();
			System.out.println();
			testConvertionBinaire();
			System.out.println();
			testTotalBin();
			System.out.println();
			testConvertionStringToInt();
			System.out.println();
			testSommeTab();
			System.out.println();
			continuation = false;
		}
		else {
			System.out.println("\u001B[31m" + "Merci d'avoir joué !" + "\u001B[0m");
			continuation = false; // Quitte le jeu
		}
    }

    /*################### MÉTHODES D'AFFICHAGE #########################*/
	/**
	 * affiche un menue pour le jeu
	 */
    void afficherMenu() {
        System.out.println("\u001B[34m" + "===========================" + "\u001B[0m");
        System.out.println("\u001B[32m" + "    Bienvenue au Jeu de   " + "\u001B[0m");
        System.out.println("\u001B[32m" + "        Marienbad         " + "\u001B[0m");
        System.out.println("\u001B[36m" + " vous etes dans la version " + "\u001B[0m");
        System.out.println("\u001B[36m" + "   joueur vs ordinateur " + "\u001B[0m");
        System.out.println("\u001B[34m" + "===========================" + "\u001B[0m");
        System.out.println("\u001B[33m" + "1. Commencer une partie" + "\u001B[0m");
        System.out.println("\u001B[33m" + "2. Lancer les tests unitaires" + "\u001B[0m");
        System.out.println("\u001B[33m" + "3. Quitter" + "\u001B[0m");
        System.out.println("\u001B[34m" + "===========================" + "\u001B[0m");
    }

    /**
     * Gère l'affichage du jeu dans le terminal.
     * @param nbrAlumette Le tableau des allumettes par ligne.
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

    /*################### DÉCLARATION DES MÉTHODES #######################*/
	/**
	 * gère la saisie du nom du joueur
	 * @param numeroJoueur, le numero du joueur
	 * @return nom, le nom entrer dans le simpleIntput
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
	 * selectionne la ligne voulut par le joueur
	 * @return la ligne voulut par le joueur 
	 */
    int saisieLigne() {
        int nbrLigne;
        do {
            nbrLigne = SimpleInput.getInt("Entrez le nombre de lignes d'allumettes souhaité, entre 2 et 15 : ");
        } while (nbrLigne < 2 || nbrLigne > 15); // Valide la saisie
        return nbrLigne;
    }


    /**
     * Crée le tableau du jeu avec un nombre d'allumettes par ligne.
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

    /**
     * Convertit la valeur d'une ligne en binaire.
     * @param nbrAlumetteLigne Le nombre d'allumettes dans la ligne.
     * @return La représentation binaire sous forme de chaîne.
     */
    String convertionBinaire(int nbrAlumetteLigne) {
        return Integer.toBinaryString(nbrAlumetteLigne); // Conversion directe
    }

    /**
     * Calcule le total binaire des allumettes dans chaque ligne.
     * @param nbrAlumette Le tableau des allumettes.
     * @return Un tableau contenant les valeurs binaires des allumettes par ligne.
     */
    int[] totalBin(int[] nbrAlumette) {
        int[] result = new int[nbrAlumette.length];
        for (int i = 0; i < nbrAlumette.length; i++) {
            String valBinaire = convertionBinaire(nbrAlumette[i]);
            int[] tabBinaire = convertionStringToInt(valBinaire);
            for (int j = 0; j < tabBinaire.length; j++) {
                result[j] += tabBinaire[j]; // Cumul des valeurs binaires
            }
        }
        return result;
    }

    /**
     * Inverse l'ordre des éléments d'un tableau.
     * @param tab Le tableau à inverser.
     * @return Le tableau inversé.
     */
    int[] reverseTab(int[] tab) {
        int[] result = new int[tab.length];
        for (int i = 0; i < tab.length; i++) {
            result[i] = tab[tab.length - 1 - i]; // Inverse le tableau
        }
        return result;
    }

    /**
     * Convertit une chaîne binaire en un tableau d'entiers.
     * @param chaine La chaîne binaire à convertir.
     * @return Un tableau d'entiers représentant la chaîne binaire.
     */
    int[] convertionStringToInt(String chaine) {
        int[] result = new int[chaine.length()];
        for (int i = 0; i < chaine.length(); i++) {
            result[i] = (chaine.charAt(i) == '0') ? 0 : 1; // Conversion caractère par caractère
        }
        return reverseTab(result); // Inverse le résultat
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
     * Gère le tour de l'ordinateur pour jouer.
     * @param nbrAlumette Le tableau des allumettes.
     */
    void jeuOrdi(int[] nbrAlumette) {
        int[] tab = totalBin(nbrAlumette);
        int aEnlever = 0;
        boolean aJoué = false;
        int plusGrandIndex = -1;
		int plusGrandVal = 0;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] % 2 != 0) {
                aEnlever += (int) Math.pow(2, i);
            }
        }

        // Enlève les allumettes
        for (int j = 0; j < nbrAlumette.length && !aJoué; j++) {
            if (aEnlever > 0 && nbrAlumette[j] >= aEnlever) {
                nbrAlumette[j] -= aEnlever;
                aJoué = true;
            } else if (nbrAlumette[j] > plusGrandVal) {
                plusGrandIndex = j; // Indice de la ligne avec le plus grand nombre d'allumettes
                plusGrandVal = nbrAlumette[j];
            }
        }

        // Si aucune allumette n'a été retirée, enlève une allumettes de la ligne la plus fournie
        if (!aJoué && plusGrandIndex != -1) {
            nbrAlumette[plusGrandIndex] -= (int) Math.random()*(nbrAlumette.length-1)+1;
        }
    }
    
    /*#######################DECLARATION DES METHODES DE TESTS UNITAIRE####################*/
    
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
	 /**
	* Teste la méthode convertionBinaire()
	*/
	void testConvertionBinaire () {
		System.out.println ("\u001B[33m" +"*** testConversionBinaire() ***"+ "\u001B[0m");
		testCasConvertionBinaire(5, "101");
		testCasConvertionBinaire(0, "0");
		testCasConvertionBinaire(1, "1");
	}
	
	/**
	* teste un appel de testConvertionBinaire()
	* @param nbrAlumetteLigne le nombre d'alumettes dans une ligne et dont on doit
	* tester la conversion en binaire
	* @param result résultat attendu
	**/
	void testCasConvertionBinaire (int nbrAlumetteLigne, String result) {
		System.out.print ("convertionBinaire (" + nbrAlumetteLigne + ") \t= " + result + "\t : ");
		String resExec = convertionBinaire(nbrAlumetteLigne);
		boolean egaux = true;
		
		if (result.length() == resExec.length()){
			for (int i = 0; i<result.length(); i++){
				if (result.charAt(i) != resExec.charAt(i)){
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
	
	/**
	* teste un appel de testTotalBin()
	**/
	void testTotalBin () {
		System.out.println ("\u001B[33m" +"*** testTotalBin() ***"+ "\u001B[0m");
		int [] t1 = {1, 3};
		int [] t2 = {2, 1};
		int [] t3 = {1,3,5,7,9,11};
		int [] t4 = {6,3,2,2,0,0};
		int [] t5 = {1,3,5,7,9,11,13,15,17,19,21,23,25,27,29};
		int [] t6 =  {15,7,7,7,7,0,0,0,0,0,0,0,0,0,0};
		testCasTotalBin(t1, t2);
		testCasTotalBin(t3, t4);
		testCasTotalBin(t5, t6);
	}
	/**
	* teste un appel de totalBin()
	* @param nbrAlumette le tableau avec le nombre d'alumettes dans chaque ligne
	* @param result résultat attendu
	**/
	void testCasTotalBin (int [] nbrAlumette, int [] result) {
		System.out.print ("totalBin (");
		displayTab(nbrAlumette);
		System.out.print(") \t= ");
		displayTab(result);
		System.out.print("\t : ");
		int [] resExec = totalBin(nbrAlumette);
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
    
    
    /**
	* teste un appel de reverseTab()
	**/
    void testReverseTab () {
		System.out.println ("\u001B[33m" +"*** testReverseTab() ***"+ "\u001B[0m");
		int [] t1 = {1, 3};
		int [] t2 = {3, 1};
		int [] t3 = {};
		int [] t4 = {};
		int [] t5 = {1,2,3};
		int [] t6 =  {3,2,1};
		int [] t7 = {8};
		int [] t8 = {8};
		testCasReverseTab(t1, t2);
		testCasReverseTab(t3, t4);
		testCasReverseTab(t5, t6);
		testCasReverseTab(t7, t8);
	}
	/**
	* teste un appel de testReverseTab()
	* @param tab le nombre de lignes donné pour tester reverseTab
	* @param result résultat attendu
	**/
	void testCasReverseTab (int [] tab, int [] result) {
		System.out.print ("reverseTab (");
		displayTab(tab);
		System.out.print(") \t= ");
		displayTab(result);
		System.out.print("\t : ");
		int [] resExec = reverseTab(tab);
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
	
	
	/**
	* teste un appel de convertionStringToInt()
	**/
    void testConvertionStringToInt () {
		System.out.println ("\u001B[33m" +"*** testConvertionStringToInt() ***"+ "\u001B[0m");
		int [] t1 = {0,1,1,0,0,1};
		int [] t2 = {};
		int [] t3 = {1};
		testCasConvertionStringToInt("011001", t1);
		testCasConvertionStringToInt("", t2);
		testCasConvertionStringToInt("1", t3);
	}
	/**
	* teste un appel de testConvertionStringToInt()
	* @param chaine le nombre binaire sous forme de chaine de caractères
	* @param result résultat attendu
	**/
	void testCasConvertionStringToInt (String chaine, int [] result) {
		System.out.print ("convertionStringToInt (" + chaine + ") \t= ");
		displayTab(result);
		System.out.print("\t : ");
		int [] resExec = reverseTab(convertionStringToInt(chaine));
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
			System.err.println ("ERREUR \t : ");
		}
	}

	
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
	
}

