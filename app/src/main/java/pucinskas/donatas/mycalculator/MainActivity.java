package pucinskas.donatas.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2;
    private int startIndex;
    private ArrayList<Integer> numbers;
    private ArrayList<Character> symbols;

    final char DAUG = '*';
    final char DAL = '/';
    final char SUM = '+';
    final char ATIM = '-';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        numbers = new ArrayList<Integer>();
        symbols = new ArrayList<Character>();
        startIndex = 0;
    }

    // prideda skaiciu.
    public void onClickNum(View view){
        Button btn = findViewById(view.getId());
        String str = textView1.getText() + btn.getText().toString();
        textView1.setText(str);
    }

    public void onClickSymbol(View view){
        String str = textView1.getText().toString();
        Button btn = findViewById(view.getId());
        char symbol = btn.getText().toString().charAt(0);
        char lastCh = str.charAt(str.length() - 1);

        // jei reikia perraso zenkla.
        if (lastCh == SUM || lastCh == ATIM || lastCh == DAUG || lastCh == DAL){
            str = str.substring(0,str.length()-1) + symbol;
            symbols.remove(symbols.size()-1);
            symbols.add(symbol);
        }
        else {
            symbols.add(symbol);
            addNumber(startIndex,str.length());
            str = str + symbol;
            startIndex = str.length();
        }
        textView1.setText(str);
    }

    // isvalo ekrana.
    public void clearScr(View view){
        textView1.setText("");
        textView2.setText("");
        numbers.clear();
        symbols.clear();
        startIndex = 0;
    }

    public void printResult(View view){
        String str = textView1.getText().toString();
        addNumber(startIndex,str.length());

        while(!symbols.isEmpty()){
            int symbolIndex;
            int rez;
            if (symbols.contains(DAUG) && symbols.contains(DAL)){
                if (symbols.indexOf(DAUG) > symbols.indexOf(DAL)){
                    mergeNumbers(symbols.indexOf(DAL),DAL);
                }
                else {
                    mergeNumbers(symbols.indexOf(DAUG),DAUG);
                }
            }
            else if (symbols.contains(DAUG)){
                mergeNumbers(symbols.indexOf(DAUG),DAUG);
            }
            else if (symbols.contains(DAL)){
                mergeNumbers(symbols.indexOf(DAL),DAL);
            }
            else {
                if (symbols.get(0).equals(SUM)){
                    mergeNumbers(symbols.indexOf(SUM),SUM);
                }
                else if (symbols.get(0).equals(ATIM)){
                    mergeNumbers(symbols.indexOf(ATIM),ATIM);
                }
            }
        }
        textView2.setText("=" + numbers.get(0));
    }

    // other methods
    private void addNumber(int start, int end){
        int number;
        if (textView1.getText().toString().equals("")){ // jei pirmas skaicius.
            number = Integer.parseInt(textView1.getText().toString());
        }
        else{
            String str = textView1.getText().subSequence(start,end).toString();
            number = Integer.parseInt(str);
        }
        numbers.add(number);
    }

    private void mergeNumbers(int symbolIndex, char symbol){
        int rez;
        switch (symbol){
            default:
            case (DAUG):
                rez = numbers.get(symbolIndex) * numbers.get(symbolIndex + 1);
                break;
            case (DAL):
                rez = numbers.get(symbolIndex) / numbers.get(symbolIndex + 1);
                break;
            case (SUM):
                rez = numbers.get(symbolIndex) + numbers.get(symbolIndex + 1);
                break;
            case (ATIM):
                rez = numbers.get(symbolIndex) - numbers.get(symbolIndex + 1);
                break;
        }
        numbers.set(symbolIndex,rez);
        numbers.remove(symbolIndex + 1);
        symbols.remove(symbolIndex);
    }
}
