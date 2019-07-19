package korEPG;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
 * Plutno na ktorym wyswietlane sa wszystkie programy ze wszystkich kanalow
 */
public class Panel extends JPanel{
	
	public Panel() {
		//OBSLUGUJEMY KLIKNIECIE NA DANY PROGRAM
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(final MouseEvent e) {
				super.mousePressed(e);
				int grubosc=Okienko.getGrubosc();
				int h=0;
				//Przechodzimy po kazdym kanale
				for (Kanal k: Kanal.kanaly.values()) {
					//i po kazdym programie z danego kanalu
					for (Program p: k.listaProgramow) {
						//i sprawdzamy czy wspulrzedne klikniecia zawieraja sie w obszarze wyznaczonym przez program
						if (new Rectangle(p.getX(), h, p.getDlugosc()-2, grubosc).contains(e.getPoint())) {
							//Sklejamy dane o programie w jeden napis, rozdzielajac je znakiem nowej lini "\n"
							String szczegolyProgramu = "";
							szczegolyProgramu+=p.getTytul()+"\n";
							szczegolyProgramu+=p.getGodzinaStart()+" - "+p.getGodzinaStop()+"\n";
							szczegolyProgramu+=p.getOpis()+"\n";
							szczegolyProgramu+=p.getWiek()+"\n";
							szczegolyProgramu+=p.getKategoria()+"\n";
							//I umieszczamy je w polu tekstowym na dole okna
							Okienko.setSzczegolyProgramu(szczegolyProgramu);
							//Oraz ustawiamy obrazek z miniaturka/ikona/obrazkiem programu
							p.zaladuj();
							//Okienko.getUstawIkone(p.getIkona());
						}
					}
					//Programy kazdego nowego kanalu sa o grubosc programu nizej + margines (biala kreska rozdzielajaca kalejne kanaly)
					h+=grubosc+2;
				}
			}
		});
		
	}
	
	//MALUJEMY PROGRAMY
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, Okienko.getRozmiarplachty().width,Okienko.getRozmiarplachty().height);
		int h=0;
		int grubosc=Okienko.getGrubosc();
		g.drawLine(0, 0, 600, 400);
		for (Kanal k: Kanal.kanaly.values()) {
			for (Program p: k.listaProgramow) {
				g.setColor(p.getKolor());
				g.fillRect(p.getX(), h, p.getDlugosc()-5, grubosc);
				g.setColor(Color.WHITE);
				Image ikona = p.getIkona();
				int x = IkonaPanel.przeliczX(ikona, grubosc-8);
				g.drawString(p.getTytul(), p.getX()+x+5+4, h+grubosc/2);
				g.drawImage(p.getIkona(), p.getX()+4, h+4,x,grubosc-8, null);
				g.setColor(Color.black);
				g.fillRect(p.getX()+p.getDlugosc()-5, h, 5, grubosc);
				
			}
			g.setColor(Color.white);
			g.fillRect(0, h-2, Okienko.getRozmiarplachty().width, 2);
			for (int i=0;i<Okienko.rozmiarPlachty.width;i+=300) {
				g.setColor(Color.white);
				g.fillRect(i, h-10, 2, 20);
			}
			
			h+=grubosc+2;
		}
	}

}
