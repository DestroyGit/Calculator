package com.example.calcv3;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Calculation implements Parcelable {
    private final Symbols symbols = new Symbols();
    private final Errors errors = new Errors();

    private String errorForResultView = ""; // текст ошибки мз класса Errors
    private ArrayList<Double> arr1; // массив с числами, введенными в enterText
    private ArrayList<Character> arr2; // массив с символами, введенными в enterText

    private String text1;
    private String text2;

    public String calculate(String s) {
        divideSumbolsFromNumbers(s);
        NumberFormat nf = new DecimalFormat("#.######"); // отсечение лишних нолей и отображение всего 6 символов после запятой
        double result = arr1.get(0);
        for (int i = 0; i < arr2.size(); i++) {
            result = countResult(result, arr1.get(i + 1), arr2.get(i));
        }
        return nf.format(result);
    }

    // разделение введенных данных в enterText на 2 массива: символы и числа
    public void divideSumbolsFromNumbers(String s){
        int b = 0;
        String sym = "";
        arr1 = new ArrayList<>();
        arr2 = new ArrayList<>();

        char [] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == symbols.getAdd() || chars[i] == symbols.getMinus() || chars[i] == symbols.getMultiply()|| chars[i] == symbols.getDivide()){
                arr2.add(chars[i]);
            }
            if (chars[i] == symbols.getAdd() || chars[i] == symbols.getMinus() || chars[i] == symbols.getMultiply()|| chars[i] == symbols.getDivide() || chars[i] == symbols.getEqual()){
                for (int j = b; j < i; j++) {
                    sym = String.format("%s%c", sym, chars[j]);
                    b = i + 1;
                }
                arr1.add(Double.parseDouble(sym));
                sym = "";
            }
        }
    }

    // выполнение операций калькулятора
    public double countResult(double a, double b, char c){
        switch (c){
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '\u00D7':
                return a * b;
            case '\u00F7':
                return a / b;
        }
        return 0;
    }

    // выпадение ошибок
    public boolean isEnterTextViewRight(String str){
        char [] chars = str.toCharArray();

        // проверка на подряд расположенные символы
        for (int i = 0; i < chars.length-1; i++) {
            if((chars[i] == symbols.getAdd() || chars[i] == symbols.getMinus() || chars[i] == symbols.getMultiply()|| chars[i] == symbols.getDivide() ||
                    chars[i] == symbols.getEqual()|| chars[i] == symbols.getDot())
                    && (chars[i + 1] == symbols.getAdd() || chars[i + 1] == symbols.getMinus() || chars[i + 1] == symbols.getMultiply()||
                    chars[i + 1] == symbols.getDivide() || chars[i + 1] == symbols.getEqual()|| chars[i + 1] == symbols.getDot())){
                errorForResultView = errors.getTwoSymbols();
                return false;
            }
        }

        // проверка на несколько точек в одном числе
        int count = 0;
        char [] charStr = str.toCharArray();
        for (int i = 0; i < charStr.length; i++) {
            if (charStr[i] == symbols.getDot()){
                count++;
            }
            if (count > 1){
                errorForResultView = errors.getTooMuchPointsOnNumber();
                return false;
            }
            if (chars[i] == symbols.getAdd() || chars[i] == symbols.getMinus() || chars[i] == symbols.getMultiply()|| chars[i] == symbols.getDivide() ||
                    chars[i] == symbols.getEqual()){
                count = 0;
            }
        }

        // если первый символ - не число
        if (chars[0] == symbols.getAdd() || chars[0] == symbols.getMinus() || chars[0] == symbols.getMultiply()|| chars[0] == symbols.getDivide() ||
                chars[0] == symbols.getEqual() || chars[0] == symbols.getDot()){
            errorForResultView = errors.getFirstSymbol();
            return false;
        }
        return true;
    }

    // чтобы сохранить данные в Parcelable
    public void forSave(String text1, String text2){
        this.text1 = text1;
        this.text2 = text2;
    }

    public Calculation(){
    }

    // то, что будем сохранять на устройстве из этого класса
    protected Calculation(Parcel in) {
        errorForResultView = in.readString();
        text1 = in.readString();
        text2 = in.readString();
    }

    // метод Parcelable
    public static final Creator<Calculation> CREATOR = new Creator<Calculation>() {
        @Override
        public Calculation createFromParcel(Parcel in) {
            return new Calculation(in);
        }

        @Override
        public Calculation[] newArray(int size) {
            return new Calculation[size];
        }
    };

    // геттеры
    public String getErrorForResultView() {
        return errorForResultView;
    }
    public String getText1() {
        return text1;
    }
    public String getText2() {
        return text2;
    }

    // сеттер для errorForResultView - используется для обнуления данных для кнопки CLEAR
    public void setErrorForResultView(String errorForResultView) {
        this.errorForResultView = errorForResultView;
    }

    // метод Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    // метод Parcelable для чтения данных с этого класса
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(errorForResultView);
        parcel.writeString(text1);
        parcel.writeString(text2);
    }
}

