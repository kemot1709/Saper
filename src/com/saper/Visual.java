package com.saper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import static java.awt.Color.*;

/**
 * Ta klasa odpowiada za całą część graficzną programu
 */
public class Visual {
    public enum WindowState {gra, own, score}
    
    private Controller controller;
    
    private JFrame frame;
    
    private JPanel poleMinowe;
    
    private JPanel window;
    private JLabel time, mineCounter;
    
    private JPanel windowOwnSize;
    private JFormattedTextField getWidth, getHeight, getMines;
    
    private JPanel windowScore;
    
    private JMenuBar menuBar;
    
    private MineField[][] tablicaMin;
    private WindowState windowState;
    
    // TODO skróty klawiszowe do menu
    
    /**
     * Konstruktor klasy
     * Tworzy okno odpowiedzialne za wyświetlanie gry
     *
     * @param ctrl odnośnik do kontrolera
     */
    public Visual(Controller ctrl) {
        controller = ctrl;
        
        frame = new JFrame("Saper.exe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(250, 300));
        frame.setMaximumSize(new Dimension(1000, 500));
        createMenu();
        frame.setJMenuBar(menuBar);
        setWindowContent();
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Funkcja tworzy pasek menu gry
     */
    private void createMenu() {
        
        JMenu menu, menuWyniki;
        JMenuItem menuNowaGra, menuPoziom1, menuPoziom2, menuPoziom3, menuPoziom4, menuZakoncz, menuScore;
        
        menuBar = new JMenuBar();
        
        menu = new JMenu("Opcje");
        menuBar.add(menu);
        
        menuNowaGra = new JMenuItem("Nowa gra");
        menuNowaGra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getButton("nowa");
            }
        });
        menu.add(menuNowaGra);
        
        menu.addSeparator();
        
