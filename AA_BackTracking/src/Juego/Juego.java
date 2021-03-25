/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author Alejandra G
 */
public class Juego {
    private ArrayList<String> juegoCartas;
    private int[] solucionArray;
    private ArrayList<Boolean> validas;
    private ArrayList<int []> restricciones;
    private int cantidadRestr;
    private int[] cartas;
    private String[] nombres = {"Mejor amigo","El novio","El vecino","El mensajero","El extraño","El hermanastro","El colega",
                                    "Pistola","Cuchillo","Machete","Pala","Bate","Botella","Tubo","Cuerda","Venganza","Celos","Dinero",
                                    "Accidente","Drogas","Robo","Cabeza","Pecho","Abdomen","Espalda","Piernas","Brazos","Sala","Comedor",
                                    "Baño","Terraza","Cuarto","Garage","Patio","Balcón","Cocina"};
    private ArrayList sospechoso;
    private ArrayList arma;
    private ArrayList parteCuerpo;
    private ArrayList lugar;
    private ArrayList motivo;
    private boolean[] logicBF;
    private Boolean[] logicBT;
    private int[] guessBF;
    private int[] guessBT;
    public Juego(){
        juegoCartas = new ArrayList<> ();
        solucionArray = new int[5];
        validas = new ArrayList<> ();
        sospechoso = new ArrayList<> ();
        arma = new ArrayList<> ();
        parteCuerpo = new ArrayList<> ();
        lugar = new ArrayList<> ();
        motivo = new ArrayList<> ();
        restricciones = new ArrayList<>();
        cartas = new int[36];
        cantidadRestr = 5; //Aqui vamos a enlazarlo con la GUI para determinar la cantidad de restricciones.
        logicBT = new Boolean[36];
        logicBF = new boolean[36]; // estos arreglos son para ver qué carta pueden usar aún
        guessBF = new int[5];
        guessBT = new int[5];
        
        
        System.out.println("Empieza juego");
        iniciarDatos();
        obtenerRestriccion(cantidadRestr);
        buscarRespuesta();
        System.out.println("La respuesta dada por correcta es: "+nombres[solucionArray[0]]+" con "+nombres[solucionArray[1]]+" por "+nombres[solucionArray[2]]+" en el/la "+nombres[solucionArray[3]]+" en el/la "+nombres[solucionArray[4]]);        
        fuerzaBruta(sospechoso, arma, motivo, parteCuerpo, lugar, solucionArray, logicBF);
    }
    
    public void fuerzaBruta(ArrayList<Integer> sospechosos, ArrayList<Integer> armas, ArrayList<Integer> motivos, ArrayList<Integer> parteCuerpos, ArrayList<Integer> lugares, int[] solucion,boolean[] cartasValidas){
        for (int sospechoso: sospechosos) {
            for (int arma : armas) {
                if (cartasValidas[arma]) {
                    for (int motivo : motivos) {
                        if (cartasValidas[motivo]) {
                            for (int parteC : parteCuerpos) {
                                if (cartasValidas[parteC]) {
                                    for (int lugar : lugares) {
                                        if (cartasValidas[lugar]) {
                                            if (sospechoso==solucion[0] && arma ==solucion[1] && motivo==solucion[2] && parteC==solucion[3] && lugar==solucion[4]) {
                                            System.out.println("La respuesta dada por el Brute Force es: "+nombres[sospechoso]+" con "+nombres[arma]+" por "+nombres[motivo]+" en el/la "+nombres[parteC]+" en el/la "+nombres[lugar]);
                                            return;
                                            }
                                            else{
                                                boolean bandera=true;
                                                do {                                    
                                                    int numero = (int) (Math.random() * 5);
                                                    if (numero==0 && sospechoso!=solucion[0]) {
                                                        cartasValidas[sospechoso]=false;
                                                        bandera=false;
                                                    }
                                                    else if (numero == 1 && arma!=solucion[1]) {
                                                        cartasValidas[arma]=false;
                                                        bandera=false;
                                                    }
                                                    else if (numero ==2 && motivo!= solucion[2]) {
                                                        cartasValidas[motivo]=false;
                                                        bandera=false;
                                                    }else if (numero == 3 && parteC!= solucion[3]) {
                                                        cartasValidas[parteC]=false;
                                                        bandera=false;
                                                    }
                                                    else if (lugar!=solucion[4]) {
                                                        cartasValidas[lugar]=false;
                                                        bandera=false;
                                                    }
                                                } while (bandera);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void iniciarDatos(){
        for (int i = 0; i < 36; i++) {
            cartas[i]= i;
            logicBT[i]=Boolean.TRUE;
            logicBF[i]=Boolean.TRUE;
        }
         for (int i = 0; i < 7; i++) {
            sospechoso.add(i);
        }
        for (int i = 7; i < 15; i++) {
            arma.add(i);
        }
        for (int i = 15; i < 21; i++) {
            motivo.add(i);
        }
        for (int i = 21; i < 27; i++) {
            parteCuerpo.add(i);
        }
        for (int i = 27; i < 36; i++) {
            lugar.add(i);
        }
        /*
        for (int i = 0; i < 5; i++) {
            guessBF[i]=0;
            guessBT[i]=0;
        }*/
        
    }
    public void buscarRespuesta(){
        int numero;
        do {
            numero = (int) (Math.random() * 7);
            solucionArray[0] = numero;
            numero = (int) (Math.random() * 8);
            solucionArray[1] = numero+7;
            numero = (int) (Math.random() * 6);
            solucionArray[2] = numero + 15;
            numero = (int) (Math.random() * 6);
            solucionArray[3] = numero + 21;
            numero = (int) (Math.random() * 9);
            solucionArray[4] = numero +27;
        } while (!solucionValida());
    }
    public boolean solucionValida(){
        for (int i = 0; i < restricciones.size(); i++) {
            int[] rest = restricciones.get(i);
            if (rest[0]==solucionArray[0] || rest[0]==solucionArray[1] || rest[0]==solucionArray[2]|| rest[0]==solucionArray[3] || rest[0]==solucionArray[4]) {
                if (rest[1]==solucionArray[0] || rest[1]==solucionArray[1] || rest[1]==solucionArray[2]|| rest[1]==solucionArray[3] || rest[1]==solucionArray[4]) {
                    return false;   
                }
            }
        }
        return true;
    }
    public boolean revisarRestriccion(int num1, int num2){
        
        if ((num1<7 && num2<7)||(6<num1 && num1<15 && 6<num2 && num2<15)||(14<num1 && num1<21 && 14<num2 && num2<21)||(20<num1 && num1<27 && 20<num2 && num2<27)||(26<num1 && num1<36 && 26<num2 && num2<36)) {
            return false;
        }
        for (int i = 0; i < restricciones.size(); i++) {
            int [] get = restricciones.get(i);
            if ((get[0] == num1 && get[1] == num2) || (get[1] == num1 && get[0] == num2))
                return false;
        }
        return true;
    }
    public void obtenerRestriccion(int cant){
        int primeroR;
        int segundoR;
        int[] rest = new int[2];
        for (int i = 0; i < cant; i++) {
            primeroR = (int) (Math.random() * 36);
            segundoR = (int) (Math.random() * 36);
            while (!revisarRestriccion(primeroR, segundoR)){
                primeroR = (int) (Math.random() * 36);
                segundoR = (int) (Math.random() * 36);
            }
            rest[0] = primeroR;
            rest[1] = segundoR;
            restricciones.add(rest);
        }
    }
}
