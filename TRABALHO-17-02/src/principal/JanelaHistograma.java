package principal;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class JanelaHistograma extends JFrame {

	private static final String TITULO = "Histograma";

	// referencia a imagem atual do programa
	private BufferedImage imagem;
	
	// valores do mapa do histograma
	private Map<Integer, Integer> mapa;

	public JanelaHistograma(BufferedImage imagem)
	{
		this.imagem = imagem;
		// carrega os dados da imagem para gerar o histograma
		carregarDados(imagem);
		// cria e exibe a janela e seus componentes com o mapa do histograma
		criarJanela();
	}
	
	// criacao basica da janela
	private void criarJanela() 
	{
		setTitle(TITULO);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		add(new JScrollPane(new Histograma(mapa)));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// recarrega os dados do histograma de acordo com a imagem atual
	private void recarregarDados() 
	{
		carregarDados(imagem);
		repaint();
	}
	
	// converter o valor int do rgb para escala de cinza
	private int converterParaCinza(BufferedImage imagem, int x, int y)
	{
		int rgb = imagem.getRGB(x, y);
		int r = (rgb >> 16) & 0xFF;		// vermelho
		int g = (rgb >> 8) &  0xFF;		// verde
		int b = (rgb & 		  0xFF);	// azul
		
		int cinza = (r + g + b) / 3; 	// gerar nivel de cinza
		return cinza;
	}

	private void carregarDados(BufferedImage imagem)
	{

		int largura = imagem.getWidth();
		int altura = imagem.getHeight();
		
		int[][] dados = new int[largura][altura];

		for (int y = 0; y < altura; y++)
		{
			for (int x = 0; x < largura; x++)
			{
				dados[x][y] = converterParaCinza(imagem, x, y);
			}
		}

		mapa = new TreeMap<Integer, Integer>();

		for (int y = 0; y < dados.length; y++)
		{
			for (int x = 0; x < dados[y].length; x++)
			{
				int valor = dados[y][x];
				int quantidade = 0;

				if (mapa.containsKey(valor))
				{
					quantidade = mapa.get(valor);
					quantidade++;
				} else
				{
					quantidade = 1;
				}
				
				mapa.put(valor, quantidade);
			}
		}
	}

	

	protected class Histograma extends JPanel {

		protected static final int LARGURADA_DA_LINHA = 3;
		protected static final int ALTURA_MINIMA = 128;
		protected static final int ALTURA_PADRAO = 256;
		
		private Map<Integer, Integer> histograma;

		public Histograma(Map<Integer, Integer> mapa)
		{
			this.histograma = mapa;
			int largura = (mapa.size() * LARGURADA_DA_LINHA) + 11;
			Dimension tamanhoMinimo = new Dimension(largura, ALTURA_MINIMA);
			Dimension tamanhoPadrao = new Dimension(largura, ALTURA_PADRAO);
			setMinimumSize(tamanhoMinimo);
			setPreferredSize(tamanhoPadrao);
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if (histograma != null)
			{
				int xOffset = 5;
				int yOffset = 5;
				int largura = getWidth() - 1 - (xOffset * 2);
				int altura = getHeight() - 1 - (yOffset * 2);
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setColor(Color.DARK_GRAY);
				g2d.drawRect(xOffset, yOffset, largura, altura);
				int barWidth = Math.max(LARGURADA_DA_LINHA, (int) Math.floor((float) largura / (float) histograma.size()));
				
				int valorMaximo = 0;
				for (Integer key : histograma.keySet()) {
					int valor = histograma.get(key);
					valorMaximo = Math.max(valorMaximo, valor);
				}
				int xPosicao = xOffset;
				for (Integer key : histograma.keySet()) {
					int value = histograma.get(key);
					int alturaBarra = Math.round(((float) value / (float) valorMaximo) * altura);
					g2d.setColor(new Color(key, key, key));
					int yPosicao = altura + yOffset - alturaBarra;
	
					Rectangle2D bar = new Rectangle2D.Float(xPosicao, yPosicao, barWidth, alturaBarra);
					g2d.fill(bar);
					g2d.setColor(Color.DARK_GRAY);
					g2d.draw(bar);
					xPosicao += barWidth;
				}
			}
		}
		
	}
	
}
