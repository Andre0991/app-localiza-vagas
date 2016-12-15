package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities;

/**
 * Created by andreperictavares on 14/12/2016.
 */
public class Placa {
    public static final int PLATE_LENGTH = 7;
    public static final int CORECT_NUMBER_OF_DIGITS_IN_PLATE = 4;
    public static final int CORECT_NUMBER_OF_LETTERS_IN_PLATE = 3;
    String letters;
    String numbers;

    public Placa(String characters) throws Exception {
        String placa_sem_hifen = characters.replace("-", "");
        if (placa_sem_hifen.length() != PLATE_LENGTH) {
            throw new Exception("Placa deve ter exatamente 7 caracteres! (sem o hífen)");
        }
        this.letters = placa_sem_hifen.substring(0, 3);
        this.numbers = placa_sem_hifen.substring(4, 7);
    }

    public static boolean sequenceOfDigitsHasCorrectLength(String s) {
        return s.length() == CORECT_NUMBER_OF_DIGITS_IN_PLATE;
    }

    public static boolean sequenceOfLettersHasCorrectLength(String s) {
        return s.length() == CORECT_NUMBER_OF_LETTERS_IN_PLATE;
    }

    @Override
    public String toString() {
        return String.format("%s-%s", letters, numbers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Placa placa = (Placa) o;

        if (!letters.equals(placa.letters)) return false;
        return numbers.equals(placa.numbers);

    }

    @Override
    public int hashCode() {
        int result = letters.hashCode();
        result = 31 * result + numbers.hashCode();
        return result;
    }
}
