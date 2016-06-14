package SolitaireChess.metier.pieces;


import SolitaireChess.metier.Echiquier;

/**
 * SolitaireChess - Projet Tutoré
 * Classe métier qui gère les dames
 * @author Boulant Florian, Di Gregorio Thomas, Edouard Clemence et Emion Thibaut
 * @date 13/06/2016
 */
public class Dame extends Piece
{
	public Dame( Echiquier echiquier )
	{
		super( echiquier );
	}

	@Override
	public boolean peutSeDeplacer( int x, int y, int xCible, int yCible )
	{
		return Math.abs( xCible - x ) == Math.abs( yCible - y ) || x == xCible || y == yCible;
	}
}
