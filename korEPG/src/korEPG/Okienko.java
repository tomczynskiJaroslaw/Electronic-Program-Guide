package korEPG;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Okienko extends JFrame{
	/**
	 * rozmiar w pionie paska z programem telewizyjnym
	 */
	private static int grubosc=50;
	/**
	 * rozmiar w poziomie paska z programem telewizyjnym
	 */
	private static int skala=20000;
	/**
	 * plutno na ktorym namalowany jest caly program telewizyjny (wszystkie paski symbolizujace programy)
	 */
	private static Panel panel;
	/**
	 * panel boczny z nazwami kanalow
	 */
	private PanelNazwyKanalow panelKanaly;
	/**
	 * panel gorny z wypisanymi godzinami, dzieki czemu mozna umiescic konkretne programy w czasie
	 */
	private PanelOsCzasu panelOsCzasu;
	/**
	 * Rozmiar {@link Okienko#panel}. O jego wysokosci decyduje liczba kanalow wymnozona przez grubosc+margines(pozioma kreska rozdzielajaca kanaly) jednego paska symbolizujacego program.
	 * A szerokosc to czas zakonczenia ostatniego programu pomniejszona o czas rozpoczecia najwczesniejszego programu. 
	 * Tak aby zmiescily sie na nim wszystkie programy na wszystkich kanalach
	 */
	final static Dimension rozmiarPlachty = new Dimension((int) (Program.getMaxStop()-Program.getMinStart())/skala,Kanal.kanaly.size()*(grubosc+2));
	/**
	 * Dolne pole tekstowe w ktorym wypisuja sie szczegolowe dane dotyczoce programu takie jak opis, godziny trwania, ...
	 */
	private static JTextArea opisProgramu;
	
	/**
	 * Dolny panel ktory zawiera zarowno {@link Okienko#obrazekPanel}, jak i {@link Okienko#opisProgramu}
	 */
	private static JPanel opisProgramuPanel = new JPanel();
	
	/**
	 * Panel w lewym dolnym rogu okna z ikona/obrazkiem/miniaturka programu
	 */
	private static IkonaPanel obrazekPanel = new IkonaPanel();
	
	public Okienko() {
		//ustawiamy rozklad elementow w oknie Sprawdz czym jest "BorderLayout"
		setLayout(new BorderLayout());
		
		panel=new Panel();
		//Ustalamy rozmiar panelu z programami
		System.out.println(rozmiarPlachty+" "+Kanal.kanaly.size());
		panel.setPreferredSize(rozmiarPlachty);
		panel.setOpaque(false);//optymalizacja, aby panel sie nie zacinal przy przewijaniu
		panel.setDoubleBuffered(false);//optymalizacja, aby panel sie nie zacinal przy przewijaniu
		//komponent ktory umozliwia zmiaszczenie wiekszego elementu w mniejszym poprzez dodanie suwakow umorzliwiajacych jego przewijanie
		//bedzie on przechowywal oczywiscie "panel"
		JScrollPane jsp = new JScrollPane(panel);
		//decyduje o predkosci przewijania w pionie
		jsp.getVerticalScrollBar().setUnitIncrement(16);
		//umieszczamy w glownym obszarze 
		add(jsp,BorderLayout.CENTER);
		
		
		panelKanaly=new PanelNazwyKanalow();
		panelKanaly.setPreferredSize(new Dimension(100, rozmiarPlachty.height));
		jsp.setRowHeaderView(panelKanaly);
		
		panelOsCzasu=new PanelOsCzasu();
		panelOsCzasu.setPreferredSize(new Dimension(rozmiarPlachty.width, 40));
		jsp.setColumnHeaderView(panelOsCzasu);
		
		opisProgramuPanel = new JPanel();
		opisProgramuPanel.setLayout(new BorderLayout());
		
		
		opisProgramuPanel.add(obrazekPanel,BorderLayout.WEST);
		
		opisProgramu=new JTextArea();
		opisProgramu.setEditable(false);
		opisProgramu.setLineWrap(true);
		opisProgramu.setWrapStyleWord(true);
		opisProgramu.setFont(new Font("Arial", 0, 20));
		JScrollPane jsp2 = new JScrollPane(opisProgramu);
//		jsp2.add(opisProgramu);
		opisProgramuPanel.add(jsp2,BorderLayout.CENTER);
		//interesuje nas tylko nadanie odpowiedniej wysokosci, gdyz 
		//ten komponent jest ustawiony w BorderLayout.CENTER, czyli 
		//wypelnia cala dostepna przestrzen, ktora to ograniczaja
		//komponenty dookola
		opisProgramuPanel.setPreferredSize(new Dimension(0, IkonaPanel.wysokosc));
		
		add(opisProgramuPanel,BorderLayout.SOUTH);
		
		//opcje okienka
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setVisible(true);
	}

	public static int getGrubosc() {
		return grubosc;
	}
	
	public static int getSkala() {
		return skala;
	}
	
	public static void setDlugosc(int d) {
		skala=d;
	}
	
	public static void setSzczegolyProgramu(String opis) {
		opisProgramu.setText(opis);
	}
	
	public static void odmaluj() {
		panel.repaint();
	}
	
	public static void getUstawIkone(final Image ikona) {
		obrazekPanel.zmienObrazek(ikona);
		opisProgramuPanel.revalidate();
	}

	public static Dimension getRozmiarplachty() {
		return rozmiarPlachty;
	}
}