        menuPoziom1 = new JMenuItem("Poczatkujacy");
        menuPoziom1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getButton("small");
            }
        });
        menu.add(menuPoziom1);
        
        menuPoziom2 = new JMenuItem("Zaawansowany");
        menuPoziom2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getButton("medium");
            }
        });
        menu.add(menuPoziom2);
        
        menuPoziom3 = new JMenuItem("Expert");
        menuPoziom3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getButton("big");
            }
        });
        menu.add(menuPoziom3);
        
        menuPoziom4 = new JMenuItem("Dowolny");
        menuPoziom4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getButton("own");
            }
        });
        menu.add(menuPoziom4);
        
        menu.addSeparator();
        
        menuWyniki = new JMenu("Najlepsze wyniki");
        menuScore = new JMenuItem("Poczatkujacy");
        menuScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getButton("scoreSmall");
            }
        });
        menuWyniki.add(menuScore);
        menuScore = new JMenuItem("Zaawansowany");
        menuScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getButton("scoreMedium");
            }
        });
        menuWyniki.add(menuScore);
        menuScore = new JMenuItem("Expert");
        menuScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getButton("scoreBig");
            }
        });
        menuWyniki.add(menuScore);
        menu.add(menuWyniki);
        
        menu.addSeparator();
        
        menuZakoncz = new JMenuItem("Zakoncz");
        menuZakoncz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(menuZakoncz);
    }
    
    /**
     * Funkcja czyści planszę i tworzy nową o podanych parametrach
     *
     * @param x szerokość planszy
     * @param y wysokość planszy
     */
    private void initializeField(int x, int y) {
        poleMinowe.removeAll();
        
        tablicaMin = new MineField[x][y];
        for (int i = 0; i < x; i++) {
            JPanel horizontal = new JPanel(new GridLayout());
            for (int j = 0; j < y; j++) {
                tablicaMin[i][j] = new MineField(i, j, this);
                horizontal.add(tablicaMin[i][j]);
            }
            poleMinowe.add(horizontal);
        }
        
        poleMinowe.setPreferredSize(new Dimension(y * 20, x * 20));
        poleMinowe.setMinimumSize(poleMinowe.getPreferredSize());
        poleMinowe.setMaximumSize(poleMinowe.getPreferredSize());
        
        frame.setSize(new Dimension(y * 20 + 20, x * 20 + 150));
        
        poleMinowe.revalidate();
        poleMinowe.repaint();
    }
    
    /**
     * W sumie na chuj ta funkcja?
     * Do wywalenia
     *
     * @param x szerokość planszy
     * @param y wysokość planszy
     */
    public void resetView(int x, int y) {
        initializeField(x, y);
    }
    
    /**
     * Zwraca pole minowe, które jest widoczne na planszy
     *
     * @param a współrzędna szerokości pola
     * @param b współrzędna wysokości pola
     *
     * @return pole o podanych współrzędnych
     */
    public MineField getMineField(int a, int b) {
        return tablicaMin[a][b];
    }
    
    /**
     * Zwraca odnośnik na kontroler
     *
     * @return konroler
     */
    public Controller getController() {
        return controller;
    }
    
    /**
     * Zwraca okno wyswietlające grę
     *
     * @return okno wyświetlające
     */
    public JFrame getFrame() {
        return frame;
    }
    
    /**
     * Aktualizuje czas wyświetlany w oknie gry
     *
     * @param t czas
     */
    public void setTime(int t) {
        time.setText(Integer.toString(t));
    }
    
    /**
     * Aktualizuje ilość min pozostałych do zaminowania w oknie gry
     *
     * @param x ilość min
     */
    public void setMineCounter(int x) {
        mineCounter.setText(Integer.toString(x));
    }
    
    /**
     * Konstruktor okna gry, inicjuje wszystkie elementy widoczne w oknie gry
     */
    public void setWindowContent() {
        // TODO ikona nowej gry i inne ikony
        
        JButton newGame;
        newGame = new JButton();
        newGame.setPreferredSize(new Dimension(50, 50));
        newGame.setMinimumSize(newGame.getPreferredSize());
        newGame.setMaximumSize(newGame.getPreferredSize());
        
        newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == 1) {
                    controller.getButton("nowa");
                }
            }
        });
        
        time = new JLabel();
        time.setPreferredSize(new Dimension(80, 40));
        time.setMinimumSize(time.getPreferredSize());
        time.setMaximumSize(time.getPreferredSize());
        time.setBackground(black);
        time.setForeground(red);
        time.setOpaque(true);
        time.setFont(new Font("Arial", Font.BOLD, 15));
        
        mineCounter = new JLabel();
        mineCounter.setPreferredSize(time.getPreferredSize());
        mineCounter.setMinimumSize(mineCounter.getPreferredSize());
        mineCounter.setMaximumSize(mineCounter.getPreferredSize());
        mineCounter.setBackground(black);
        mineCounter.setForeground(red);
        mineCounter.setOpaque(true);
        mineCounter.setFont(new Font("Arial", Font.BOLD, 15));
        
        poleMinowe = new JPanel(new GridLayout(0, 1));
        
        window = new JPanel();
        window.add(time);
        window.add(newGame);
        window.add(mineCounter);
        window.add(poleMinowe);
        
        setWindow("gra");
    }
    
    /**
     * Konstruktor okna do wpisywania wielkości własnej planszy
     */
    public void setWindowOwnSizeContent() {
        JLabel giveWidth;
        giveWidth = new JLabel("Podaj szerokość planszy");
        
        JLabel giveHeight;
        giveHeight = new JLabel("Podaj wysokośc planszy");
        
        JLabel giveMines;
        giveMines = new JLabel("Podaj ilość min na planszy");
        
        getWidth = new JFormattedTextField(NumberFormat.getNumberInstance());
        getWidth.setMinimumSize(new Dimension(50, 20));
        getWidth.setPreferredSize(getWidth.getMinimumSize());
        getWidth.setPreferredSize(getWidth.getMinimumSize());
        
        getHeight = new JFormattedTextField(NumberFormat.getNumberInstance());
        getHeight.setMinimumSize(getWidth.getMinimumSize());
        getHeight.setPreferredSize(getHeight.getMinimumSize());
        getHeight.setPreferredSize(getHeight.getMinimumSize());
        
        getMines = new JFormattedTextField(NumberFormat.getNumberInstance());
        getMines.setMinimumSize(getWidth.getMinimumSize());
        getMines.setPreferredSize(getMines.getMinimumSize());
        getMines.setPreferredSize(getMines.getMinimumSize());
        
        JButton buttonOK;
        buttonOK = new JButton("OK");
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = Integer.valueOf(getWidth.getText());
                int height = Integer.valueOf(getHeight.getText());
                int mines = Integer.valueOf(getMines.getText());
                if (controller.setGameValues(width, height, mines)) {
                    controller.getButton("nowa");
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Podano niepoprawne dane!!!" +
                            "\nUpewnij się że wszystkie dane zostały poprawnie podane" +
                            "\nSzerokość musi się zawierac pomiędzy 8, a 60" +
                            "\nWysokość musi się zawierac pomiędzy 8, a 30" +
                            "\nIlość min musi być większa od zera i mniejsza od 1/3 ilości pól"
                    );
                }
            }
        });
        
        JButton buttonClose;
        buttonClose = new JButton("Close");
        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getButton("nowa");
            }
        });
        
        windowOwnSize = new JPanel(new GridLayout(0, 1));
        JPanel panel = new JPanel();
        panel.add(giveWidth);
        panel.add(getWidth);
        windowOwnSize.add(panel);
        panel = new JPanel();
        panel.add(giveHeight);
        panel.add(getHeight);
        windowOwnSize.add(panel);
        panel = new JPanel();
        panel.add(giveMines);
        panel.add(getMines);
        windowOwnSize.add(panel);
        panel = new JPanel();
        panel.add(buttonOK);
        panel.add(buttonClose);
        windowOwnSize.add(panel);
        
        setWindow("own");
    }
    
    /**
     * Konstruktor okna wyświetlającego najwyższe wyniki graczy
     *
     * @param size wybór wielkości planszy z której wyniki mają być wyświetlane
     * @param ints tablica wyników graczy
     * @param s    tablica imion graczy, którzy osiągnęli najlepsze wyniki
     */
    public void setWindowScoreContent(int size, int[][] ints, String[][] s) {
        
        JLabel headerLabel;
        headerLabel = new JLabel();
        switch (size) {
            case 0:
                headerLabel.setText("Najlepsze wyniki dla poziomu początkującego");
                break;
            case 1:
                headerLabel.setText("Najlepsze wyniki dla poziomu zaawansowanego");
                break;
            case 2:
                headerLabel.setText("Najlepsze wyniki dla poziomu experta");
                break;
            default:
                headerLabel.setText("Default header");
                break;
        }
        headerLabel.setPreferredSize(new Dimension(300, 80));
        headerLabel.setMinimumSize(headerLabel.getPreferredSize());
        headerLabel.setMaximumSize(headerLabel.getPreferredSize());
        
        JTextArea bodyLabel;
        bodyLabel = new JTextArea();
        String scoreTable = new String("");
        for (int i = 0; i < 10; i++) {
            scoreTable = scoreTable + (i + 1) + ".\t" + ints[size][i] + "\t" + s[size][i] + "\n";
        }
        bodyLabel.setText(scoreTable);
        bodyLabel.setDisabledTextColor(black);
        bodyLabel.setEnabled(false);
        bodyLabel.setMinimumSize(new Dimension(500, 500));
        
        windowScore = new JPanel(new GridLayout(0, 1));
        windowScore.add(headerLabel);
        windowScore.add(bodyLabel);
        
        setWindow("score");
    }
    
    /**
     * Resetuje okno wyświetlające się na ekranie i ustawia na to, które podano
     *
     * @param s okno do wyświetlenia
     */
    private void setWindow(String s) {
        frame.getContentPane().removeAll();
        
        switch (s) {
            case "gra":
                frame.setContentPane(window);
                windowState = WindowState.gra;
                break;
            case "own":
                frame.setContentPane(windowOwnSize);
                frame.setSize(frame.getMinimumSize());
                windowState = WindowState.own;
                break;
            case "score":
                frame.setContentPane(windowScore);
                frame.setSize(300, 400);
                windowState = WindowState.score;
                break;
            default:
                break;
        }
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * Zwraca stan w jakim znajduje się okno
     *
     * @return stan okna
     */
    public WindowState getWindowState() {
        return windowState;
    }
}
