package com.example.calculatorapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    static String[] huruf = {"", "Satu","Dua","Tiga","Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};
    private int[] btnNumber = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
    private int[] btnOperator = {R.id.btnTambah, R.id.btnKali, R.id.btnKurang, R.id.btnBagi, R.id.btnKurung};
    private TextView txt1;
    private TextView txt2;
    private boolean lastNumeric;
    private boolean stateError;
    private boolean lastKoma;
    String cek;
    Boolean checkKurung = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txt1 = findViewById(R.id.txtScreen);
        this.txt2 = findViewById(R.id.txtOutput);
        setNumericOnClickListener();
        setOperatorOnClickListener();
    }

    private void setOperatorOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError) {
                    Button button = (Button) v;
                    txt1.append(button.getText());
                    lastNumeric = false;
                    lastKoma = false;
                }
            }
        };
        for (int id : btnOperator) {
            findViewById(id).setOnClickListener(listener);
        }
        findViewById(R.id.btnKoma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastKoma) {
                    txt1.append(".");
                    lastNumeric = false;
                    lastKoma = true;
                }
            }
        });

        findViewById(R.id.btnKurung).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkKurung) {
                    cek = txt1.getText().toString();
                    txt1.setText(cek + ")");
                    checkKurung = false;
                } else {
                    cek = txt1.getText().toString();
                    txt1.setText(cek + "(");
                    checkKurung = true;
                }
            }
        });

        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("");
                txt2.setText("");
                lastNumeric = false;
                lastKoma = false;
            }
        });

        findViewById(R.id.btnResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasil();
            }
        });
    }

    private void hasil() {
        if (lastNumeric && !stateError) {
            String txt = txt1.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                double result = expression.evaluate();
                if (result < 1000000000) {
                    txt2.setText(angkaToTerbilang((long) result));
                } else {
                    txt2.setText(Double.toString(result));
                    lastKoma = true;
                }
//                txt2.setText(Double.toString(result));
//                lastKoma = true;
            } catch (ArithmeticException ex) {
                txt2.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }

    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (stateError) {
                    txt1.setText(button.getText());
                    stateError = false;
                } else {
                    txt1.append(button.getText());
                }
                lastNumeric = true;
            }
        };
        for (int id : btnNumber) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    public String angkaToTerbilang(Long angka){
        if(angka < 12)
            return huruf[angka.intValue()];
        if(angka >=12 && angka <= 19)
            return huruf[angka.intValue() % 10] + " Belas";
        if(angka >= 20 && angka <= 99)
            return angkaToTerbilang(angka / 10) + " Puluh " + huruf[angka.intValue() % 10];
        if(angka >= 100 && angka <= 199)
            return "Seratus " + angkaToTerbilang(angka % 100);
        if(angka >= 200 && angka <= 999)
            return angkaToTerbilang(angka / 100) + " Ratus " + angkaToTerbilang(angka % 100);
        if(angka >= 1000 && angka <= 1999)
            return "Seribu " + angkaToTerbilang(angka % 1000);
        if(angka >= 2000 && angka <= 999999)
            return angkaToTerbilang(angka / 1000) + " Ribu " + angkaToTerbilang(angka % 1000);
        if(angka >= 1000000 && angka <= 999999999)
            return angkaToTerbilang(angka / 1000000) + " Juta " + angkaToTerbilang(angka % 1000000);
        if(angka >= 1000000000 && angka <= 999999999999L)
            return angkaToTerbilang(angka / 1000000000) + " Milyar " + angkaToTerbilang(angka % 1000000000);
        if(angka >= 1000000000000L && angka <= 999999999999999L)
            return angkaToTerbilang(angka / 1000000000000L) + " Triliun " + angkaToTerbilang(angka % 1000000000000L);
        if(angka >= 1000000000000000L && angka <= 999999999999999999L)
            return angkaToTerbilang(angka / 1000000000000000L) + " Quadrilyun " + angkaToTerbilang(angka % 1000000000000000L);
        return "";
    }
}