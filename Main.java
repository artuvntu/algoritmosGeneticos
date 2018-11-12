import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Date;


public class Main {
    final static int TAMREPRESENTACION = 16; //Cuantos genes
    final static int MAXREPRESENTACION = 16; //Numero Mayor por gen

    Saver saver;

    void experimento1(){
        int tamPoblacion = 100;
        int cuantasGeneraciones = -100;
        
        double aumentoCorrecto = 10.0;
        double disminucionDuplicado = 1.50;

        // double aumentoCorrecto2 = 1.0;
        // double disminucionDuplicado2 = 15.0;
        
        int porcentajeCruza = (int)((double)tamPoblacion*33/100); //33
        int porcentajeMutacion = (int)((double)tamPoblacion*34/100); //34
        int porcentajeSeleccion = (int)((double)tamPoblacion*33/100); //33
        DateFormat formatoFecha = new SimpleDateFormat("MM-dd-HH-mm-ss-SSS");
        String fechaString = formatoFecha.format(new Date());
        saver = new Saver("experimento"+fechaString);
        Poblacion poblacionActual = OperadoresGeneticos.getGeneracionAleatoria(0, tamPoblacion);
        OperadoresGeneticos.evaluarPoblacionEstandar(poblacionActual,aumentoCorrecto,disminucionDuplicado);
        saver.empezarGuardarCabecerasGeneracion("cabeceras");
        saver.continuarGuardarCabecerasGeneracion(poblacionActual);
        saver.GuardarGeneracion(poblacionActual, null);
        // int primeraFase = 0;
        for (int i=1;/*i<cuantasGeneraciones*/true;i++){
            Poblacion nuevaPoblacion = new Poblacion(i);
            OperadoresGeneticos.cruzarPoblacion(poblacionActual, nuevaPoblacion, porcentajeCruza);
            OperadoresGeneticos.mutarPoblacion(poblacionActual, nuevaPoblacion, porcentajeMutacion);
            OperadoresGeneticos.seleccionarPoblacion(poblacionActual, nuevaPoblacion, porcentajeSeleccion);
            poblacionActual = nuevaPoblacion;
            OperadoresGeneticos.evaluarPoblacionEstandar(poblacionActual,aumentoCorrecto,disminucionDuplicado);
            saver.GuardarGeneracion(nuevaPoblacion, null);
            saver.continuarGuardarCabecerasGeneracion(poblacionActual);
            // if (poblacionActual.getMejorEvaluacion()>=116){
            //     primeraFase = i;
            //     break;}
            System.out.print(String.format("%8.3f %5d", poblacionActual.getMejorEvaluacion(),i));
        }
        // for (int i=primeraFase;i<cuantasGeneraciones+primeraFase;i++){
        //     Poblacion nuevaPoblacion = new Poblacion(i);
        //     OperadoresGeneticos.cruzarPoblacion(poblacionActual, nuevaPoblacion, porcentajeCruza);
        //     OperadoresGeneticos.mutarPoblacion(poblacionActual, nuevaPoblacion, porcentajeMutacion);
        //     OperadoresGeneticos.seleccionarPoblacion(poblacionActual, nuevaPoblacion, porcentajeSeleccion);
        //     poblacionActual = nuevaPoblacion;
        //     OperadoresGeneticos.evaluarPoblacionEstandar(poblacionActual,aumentoCorrecto2,disminucionDuplicado2);
        //     saver.GuardarGeneracion(nuevaPoblacion, String.valueOf(nuevaPoblacion.getIdGeneracion()));
        //     saver.continuarGuardarCabecerasGeneracion(poblacionActual);
        //     if (poblacionActual.getMejorEvaluacion()>=116){break;}
        //     System.out.println(i);
        // }
        saver.terminarGuardarCabecerasGeneracion();
    }

    public static void main(String[] args) {
        Main propio = new Main();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                propio.saver.terminarGuardarCabecerasGeneracion();
                System.out.println("Complete Force"); 
            }
        });
        // propio.experimento1(); 
        System.out.println("Complete"); 
    }
}