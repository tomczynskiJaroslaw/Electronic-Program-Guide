package korEPG;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.imageio.ImageIO;

public class Program {
	private static Random r = new Random();
	private static long minStart = Long.MAX_VALUE;
	private static long maxStop = Long.MIN_VALUE;
	private long start;
	private long stop;
	private Kanal kanal;
	private String tytul;
	private String opis;
	private Color kolor;
	private String adresIkony;
	private Image ikona;
	private String wiek;
	private String kategoria;

	public Program(String start, String stop, String kanal, String tytul, String opis, String wiek, String kategoria,
			String ikona) {
		super();
		this.start = zamien(start);
		this.stop = zamien(stop);
		if (minStart > this.start)
			minStart = this.start;
		if (maxStop < this.stop)
			maxStop = this.stop;
		this.kanal = Kanal.getKanal(kanal);
		this.tytul = tytul;
		this.opis = opis;
		kolor = new Color(losujKolor(), losujKolor(), losujKolor());
		this.kanal.addProgram(this);
		if (wiek==null) this.wiek=""; else this.wiek = wiek;
		if (kategoria==null) this.kategoria=""; else this.kategoria = kategoria;
		this.adresIkony=ikona;
		// setIkona(ikona);
	}

	
	public void zaladuj() {
		new Thread() {
			public void run() {
				try {
					URL url = new URL(adresIkony);
		
					ikona = ImageIO.read(url);
				} catch (IOException e) {
				}
				Okienko.getUstawIkone(ikona);
				Okienko.odmaluj();
			};
		}.start();
		
		
	}
	
	public static void wczytujIkonyZInternetu() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				try {
				for (Kanal k : Kanal.kanaly.values()) {
					for (Program p : k.listaProgramow) {
						try {
							URL url = new URL(p.adresIkony);

							p.ikona = ImageIO.read(url);
						} catch (IOException e) {
						}
						Okienko.odmaluj();
					}
				}
				}catch (OutOfMemoryError e) {
					System.out.println("Skonczyla sie pamiec :-(");
				}
			}
		}.start();

	}

	private int losujKolor() {
		return r.nextInt(256);
	}

	private long zamien(String data) {
		int rok = Integer.parseInt(data.substring(0, 4));
		int miesiac = Integer.parseInt(data.substring(4, 6));
		int dzien = Integer.parseInt(data.substring(6, 8));
		int godzina = Integer.parseInt(data.substring(8, 10));
		int minuta = Integer.parseInt(data.substring(10, 12));
		return new GregorianCalendar(rok, miesiac, dzien, godzina, minuta).getTime().getTime();
	}

	public long getStart() {
		return start;
	}

	public long getStop() {
		return stop;
	}

	public Kanal getKanal() {
		return kanal;
	}

	public String getTytul() {
		return tytul;
	}

	public String getOpis() {
		return opis;
	}

	public Color getKolor() {
		return kolor;
	}

	@Override
	public String toString() {
		return "\tProgram [tytul=" + tytul + "]\n";
	}

	public static long getMinStart() {
		return minStart;
	}

	public static long getMaxStop() {
		return maxStop;
	}

	public int getX() {
		int x = (int) (getStart() - Program.getMinStart());
		x /= Okienko.getSkala();
		return x;
	}

	public int getDlugosc() {
		int dlugosc = (int) (getStop() - getStart());
		dlugosc /= Okienko.getSkala();
		return dlugosc;
	}

	public String getGodzinaStart() {
		DateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(getStart());
	}

	public String getGodzinaStop() {
		DateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(getStop());
	}

	public Image getIkona() {
		return ikona;
	}

	public String getWiek() {
		return wiek;
	}

	public String getKategoria() {
		return kategoria;
	}
}
