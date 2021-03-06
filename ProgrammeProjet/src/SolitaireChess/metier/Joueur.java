package SolitaireChess.metier;

/**
 * SolitaireChess - Projet Tutoré
 * Classe métier qui désigne un joueur.
 *
 * @author Boulant Florian, Di Gregorio Thomas, Edouard Clemence et Emion Thibaut
 * @date 13/06/2016
 */

import SolitaireChess.Controleur;

import java.io.Serializable;

public class Joueur implements Serializable
{
	private String nom;
	private int    mouvements;
	private int    theme;

	private boolean[][] defisAccomplis;
	private boolean[][] defisDebloques;
	private int[]       dernierDefi;

	private Controleur ctrl;


	/**
	 * Construit un joueur avec le nom passé en paramètre.
	 * Lui attribue un nombre de mouvements (par défaut 0) et un thème (par défaut 1).
	 * Crée également des tableaux de booléens permettant de représenter l'avancement du joueur (défis accomplis et
	 * défis débloqués).
	 *
	 * @param nom nom du joueur
	 * @param ctrl controleur lié au joueur courant
	 */
	public Joueur( String nom, Controleur ctrl )
	{
		this.nom = nom;
		this.mouvements = 0;
		this.theme = 1;

		this.ctrl = ctrl;
		this.defisAccomplis = new boolean[4][15];
		this.defisDebloques = new boolean[4][15];
		this.defisDebloques[0][0] = true;
		this.dernierDefi = new int[]{ 1, 1 };
	}


	/**
	 * Incrémente le nombre de mouvements du joueur courant.
	 */
	public void incrementerMouvements()
	{
		mouvements++;
	}


	/**
	 * Ajoute le défi dont le niveau et le numéro sont passés en paramètre à la liste des défis accomplis.
	 * Modifie en conséquence les défis débloqués.
	 *
	 * @param i niveau du défi
	 * @param j numéro du défi
	 */
	public void addDefiAccompli( int i, int j )
	{
		defisAccomplis[i-1][( j - 1 ) % 15] = true;
		defisDebloques[i-1][j % 15] = true;
	}


	/**
	 * Retourne le nom du joueur courant.
	 *
	 * @return nom du joueur courant
	 */
	public String getNom()
	{
		return nom;
	}


	/**
	 * Retourne le nombre de mouvements du joueur courant.
	 *
	 * @return nombre de mouvements du joueur courant
	 */
	public int getNbMouvements()
	{
		return mouvements;
	}


	/**
	 * Retourne le thème du joueur courant.
	 *
	 * @return thème du joueur courant
	 */
	public int getTheme()
	{
		return theme;
	}

	/**
	 * Retourne un tableau de booléens représentant l'avancement du joueur dans le niveau passé en paramètre.
	 *
	 * @param i niveau
	 * @return tableau de booléens représentant les défis accomplis dans le niveau passé en paramètre
	 */
	public boolean[] getDefisAccomplis( int i ) { return defisAccomplis[i]; }


	/**
	 * Retourne un tableau de booléens représentant les défis débloqués par le joueur dans le niveau passé en paramètre.
	 *
	 * @param i niveau
	 * @return tableau de booléens représentant les défis débloqués dans le niveau passé en paramètre
	 */
	public boolean[] getDefisDebloques(int i) {return defisDebloques[i];}


	/**
	 * Indique si le défi dont le niveau et le numéro passés en paramètre a été débloqué.
	 *
	 * @param i niveau du défi
	 * @param j numéro du défi
	 * @return <b>true</b> si le défi a été débloqué
	 */
	public boolean getDefiDebloque( int i, int j )
	{
		return defisDebloques[i][j];
	}


	/**
	 * Retourne le numéro du dernier défi atteint par le joueur courant.
	 *
	 * @return numéro du dernier défi atteint par le joueur courant
	 */
	public int[] getDernierDefi()
	{
		return dernierDefi;
	}


	/**
	 * Retourne le controleur associé au joueur courant.
	 *
	 * @return controleur associé au joueur courant
	 */
	public Controleur getCtrl() {
		return ctrl;
	}

	/**
	 * Redéfinis le nom du joueur courant avec le nom passé en paramètre.
	 *
	 * @param nom nouveau nom du joueur
	 */
	public void setNom( String nom )
	{
		this.nom = nom;
	}


	/**
	 * Redéfinis le nombre de mouvements effectués par le joueur courant avec le nombre de mouvements passé en
	 * paramètre.
	 *
	 * @param mouvements nouveau nombre de mouvements
	 */
	public void setNbMouvements(int mouvements) {
		this.mouvements = mouvements;
	}


	/**
	 * Redéfinis le thème avec le thème passé en paramètre.
	 *
	 * @param theme nouveau thème
	 */
	public void setTheme( int theme )
	{
		this.theme = theme;
	}

	/**
	 * Redéfinis le tableau de booléens représentant les défis accomplis avec le tableau passé en paramètre.
	 *
	 * @param defisAccomplis nouveau tableau représentant les défis accomplis par le joueur courant
	 */
	public void setDefisAccomplis(boolean[][] defisAccomplis) {
		this.defisAccomplis = defisAccomplis;
	}

	/**
	 * Redéfinis le tableau de booléens représentant les défis debloques avec le tableau passé en paramètre.
	 *
	 * @param defisDebloques nouveau tableau représentant les défis débloqués par le joueur courant
	 */
	public void setDefisDebloques(boolean[][] defisDebloques) {
		this.defisDebloques = defisDebloques;
	}


	/**
	 * Remplace le dernier défi atteint par le joueur courant par le défi dont le niveau et le numéro sont passés en
	 * paramètre.
	 *
	 * @param niveau niveau du défi
	 * @param defi numéro du défi
	 */
	public void setDernierDefi( int niveau, int defi )
	{
		dernierDefi = new int[]{ niveau, defi };
	}


	/**
	 * Redéfinis le controleur associé au joueur avec le controleur passé en paramètre.
	 *
	 * @param ctrl nouveau controleur associé au joueur
	 */
	public void setCtrl(Controleur ctrl) {
		this.ctrl = ctrl;
	}
}
