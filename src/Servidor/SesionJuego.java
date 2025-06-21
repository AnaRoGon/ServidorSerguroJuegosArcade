/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

// *******************************************************************
// *                       - SESIONJUEGO-                            *
// *   Representa una sesión individual de un usuario en el          *
// *   servidor.                                                     *
// *   @author: Ana Rodríguez González                               *
// *   @version 2.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class SesionJuego {

    int rondaDados;
    int rondaPPT;
    int puntosServidorDados;
    int puntosClienteDados;
    int puntosServidorPPT;
    int puntosClientePPT;
    int intentosAdivina;
    int numAleatorioAdivina;
    boolean logeado;

    public SesionJuego() {
        this.rondaDados = 0;
        this.rondaPPT = 0;
        this.puntosServidorDados = 0;
        this.puntosClienteDados = 0;
        this.puntosServidorPPT = 0;
        this.puntosClientePPT = 0;
        this.intentosAdivina = 1;
        this.numAleatorioAdivina = (int) (Math.random() * 100) + 1;
        //Hemos añadido una variable booleana para saber si el login del usuario es válido
        this.logeado = false;
    }

}
