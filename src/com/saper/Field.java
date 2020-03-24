package com.saper;

/**
 * Ta klasa dla każdego pola minowego przechowuje informacje o jego stanie
 */
public class Field {
    public enum StanPola {hiden, showed, flaged}
    
    private int bombCount;
    private boolean bombState;
    private StanPola stan;
    
    /**
     * Konstruktor pola ustawia zerowe wartości
     */
    Field() {
        stan = StanPola.hiden;
        bombState = false;
        bombCount = 0;
    }
    
    /**
     * Dodaje 1 do licznika sąsiednich bomb
     */
    public void addBombNeigbour() {
        bombCount++;
    }
    
    /**
     * Ustawia ilość sąsiednich bomb na podaną przez użytkownika
     *
     * @param a ilość bomb
     */
    public void setBombCount(int a) {
        bombCount = a;
    }
    
    /**
     * Ustawia czy na danym polu znajduje się mina
     *
     * @param a true ustawia minę, false ściąga minę
     */
    public void setBombState(boolean a) {
        bombState = a;
    }
    
    /**
     * Zwraca ilość sąsiadujących z polem bomb
     *
     * @return ilość bomb
     */
    public int getBombCount() {
        return bombCount;
    }
    
    /**
     * Zwraca czy na polu jest bomba
     *
     * @return true jeśli tak, false jeśli nie
     */
    public boolean getBombState() {
        return bombState;
    }
    
    /**
     * Ustawia stan pola spośrod (hiden, showed, flagged)
     *
     * @param s stan pola
     */
    public void setStan(StanPola s) {
        stan = s;
    }
    
    /**
     * Zwraca stan pola
     *
     * @return stan pola
     */
    public StanPola getStan() {
        return stan;
    }
}
