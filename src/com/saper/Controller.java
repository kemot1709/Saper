package com.saper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * W tej klasie zawiera się cała logika programu, obsługa części wizualnej i przechowującej pamięć
 */
public class Controller {
    private enum Stan {small, medium, big, custom, score}
    
    private int gameMines;
    private int gameWidth;
    private int gameHeight;
    private boolean gameStarted;
    private boolean gameOver;
    private int gameFields;
    private int minesFlagged;
    
    private int maxWidth, maxHeight, minWidth, minHeight;
    
    private int timeCurrent;
    private Timer timeEllapsed;
    private ActionListener timerAction;
    
    private Stan stan;
    private Visual visual;
    private Model model;
    
    /**
     * Konstruktor kontrolera
     * Inincjuje dane małej planszy i warunki wielkości planszy
     */
    Controller() {
        stan = Stan.small;
        gameMines = 10;
        gameWidth = 8;
        gameHeight = 8;
        
        minWidth = 8;
        minHeight = 8;
        maxWidth = 60;
        maxHeight = 30;
    }
    
    /**
     * Funkcja przekazuje do kontrolera odnośniki do części wizualnej i pamięciowej
     * dodatkowo po przekazaniu tych parametrów tworzy pierwszą grę
     * właściwie dalsza część konstruktora
     *
     * @param v visual
     * @param m model
     */
    public void addMVC(Visual v, Model m) {
        visual = v;
        model = m;
        
        timerAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeCurrent++;
                visual.setTime(timeCurrent);
            }
        };
        timeEllapsed = new Timer(1000, timerAction);
        resetGameProperties();
    }
    
    /**
     * obsługa kliknięcia na pole minowe lewym przyciskiem myszy
     *
     * @param mineField pole minowe
     */
    public void makeClick(MineField mineField) {
        /*tworzenie nowej planszy, jeśli pierwszy strzał był na minę*/
        if (gameStarted == false) {
            while (model.getField(mineField.getCordinates()).getBombState() == true) {
                makeGame();
            }
            timeCurrent = 0;
            timeEllapsed.start();
            gameStarted = true;
        }
        
        if (gameOver != false) {
            return;
        }
        
        /*jeśli pole jest zflagowane to pomijamy jego dalszą analizę*/
        if (model.getField(mineField.getCordinates()).getStan() == Field.StanPola.flaged) {
            return;
        }
        /*obsługa kliknięcia pola z miną*/
        else if (model.getField(mineField.getCordinates()).getBombState() == true) {
            mineField.setText("X");
            gameOver = true;
            timeEllapsed.stop();
            JOptionPane.showMessageDialog(visual.getFrame(), "Przegrałeś");
            showAllMines();
        }
        /*obsługa kliknięcia pola bez miny i bez sąsiadująej miny
         * dodatkowo klikane są wszystkie sąsiednie pola*/
        else if (model.getField(mineField.getCordinates()).getBombCount() == 0 && model.getField(mineField.getCordinates()).getStan() == Field.StanPola.hiden) {
            gameFields--;
            model.getField(mineField.getCordinates()).setStan(Field.StanPola.showed);
            if (mineField.getCordinates().width == 0) {
                makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height));
                if (mineField.getCordinates().height == 0) {
                    makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height + 1));
                    makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height + 1));
                }
                else if (mineField.getCordinates().height == gameHeight - 1) {
                    makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height - 1));
                    makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height - 1));
                }
                else {
                    makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height + 1));
                    makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height + 1));
                    makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height - 1));
                    makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height - 1));
                }
            }
            else if (mineField.getCordinates().width == gameWidth - 1) {
                makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height));
                if (mineField.getCordinates().height == 0) {
                    makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height + 1));
                    makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height + 1));
                }
                else if (mineField.getCordinates().height == gameHeight - 1) {
                    makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height - 1));
                    makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height - 1));
                }
                else {
                    makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height + 1));
                    makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height + 1));
                    makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height - 1));
                    makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height - 1));
                }
            }
            else if (mineField.getCordinates().height == 0) {
                makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height));
                makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height));
                makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height + 1));
                makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height + 1));
                makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height + 1));
            }
            else if (mineField.getCordinates().height == gameHeight - 1) {
                makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height));
                makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height));
                makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height - 1));
                makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height - 1));
                makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height - 1));
            }
            else {
                makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height));
                makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height));
                makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height + 1));
                makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height + 1));
                makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height + 1));
                makeClick(visual.getMineField(mineField.getCordinates().width + 1, mineField.getCordinates().height - 1));
                makeClick(visual.getMineField(mineField.getCordinates().width, mineField.getCordinates().height - 1));
                makeClick(visual.getMineField(mineField.getCordinates().width - 1, mineField.getCordinates().height - 1));
            }
        }
        /*obsługa kliknięcia na pole z sąsiadującą bombą i wyświetlenie cyfry*/
        else if (model.getField(mineField.getCordinates()).getStan() == Field.StanPola.hiden) {
            mineField.setFontColor(model.getField(mineField.getCordinates()).getBombCount());
            mineField.setText(String.valueOf(model.getField(mineField.getCordinates()).getBombCount()));
            gameFields--;
            model.getField(mineField.getCordinates()).setStan(Field.StanPola.showed);
        }
        mineField.setEnabled(false);
        /*sprawdzenie warunku na zwycięstwo w grze*/
        if (gameFields == gameMines && gameOver == false) {
            gameOver = true;
            timeEllapsed.stop();
            makeWinGame();
        }
    }
    
    /**
     * obsługa kliknięcia na pole minowe prawym przyciskiem myszy
     *
     * @param mineField pole minowe
     */
    public void makeFlag(MineField mineField) {
        if (gameStarted == false || gameOver != false) {
            return;
        }
        if (model.getField(mineField.getCordinates()).getStan() == Field.StanPola.hiden) {
            /*stawianie flagi*/
            mineField.setText("!");
            minesFlagged++;
            visual.setMineCounter(gameMines - minesFlagged);
            model.getField(mineField.getCordinates()).setStan(Field.StanPola.flaged);
        }
        else if (model.getField(mineField.getCordinates()).getStan() == Field.StanPola.flaged) {
            /*ściąganie flagi*/
            mineField.setText("");
            minesFlagged--;
            visual.setMineCounter(gameMines - minesFlagged);
            model.getField(mineField.getCordinates()).setStan(Field.StanPola.hiden);
        }
    }
    
    /**
     * obsługa opcji z menu gry
     *
     * @param s polecenie do obsługi
     */
    public void getButton(String s) {
        switch (s) {
            case "small":
                stan = Stan.small;
                gameHeight = 8;
                gameWidth = 8;
                gameMines = 10;
                resetGameProperties();
                break;
            case "nowa":
                resetGameProperties();
                break;
            case "medium":
                stan = Stan.medium;
                gameHeight = 16;
                gameWidth = 16;
                gameMines = 40;
                resetGameProperties();
                break;
            case "big":
                stan = Stan.big;
                gameHeight = 30;
                gameWidth = 16;
                gameMines = 99;
                resetGameProperties();
                break;
            case "own":
                stan = Stan.custom;
                visual.setWindowOwnSizeContent();
                break;
            case "scoreSmall":
                stan = Stan.score;
                visual.setWindowScoreContent(0, model.getScoreString(), model.getScoreNames());
                break;
            case "scoreMedium":
                stan = Stan.score;
                visual.setWindowScoreContent(1, model.getScoreString(), model.getScoreNames());
                break;
            case "scoreBig":
                stan = Stan.score;
                visual.setWindowScoreContent(2, model.getScoreString(), model.getScoreNames());
                break;
            default:
                break;
        }
    }
    
    /**
     * Tworzenie nowej gry w szczególności rozmieszczenie bomb
     */
    private void makeGame() {
        //try
        model.makeField(gameWidth, gameHeight);
        int iloscMin = gameMines;
        
        while (iloscMin > 0) {
            /*szukanie miejsc na bomby*/
            int x, y;
            Random rand = new Random();
            x = rand.nextInt(gameWidth);
            y = rand.nextInt(gameHeight);
            
            if (x == 0 || x == gameWidth - 1) {
                if (y == 0 || y == gameHeight - 1) { //któryś z czterech rogowych
                    if (model.getField(new Dimension(x, y)).getBombState() != true && model.getField(new Dimension(x, y)).getBombCount() < 3) {
                        model.getField(new Dimension(x, y)).setBombState(true);
                        if (x == 0) {
                            model.getField(new Dimension(x + 1, y)).addBombNeigbour();
                            if (y == 0) {
                                model.getField(new Dimension(x + 1, y + 1)).addBombNeigbour();
                                model.getField(new Dimension(x, y + 1)).addBombNeigbour();
                            }
                            else {
                                model.getField(new Dimension(x + 1, y - 1)).addBombNeigbour();
                                model.getField(new Dimension(x, y - 1)).addBombNeigbour();
                            }
                        }
                        else {
                            model.getField(new Dimension(x - 1, y)).addBombNeigbour();
                            if (y == 0) {
                                model.getField(new Dimension(x - 1, y + 1)).addBombNeigbour();
                                model.getField(new Dimension(x, y + 1)).addBombNeigbour();
                            }
                            else {
                                model.getField(new Dimension(x - 1, y - 1)).addBombNeigbour();
                                model.getField(new Dimension(x, y - 1)).addBombNeigbour();
                            }
                        }
                        iloscMin--;
                    }
                }
                else { // graniczy tylko z bokiem x
                    if (model.getField(new Dimension(x, y)).getBombState() != true && model.getField(new Dimension(x, y)).getBombCount() < 5) {
                        model.getField(new Dimension(x, y)).setBombState(true);
                        model.getField(new Dimension(x, y - 1)).addBombNeigbour();
                        model.getField(new Dimension(x, y + 1)).addBombNeigbour();
                        if (x == 0) {
                            model.getField(new Dimension(x + 1, y - 1)).addBombNeigbour();
                            model.getField(new Dimension(x + 1, y)).addBombNeigbour();
                            model.getField(new Dimension(x + 1, y + 1)).addBombNeigbour();
                        }
                        else {
                            model.getField(new Dimension(x - 1, y - 1)).addBombNeigbour();
                            model.getField(new Dimension(x - 1, y)).addBombNeigbour();
                            model.getField(new Dimension(x - 1, y + 1)).addBombNeigbour();
                        }
                        iloscMin--;
                    }
                }
            }
            else if (y == 0 || y == gameHeight - 1) { // graniczy tylko z bokiem y
                if (model.getField(new Dimension(x, y)).getBombState() != true && model.getField(new Dimension(x, y)).getBombCount() < 5) {
                    model.getField(new Dimension(x, y)).setBombState(true);
                    model.getField(new Dimension(x + 1, y)).addBombNeigbour();
                    model.getField(new Dimension(x - 1, y)).addBombNeigbour();
                    if (y == 0) {
                        model.getField(new Dimension(x - 1, y + 1)).addBombNeigbour();
                        model.getField(new Dimension(x, y + 1)).addBombNeigbour();
                        model.getField(new Dimension(x + 1, y + 1)).addBombNeigbour();
                    }
                    else {
                        model.getField(new Dimension(x - 1, y - 1)).addBombNeigbour();
                        model.getField(new Dimension(x, y - 1)).addBombNeigbour();
                        model.getField(new Dimension(x + 1, y - 1)).addBombNeigbour();
                    }
                    iloscMin--;
                }
            }
            else {
                if (model.getField(new Dimension(x, y)).getBombState() != true && model.getField(new Dimension(x, y)).getBombCount() < 8) {
                    model.getField(new Dimension(x, y)).setBombState(true);
                    model.getField(new Dimension(x - 1, y - 1)).addBombNeigbour();
                    model.getField(new Dimension(x - 1, y)).addBombNeigbour();
                    model.getField(new Dimension(x - 1, y + 1)).addBombNeigbour();
                    model.getField(new Dimension(x, y - 1)).addBombNeigbour();
                    model.getField(new Dimension(x, y + 1)).addBombNeigbour();
                    model.getField(new Dimension(x + 1, y - 1)).addBombNeigbour();
                    model.getField(new Dimension(x + 1, y)).addBombNeigbour();
                    model.getField(new Dimension(x + 1, y + 1)).addBombNeigbour();
                    iloscMin--;
                }
            }
//            System.out.println(x + " " + y);
        }
    }
    
    /**
     * Tworzenie nowej gry, w szczególności części wizualnej
     * Resetowanie stanu gry
     */
    private void resetGameProperties() {
        if (visual.getWindowState() != Visual.WindowState.gra) {
            visual.setWindowContent();
        }
        visual.resetView(gameWidth, gameHeight);
        gameFields = gameWidth * gameHeight;
        makeGame();
        gameStarted = false;
        gameOver = false;
        timeEllapsed.stop();
        timeCurrent = 0;
        minesFlagged = 0;
        visual.setTime(timeCurrent);
        visual.setMineCounter(gameMines);
    }
    
    /**
     * Wyświetlanie wszystkich min po przegranej grze
     */
    private void showAllMines() {
        for (int i = 0; i < gameWidth; i++) {
            for (int j = 0; j < gameHeight; j++) {
                if (model.getField(new Dimension(i, j)).getStan() == Field.StanPola.hiden || model.getField(new Dimension(i, j)).getStan() == Field.StanPola.flaged) {
                    if (model.getField(new Dimension(i, j)).getBombState() == true) {
                        visual.getMineField(i, j).setText("X");
                    }
                }
            }
        }
    }
    
    /**
     * Wywołuje okno dialogowe po zwycięskiej grze
     */
    private void makeWinGame() {
        int k = 0;
        switch (stan) {
            case custom:
                JOptionPane.showMessageDialog(visual.getFrame(), "Wygrałeś! \n Twój czas to: " + timeCurrent + " sekund");
                return;
            case small:
                k = 0;
                break;
            case medium:
                k = 1;
                break;
            case big:
                k = 2;
                break;
            default:
                break;
        }
        int[][] scoreBoard = model.getScoreString();
        String[][] scoreName = model.getScoreNames();
        if (scoreBoard[k][9] > 0 && scoreBoard[k][9] <= timeCurrent) {
            JOptionPane.showMessageDialog(visual.getFrame(), "Wygrałeś! \n Twój czas to: " + timeCurrent + " sekund");
            return;
        }
        else {
            String name = (String) JOptionPane.showInputDialog(visual.getFrame(), "Wygrałeś! \n  Twój czas to: " + timeCurrent + " sekund \n Podaj swoje imię: ");
            for (int i = 0; i < 10; i++) {
                if (timeCurrent < scoreBoard[k][i] || scoreBoard[k][i] == 0) {
                    for (int j = 9; j > i; j--) {
                        scoreBoard[k][j] = scoreBoard[k][j - 1];
                        scoreName[k][j] = scoreName[k][j - 1];
                    }
                    scoreBoard[k][i] = timeCurrent;
                    scoreName[k][i] = name;
                    break;
                }
            }
            model.sendToFile();
        }
    }
    
    /**
     * Ustawia parametry gry na podane przez użytkownika
     *
     * @param width  szerokość
     * @param height wysokość
     * @param mines  ilość min
     *
     * @return true jeśli się udało, false jeśli, któryś jest zły
     */
    public boolean setGameValues(int width, int height, int mines) {
        if (width < minWidth || width > maxWidth) {
            return false;
        }
        if (height < minHeight || height > maxHeight) {
            return false;
        }
        if (mines <= 0 || mines > width * height / 3) {
            return false;
        }
        gameWidth = width;
        gameHeight = height;
        gameMines = mines;
        return true;
    }
}
