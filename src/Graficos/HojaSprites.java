package Graficos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HojaSprites {
    public final int[] pixeles;
    private final int ancho;
    private final int alto;


    public HojaSprites(final int ancho, final int alto, final String ruta) throws IOException {
        this.pixeles = new int[ancho * alto];
        this.ancho = ancho;
        this.alto = alto;

        try {
            BufferedImage image = ImageIO.read(HojaSprites.class.getResource(ruta));
            image.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public int[] getPixeles() {
        return pixeles;
    }

    public int getAncho() {
        return ancho;
    }


    public int getAlto() {
        return alto;
    }


}
