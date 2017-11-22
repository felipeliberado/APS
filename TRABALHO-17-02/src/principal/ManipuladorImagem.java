package principal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class ManipuladorImagem
{

	// aplica o filtro de preto de branco (escada de cinza)
	public BufferedImage escalaCinza(BufferedImage imagem)
	{
		BufferedImage imagemAlterada = copiarImagem(imagem);

		for (int x = 0; x < imagemAlterada.getWidth(); ++x)
			for (int y = 0; y < imagemAlterada.getHeight(); ++y)
			{
				// coleta dados da cor de cada pixel da imagem
				int rgba = imagemAlterada.getRGB(x, y);
				int r = (rgba >> 16) & 0xFF; 	// VERMELHO
				int g = (rgba >> 8) & 0xFF; 	// VERDE
				int b = (rgba & 0xFF); 			// AZUL
				
				// aplica a escala de cinza
				int cinza = (r + g + b) / 3;
				
				// ajuste para a profundidade de cor
				int gray = (cinza << 16) + (cinza << 8) + cinza;

				// aplica a cor de volta na imagem
				imagemAlterada.setRGB(x, y, gray);
			}
		return imagemAlterada;
	}

	// aplica o filtro de negativo na imagem
	public BufferedImage negativo(BufferedImage imagem)
	{
		BufferedImage imagemAlterada = copiarImagem(imagem);

		for (int x = 0; x < imagemAlterada.getWidth(); x++)
		{
			for (int y = 0; y < imagemAlterada.getHeight(); y++)
			{
				// coleta dados da cor de cada pixel da imagem
				int rgba = imagemAlterada.getRGB(x, y);

				// inverte a cor atual do pixel
				Color cor = new Color(rgba, true);
				// subtrai cada valor do pixel do seu maximo
				cor = new Color(255 - cor.getRed(), 255 - cor.getGreen(), 255 - cor.getBlue());
				
				// aplica a cor de volta na imagem
				imagemAlterada.setRGB(x, y, cor.getRGB());
			}
		}
		return imagemAlterada;
	}

	public BufferedImage mono(BufferedImage imagem)
	{
		BufferedImage imagemAlterada = copiarImagem(imagem);

		for (int x = 0; x < imagemAlterada.getWidth(); x++)
		{
			for (int y = 0; y < imagemAlterada.getHeight(); y++)
			{
				// coleta dados da cor de cada pixel da imagem
				int rgba = imagemAlterada.getRGB(x, y);

				// faz a comparação para verificar se o pixel será preto ou branco
				Color cor = new Color(rgba, true);
				int limite = 368;
				// faz a comparação usando o limite
				if (cor.getRed() + cor.getGreen() + cor.getBlue() > limite)
					cor = new Color(255, 255, 255);
				else
					cor = new Color(0, 0, 0);
				
				// aplica a cor de volta na imagem (no caso preto ou branco)
				imagemAlterada.setRGB(x, y, cor.getRGB());
			}
		}
		return imagemAlterada;
	}

	// rotacao da imagem
	public BufferedImage rotacionarDireita(BufferedImage imagem) {
		return rotacionar(imagem, 90);
	}

	public BufferedImage rotacionarEsquerda(BufferedImage imagem) {
		return rotacionar(imagem, 270);
	}

	private BufferedImage rotacionar(BufferedImage imagem, double angulo) {
		BufferedImage imagemAlterada = copiarImagem(imagem);
		// ajuste do angulo
		angulo %= 360;
		if (angulo < 0)
			angulo += 360;
		// AffineTransform usado para fazer tranformações de rotacao e translacao
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(angulo), imagemAlterada.getWidth() / 2.0, imagemAlterada.getHeight() / 2.0);
		double ytrans = 0;
		double xtrans = 0;
		// verifica os angulos e faz a tranformacaoo de acordo o angulo especifico 
		if (angulo <= 90) {
			xtrans = tx.transform(new Point2D.Double(0, imagemAlterada.getHeight()), null).getX();
			ytrans = tx.transform(new Point2D.Double(0.0, 0.0), null).getY();
		} else if (angulo <= 180) {
			xtrans = tx.transform(new Point2D.Double(imagemAlterada.getWidth(), imagemAlterada.getHeight()), null)
					.getX();
			ytrans = tx.transform(new Point2D.Double(0, imagemAlterada.getHeight()), null).getY();
		} else if (angulo <= 270) {
			xtrans = tx.transform(new Point2D.Double(imagemAlterada.getWidth(), 0), null).getX();
			ytrans = tx.transform(new Point2D.Double(imagemAlterada.getWidth(), imagemAlterada.getHeight()), null)
					.getY();
		} else {
			xtrans = tx.transform(new Point2D.Double(0, 0), null).getX();
			ytrans = tx.transform(new Point2D.Double(imagemAlterada.getWidth(), 0), null).getY();
		}
		AffineTransform translationTransform = new AffineTransform();
		translationTransform.translate(-xtrans, -ytrans);
		tx.preConcatenate(translationTransform);
		// aplica a rotacao na imagem
		imagemAlterada = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR).filter(imagemAlterada, null);
		return imagemAlterada;
	}

	
	public BufferedImage virarHorizontal(BufferedImage imagem) {
		BufferedImage imagemAlterada = copiarImagem(imagem);
		// AffineTransform usado para fazer tranformações de rotacao e translacao
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1); // unica mudança (na escala q vai ser invertida)
		// aplica a translacao no AffineTransform
		tx.translate(-imagemAlterada.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		// aplica a translacao do AffineTransform na imagem em si
		imagemAlterada = op.filter(imagemAlterada, null);
		return imagemAlterada;
	}

	public BufferedImage virarVertical(BufferedImage imagem) {
		BufferedImage imagemAlterada = copiarImagem(imagem);
		// AffineTransform usado para fazer tranformações de rotacao e translacao
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1); // unica mudança (na escala q vai ser invertida)
		// aplica a translacao no AffineTransform
		tx.translate(0, -imagemAlterada.getHeight(null));
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		// aplica a translacao do AffineTransform na imagem em si
		imagemAlterada = op.filter(imagemAlterada, null);
		return imagemAlterada;
	}

	//region REDIMENSIONAR
	// redimensionar a imagem em ambas as dimensoes
	public static BufferedImage redimensionar(BufferedImage imagem, int largura, int altura)
	{
		BufferedImage imagemRedimensionada = new BufferedImage(largura, altura, imagem.getType());

		// redimensionar a imagem usando graphics2D para recriar ela em outra resolucao
		Graphics2D graphics2D = (Graphics2D) imagemRedimensionada.createGraphics();
		graphics2D.drawImage(imagem, 0, 0, largura, altura, null);
		graphics2D.dispose();

		return imagemRedimensionada;
	}
	
	// (sobrecarga)
	// redimensionar a imagem mantendo a sua proporcao em ambas as dimensoes
	public static BufferedImage redimensionar(BufferedImage imagem, int largura, int altura, boolean manterProporcao)
	{
	    if (manterProporcao)
	    {
	        int alturaDaImagem = imagem.getHeight();
	        int larguraDaImagem = imagem.getWidth();
	        
	        //bloqueia o aspectRatio (se ele for maior q a proporção atual da imagem ela é limitada)
	        if (alturaDaImagem / altura > larguraDaImagem / largura)
	        {
	            altura = largura * alturaDaImagem / larguraDaImagem;
	        }
	        if (larguraDaImagem / largura > alturaDaImagem / altura)
	        {
	            largura = altura * larguraDaImagem / alturaDaImagem;
	        }
	    }
		
	    // chama o redimensionar acima
		return redimensionar(imagem, largura, altura);
	}
	//endregion


	// aplicar brilho na imagem
	public static BufferedImage aplicarBrilhoNaImagem(BufferedImage imagem, float intensidade) {

		BufferedImage imagemAlterada = copiarImagem(imagem);
		
		// aplica o filtro de brilho de acrodo com a intensidade
		RescaleOp op = new RescaleOp(intensidade, 0, null);
		imagemAlterada = op.filter(imagemAlterada, imagemAlterada);

		return imagemAlterada;
	}

	// copiar imagem
	public static BufferedImage copiarImagem(BufferedImage imagem) {

		BufferedImage imagemCopiada = new BufferedImage(imagem.getWidth(), imagem.getHeight(), imagem.getType());

		Graphics2D graphics2D = (Graphics2D) imagemCopiada.getGraphics();
		graphics2D.drawImage(imagem, 0, 0, null);
		graphics2D.dispose();

		return imagemCopiada;
	}

}
