/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graficosjava3d;

import com.sun.j3d.utils.applet.MainFrame;
import java.awt.Frame;

/**
 *
 * @author julian.ruiz.aya
 */
public class Main {
    public static void main(String[] args) {
        Frame frame = new MainFrame(new Grafico(),800,620);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Graficacion Universo -j3d");
    }
    
}
