package io.nson.javapt.ui;


import io.nson.javapt.core.Frame;
import org.apache.logging.log4j.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WndMain extends Main {
    private static final Logger logger = LogManager.getLogger(WndMain.class);

    class RenderFrame extends JFrame {
        private final Insets ins;
        private final Dimension dim;
        private final Graphics gr2d;

        RenderFrame(String title) {
            super(title);

            pack();

            ins = getInsets();
            dim = new Dimension(w + ins.left + ins.right, h + ins.top + ins.bottom);
            setSize(dim);
            setResizable(false);
            addWindowListener(new WindowAdapter() {
                @Override
                public void  windowClosing(WindowEvent we) {
                    closing = true;
                    dispose();
                }
            });

            setLocationRelativeTo(null);
            setBackground(Color.RED);
            setVisible(true);

            gr2d = image.getGraphics();
            gr2d.setColor(Color.BLUE);
            gr2d.drawRect(0, 0, w - 1, h - 1);
            repaint(ins.left, ins.top, w, h) ;
        }

        @Override
        public void paint(Graphics graphics) {
            final Graphics2D g2d = (Graphics2D)graphics;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.drawImage(image, ins.left, ins.top, null);
        }
    }

    private boolean closing = false;
    private final RenderFrame wnd;

    public WndMain(Config cfg) {
        super(cfg);

        wnd = new RenderFrame("JavaPT");
    }

    @Override
    protected boolean isClosing() {
        return closing;
    }

    @Override
    protected void writeRow(int y, Frame.Row row) {
        final int sy = h - y - 1;
        super.writeRow(y, row);

        wnd.repaint(wnd.ins.left, wnd.ins.top + sy, w, 1);
    }
}
