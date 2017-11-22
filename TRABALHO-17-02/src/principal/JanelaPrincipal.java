package principal;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame {

	public static final String NOME = "Processamento de Imagens";
	
	public  final int LARGURA_INI = 1280;
	public final int ALTURA_INI = 720;
	public final int LARGURA_MIN = 720;
	public final int ALTURA_MIN = 480;
	
	public final Font fonteBotoes = new Font("Tahoma", Font.PLAIN, 14);
	public final Font fonteDosTextos = new Font("Tahoma", Font.PLAIN, 16);

	private CampoImagem campoImagem;
	private ManipuladorImagem processadorDeImagens;

	private JTextField campoTextoLargura;
	private JTextField campoTextoAltura;
	
	JButton botaoRedimensionar;
	
	JCheckBox marcadorManterProporcao;

	public static void main(String[] args) {
		new JanelaPrincipal();
	}

	public JanelaPrincipal() {
		this.setTitle(NOME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(LARGURA_MIN, ALTURA_MIN));
		this.setPreferredSize(new Dimension(LARGURA_INI, ALTURA_INI));
		this.setSize(new Dimension(LARGURA_INI, ALTURA_INI));
		this.setLocationRelativeTo(null);
		inicializarComponentes();
		this.setVisible(true);
	}

	private void inicializarComponentes() {
		processadorDeImagens = new ManipuladorImagem();
		// PAINEL PRINCIPAL
		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setBackground(Color.DARK_GRAY);
		painelPrincipal.setLayout(new BorderLayout());
		this.setContentPane(painelPrincipal);

		// PAINEL DE IMAGEM
		JPanel painelDeImagem = new JPanel();
		painelDeImagem.setBackground(Color.BLACK);
		painelPrincipal.add(painelDeImagem, BorderLayout.CENTER);
		painelDeImagem.setLayout(new BorderLayout());
		campoImagem = new CampoImagem();
		painelDeImagem.add(campoImagem, BorderLayout.CENTER);

		// PAINEL DE OPCOES
		JPanel painelOpcoes = new JPanel();
		painelOpcoes.setBackground(Color.LIGHT_GRAY);
		painelPrincipal.add(painelOpcoes, BorderLayout.WEST);
		GridBagLayout painelDeOpcoesLayout = new GridBagLayout();
		painelDeOpcoesLayout.columnWidths = new int[] { 140, 140 };
		painelDeOpcoesLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		painelDeOpcoesLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		painelDeOpcoesLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		painelOpcoes.setLayout(painelDeOpcoesLayout);

		// TEXTO IMAGEM
		JLabel textoImagem = new JLabel("Imagem");
		textoImagem.setForeground(new Color(50, 140, 220));
		textoImagem.setHorizontalAlignment(SwingConstants.CENTER);
		textoImagem.setFont(fonteDosTextos);
		GridBagConstraints textoImagemLayout = new GridBagConstraints();
		textoImagemLayout.gridwidth = 2;
		textoImagemLayout.fill = GridBagConstraints.HORIZONTAL;
		textoImagemLayout.insets = new Insets(15, 5, 5, 5);
		textoImagemLayout.gridx = 0;
		textoImagemLayout.gridy = 0;
		painelOpcoes.add(textoImagem, textoImagemLayout);

		// BOTAO ABRIR IMAGEM
		JButton botaoAbrirImagem = new JButton("Abrir");
		botaoAbrirImagem.setBackground(new Color(50, 140, 220));
		botaoAbrirImagem.setForeground(Color.WHITE);
		botaoAbrirImagem.setFont(fonteBotoes);
		botaoAbrirImagem.setFocusPainted(false);
		botaoAbrirImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				JFileChooser janelaAbrirImagem = new JFileChooser();
				int retorno = janelaAbrirImagem.showDialog(null, "Abrir Imagem");
				if (retorno == JFileChooser.APPROVE_OPTION) {
					try {
						campoImagem.abriImagem(janelaAbrirImagem.getSelectedFile().getAbsolutePath());
						setTamanhoDaImagem(campoImagem.imagem().getWidth(), campoImagem.imagem().getHeight());
					} catch (IOException erro) {
					}
				}
			}
		});
		GridBagConstraints botaoAbrirImagemLayout = new GridBagConstraints();
		botaoAbrirImagemLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoAbrirImagemLayout.insets = new Insets(0, 5, 5, 5);
		botaoAbrirImagemLayout.gridx = 0;
		botaoAbrirImagemLayout.gridy = 1;
		painelOpcoes.add(botaoAbrirImagem, botaoAbrirImagemLayout);

		// BOTAO SALVAR IMAGEM
		JButton botaoSalvarImagem = new JButton("Salvar");
		botaoSalvarImagem.setBackground(new Color(50, 140, 220));
		botaoSalvarImagem.setForeground(Color.WHITE);
		botaoSalvarImagem.setFont(fonteBotoes);
		botaoSalvarImagem.setFocusPainted(false);
		botaoSalvarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				JFileChooser janelaSalvarImagem = new JFileChooser();
				int retorno = janelaSalvarImagem.showDialog(null, "Salvar Imagem");
				if (retorno == JFileChooser.APPROVE_OPTION) {
					try {
						campoImagem.salvarImagem(janelaSalvarImagem.getSelectedFile().getAbsolutePath());
					} catch (IOException erro) {
					}
				}
			}
		});
		GridBagConstraints botaoSalvarImagemLayout = new GridBagConstraints();
		botaoSalvarImagemLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoSalvarImagemLayout.insets = new Insets(0, 0, 5, 5);
		botaoSalvarImagemLayout.gridx = 1;
		botaoSalvarImagemLayout.gridy = 1;
		painelOpcoes.add(botaoSalvarImagem, botaoSalvarImagemLayout);

		// TEXTO ROTACIONAR
		JLabel textoRotacionar = new JLabel("Rotacionar");
		textoRotacionar.setHorizontalAlignment(SwingConstants.CENTER);
		textoRotacionar.setFont(fonteDosTextos);
		GridBagConstraints textoRotacionarLayout = new GridBagConstraints();
		textoRotacionarLayout.fill = GridBagConstraints.HORIZONTAL;
		textoRotacionarLayout.gridwidth = 2;
		textoRotacionarLayout.insets = new Insets(15, 5, 5, 5);
		textoRotacionarLayout.gridx = 0;
		textoRotacionarLayout.gridy = 2;
		painelOpcoes.add(textoRotacionar, textoRotacionarLayout);

		// BOTAO ROTACIONAR ESQUERDA
		JButton botaoRotacionarEsquerda = new JButton("Equerda");
		botaoRotacionarEsquerda.setBackground(Color.WHITE);
		botaoRotacionarEsquerda.setFont(fonteBotoes);
		botaoRotacionarEsquerda.setFocusPainted(false);
		botaoRotacionarEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoImagem.trocarImagem(processadorDeImagens.rotacionarEsquerda(campoImagem.imagem()));
			}
		});
		GridBagConstraints botaoRotacionarEsquerdaLayout = new GridBagConstraints();
		botaoRotacionarEsquerdaLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoRotacionarEsquerdaLayout.insets = new Insets(0, 5, 5, 5);
		botaoRotacionarEsquerdaLayout.gridx = 0;
		botaoRotacionarEsquerdaLayout.gridy = 3;
		painelOpcoes.add(botaoRotacionarEsquerda, botaoRotacionarEsquerdaLayout);

		// BOTAO ROTACIONAR DIREITA
		JButton botaoRotacionarDireita = new JButton("Direita");
		botaoRotacionarDireita.setBackground(Color.WHITE);
		botaoRotacionarDireita.setFont(fonteBotoes);
		botaoRotacionarDireita.setFocusPainted(false);
		botaoRotacionarDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoImagem.trocarImagem(processadorDeImagens.rotacionarDireita(campoImagem.imagem()));
			}
		});
		GridBagConstraints botaoRotacionarDireitaLayout = new GridBagConstraints();
		botaoRotacionarDireitaLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoRotacionarDireitaLayout.insets = new Insets(0, 0, 5, 5);
		botaoRotacionarDireitaLayout.gridx = 1;
		botaoRotacionarDireitaLayout.gridy = 3;
		painelOpcoes.add(botaoRotacionarDireita, botaoRotacionarDireitaLayout);

		// TEXTO VIRAR
		JLabel textoVirar = new JLabel("Inverter");
		textoVirar.setFont(fonteDosTextos);
		textoVirar.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints textoVirarLayout = new GridBagConstraints();
		textoVirarLayout.gridwidth = 2;
		textoVirarLayout.insets = new Insets(0, 5, 5, 5);
		textoVirarLayout.gridx = 0;
		textoVirarLayout.gridy = 4;
		painelOpcoes.add(textoVirar, textoVirarLayout);

		// BOTAO VIRAR VERTICAL
		JButton botaoVirarVertical = new JButton("Vertical");
		botaoVirarVertical.setBackground(Color.WHITE);
		botaoVirarVertical.setFont(fonteBotoes);
		botaoVirarVertical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoImagem.trocarImagem(processadorDeImagens.virarVertical(campoImagem.imagem()));
			}
		});
		GridBagConstraints botaoVirarVerticalLayout = new GridBagConstraints();
		botaoVirarVerticalLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoVirarVerticalLayout.insets = new Insets(0, 5, 5, 5);
		botaoVirarVerticalLayout.gridx = 0;
		botaoVirarVerticalLayout.gridy = 5;
		painelOpcoes.add(botaoVirarVertical, botaoVirarVerticalLayout);

		// BOTAO VIRAR HORIZONTAL
		JButton botaoVirarHorizontal = new JButton("Horizontal");
		botaoVirarHorizontal.setBackground(Color.WHITE);
		botaoVirarHorizontal.setFont(fonteBotoes);
		botaoVirarHorizontal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoImagem.trocarImagem(processadorDeImagens.virarHorizontal(campoImagem.imagem()));
			}
		});
		GridBagConstraints botaoVirarHorizontalLayout = new GridBagConstraints();
		botaoVirarHorizontalLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoVirarHorizontalLayout.insets = new Insets(0, 0, 5, 5);
		botaoVirarHorizontalLayout.gridx = 1;
		botaoVirarHorizontalLayout.gridy = 5;
		painelOpcoes.add(botaoVirarHorizontal, botaoVirarHorizontalLayout);

		// TEXTO FILTROS
		JLabel textoFiltros = new JLabel("Filtros");
		textoFiltros.setFont(fonteDosTextos);
		textoFiltros.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints textoFiltrosLayout = new GridBagConstraints();
		textoFiltrosLayout.fill = GridBagConstraints.HORIZONTAL;
		textoFiltrosLayout.gridwidth = 2;
		textoFiltrosLayout.insets = new Insets(0, 5, 5, 5);
		textoFiltrosLayout.gridx = 0;
		textoFiltrosLayout.gridy = 6;
		painelOpcoes.add(textoFiltros, textoFiltrosLayout);

		// BOTAO ESCALA CINZA
		JButton botaoEscalaCinza = new JButton("Escala de cinza");
		botaoEscalaCinza.setBackground(Color.WHITE);
		botaoEscalaCinza.setFont(fonteBotoes);
		botaoEscalaCinza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoImagem.trocarImagem(processadorDeImagens.escalaCinza(campoImagem.imagem()));
			}
		});
		GridBagConstraints botaoEscalaCinzaLayout = new GridBagConstraints();
		botaoEscalaCinzaLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoEscalaCinzaLayout.insets = new Insets(0, 5, 5, 5);
		botaoEscalaCinzaLayout.gridx = 0;
		botaoEscalaCinzaLayout.gridy = 7;
		painelOpcoes.add(botaoEscalaCinza, botaoEscalaCinzaLayout);

		// BOTAO NEGATIVO
		JButton botaoNegativo = new JButton("Negativo");
		botaoNegativo.setBackground(Color.WHITE);
		botaoNegativo.setFont(fonteBotoes);
		botaoNegativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoImagem.trocarImagem(processadorDeImagens.negativo(campoImagem.imagem()));
			}
		});
		GridBagConstraints botaoNegativoLayout = new GridBagConstraints();
		botaoNegativoLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoNegativoLayout.insets = new Insets(0, 0, 5, 5);
		botaoNegativoLayout.gridx = 1;
		botaoNegativoLayout.gridy = 7;
		painelOpcoes.add(botaoNegativo, botaoNegativoLayout);

		// BOTAO MONO
		JButton botaoMono = new JButton("Preto e branco");
		botaoMono.setBackground(Color.WHITE);
		botaoMono.setFont(fonteBotoes);
		botaoMono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoImagem.trocarImagem(processadorDeImagens.mono(campoImagem.imagem()));
			}
		});
		GridBagConstraints botaoMonoLayout = new GridBagConstraints();
		botaoMonoLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoMonoLayout.insets = new Insets(0, 5, 5, 5);
		botaoMonoLayout.gridx = 0;
		botaoMonoLayout.gridy = 8;
		painelOpcoes.add(botaoMono, botaoMonoLayout);
		
		
		
		
		// TEXTO LARGURA
		JLabel textoLargura = new JLabel("Largura");
		textoLargura.setHorizontalAlignment(JLabel.HORIZONTAL);
		textoLargura.setBackground(Color.WHITE);
		textoLargura.setForeground(Color.BLACK);
		textoLargura.setFont(new Font("Times Roman", Font.PLAIN, 14));
		GridBagConstraints textoLarguraPosicao = new GridBagConstraints();
		textoLarguraPosicao.fill = GridBagConstraints.HORIZONTAL;
		textoLarguraPosicao.insets = new Insets(25, 5, 5, 5);
		textoLarguraPosicao.gridx = 0;
		textoLarguraPosicao.gridy = 9;
		painelOpcoes.add(textoLargura, textoLarguraPosicao);
		
		// TEXTO ALTURA
		JLabel textoAltura = new JLabel("Altura");
		textoAltura.setHorizontalAlignment(JLabel.HORIZONTAL);
		textoAltura.setBackground(Color.WHITE);
		textoAltura.setForeground(Color.BLACK);
		textoAltura.setFont(new Font("Times Roman", Font.PLAIN, 14));
		GridBagConstraints textoAlturaPosicao = new GridBagConstraints();
		textoAlturaPosicao.fill = GridBagConstraints.HORIZONTAL;
		textoAlturaPosicao.insets = new Insets(25, 0, 5, 5);
		textoAlturaPosicao.gridx = 1;
		textoAlturaPosicao.gridy = 9;
		painelOpcoes.add(textoAltura, textoAlturaPosicao);

		// CAMPO TEXTO LARGURA
		campoTextoLargura = new JTextField();
		campoTextoLargura.setHorizontalAlignment(JTextField.HORIZONTAL);
		campoTextoLargura.setBackground(Color.WHITE);
		campoTextoLargura.setForeground(Color.BLACK);
		campoTextoLargura.setFont(new Font("Times Roman", Font.PLAIN, 16));
		GridBagConstraints campoTextoLarguraPosicao = new GridBagConstraints();
		campoTextoLarguraPosicao.fill = GridBagConstraints.HORIZONTAL;
		campoTextoLarguraPosicao.insets = new Insets(0, 5, 5, 5);
		campoTextoLarguraPosicao.gridx = 0;
		campoTextoLarguraPosicao.gridy = 10;
		painelOpcoes.add(campoTextoLargura, campoTextoLarguraPosicao);

		// CAMPO TEXTO ALTURA
		campoTextoAltura = new JTextField();
		campoTextoAltura.setHorizontalAlignment(JTextField.HORIZONTAL);
		campoTextoAltura.setBackground(Color.WHITE);
		campoTextoAltura.setForeground(Color.BLACK);
		campoTextoAltura.setFont(new Font("Times Roman", Font.PLAIN, 16));
		GridBagConstraints campoTextoAlturaPosicao = new GridBagConstraints();
		campoTextoAlturaPosicao.fill = GridBagConstraints.HORIZONTAL;
		campoTextoAlturaPosicao.insets = new Insets(0, 0, 5, 5);
		campoTextoAlturaPosicao.gridx = 1;
		campoTextoAlturaPosicao.gridy = 10;
		painelOpcoes.add(campoTextoAltura, campoTextoAlturaPosicao);
		
		// BOTAO REDIMENSIONAR
		botaoRedimensionar = new JButton("Redimensionar");
		botaoRedimensionar.setFocusPainted(false);
		botaoRedimensionar.setBackground(Color.WHITE);
		botaoRedimensionar.setForeground(Color.BLACK);
		botaoRedimensionar.setFont(new Font("Times Roman", Font.PLAIN, 16));
		botaoRedimensionar.addActionListener(acaoRedimensionar());
		GridBagConstraints botaoRedimensionarPosicao = new GridBagConstraints();
		botaoRedimensionarPosicao.fill = GridBagConstraints.HORIZONTAL;
		botaoRedimensionarPosicao.insets = new Insets(0, 5, 5, 5);
		botaoRedimensionarPosicao.gridx = 0;
		botaoRedimensionarPosicao.gridy = 11;
		painelOpcoes.add(botaoRedimensionar, botaoRedimensionarPosicao);
		
		// MARCADOR MANTER PROPORCAO
		marcadorManterProporcao = new JCheckBox("Manter Proporção");
		marcadorManterProporcao.setHorizontalAlignment(JCheckBox.HORIZONTAL);
		marcadorManterProporcao.setFocusPainted(false);
		marcadorManterProporcao.setBackground(painelOpcoes.getBackground());
		marcadorManterProporcao.setForeground(Color.BLACK);
		marcadorManterProporcao.setFont(new Font("Times Roman", Font.PLAIN, 12));
		GridBagConstraints marcadorManterProporcaoPosicao = new GridBagConstraints();
		marcadorManterProporcaoPosicao.fill = GridBagConstraints.HORIZONTAL;
		marcadorManterProporcaoPosicao.insets = new Insets(0, 0, 5, 5);
		marcadorManterProporcaoPosicao.gridx = 1;
		marcadorManterProporcaoPosicao.gridy = 11;
		painelOpcoes.add(marcadorManterProporcao, marcadorManterProporcaoPosicao);
		
		// TEXTO AJUSTES
		JLabel textoAjustes = new JLabel("Ajustes");
		textoAjustes.setHorizontalAlignment(JLabel.HORIZONTAL);
		textoAjustes.setBackground(Color.WHITE);
		textoAjustes.setForeground(Color.BLACK);
		textoAjustes.setFont(new Font("Times Roman", Font.PLAIN, 14));
		GridBagConstraints textoAjustesPosicao = new GridBagConstraints();
		textoAjustesPosicao.fill = GridBagConstraints.HORIZONTAL;
		textoAjustesPosicao.gridwidth = 2;
		textoAjustesPosicao.insets = new Insets(25, 5, 5, 5);
		textoAjustesPosicao.gridx = 0;
		textoAjustesPosicao.gridy = 12;
		painelOpcoes.add(textoAjustes, textoAjustesPosicao);
		
		// BOTAO HISTOGRAMA
		JButton botaoHistograma = new JButton("Histograma");
		botaoHistograma.setFocusPainted(false);
		botaoHistograma.setBackground(Color.WHITE);
		botaoHistograma.setForeground(Color.BLACK);
		botaoHistograma.setFont(new Font("Times Roman", Font.PLAIN, 16));
		botaoHistograma.addActionListener(acaoHistograma());
		GridBagConstraints botaoHistogramaPosicao = new GridBagConstraints();
		botaoHistogramaPosicao.fill = GridBagConstraints.HORIZONTAL;
		botaoHistogramaPosicao.gridwidth = 2;
		botaoHistogramaPosicao.insets = new Insets(0, 5, 5, 5);
		botaoHistogramaPosicao.gridx = 0;
		botaoHistogramaPosicao.gridy = 13;
		painelOpcoes.add(botaoHistograma, botaoHistogramaPosicao);
		
		// TEXTO BRILHO
		JLabel textoBrilho = new JLabel("Brilho");
		textoBrilho.setHorizontalAlignment(JLabel.HORIZONTAL);
		textoBrilho.setBackground(Color.WHITE);
		textoBrilho.setForeground(Color.BLACK);
		textoBrilho.setFont(new Font("Times Roman", Font.PLAIN, 14));
		GridBagConstraints textoBrilhoPosicao = new GridBagConstraints();
		textoBrilhoPosicao.fill = GridBagConstraints.HORIZONTAL;
		textoBrilhoPosicao.gridwidth = 2;
		textoBrilhoPosicao.insets = new Insets(10, 5, 5, 5);
		textoBrilhoPosicao.gridx = 0;
		textoBrilhoPosicao.gridy = 14;
		painelOpcoes.add(textoBrilho, textoBrilhoPosicao);
		
				
		JSlider sliderBrilho = new JSlider(0, 200, 100);
		sliderBrilho.setBackground(Color.LIGHT_GRAY);
		sliderBrilho.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				float brilho = sliderBrilho.getValue() / 100f;
				campoImagem.trocarImagem(processadorDeImagens.aplicarBrilhoNaImagem(campoImagem.imagem(), brilho));
			}
		});
		GridBagConstraints sliderBrilhoLayout = new GridBagConstraints();
		sliderBrilhoLayout.fill = GridBagConstraints.HORIZONTAL;
		sliderBrilhoLayout.gridwidth = 2;
		sliderBrilhoLayout.insets = new Insets(0, 5, 5, 5);
		sliderBrilhoLayout.gridx = 0;
		sliderBrilhoLayout.gridy = 15;
		painelOpcoes.add(sliderBrilho, sliderBrilhoLayout);

	}
	
	// ACAO DO BOTAO HISTOGRAMA
	public ActionListener acaoHistograma()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent  e)
			{
				new JanelaHistograma(campoImagem.imagem());
			}
		};
	}
	
	// ACAO DO BOTAO REDIMENSIONAR
	public ActionListener acaoRedimensionar()
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent  e)
			{
				int largura = stringToInt(campoTextoLargura.getText());
				int altura = stringToInt(campoTextoAltura.getText());
				
				if(marcadorManterProporcao.isSelected()) 
				{
				campoImagem.trocarImagem(ManipuladorImagem.redimensionar(
						campoImagem.imagem(), largura, altura, true));
				} else {
					campoImagem.trocarImagem(ManipuladorImagem.redimensionar(
							campoImagem.imagem(), largura, altura));
				}
				
				setTamanhoDaImagem(campoImagem.imagem().getWidth(),
						campoImagem.imagem().getHeight());
			}
		};
	}
	
	// setar valor da largura e altura do imagem no GUI
	private void setTamanhoDaImagem(int largura, int altura)
	{
		campoTextoLargura.setText(intToString(largura));
		campoTextoAltura.setText(intToString(altura));
	}
	
	// metodos de ajuda (conversoes)
	private String intToString(int i)
	{
		return Integer.toString(i);
	}
	
	private int stringToInt(String s)
	{
		return Integer.parseInt(s);
	}

}
