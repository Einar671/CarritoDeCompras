package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {
    private MensajeInternacionalizacionHandler mensajes;

    public MiJDesktopPane(MensajeInternacionalizacionHandler mensajes) {
        super();
        this.mensajes = mensajes;
    }


    public void actualizarTextos() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint sky = new GradientPaint(0, 0, new Color(135, 206, 250), 0, height, Color.WHITE);
        g2.setPaint(sky);
        g2.fillRect(0, 0, width, height);

        int sunX = 80, sunY = 80;
        g2.setColor(Color.YELLOW);
        g2.fillOval(sunX, sunY, 80, 80);
        for (int i = 0; i < 360; i += 30) {
            double angle = Math.toRadians(i);
            int x1 = sunX + 40, y1 = sunY + 40;
            int x2 = x1 + (int)(Math.cos(angle) * 60);
            int y2 = y1 + (int)(Math.sin(angle) * 60);
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setColor(Color.WHITE);
        g2.fillOval(200, 100, 50, 40);
        g2.fillOval(220, 90, 60, 50);
        g2.fillOval(240, 100, 50, 40);

        g2.fillOval(500, 80, 50, 40);
        g2.fillOval(520, 70, 60, 50);
        g2.fillOval(540, 80, 50, 40);

        Polygon m1 = new Polygon();
        m1.addPoint(100, height - 100);
        m1.addPoint(200, height - 300);
        m1.addPoint(300, height - 100);
        g2.setColor(new Color(110, 110, 110));
        g2.fillPolygon(m1);

        Polygon snow1 = new Polygon();
        snow1.addPoint(200, height - 300);
        snow1.addPoint(180, height - 260);
        snow1.addPoint(220, height - 260);
        g2.setColor(Color.WHITE);
        g2.fillPolygon(snow1);

        Polygon m2 = new Polygon();
        m2.addPoint(300, height - 100);
        m2.addPoint(450, height - 350);
        m2.addPoint(600, height - 100);
        g2.setColor(new Color(110, 110, 110));
        g2.fillPolygon(m2);

        Polygon snow2 = new Polygon();
        snow2.addPoint(450, height - 350);
        snow2.addPoint(430, height - 310);
        snow2.addPoint(470, height - 310);
        g2.setColor(Color.WHITE);
        g2.fillPolygon(snow2);

        g2.setColor(new Color(34, 139, 34));
        g2.fillRect(0, height - 100, width, 100);

        g2.setColor(new Color(30, 144, 255));
        Polygon river = new Polygon();
        river.addPoint(width / 2 - 40, height);
        river.addPoint(width / 2 + 40, height);
        river.addPoint(width / 2 + 100, height - 100);
        river.addPoint(width / 2 - 100, height - 100);
        g2.fillPolygon(river);

        int treeX = 650, treeY = height - 160;
        g2.setColor(new Color(101, 67, 33));
        g2.fillRect(treeX, treeY, 20, 60);

        g2.setColor(new Color(34, 139, 34));
        g2.fillOval(treeX - 30, treeY - 40, 80, 50);
        g2.fillOval(treeX - 20, treeY - 70, 60, 50);
        g2.fillOval(treeX - 10, treeY - 60, 50, 60);


        g2.setColor(Color.GREEN);
        g2.drawLine(100 + 5, height - 40, 100 + 5, height - 30);
        g2.setColor(Color.PINK);
        g2.fillOval(100, height - 40, 5, 5);
        g2.fillOval(105, height - 40, 5, 5);
        g2.fillOval(100, height - 35, 5, 5);
        g2.fillOval(105, height - 35, 5, 5);
        g2.setColor(Color.YELLOW);
        g2.fillOval(103, height - 38, 4, 4);

        g2.setColor(Color.GREEN);
        g2.drawLine(180 + 5, height - 35, 180 + 5, height - 25);
        g2.setColor(Color.MAGENTA);
        g2.fillOval(180, height - 35, 5, 5);
        g2.fillOval(185, height - 35, 5, 5);
        g2.fillOval(180, height - 30, 5, 5);
        g2.fillOval(185, height - 30, 5, 5);
        g2.setColor(Color.YELLOW);
        g2.fillOval(183, height - 33, 4, 4);
        for (int i = 0; i < 80; i++) {
            int fx = (int) (Math.random() * width);
            int fy = height - 100 + (int)(Math.random() * 80);

            g2.setColor(Color.GREEN);
            g2.drawLine(fx + 2, fy + 5, fx + 2, fy + 15);

            g2.setColor(new Color(255, 105, 180));
            g2.fillOval(fx, fy, 4, 4);
            g2.fillOval(fx + 4, fy, 4, 4);
            g2.fillOval(fx, fy + 4, 4, 4);
            g2.fillOval(fx + 4, fy + 4, 4, 4);

            g2.setColor(Color.YELLOW);
            g2.fillOval(fx + 3, fy + 3, 3, 3);
        }
        String mensaje = mensajes.get("mensaje.usuario.login.exito");
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        FontMetrics metrics = g2.getFontMetrics();
        int textWidth = metrics.stringWidth(mensaje);
        int carritoX = width / 2 - 70;
        int carritoY = height - 220;

        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillOval(carritoX + 20, carritoY + 80, 100, 20);


        g2.setColor(Color.BLACK);
        g2.fillOval(carritoX + 10, carritoY + 70, 20, 20);
        g2.fillOval(carritoX + 100, carritoY + 70, 20, 20);
        g2.setColor(Color.GRAY);
        g2.fillOval(carritoX + 15, carritoY + 75, 10, 10);
        g2.fillOval(carritoX + 105, carritoY + 75, 10, 10);

        g2.setColor(new Color(169, 169, 169));
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(carritoX, carritoY, 120, 60);

        for (int i = 1; i < 5; i++) {
            int y = carritoY + i * 12;
            g2.drawLine(carritoX, y, carritoX + 120, y);
        }

        for (int i = 1; i < 6; i++) {
            int x = carritoX + i * 20;
            g2.drawLine(x, carritoY, x, carritoY + 60);
        }

        g2.setColor(new Color(105, 105, 105));
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(carritoX + 110, carritoY, carritoX + 130, carritoY - 20);
        g2.drawLine(carritoX + 130, carritoY - 20, carritoX + 130, carritoY - 40);

        g2.setStroke(new BasicStroke(2));
        g2.drawRect(carritoX + 90, carritoY - 10, 30, 10);

        g2.setColor(new Color(255, 182, 193)); // Caja rosa
        g2.fillRect(carritoX + 10, carritoY + 10, 15, 20);

        g2.setColor(new Color(173, 216, 230)); // Botella azul
        g2.fillRoundRect(carritoX + 40, carritoY + 5, 10, 30, 5, 5);

        g2.setColor(new Color(255, 255, 0)); // Caja amarilla
        g2.fillRect(carritoX + 70, carritoY + 20, 20, 15);

        g2.setColor(Color.DARK_GRAY); // Tapas
        g2.fillRect(carritoX + 40, carritoY + 5, 10, 3);

    }
}