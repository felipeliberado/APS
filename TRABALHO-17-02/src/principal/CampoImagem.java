package principal;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class CampoImagem extends JComponent
{

	private BufferedImage imagem;
	private BufferedImage imagemDeBackup;

	public void abriImagem(String caminho) throws IOException
	{
		imagem = ImageIO.read(new File(caminho));
		fazerBackup();
	}

	public void salvarImagem(String caminho) throws IOException
	{
		ImageIO.write(imagem, "png", new File(caminho));
	}

	// desenha a imagem no componente (repetidamente)
	@Override
	protected void paintComponent(Graphics g)
	{
		if (imagem != null)
		{
			int xPosition = this.getWidth() / 2 - imagem.getWidth() / 2;
			int yPosition = this.getHeight() / 2 - imagem.getHeight() / 2;
			g.drawImage(imagem, xPosition, yPosition, null);
		}
		repaint();
	}

	//region MANIPULACAO
	public boolean temImagem()
	{
		if(imagem != null) return true;
		else return false;
	}
	
	public void trocarImagem(BufferedImage imagem)
	{
		this.imagem = imagem;
	}

	public BufferedImage imagem()
	{
		return imagem;
	}
	
	public BufferedImage pegarImagemDeBackup()
	{
		return imagemDeBackup;
	}
	//endregion
	
	//region BACKUP
	private void fazerBackup()
	{
		imagemDeBackup = ManipuladorImagem.copiarImagem(imagem);
	}
	
	private void restaurarBackup()
	{
		imagem = ManipuladorImagem.copiarImagem(imagemDeBackup);
	}
	//endregion

}
