package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame {

	// PINCEL
	static final int TAMANHO_MIN = 1;
	static final int TAMANHO_MAX = 50;
	static final int TAMANHO_INI = 15;

	JTextField textoTamanhoDoPincel;

	String nome = "APS-17-2";

	static final int LARGURA = 1298;
	static final int ALTURA = 720;

	FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagem", "bmp", "gif", "jpeg", "jpg", "png");
	CampoDeDesenho campoDeDesenho;
	private JTextField textoVezesATreinar;

	public static void main(String[] args) {
		new JanelaPrincipal();
	}

	public JanelaPrincipal() {
		this.setTitle(nome);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(LARGURA, ALTURA);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		inicializarComponentes();

		this.setVisible(true);
	}

	void inicializarComponentes() {
		JPanel PainelPrincipal = new JPanel();
		PainelPrincipal.setBackground(Color.DARK_GRAY);
		PainelPrincipal.setLayout(null);
		setContentPane(PainelPrincipal);

		JPanel PainelControle = new JPanel();
		PainelControle.setBackground(Color.GRAY);
		PainelControle.setBounds(532, 35, 742, 50);
		PainelControle.setLayout(null);
		PainelPrincipal.add(PainelControle);

		// CAMPO DE DESENHO (ENTRADA)
		JPanel painelDeDesenhoEntrada = new JPanel();
		painelDeDesenhoEntrada.setBounds(10, 96, 512, 512);
		painelDeDesenhoEntrada.setLayout(new BorderLayout());
		campoDeDesenho = new CampoDeDesenho();
		campoDeDesenho.setTamanhoDoPincel(TAMANHO_INI);
		painelDeDesenhoEntrada.add(campoDeDesenho, BorderLayout.CENTER);
		PainelPrincipal.add(painelDeDesenhoEntrada);

		// CAMPO DE DESENHO (SAIDA)
		JPanel painelDeDesenhoSaida = new JPanel();
		painelDeDesenhoSaida.setBounds(532, 96, 512, 512);
		painelDeDesenhoSaida.setLayout(new BorderLayout());
		PainelPrincipal.add(painelDeDesenhoSaida);

		// SLIDER PINCEL
		textoTamanhoDoPincel = new JTextField();
		textoTamanhoDoPincel.setBounds(10, 55, 40, 30);
		textoTamanhoDoPincel.setHorizontalAlignment(JTextField.CENTER);
		textoTamanhoDoPincel.setText(String.valueOf(TAMANHO_INI));
		textoTamanhoDoPincel.setEditable(false);
		PainelPrincipal.add(textoTamanhoDoPincel);

		JSlider sliderTamanhoDoPincel = new JSlider(TAMANHO_MIN, TAMANHO_MAX, TAMANHO_INI);
		sliderTamanhoDoPincel.setBackground(Color.DARK_GRAY);
		sliderTamanhoDoPincel.setBounds(60, 55, 180, 30);
		sliderTamanhoDoPincel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textoTamanhoDoPincel.setText(String.valueOf(sliderTamanhoDoPincel.getValue()));
				campoDeDesenho.setTamanhoDoPincel(sliderTamanhoDoPincel.getValue());
			}
		});
		PainelPrincipal.add(sliderTamanhoDoPincel);

		// BOTAO MUDAR DE COR
		JButton botaoMudarCor = new JButton();
		botaoMudarCor.setBounds(250, 55, 30, 30);
		botaoMudarCor.setBackground(Color.BLACK);
		botaoMudarCor.setFocusPainted(false);
		botaoMudarCor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color novaCor = JColorChooser.showDialog(null, "Escolha uma cor...", campoDeDesenho.getCor());
				if (novaCor != null) {
					campoDeDesenho.setCor(novaCor);
					botaoMudarCor.setBackground(novaCor);
				}
			}
		});
		PainelPrincipal.add(botaoMudarCor);

		// BOTAO LIMPAR
		JButton botaoLimpar = new JButton("Limpar");
		botaoLimpar.setBounds(402, 55, 120, 30);
		botaoLimpar.setBackground(Color.WHITE);
		botaoLimpar.setFocusPainted(false);
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				campoDeDesenho.limpar();
			}
		});
		PainelPrincipal.add(botaoLimpar);

		// BOTAO ABRIR IMAGEM (NAO IMPLEMENTADO)
		JButton botaoAbrirImagem = new JButton("Abrir imagem");
		botaoAbrirImagem.setBounds(272, 619, 120, 30);
		botaoAbrirImagem.setBackground(Color.WHITE);
		botaoAbrirImagem.setEnabled(false);
		botaoAbrirImagem.setFocusPainted(false);
		PainelPrincipal.add(botaoAbrirImagem);

		// BOTAO SALVAR IMAGEM
		JButton botaoSalvarImagem = new JButton("Salvar imagem");
		botaoSalvarImagem.setBounds(402, 619, 120, 30);
		botaoSalvarImagem.setBackground(Color.WHITE);
		botaoSalvarImagem.setFocusPainted(false);
		botaoSalvarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser janelaSalvarImagem = new JFileChooser();
				janelaSalvarImagem.setDialogTitle("Salvar imagem...");
				janelaSalvarImagem.setFileFilter(filtro);
				janelaSalvarImagem.setAcceptAllFileFilterUsed(false);
				
				int retorno = janelaSalvarImagem.showSaveDialog(null);

				if (retorno == JFileChooser.APPROVE_OPTION) {
					String caminho = janelaSalvarImagem.getSelectedFile().getPath();
					String extensao = caminho.substring(caminho.lastIndexOf("."), caminho.length());

					BufferedImage imagem = (BufferedImage) campoDeDesenho.getImagem();
					File saidaDeArquivos = new File(caminho);
					try {
						ImageIO.write(imagem, "png", saidaDeArquivos);
					} catch (IllegalArgumentException | IOException erro) {
					}
				}
			}
		});
		PainelPrincipal.add(botaoSalvarImagem);
		
		
		
		
		
		
		
		
		
		

		JButton botaoClassificar = new JButton("Classificar");
		botaoClassificar.setBounds(10, 11, 120, 30);
		botaoClassificar.setBackground(new Color(230, 60, 60));
		botaoClassificar.setForeground(Color.WHITE);
		botaoClassificar.setFocusPainted(false);
		PainelControle.add(botaoClassificar);

		textoVezesATreinar = new JTextField();
		textoVezesATreinar.setBounds(261, 11, 40, 30);
		PainelControle.add(textoVezesATreinar);
		textoVezesATreinar.setColumns(10);

		JButton botaoTreinar = new JButton("Treinar");
		botaoTreinar.setBounds(311, 11, 120, 30);
		PainelControle.add(botaoTreinar);

		JComboBox comboBoxLetra = new JComboBox();
		comboBoxLetra.setBounds(211, 11, 40, 30);
		PainelControle.add(comboBoxLetra);

		JTextArea AreaResultados = new JTextArea();
		AreaResultados.setEditable(false);
		AreaResultados.setBounds(1054, 94, 220, 553);
		PainelPrincipal.add(AreaResultados);

		JLabel feedbackEntrada = new JLabel("");
		feedbackEntrada.setBounds(10, 619, 252, 30);
		PainelPrincipal.add(feedbackEntrada);

		JLabel feedbackSaida = new JLabel("");
		feedbackSaida.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackSaida.setBounds(532, 619, 512, 30);
		PainelPrincipal.add(feedbackSaida);
		botaoMudarCor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		repaint();
	}

}
