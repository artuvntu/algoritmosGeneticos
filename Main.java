import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    final static int TAMREPRESENTACION = 16; //Cuantos genes
    final static int MAXREPRESENTACION = 16; //Numero Mayor por gen

    void experimento1(){
        int tamPoblacion = 100;
        int cuantasGeneraciones = 100;
        
        double aumentoCorrecto = 10.0;
        double disminucionDuplicado = 15.0;

        int porcentajeCruza = (int)((double)tamPoblacion*33/100); //33
        int porcentajeMutacion = (int)((double)tamPoblacion*34/100); //34
        int porcentajeSeleccion = (int)((double)tamPoblacion*33/100); //33

        Saver saver = new Saver("experimento1");
        
        Poblacion poblacionActual = OperadoresGeneticos.getGeneracionAleatoria(0, tamPoblacion);
        OperadoresGeneticos.evaluarPoblacionEstandar(poblacionActual,10,15);
        saver.empezarGuardarCabecerasGeneracion("cabeceras");
        saver.continuarGuardarCabecerasGeneracion(poblacionActual);
        saver.GuardarGeneracion(poblacionActual, "primera");
        for (int i=1;i<cuantasGeneraciones;i++){
            Poblacion nuevaPoblacion = new Poblacion(i);
            OperadoresGeneticos.cruzarPoblacion(poblacionActual, nuevaPoblacion, porcentajeCruza);
            OperadoresGeneticos.mutarPoblacion(poblacionActual, nuevaPoblacion, porcentajeMutacion);
            OperadoresGeneticos.seleccionarPoblacion(poblacionActual, nuevaPoblacion, porcentajeSeleccion);
            poblacionActual = nuevaPoblacion;
            OperadoresGeneticos.evaluarPoblacionEstandar(poblacionActual,10,15);
            saver.GuardarGeneracion(nuevaPoblacion, null);
            saver.continuarGuardarCabecerasGeneracion(poblacionActual);
            System.out.println(i);
        }
        saver.terminarGuardarCabecerasGeneracion();
    }

    public static void main(String[] args) {
        (new Main()).experimento1(); 
        System.out.println("Complete"); 
    }
}