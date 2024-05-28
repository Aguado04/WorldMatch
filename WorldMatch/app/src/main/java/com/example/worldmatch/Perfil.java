package com.example.worldmatch;

import static com.example.worldmatch.Login.nombreCliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.fondo));

        Button menuPerfil = findViewById(R.id.menuPerfil);
        menuPerfil.setBackgroundColor(Color.TRANSPARENT);

        TextView nombrePerfil = findViewById(R.id.nombrePerfil);
        nombrePerfil.setText(nombreCliente);

        Button cookies = findViewById(R.id.BTNcookies);
        cookies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookies();
            }
        });

        Button info = findViewById(R.id.infoLegal);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoLegal();
            }
        });

        Button sobreRetro = findViewById(R.id.sobreRetro);
        sobreRetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sobreRetro();
            }
        });

    }

    private void infoLegal(){
        new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
                .setTitle("Información legal")
                .setMessage("De acuerdo con lo dispuesto en el artículo 10 de la Ley 34/2002, de 11 de julio, de Servicios de la Sociedad de la Información y de Comercio Electrónico (“LSSI”), ponemos en su conocimiento que la aplicación Alertas Normativas Financieras (la “App”) es una aplicación operada por GMS Management Solutions, S.L. (en adelante, “MS”o “Management Solutions”, indistintamente), con C.I.F. B-83509307, inscrita en el Registro Mercantil de Madrid (España), Tomo 18.271, Folio 219, Sección 8ª, Hoja M-316664, cuyo domicilio se encuentra en la Plaza Pablo Ruiz Picasso, 1, Torre Picasso, 28020, Madrid, España. Puede obtener más información sobre cómo ponerse en contacto con nosotros a través de la sección “Donde estamos” de nuestra página web.\n" +
                        "\n" +
                        "La descarga de la App por el usuario (el “Usuario” o “Usted”) supone que acepta en su totalidad y se obliga a cumplir por completo los términos y condiciones recogidos en el presente Aviso Legal y Términos de Uso, así como en la Política de Privacidad de la App. Por lo tanto, el Usuario debe leer atentamente el presente Aviso Legal y Términos de Uso en cada una de las ocasiones en que se proponga utilizar la App, ya que ésta y sus condiciones de uso pueden sufrir modificaciones.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Mensaje","Cancelado");
                    }
                })
                .show();
    }

    private void cookies(){
        new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
                .setTitle("Cookies")
                .setMessage("Los sitios web y los servicios online pueden usar «cookies». Las cookies te permiten comprar mediante el carrito y personalizar los sitios, y a nosotros nos permiten saber qué páginas visitan los clientes. Nos ayudan a medir la eficacia de los anuncios y búsquedas, y nos dan información sobre el comportamiento de los usuarios, que utilizamos para mejorar nuestros productos y mensajes.\n" +
                        "\n" +
                        "Si quieres desactivar las cookies en el navegador Safari, ve a Preferencias y, en el panel Privacidad, elige Bloquear Cookies. En tu iPad, iPhone o iPod touch, ve a Ajustes, selecciona Safari y dirígete al apartado Cookies. Para otros navegadores, consulta con el proveedor cómo desactivar las cookies.\n" +
                        "\n" +
                        "Muchos de nuestros sitios utilizan cookies, por lo que es posible que no puedas acceder a determinadas secciones si las desactivas.\n" +
                        "\n" +
                        "Las cookies empleadas en nuestras páginas han sido catalogadas según las directrices de la guía sobre cookies de la Cámara de Comercio Internacional (ICC) del Reino Unido. En nuestros sitios web y servicios online aplicamos las siguientes categorías:")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Mensaje","Cancelado");
                    }
                })
                .show();
    }

    private void sobreRetro(){
        new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
                .setTitle("Sobre WorldMatch")
                .setMessage("La mejor app del mundo")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Mensaje","Cancelado");
                    }
                })
                .show();
    }
}