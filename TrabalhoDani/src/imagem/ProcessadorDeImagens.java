package imagem;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class ProcessadorDeImagens {

	public BufferedImage escalaDeCinza(BufferedImage imagem) {
		BufferedImage imagemAlterada = copiarImagem(imagem);

		for (int x = 0; x < imagemAlterada.getWidth(); ++x)
			for (int y = 0; y < imagemAlterada.getHeight(); ++y) {
				int rgba = imagemAlterada.getRGB(x, y);
				// COMECO ---------- PARTE CRITICA
				int r = (rgba >> 16) & 0xFF; // VERMELHO
				int g = (rgba >> 8) & 0xFF; // VERDE
				int b = (rgba & 0xFF); // AZUL
				int grayLevel = (r + g + b) / 3;
				int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
				// FINAL ---------- PARTE CRITICA
				imagemAlterada.setRGB(x, y, gray);
			}
		return imagemAlterada;
	}

	public BufferedImage negativo(BufferedImage imagem) {
		BufferedImage imagemAlterada = copiarImagem(imagem);

		for (int x = 0; x < imagemAlterada.getWidth(); x++) {
			for (int y = 0; y < imagemAlterada.getHeight(); y++) {
				int rgba = imagemAlterada.getRGB(x, y);
				// COMECO ---------- PARTE CRITICA
				Color col = new Color(rgba, true);
				col = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue());
				// FINAL ---------- PARTE CRITICA
				imagemAlterada.setRGB(x, y, col.getRGB());
			}
		}
		return imagemAlterada;
	}

	public BufferedImage mono(BufferedImage imagem) {
		BufferedImage imagemAlterada = copiarImagem(imagem);

		for (int x = 0; x < imagemAlterada.getWidth(); x++) {
			for (int y = 0; y < imagemAlterada.getHeight(); y++) {
				int rgba = imagemAlterada.getRGB(x, y);
				// COMECO ---------- PARTE CRITICA
				Color col = new Color(rgba, true);
				int MONO_THRESHOLD = 368;
				if (col.getRed() + col.getGreen() + col.getBlue() > MONO_THRESHOLD)
					col = new Color(255, 255, 255);
				else
					col = new Color(0, 0, 0);
				// FINAL ---------- PARTE CRITICA
				imagemAlterada.setRGB(x, y, col.getRGB());
			}
		}
		return imagemAlterada;
	}

	public BufferedImage rotacionarDireita(BufferedImage imagem) {
		return rotacionar(imagem, 90);
	}

	public BufferedImage rotacionarEsquerda(BufferedImage imagem) {
		return rotacionar(imagem, 270);
	}

	private BufferedImage rotacionar(BufferedImage imagem, double angulo) {
		BufferedImage imagemAlterada = copiarImagem(imagem);
		// COMECO ---------- PARTE CRITICA
		angulo %= 360;
		if (angulo < 0)
			angulo += 360;

		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(angulo), imagemAlterada.getWidth() / 2.0, imagemAlterada.getHeight() / 2.0);

		double ytrans = 0;
		double xtrans = 0;
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
		// FINAL ---------- PARTE CRITICA
		AffineTransform translationTransform = new AffineTransform();
		translationTransform.translate(-xtrans, -ytrans);
		tx.preConcatenate(translationTransform);

		imagemAlterada = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR).filter(imagemAlterada, null);
		return imagemAlterada;
	}

	public BufferedImage virarHorizontal(BufferedImage imagem) {
		BufferedImage imagemAlterada = copiarImagem(imagem);
		// COMECO ---------- PARTE CRITICA
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-imagemAlterada.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		imagemAlterada = op.filter(imagemAlterada, null);
		// FINAL ---------- PARTE CRITICA
		return imagemAlterada;
	}

	public BufferedImage virarVertical(BufferedImage imagem) {
		BufferedImage imagemAlterada = copiarImagem(imagem);
		// COMECO ---------- PARTE CRITICA
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		tx.translate(0, -imagemAlterada.getHeight(null));
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		imagemAlterada = op.filter(imagemAlterada, null);
		// FINAL ---------- PARTE CRITICA
		return imagemAlterada;
	}

	public BufferedImage redimensionar(BufferedImage imagem, int largura, int altura) {

		BufferedImage imagemAlterada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2D = imagemAlterada.createGraphics();
		g2D.drawImage(imagem, 0, 0, largura, altura, null);
		g2D.dispose();

		return imagemAlterada;
	}

	public BufferedImage mudarBrilho(BufferedImage imagem, float brilho) {

		BufferedImage imagemAlterada = copiarImagem(imagem);;

		RescaleOp op = new RescaleOp(brilho, 0, null);
		imagemAlterada = op.filter(imagemAlterada, imagemAlterada);

		return imagemAlterada;
	}
	
	public static BufferedImage copiarImagem(BufferedImage imagem) {
		BufferedImage copia = new BufferedImage(imagem.getWidth(), imagem.getHeight(), imagem.getType());
		Graphics2D g2D = (Graphics2D) copia.getGraphics();
		g2D.drawImage(imagem, 0, 0, null);
		g2D.dispose();
		return copia;
	}

}
