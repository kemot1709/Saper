package com.saper;

import java.awt.*;
import java.io.*;

/**
 * W tej klasie zawierają się metody obsługujące pamięć,
 * a także wysyłanie i czytanie pliku
 */
public class Model {
    
    private Controller controller;
    
    private Field field[][];
    private int[][] scoreString;
    private String[][] scoreNames;
    
    private String file;
    private String[] scoreState;
    
    private int scoreCapacity;
    private int scoreTypes;
    
    private boolean readComplete;
    private boolean writeComplete;
    
    /**
     * Kostruktor klasy
     *
     * @param ctrl odnośnik do kontrolera
     */
    Model(Controller ctrl) {
        controller = ctrl;
        
        scoreCapacity = 10;
        scoreTypes =3;
        
        scoreString = new int[scoreTypes][scoreCapacity];
        scoreNames = new String[scoreTypes][scoreCapacity];
        for (int i = 0; i < scoreTypes; i++) {
            for (int j = 0; j < scoreCapacity; j++) {
                scoreString[i][j] = 0;
                scoreNames[i][j] = "...";
            }
        }
        
        scoreState = new String[] {"small", "medium", "big"};
        file = "scoreData.txt";
        readFile();
    }
    
    /**
     * Tworzy odpowiednik pola minowego, zawiera informacje o stanie tych pól
     *
     * @param a szerokość planszy
     * @param b wysokość planszy
     */
    public void makeField(int a, int b) {
        field = new Field[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                field[i][j] = new Field();
            }
        }
    }
    
    /**
     * Zwraca pole o współrzędnych podanych przez kontroler
     *
     * @param dimension współrzędne pola do wyciągnięcia
     *
     * @return pole minowe
     */
    public Field getField(Dimension dimension) {
        return field[dimension.width][dimension.height];
    }
    
    /**
     * Zwraca imiona użytkowników, którzy zapisali sie w najlepszych wynikach
     *
     * @return tablica imion najlepszych użytkowników
     */
    public String[][] getScoreNames() {
        return scoreNames;
    }
    
    /**
     * Zwraca wyniki użytkowników, którzy zapisali sie w najlepszych wynikach
     *
     * @return tablica wyników najlepszych użytjowników
     */
    public int[][] getScoreString() {
        return scoreString;
    }
    
    /**
     * Funkcja czytająca plik z wynikami
     */
    private void readFile() {
        BufferedReader reader;
        readComplete = false;
        try {
            reader = new BufferedReader(new FileReader(file));
        }
        catch (IOException e) {
            return;
        }
        try {
            for (int i = 0; i < scoreTypes; i++) {
                String currentLine = reader.readLine();
                if (!currentLine.equals(scoreState[i])) {
                    reader.close();
                    return;
                }
                for (int j = 0; j < scoreCapacity; j++) {
                    String numberLine = reader.readLine();
                    try {
                        int currentNumber;
                        currentNumber = Integer.valueOf(numberLine);
                        if (currentNumber < 0) {
                            throw new ArithmeticException();
                        }
                        scoreString[i][j] = currentNumber;
                    }
                    catch (Exception e) {
                        reader.close();
                        return;
                    }
                    numberLine = reader.readLine();
                    scoreNames[i][j] = numberLine;
                }
            }
            reader.close();
        }
        catch (IOException e) {
            return;
        }
        readComplete = true;
    }
    
    /**
     * Funkja wysyłająca wyniki do pliku
     * Jest wywoływana za każdym razem kiedy ktoś nowy dopisze się do tej listy
     */
    public void sendToFile() {
        BufferedWriter writer;
        writeComplete = false;
        try {
            writer = new BufferedWriter(new FileWriter(file));
        }
        catch (IOException e) {
            return;
        }
        try {
            for (int i = 0; i < scoreTypes; i++) {
                writer.write(scoreState[i]);
                writer.newLine();
                for (int j = 0; j < scoreCapacity; j++) {
                    writer.write(Integer.toString(scoreString[i][j]));
                    writer.newLine();
                    writer.write(scoreNames[i][j]);
                    writer.newLine();
                }
            }
            writer.close();
        }
        catch (IOException e) {
            return;
        }
        writeComplete = true;
    }
}