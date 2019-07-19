package korEPG;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Panel w lewym dolnym rogu (kolo opisu programu) z obrazkiem
 */
public class IkonaPanel extends JPanel{
	/**
	 * Wysokosc calego dolnego panelu w tym w szczegulgosci obrazka
	 */
	final static int wysokosc = 150;
	/**
	 * obrazek
	 */
	Image ikona;
	/**
	 * szerokosc obrazka wyliczana proporcjonalnie do wysokosci
	 */
	int x;
	
	/**
	 * Standardowa metoda umozliwiajaca rysowanie 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//malujemy obrazek w skali
		g.drawImage(ikona,0,0,x,wysokosc,null);
	}
	
	/**
	 * Podmienia obrazek
	 */
	public void zmienObrazek(Image obrazek) {
		ikona=obrazek;
		x = przeliczX(ikona,wysokosc);
		//Ustawiamy szerokosc calego panelu na taka samoa jak szerokosc obrazka
		setPreferredSize(new Dimension(x, wysokosc));
		//odmalowujemy panel (posrednio wywolujemy metode "paintComponent")
		repaint();
	}
	
	/**
	 * 
	 * @param ikona - obrazek (miniaturka programu)
	 * @param y - wysokosc jaka chcemy, zeby mial nasz obrazek
	 * @return szerokosc jaka powinien miec nasz obrazek przy danej wysokosci
	 */
	public static int przeliczX(Image ikona,int y) {
		if (ikona==null) {
			//jesli nie ma obrazka
			return 1;
		}else {
			//wyliczamy z proporcji
			return ikona.getWidth(null)*y/ikona.getHeight(null);
		}
		
	}
}
