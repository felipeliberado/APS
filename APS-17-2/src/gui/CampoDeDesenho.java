package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class CampoDeDesenho extends JComponent {

	private Image imagem;
	private Graphics2D g2D;

	private int currentX, currentY, oldX, oldY;

	private int TamanhoDoPincel;

	public CampoDeDesenho() {
		setDoubleBuffered(false);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				oldX = e.getX();
				oldY = e.getY();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();

				Ellipse2D.Double circle = new Ellipse2D.Double(currentX - TamanhoDoPincel / 2,
						currentY - TamanhoDoPincel / 2, TamanhoDoPincel, TamanhoDoPincel);
				g2D.fill(circle);

				if (g2D != null) {
					g2D.drawLine(oldX, oldY, currentX, currentY);
					repaint();
					oldX = currentX;
					oldY = currentY;
				}
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (imagem == null) {
			imagem = createImage(getSize().width, getSize().height);
			g2D = (Graphics2D) imagem.getGraphics();
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			limpar();
		}
		g.drawImage(imagem, 0, 0, null);
	}

	public void limpar() {
		Color corAtual = g2D.getColor();
		g2D.setPaint(Color.WHITE);
		g2D.fillRect(0, 0, getSize().width, getSize().height);
		g2D.setPaint(corAtual);
		repaint();
	}

	public void setCor(Color novaCor) {
		g2D.setPaint(novaCor);
	}

	public Color getCor() {
		return g2D.getColor();
	}

	public Image getImagem() {
		return imagem;
	}

	public void setTamanhoDoPincel(int tamanhoDoPincel) {
		TamanhoDoPincel = tamanhoDoPincel;
	}

}
