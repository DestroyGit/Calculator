package com.example.calcv3;

/**
 * 1. Допилить Parcelable: если выскочил ERROR, и после этого какой-то новый результат
 * записался в resultText, то при, например, повороте экрана, вместо числа будет
 * последняя ошибка, которая была ранее
 * 2. Допилить нажатие кнопки % процента
 * 3. 
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Symbols symbols;
    private Calculation calculation;

    private final static String keyEnterAndResult = "EnterResult";

    private TextView enterText;
    private TextView resultText;
    private String stringEnterText = "";
    private String stringResultText = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        symbols = new Symbols();
        calculation = new Calculation();

        initViews();

    }

    // инициализация view
    private void initViews(){
        initTextViews();
        initButtons();
    }

    // инициализация текстовых view
    private void initTextViews(){
        enterText = findViewById(R.id.enterText);
        resultText = findViewById(R.id.resultText);
    }

    // инициализация кнопок
    public void initButtons() {
        Button btn0 = findViewById(R.id.btn_0);
        Button btn1 = findViewById(R.id.btn_1);
        Button btn2 = findViewById(R.id.btn_2);
        Button btn3 = findViewById(R.id.btn_3);
        Button btn4 = findViewById(R.id.btn_4);
        Button btn5 = findViewById(R.id.btn_5);
        Button btn6 = findViewById(R.id.btn_6);
        Button btn7 = findViewById(R.id.btn_7);
        Button btn8 = findViewById(R.id.btn_8);
        Button btn9 = findViewById(R.id.btn_9);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnMin = findViewById(R.id.btn_minus);
        Button btnMult = findViewById(R.id.btn_mul);
        Button btnDiv = findViewById(R.id.btn_div);
        Button btnDot = findViewById(R.id.btn_dot);
        Button btnEq = findViewById(R.id.btn_equal);
        Button btnClear = findViewById(R.id.btn_clear);
        Button btnBcksp = findViewById(R.id.btn_backspace);
        Button[] buttons = {btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnAdd, btnMin, btnMult, btnDiv, btnEq, btnDot, btnClear, btnBcksp};
        for (int i = 0; i < buttons.length; i++) {
            clickBtn(buttons[i]);
        }
    }

    // сделать кнопки кликабельными для метода onClick
    public void clickBtn(Button btn) {
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_0){
            addEnterText('0');
        }
        if (view.getId() == R.id.btn_1){
            addEnterText('1');
        }
        if (view.getId() == R.id.btn_2){
            addEnterText('2');
        }
        if (view.getId() == R.id.btn_3){
            addEnterText('3');
        }
        if (view.getId() == R.id.btn_4){
            addEnterText('4');
        }
        if (view.getId() == R.id.btn_5){
            addEnterText('5');
        }
        if (view.getId() == R.id.btn_6){
            addEnterText('6');
        }
        if (view.getId() == R.id.btn_7){
            addEnterText('7');
        }
        if (view.getId() == R.id.btn_8){
            addEnterText('8');
        }
        if (view.getId() == R.id.btn_9){
            addEnterText('9');
        }
        if (view.getId() == R.id.btn_add){
            addEnterText(symbols.getAdd());
        }
        if (view.getId() == R.id.btn_mul){
            addEnterText(symbols.getMultiply());
        }
        if (view.getId() == R.id.btn_div){
            addEnterText(symbols.getDivide());
        }
        if (view.getId() == R.id.btn_minus){
            addEnterText(symbols.getMinus());
        }
        if (view.getId() == R.id.btn_dot){
            addEnterText(symbols.getDot());
        }
        if (view.getId() == R.id.btn_clear){
            clearTextView();
        }
        if (view.getId() == R.id.btn_equal){
            addEnterText(symbols.getEqual());
            showResult();
        }
        if (view.getId() == R.id.btn_backspace){
            deleteLastSymbol();
        }
    }

    // добавление символов при нажатии кнопок в enterText
    private void addEnterText(char c){
        stringEnterText = String.format("%s%c", stringEnterText, c);
        enterText.setText(stringEnterText);
    }

    // очистить все текстовые view
    private void clearTextView(){
        stringEnterText = "";
        stringResultText = "0";
        calculation.setErrorForResultView(""); //очищаем ошибку, которая хранится в Calculation
        enterText.setText(stringEnterText);
        resultText.setText(stringResultText);
    }

    // показать результат вычислений
    private void showResult(){
        if (calculation.isEnterTextViewRight(stringEnterText)) { //корректная ли строка в enterText
            stringResultText = calculation.calculate(stringEnterText);
            resultText.setText(stringResultText);
        } else {
            resultText.setText(calculation.getErrorForResultView());
        }
    }

    //удаление последнего символа из enterText
    public void deleteLastSymbol() {
        String str = "";
        ArrayList<Character> strArray = new ArrayList<>();
        if (stringEnterText.length() > 0) {
            char[] chars = stringEnterText.toCharArray();
            for (int i = 0; i < chars.length - 1; i++) {
                strArray.add(chars[i]);
            }
            for (char c : strArray) {
                str = String.format("%s%c", str, c);
            }
            stringEnterText = str;
            enterText.setText(stringEnterText);
        }
    }

    // сохранение данных на устройстве при повороте экрана
    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        if (calculation.getErrorForResultView().equals("")) { // если нет ошибок при вычислении
            calculation.forSave(stringEnterText, stringResultText);
        } else {
            calculation.forSave(stringEnterText, calculation.getErrorForResultView());
        }
        instanceState.putParcelable(keyEnterAndResult, calculation);
    }

    // восстановление данных при повороте экрана
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculation = savedInstanceState.getParcelable(keyEnterAndResult);
        stringEnterText = calculation.getText1();
        stringResultText = calculation.getText2();
        enterText.setText(stringEnterText);
        resultText.setText(stringResultText);
    }
}