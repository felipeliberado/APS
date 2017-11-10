package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import imagem.ProcessadorDeImagens;

@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame {

	public static final String NOME = "Processamento de Imagens";
	public static final int LARGURA_INI = 1280;
	public static final int ALTURA_INI = 720;
	public static final int LARGURA_MIN = 720;
	public static final int ALTURA_MIN = 480;

	private CampoDeImagem campoDeImagem;
	private ProcessadorDeImagens processadorDeImagens;

	private JTextField textoTamanhoEmX;
	private JTextField textoTamanhoEmY;

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
		processadorDeImagens = new ProcessadorDeImagens();
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
		campoDeImagem = new CampoDeImagem();
		painelDeImagem.add(campoDeImagem, BorderLayout.CENTER);

		// PAINEL DE OPCOES
		JPanel painelDeOpcoes = new JPanel();
		painelDeOpcoes.setBackground(Color.LIGHT_GRAY);
		painelPrincipal.add(painelDeOpcoes, BorderLayout.WEST);
		GridBagLayout painelDeOpcoesLayout = new GridBagLayout();
		painelDeOpcoesLayout.columnWidths = new int[] { 120, 120 };
		painelDeOpcoesLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		painelDeOpcoesLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		painelDeOpcoesLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		painelDeOpcoes.setLayout(painelDeOpcoesLayout);

		// TEXTO IMAGEM
		JLabel textoImagem = new JLabel("Imagem");
		textoImagem.setForeground(new Color(50, 140, 220));
		textoImagem.setHorizontalAlignment(SwingConstants.CENTER);
		textoImagem.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints textoImagemLayout = new GridBagConstraints();
		textoImagemLayout.gridwidth = 2;
		textoImagemLayout.fill = GridBagConstraints.HORIZONTAL;
		textoImagemLayout.insets = new Insets(15, 5, 5, 5);
		textoImagemLayout.gridx = 0;
		textoImagemLayout.gridy = 0;
		painelDeOpcoes.add(textoImagem, textoImagemLayout);

		// BOTAO ABRIR IMAGEM
		JButton botaoAbrirImagem = new JButton("Abrir");
		botaoAbrirImagem.setBackground(new Color(50, 140, 220));
		botaoAbrirImagem.setForeground(Color.WHITE);
		botaoAbrirImagem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		botaoAbrirImagem.setFocusPainted(false);
		botaoAbrirImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				JFileChooser janelaAbrirImagem = new JFileChooser();
				int retorno = janelaAbrirImagem.showDialog(null, "Abrir Imagem");
				if (retorno == JFileChooser.APPROVE_OPTION) {
					try {
						campoDeImagem.abriImagem(janelaAbrirImagem.getSelectedFile().getAbsolutePath());
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
		painelDeOpcoes.add(botaoAbrirImagem, botaoAbrirImagemLayout);

		// BOTAO SALVAR IMAGEM
		JButton botaoSalvarImagem = new JButton("Salvar");
		botaoSalvarImagem.setBackground(new Color(50, 140, 220));
		botaoSalvarImagem.setForeground(Color.WHITE);
		botaoSalvarImagem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		botaoSalvarImagem.setFocusPainted(false);
		botaoSalvarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				JFileChooser janelaSalvarImagem = new JFileChooser();
				int retorno = janelaSalvarImagem.showDialog(null, "Salvar Imagem");
				if (retorno == JFileChooser.APPROVE_OPTION) {
					try {
						campoDeImagem.salvarImagem(janelaSalvarImagem.getSelectedFile().getAbsolutePath());
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
		painelDeOpcoes.add(botaoSalvarImagem, botaoSalvarImagemLayout);

		// TEXTO ROTACIONAR
		JLabel textoRotacionar = new JLabel("Rotacionar");
		textoRotacionar.setHorizontalAlignment(SwingConstants.CENTER);
		textoRotacionar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints textoRotacionarLayout = new GridBagConstraints();
		textoRotacionarLayout.fill = GridBagConstraints.HORIZONTAL;
		textoRotacionarLayout.gridwidth = 2;
		textoRotacionarLayout.insets = new Insets(15, 5, 5, 5);
		textoRotacionarLayout.gridx = 0;
		textoRotacionarLayout.gridy = 2;
		painelDeOpcoes.add(textoRotacionar, textoRotacionarLayout);

		// BOTAO ROTACIONAR ESQUERDA
		JButton botaoRotacionarEsquerda = new JButton("Equerda");
		botaoRotacionarEsquerda.setBackground(Color.WHITE);
		botaoRotacionarEsquerda.setFont(new Font("Tahoma", Font.PLAIN, 16));
		botaoRotacionarEsquerda.setFocusPainted(false);
		botaoRotacionarEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoDeImagem.mostrarImagem(processadorDeImagens.rotacionarEsquerda(campoDeImagem.pegarImagem()));
			}
		});
		GridBagConstraints botaoRotacionarEsquerdaLayout = new GridBagConstraints();
		botaoRotacionarEsquerdaLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoRotacionarEsquerdaLayout.insets = new Insets(0, 5, 5, 5);
		botaoRotacionarEsquerdaLayout.gridx = 0;
		botaoRotacionarEsquerdaLayout.gridy = 3;
		painelDeOpcoes.add(botaoRotacionarEsquerda, botaoRotacionarEsquerdaLayout);

		// BOTAO ROTACIONAR DIREITA
		JButton botaoRotacionarDireita = new JButton("Direita");
		botaoRotacionarDireita.setBackground(Color.WHITE);
		botaoRotacionarDireita.setFont(new Font("Tahoma", Font.PLAIN, 16));
		botaoRotacionarDireita.setFocusPainted(false);
		botaoRotacionarDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoDeImagem.mostrarImagem(processadorDeImagens.rotacionarDireita(campoDeImagem.pegarImagem()));
			}
		});
		GridBagConstraints botaoRotacionarDireitaLayout = new GridBagConstraints();
		botaoRotacionarDireitaLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoRotacionarDireitaLayout.insets = new Insets(0, 0, 5, 5);
		botaoRotacionarDireitaLayout.gridx = 1;
		botaoRotacionarDireitaLayout.gridy = 3;
		painelDeOpcoes.add(botaoRotacionarDireita, botaoRotacionarDireitaLayout);

		// TEXTO VIRAR
		JLabel textoVirar = new JLabel("Virar");
		textoVirar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textoVirar.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints textoVirarLayout = new GridBagConstraints();
		textoVirarLayout.gridwidth = 2;
		textoVirarLayout.insets = new Insets(0, 5, 5, 5);
		textoVirarLayout.gridx = 0;
		textoVirarLayout.gridy = 4;
		painelDeOpcoes.add(textoVirar, textoVirarLayout);

		// BOTAO VIRAR VERTICAL
		JButton botaoVirarVertical = new JButton("Vertical");
		botaoVirarVertical.setBackground(Color.WHITE);
		botaoVirarVertical.setFont(new Font("Tahoma", Font.PLAIN, 16));
		botaoVirarVertical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoDeImagem.mostrarImagem(processadorDeImagens.virarVertical(campoDeImagem.pegarImagem()));
			}
		});
		GridBagConstraints botaoVirarVerticalLayout = new GridBagConstraints();
		botaoVirarVerticalLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoVirarVerticalLayout.insets = new Insets(0, 5, 5, 5);
		botaoVirarVerticalLayout.gridx = 0;
		botaoVirarVerticalLayout.gridy = 5;
		painelDeOpcoes.add(botaoVirarVertical, botaoVirarVerticalLayout);

		// BOTAO VIRAR HORIZONTAL
		JButton botaoVirarHorizontal = new JButton("Horizontal");
		botaoVirarHorizontal.setBackground(Color.WHITE);
		botaoVirarHorizontal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		botaoVirarHorizontal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoDeImagem.mostrarImagem(processadorDeImagens.virarHorizontal(campoDeImagem.pegarImagem()));
			}
		});
		GridBagConstraints botaoVirarHorizontalLayout = new GridBagConstraints();
		botaoVirarHorizontalLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoVirarHorizontalLayout.insets = new Insets(0, 0, 5, 5);
		botaoVirarHorizontalLayout.gridx = 1;
		botaoVirarHorizontalLayout.gridy = 5;
		painelDeOpcoes.add(botaoVirarHorizontal, botaoVirarHorizontalLayout);

		// TEXTO FILTROS
		JLabel textoFiltros = new JLabel("Filtros");
		textoFiltros.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textoFiltros.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints textoFiltrosLayout = new GridBagConstraints();
		textoFiltrosLayout.fill = GridBagConstraints.HORIZONTAL;
		textoFiltrosLayout.gridwidth = 2;
		textoFiltrosLayout.insets = new Insets(0, 5, 5, 5);
		textoFiltrosLayout.gridx = 0;
		textoFiltrosLayout.gridy = 6;
		painelDeOpcoes.add(textoFiltros, textoFiltrosLayout);

		// BOTAO ESCALA CINZA
		JButton botaoEscalaCinza = new JButton("Escala Cinza");
		botaoEscalaCinza.setBackground(Color.WHITE);
		botaoEscalaCinza.setFont(new Font("Tahoma", Font.PLAIN, 16));
		botaoEscalaCinza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoDeImagem.mostrarImagem(processadorDeImagens.escalaDeCinza(campoDeImagem.pegarImagem()));
			}
		});
		GridBagConstraints botaoEscalaCinzaLayout = new GridBagConstraints();
		botaoEscalaCinzaLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoEscalaCinzaLayout.insets = new Insets(0, 5, 5, 5);
		botaoEscalaCinzaLayout.gridx = 0;
		botaoEscalaCinzaLayout.gridy = 7;
		painelDeOpcoes.add(botaoEscalaCinza, botaoEscalaCinzaLayout);

		// BOTAO NEGATIVO
		JButton botaoNegativo = new JButton("Negativo");
		botaoNegativo.setBackground(Color.WHITE);
		botaoNegativo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		botaoNegativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoDeImagem.mostrarImagem(processadorDeImagens.negativo(campoDeImagem.pegarImagem()));
			}
		});
		GridBagConstraints botaoNegativoLayout = new GridBagConstraints();
		botaoNegativoLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoNegativoLayout.insets = new Insets(0, 0, 5, 5);
		botaoNegativoLayout.gridx = 1;
		botaoNegativoLayout.gridy = 7;
		painelDeOpcoes.add(botaoNegativo, botaoNegativoLayout);

		// BOTAO MONO
		JButton botaoMono = new JButton("Mono");
		botaoMono.setBackground(Color.WHITE);
		botaoMono.setFont(new Font("Tahoma", Font.PLAIN, 16));
		botaoMono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				campoDeImagem.mostrarImagem(processadorDeImagens.mono(campoDeImagem.pegarImagem()));
			}
		});
		GridBagConstraints botaoMonoLayout = new GridBagConstraints();
		botaoMonoLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoMonoLayout.insets = new Insets(0, 5, 5, 5);
		botaoMonoLayout.gridx = 0;
		botaoMonoLayout.gridy = 8;
		painelDeOpcoes.add(botaoMono, botaoMonoLayout);

		// BOTAO REDIMENSIONAR
		textoTamanhoEmX = new JTextField("1280");
		textoTamanhoEmX.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints textoTamanhoEmXLayout = new GridBagConstraints();
		textoTamanhoEmXLayout.fill = GridBagConstraints.HORIZONTAL;
		textoTamanhoEmXLayout.insets = new Insets(25, 5, 5, 5);
		textoTamanhoEmXLayout.gridx = 0;
		textoTamanhoEmXLayout.gridy = 9;
		painelDeOpcoes.add(textoTamanhoEmX, textoTamanhoEmXLayout);

		textoTamanhoEmY = new JTextField("720");
		textoTamanhoEmY.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints textoTamanhoEmYLayout = new GridBagConstraints();
		textoTamanhoEmYLayout.fill = GridBagConstraints.HORIZONTAL;
		textoTamanhoEmYLayout.insets = new Insets(25, 5, 5, 5);
		textoTamanhoEmYLayout.gridx = 1;
		textoTamanhoEmYLayout.gridy = 9;
		painelDeOpcoes.add(textoTamanhoEmY, textoTamanhoEmYLayout);

		JButton botaoRedimensionar = new JButton("Redimensionar");
		botaoRedimensionar.setBackground(Color.WHITE);
		botaoRedimensionar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		botaoRedimensionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ACAO DO BOTAO
				int largura = Integer.parseInt(textoTamanhoEmX.getText());
				int altura = Integer.parseInt(textoTamanhoEmY.getText());
				campoDeImagem.mostrarImagem(
						processadorDeImagens.redimensionar(campoDeImagem.pegarImagem(), largura, altura));
			}
		});
		GridBagConstraints botaoRedimensionarLayout = new GridBagConstraints();
		botaoRedimensionarLayout.fill = GridBagConstraints.HORIZONTAL;
		botaoRedimensionarLayout.insets = new Insets(0, 5, 5, 5);
		botaoRedimensionarLayout.gridx = 0;
		botaoRedimensionarLayout.gridy = 10;
		painelDeOpcoes.add(botaoRedimensionar, botaoRedimensionarLayout);

		JSlider sliderBrilho = new JSlider(0, 200, 100);
		sliderBrilho.setBackground(Color.LIGHT_GRAY);
		sliderBrilho.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				float brilho = sliderBrilho.getValue() / 100f;
				campoDeImagem.mostrarImagem(processadorDeImagens.mudarBrilho(campoDeImagem.pegarImagemBackup(), brilho));
			}
		});
		GridBagConstraints sliderBrilhoLayout = new GridBagConstraints();
		sliderBrilhoLayout.fill = GridBagConstraints.HORIZONTAL;
		sliderBrilhoLayout.gridwidth = 2;
		sliderBrilhoLayout.insets = new Insets(0, 5, 5, 5);
		sliderBrilhoLayout.gridx = 0;
		sliderBrilhoLayout.gridy = 11;
		painelDeOpcoes.add(sliderBrilho, sliderBrilhoLayout);

	}

}
