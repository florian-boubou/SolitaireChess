package SolitaireChess.metier;


/**
 * SolitaireChess - Projet Tutoré
 *
 * @author Boulant Florian, Di Gregorio Thomas, Edouard Clemence et Emion Thibaut
 * @date 13/06/2016
 */

import SolitaireChess.metier.pieces.*;

import java.io.FileReader;
import java.util.Scanner;

public class Echiquier
{
	private Piece[][] echiquier;
	private int       defi;
	private int       niveau;


	public Echiquier( int niveau, int defi )
	{
		this.echiquier = new Piece[4][4];
		this.niveau = niveau;
		this.defi = defi;
		this.initNiveau();
	}


	private void initNiveau()
	{
		String sFichier = String.format( "/Users/thomasdigregorio/Documents/OneDrive/Documents/SolitaireChess" +
										 "/ProgrammeProjet/niveaux/niveau%02d/defi%02d.data", this.niveau, this.defi );

		try
		{
			Scanner sc = new Scanner( new FileReader( sFichier ) );

			String ligSc = "";

			for ( int i = 0; i < this.echiquier.length && sc.hasNextLine(); i++ )
			{
				ligSc = sc.nextLine();

				for ( int j = 0; j < this.echiquier[0].length && j < ligSc.length(); j++ )
				{
					this.ajouterPiece( i, j, ligSc.charAt( j ) );
				}
			}
			sc.close();
		} catch ( Exception e ) { e.printStackTrace(); }
	}


	private void ajouterPiece( int lig, int col, char piece )
	{
		if ( piece == 'R' ) this.echiquier[lig][col] = new Roi( this );
		else if ( piece == 'D' ) this.echiquier[lig][col] = new Dame( this );
		else if ( piece == 'C' ) this.echiquier[lig][col] = new Cavalier( this );
		else if ( piece == 'T' ) this.echiquier[lig][col] = new Tour( this );
		else if ( piece == 'F' ) this.echiquier[lig][col] = new Fou( this );
		else if ( piece == 'P' ) this.echiquier[lig][col] = new Pion( this );
	}


	public String getSymbole( int i, int j )
	{
		if ( this.echiquier[i][j] != null )
			return this.echiquier[i][j].getSymbole();

		return null;
	}


	public int getDefi()
	{
		return this.defi;
	}


	public int getNiveau()
	{
		return this.niveau;
	}


	public int getNbLigne()   { return 4; }


	public int getNbColonne() { return 4; }
}