package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import imagem.ProcessadorDeImagens;

@SuppressWarnings("serial")
public class CampoDeImagem extends JComponent {

	private BufferedImage imagemBackup;
	private BufferedImage imagem;

	public void abriImagem(String path) throws IOException {
		imagem = ImageIO.read(new File(path));
		imagemBackup = ProcessadorDeImagens.copiarImagem(imagem);
		repaint();
	}

	public void salvarImagem(String path) throws IOException {
		ImageIO.write(imagem, "png", new File(path));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (imagem != null) {
			int xPosition = this.getWidth() / 2 - imagem.getWidth() / 2;
			int yPosition = this.getHeight() / 2 - imagem.getHeight() / 2;
			g.drawImage(imagem, xPosition, yPosition, null);
		}
	}

	public void mostrarImagem(BufferedImage imagem) {
		this.imagem = imagem;
		repaint();
	}

	public BufferedImage pegarImagemBackup() {
		return imagemBackup;
	}

	public BufferedImage pegarImagem() {
		return imagem;
	}

}
