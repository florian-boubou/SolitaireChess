package SolitaireChess.metier.pieces;


import SolitaireChess.metier.Echiquier;

/**
 * SolitaireChess - Projet Tutoré
 *
 * @author Boulant Florian, Di Gregorio Thomas, Edouard Clemence et Emion Thibaut
 * @date 13/06/2016
 */
public class Tour extends Piece
{
	public Tour( Echiquier echiquier )
	{
		super( echiquier );
	}


	@Override
	public boolean deplacer()
	{
		return false;
	}
}