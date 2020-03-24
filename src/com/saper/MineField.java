package com.saper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.*;

/**
 *  Ta klasa tworzy jest pojedynczym polem minowym wyświetlanym na ekranie
 */
public class MineField extends JButton {
    private int x;
    private int y;
    private Visual visual;
    
    /**
     * Tworzenie pola minowego widocznego na planszy
     *
     * @param a miejsce x pola
     * @param b miejsce y pola
     * @param v odnośnik do klasy tworzącej dane pole minowe
     */
    MineField(int a, int b, Visual v) {
        x = a;
        y = b;
        visual = v;
        Dimension dim = new Dimension(20, 20);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        this.setPreferredSize(dim);
        this.setFont(new Font("Arial", Font.BOLD, 10));
        this.setMargin(new Insets(0, 0, 0, 0));
        MineField field = this;
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (e.getButton()) {
                    case 1:
                        visual.getController().makeClick(field);
                        break;
                    case 3:
                        visual.getController().makeFlag(field);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    /**
     * Zwaraca współrzędne pola minowego
     *
     * @return współrzędne pola minowego
     */
    public Dimension getCordinates() {
        return new Dimension(x, y);
    }
    
    /**
     * Ustawia kolor napisu w zależności od ilości sasiadujących min
     *
     * @param n Ilość sąsidujących min
     */
    public void setFontColor(int n) {
        // TODO pozmieniać kolory liter w zależności od ilości min sąsiadujących
        // nie chce działać
        switch (n) {
            case 1:
                this.setForeground(blue);
                break;
            case 2:
                this.setForeground(green);
                break;
            case 3:
                this.setForeground(red);
                break;
            case 4:
                this.setForeground(new Color(50, 50, 150));
                break;
            case 5:
                this.setForeground(new Color(150, 50, 50));
                break;
            case 6:
                this.setForeground(new Color(50, 150, 50));
                break;
            case 7:
                this.setForeground(cyan);
                break;
            case 8:
                this.setForeground(magenta);
                break;
            default:
//                setForeground(black);
                break;
        }
    }
}
