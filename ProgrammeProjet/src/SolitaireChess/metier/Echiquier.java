package SolitaireChess.metier;

/**
 * SolitaireChess - Projet Tutoré
 * Classe métier qui gère les échiquiers.
 *
 * @author Boulant Florian, Di Gregorio Thomas, Edouard Clemence et Emion Thibaut
 * @date 13/06/2016
 */

import SolitaireChess.Controleur;
import SolitaireChess.metier.pieces.*;

import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Echiquier implements Serializable
{
	private Piece[][]  echiquier;
	private int        defi;
	private int        niveau;
	private int        nbPiece;
	private int        nbIndice;
	private boolean    aUnRoi;
	private String     sFichier;
	private Controleur ctrl;

	private ArrayList<Piece[][]> mouvements;

	public boolean niveauUtilisateur;
	public int     defiUtilisateur;


	/**
	 * Construit un échiquier.
	 *
	 * @param ctrl controleur lié à l'échiquier courant
	 */
	public Echiquier( Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.aUnRoi = false;
		this.mouvements = new ArrayList<>();
	}


	/**
	 * Construit un échiquier à partir d'un autre échiquier.
	 *
	 * @param autre l'autre échiquier
	 * @return tableau de Piece représentant l'échiquier
	 */
	public static Piece[][] clonerEchiquier( Piece[][] autre )
	{
		Piece[][] tmp = new Piece[4][4];

		for ( int i = 0; i < tmp.length; i++ )
			for ( int j = 0; j < tmp[i].length; j++ )
				tmp[i][j] = Piece.clonerPiece( autre[i][j] );

		return tmp;
	}


	public void setDefi()
	{
		niveau = ctrl.getJoueurCourant().getDernierDefi()[0];
		defi = ctrl.getJoueurCourant().getDernierDefi()[1];
		this.initDefi();
	}


	public void setEchiquier( int niveau, int defi )
	{
		this.niveau = niveau;
		this.defi = defi;
		initDefi();
	}


	/**
	 * Permet de déplacer une pièce sur l'échiquier.
	 *
	 * @param x1 la position horizontale de la pièce à déplacer
	 * @param y1 la position verticale de la pièce à déplacer
	 * @param x2 la position horizontale de la pièce vers laquelle déplacer
	 * @param y2 la position verticale de la pièce vers laquelle déplacer
	 * @return vrai si on peut déplacer la pièce, sinon faux
	 */
	public boolean deplacer( int x1, int y1, int x2, int y2 )
	{
		boolean estRoi = false;
		if ( aUnRoi ) estRoi = echiquier[x2][y2] instanceof
				Roi;

		if ( echiquier[x1][y1].deplacer( x2, y2 ) )
		{
			nbPiece--;
			mouvements.add( Echiquier.clonerEchiquier( echiquier ) );
			ctrl.getJoueurCourant().incrementerMouvements();

			if ( estRoi || nbPiece > 1 && aPerdu() ) recommencer();
			else if ( aGagne() )
			{
				ctrl.afficherMessage( "Gagné" );
				if(niveauUtilisateur)
				{
					initDefi();
				}
				else
				{
					ctrl.getJoueurCourant().addDefiAccompli( niveau, defi );
					ctrl.enregistrer();
					incrementerDefi();
				}
			}
			else
				return true;
		}

		return false;
	}


	/**
	 * Permet de recommencer un niveau en cours.
	 */
	public void recommencer()
	{
		if( niveauUtilisateur )
			initDefiUtilisateur(defiUtilisateur);
		else
			initDefi();

		getCtrl().getJoueurCourant().incrementerMouvements();
		getCtrl().getJoueurCourant().incrementerMouvements();
		ctrl.majIHM();
	}


	/**
	 * Permet de savoir si on a gagné le niveau en cours.
	 *
	 * @return vrai si on gagné, sinon faux
	 */
	private boolean aGagne()
	{
		return nbPiece == 1;
	}


	/**
	 * Permet de savoir si on a perdu le niveau en cours.
	 *
	 * @return vrai si on a perdu, sinon faux
	 */
	private boolean aPerdu()
	{
		for ( int i = 0; i < echiquier.length; i++ )
			for ( int j = 0; j < echiquier[0].length; j++ )
				if ( echiquier[i][j] != null && echiquier[i][j].peutPrendreUnePiece() )
					return false;

		ctrl.afficherMessage( "Perdu !" );
		return true;
	}


	/**
	 * Incrémente le defi  une fois que celui-ci est fini.
	 */
	private void incrementerDefi()
	{
		if ( defi % 15 == 0 )
			niveau++;

		if ( defi < 60 )
			defi++;

		initDefi();
		ctrl.majIHM();
	}


	/**
	 * Initialise le défi cours en fonction du niveau.
	 */
	private void initDefi()
	{
		nbIndice = 0;

		sFichier = String.format( "./niveaux/niveau%02d/defi%02d.data", niveau, defi );

		parcourirFichier();

		mouvements.clear();
		mouvements.add( Echiquier.clonerEchiquier( echiquier ) );

		ctrl.getJoueurCourant().setDernierDefi( niveau, defi );
	}

	/**
	 * Initialise le défi cours en fonction du niveau.
	 */
	public void initDefiUtilisateur( int defi )
	{
		niveauUtilisateur = true;
		defiUtilisateur = defi;

		sFichier = String.format( "./niveaux/niveauUtilisateur/defi%02d.data", defi );

		parcourirFichier();

		mouvements.clear();
		mouvements.add( Echiquier.clonerEchiquier( echiquier ) );
	}


	private void parcourirFichier()
	{
		echiquier = new Piece[4][4];

		try
		{
			Scanner sc = new Scanner( new FileReader( sFichier ) );

			sc.useDelimiter( "-----" );

			String ligSc = "";

			for ( int i = 0; sc.hasNextLine() && i < nbIndice * 5; i++ )
			{
				ligSc = sc.nextLine();
			}

			nbPiece = 0;

			for ( int i = 0; sc.hasNextLine() && i < echiquier.length; i++ )
			{
				ligSc = sc.nextLine();

				for ( int j = 0; j < ligSc.length() && j < echiquier[0].length; j++ )
					ajouterPiece( i, j, ligSc.charAt( j ) );

			}
			sc.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}


	public void initIndiceDefi()
	{
		if (niveau<2 && ! niveauUtilisateur)
		{
			sFichier = String.format( "./solutions/niveau01/defi%02d.data", defi );

			parcourirFichier();

			nbIndice++;

			try
			{
				Thread.sleep( 75 );
			} catch ( InterruptedException exe ) {}

			parcourirFichier();

			try
			{
				Thread.sleep( 25 );
			} catch ( InterruptedException exe ) {}

			getCtrl().majIHM();

			if ( aGagne() )
			{
				ctrl.afficherMessage( "Gagné" );
				ctrl.getJoueurCourant().addDefiAccompli( niveau, defi );
				ctrl.enregistrer();
				incrementerDefi();
			}
		}
	}


	public void annuler()
	{
		if ( mouvements.size() > 1 )
		{
			mouvements.remove( mouvements.size() - 1 );
			echiquier = Echiquier.clonerEchiquier( mouvements.get( mouvements.size() - 1 ) );
			nbPiece++;
			ctrl.majIHM();
		}

	}


	/**
	 * Ajoute un pièce à l'échiquier.
	 *
	 * @param lig   la position horizontale de la pièce à créer
	 * @param col   la position verticale de la pièce à créer
	 * @param piece le caractère permettant de savoir quelle pièce créer
	 */
	private void ajouterPiece( int lig, int col, char piece )
	{
		if ( piece == 'R' )
		{
			echiquier[lig][col] = new Roi( lig, col, this );
			aUnRoi = true;
		}
		else if ( piece == 'D' ) echiquier[lig][col] = new Dame( lig, col, this );
		else if ( piece == 'C' ) echiquier[lig][col] = new Cavalier( lig, col, this );
		else if ( piece == 'T' ) echiquier[lig][col] = new Tour( lig, col, this );
		else if ( piece == 'F' ) echiquier[lig][col] = new Fou( lig, col, this );
		else if ( piece == 'P' ) echiquier[lig][col] = new Pion( lig, col, this );

		if ( echiquier[lig][col] != null ) nbPiece++;
	}


	/**
	 * Permet d'obtenir le nom de la pièce d'une case de l'échiquier.
	 *
	 * @param i la position horizontale de la pièce
	 * @param j la position verticale de la pièce
	 * @return nom de la pièce
	 */
	public String getSymbole( int i, int j )
	{
		if ( echiquier[i][j] != null )
			return echiquier[i][j].getSymbole();

		return null;
	}


	/**
	 * Permet de retourner l'échiquier.
	 *
	 * @return l'échiquier.
	 */
	public Piece[][] getEchiquier()
	{
		return echiquier;
	}


	/**
	 * Permet d'obtenir le numéro du défi en cours.
	 *
	 * @return le numéro du défi en cours
	 */
	public int getDefi()
	{
		return defi;
	}


	/**
	 * Permet d'obtenir le numéro du niveau en cours.
	 *
	 * @return le numéro du niveau en cours
	 */
	public int getNiveau()
	{
		return niveau;
	}


	/**
	 * Permet d'obtenir le nombre de lignes de l'échiquier.
	 *
	 * @return le nombre de lignes de l'échiquier
	 */
	public int getNbLigne() { return echiquier.length; }


	/**
	 * Permet d'obtenir le nombre de colonnes de l'échiquier.
	 *
	 * @return le nombre de colonnes de l'échiquier
	 */
	public int getNbColonne() { return echiquier[0].length; }


	/**
	 * Permet de savoir si un roi est présent sur le niveau en cours.
	 *
	 * @return vrai si un roi est sur l'échiquier du niveau actuel, sinon faux
	 */
	public boolean isaUnRoi()
	{
		return aUnRoi;
	}


	/**
	 * Permet d'obtenir le nombre de pièces restantes sur l'échiquier.
	 *
	 * @return le nombre de pièces restantes sur l'échiquier
	 */
	public int getNbPiece()
	{
		return nbPiece;
	}


	/**
	 * Permet d'obtenir le nombre de mouvements.
	 *
	 * @return le nombre de mouvements
	 */
	public int getNbMouvements()
	{
		return 0;
	}


	public Controleur getCtrl() { return ctrl; }
}
