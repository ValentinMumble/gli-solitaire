import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import solitaire.main.SolitaireGLI;

/**
 * Starts the solitaire and saves its window as a PNG.
 *
 * Usage: java -cp target/gli-solitaire-0.0.1-SNAPSHOT.jar:target/tools Screenshot out.png
 */
public class Screenshot {
    public static void main(String[] args) throws Exception {
        new Thread(() -> SolitaireGLI.main(new String[0])).start();
        Thread.sleep(2500);
        SwingUtilities.invokeAndWait(() -> {
            for (Frame frame : Frame.getFrames()) {
                if (frame instanceof JFrame && frame.isVisible()) {
                    JFrame window = (JFrame) frame;
                    BufferedImage image = new BufferedImage(window.getRootPane().getWidth(),
                            window.getRootPane().getHeight(), BufferedImage.TYPE_INT_RGB);
                    window.getRootPane().paint(image.getGraphics());
                    try {
                        ImageIO.write(image, "png", new File(args[0]));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        System.exit(0);
    }
}
