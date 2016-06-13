package SolitaireChess.ihm;


import SolitaireChess.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * SolitaireChess - Projet Tutoré
 *
 * @author Boulant Florian, Di Gregorio Thomas, Edouard Clemence et Emion Thibaut
 * @date 13/06/2016
 */
public class PanelSolitaireChess extends JPanel
{
	private final int        TAILLE_IMG;
	private       Controleur ctrl;

	public PanelSolitaireChess( Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.TAILLE_IMG = this.ctrl.getTailleImg();
		setPreferredSize( new Dimension( 400, 400 ) );

		GererSouris evtSouris = new GererSouris();
		addMouseListener( evtSouris );
		setFocusable( true );
	}


	public void paintComponent( Graphics g )
	{
		super.paintComponent( g );

		Graphics2D g2 = (Graphics2D)g;

		String sImg;
		Image  img;

		sImg = this.ctrl.getImgFond();
		img = getToolkit().getImage( sImg );
		g2.drawImage( img, 0, 0, null );

		for ( int i = 0; i < this.ctrl.getNbLigne(); i++ )
			for ( int j = 0; j < this.ctrl.getNbColonne(); j++ )
			{
				sImg = this.ctrl.getImg( i, j );
				img = getToolkit().getImage( sImg );
				g2.drawImage( img, j * TAILLE_IMG, i * TAILLE_IMG, this );
			}
	}


	private class GererSouris extends MouseAdapter
	{
		public void mouseClicked( MouseEvent e )
		{

		}
	}
}