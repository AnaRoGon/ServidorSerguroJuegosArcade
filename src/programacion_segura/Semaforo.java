/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programacion_segura;

// *******************************************************************
// *                         - SEMAFORO-                             *
// *   Indica el estado del fichero usuarios.txt                     *
// *   En base a su estado, los hilos del servidor podrán acceder    *
// *   o no, a la escritura o lectura del fichero                    *
// *   @author: Ana Rodríguez González Fecha: 06/03/2025             *
// *   @version 2.0 Fecha: 14-05-2025                                *
// *******************************************************************
public class Semaforo {

    public final static int LIBRE = 0;
    public final static int CON_LECTORES = 1;
    public final static int CON_ESCRITOR = 2;
    private int estado = LIBRE;
    private int tLectores = 0;

    //Método sincronizado que da acceso a la lectura de datos
    public synchronized void accesoLeer() {
        //método sincronizado. Sólo un hilo lo usa a la vez
        String nombre = Thread.currentThread().getName();
        //guarda el nombre del hilo que se hace con el método
        if (estado == LIBRE) {
            //BD sin lectores ni escritores. Puede netrar a leer
            System.out.println("BD:" + estado + " " + tLectores + "L " + nombre
                    + " entra a leer.");
            //mensaje para comprobar el funcionamiento
            estado = CON_LECTORES;
            //cambia estado, yahay lector
        } else if (estado != CON_LECTORES) {
            //si no está libre, ni con lectores
            while (estado == CON_ESCRITOR) {
                try {
                    System.out.println("BD:" + estado + " " + tLectores + "L "
                            + nombre + " trata de leer.ESPERA");
                    //mensaje para comprobar el funcionamiento
                    wait();
                    //pone en espera al hilo que intenta leer datos
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            System.out.println("BD:" + estado + " " + tLectores + "L "
                    + nombre + " entra a leer.");
            //mensaje para comprobar el funcionamiento
            estado = CON_LECTORES;
            //cambia estado, ya hay lector
        } else {//en este punto el estado es CON_LECTORES
            System.out.println("BD:" + estado + " " + tLectores + "L "
                    + nombre + " entra a leer.");
            //mensaje para comprobar funcionamiento
        }
        tLectores++;
        //otro lector más
        System.out.println("BD:" + estado + " " + tLectores + "L "
                + nombre + " Leyendo.....");
        //mensaje para comprobar funcionamiento
    }

    //Método que da acceso para escribir datos si el estado de la BD lo permite
    public synchronized void accesoEscribir() {
        String nombre = Thread.currentThread().getName();
        //guarda el nombre del hilo que se hace con el método
        if (estado == LIBRE) {
            //sin lectores ni escritores
            System.out.println("BD:" + estado + " " + tLectores + "L "
                    + nombre + " entra a escribir.");
            //mensaje para comprobar el funcionamiento
            estado = CON_ESCRITOR;
            //cambia estado
        } else {//si no está libre
            while (estado != LIBRE) {
                //miestras BD está ocupada con lectores, o con un escritor
                try {
                    System.out.println("BD:" + estado + " " + tLectores + "L "
                            + nombre + " trata de escribir.ESPERA");
                    //mensaje para comprobar funcionamiento
                    wait();
                    //pone en espera al hilo que intenta escribir datos
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }// el estado ahora es LIBRE
            System.out.println("BD:" + estado + " " + tLectores + "L "
                    + nombre + " entra a escribir.");
            //mensaje para comprobar el funcionamiento
            estado = CON_ESCRITOR;
            //cambia estado
        }
        System.out.println("BD:" + estado + " " + tLectores + "L "
                + nombre + " Escribiendo..");
        //mensaje para comprobar el funcionamiento
    }

    /**
     * ************************************************************************
     * método que invoca un HiloEscritor al terminar de escribir, para
     * actualizar el estado del semáforo y en su caso notificarlo a los hilos en
     * espera. Por supuesto, no se permite que dos hilos ejecuten estas
     * instrucciones a la vez
     */
    public synchronized void escrituraFinalizada() {
        estado = LIBRE;
        //cambia estado
        System.out.println(Thread.currentThread().getName() + ": Ya ha escrito");
        //mensaje para comprobar el funcionamiento
        notify();
        //notifica a los hilos en espera que ya ha finalizado
    }

    /**
     * ************************************************************************
     * método que que invoca un HiloLector cuando termina de escribir, para
     * actualizar el estado del semáforo y en su caso notificarlo a los hilos en
     * espera
     *
     * por supuesto, no se permite que dos hilos ejecuten estas instrucciones a
     * la vez
     */
    public synchronized void lecturaFinalizada() {
        System.out.println(Thread.currentThread().getName() + ": Ya ha leido");
        //mensaje para comprobar el funcionamiento
        tLectores--;
        //un lector menos leyendo
        if (tLectores == 0) {
            //no hay lectores en la BD
            estado = LIBRE;
            //cambia el estado
            notify();
            //notifica a los hilos en espera que ya ha finalizado
        }
    }

}
