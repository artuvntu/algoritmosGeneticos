import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jdk.nashorn.internal.ir.ReturnNode;

public class Saver{
    private File dir;
    private BufferedWriter salidaCabeceraDir;
    Saver (String nombreDir){
        File dirName = new File(nombreDir);
        if (!dirName.exists()){
            dirName.mkdirs();
        }
        if (dirName.isDirectory()){
            this.dir = dirName;
        }else {
            throw new NullPointerException();
        }
    }
    boolean GuardarGeneracion(Poblacion generacion,String nombre){
        DateFormat formatoFecha = new SimpleDateFormat("MM-dd-HH-mm-ss-SSS");
        String fechaString = formatoFecha.format(new Date());
        String nombreFile =  fechaString;
        if (nombre != null && !nombre.equals("")){nombreFile += "-" + nombre;}
        else{nombreFile += "-Generacion";}
        nombreFile += ".txt";
        try {
            BufferedWriter salida = new BufferedWriter(new FileWriter(new File(this.dir, nombreFile)));
            salida.write("Fecha: " + fechaString );
            salida.newLine();
            salida.write(generacion.aCadenaCompleta());
            salida.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    boolean GuardarGeneraciones(ArrayList<Poblacion> generaciones,String nombre){
        DateFormat formatoFecha = new SimpleDateFormat("MM-dd-HH-mm-ss-SSS");
        String fechaString = formatoFecha.format(new Date());
        String nombreFile =  fechaString;
        if (nombre != null && !nombre.equals("")){nombreFile += "-" + nombre;}
        else{nombreFile += "-Generaciones";}
        nombreFile += ".txt";
        try {
            BufferedWriter salida = new BufferedWriter(new FileWriter(new File(this.dir, nombreFile)));
            salida.write("Generaciones Contenidas: " + String.valueOf(generaciones.size()) + ", Fecha: " + fechaString );
            for (Poblacion generacion:generaciones){
                salida.newLine();
                salida.write("----------");
                salida.newLine();
                salida.write(generacion.aCadenaCompleta());
                salida.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    boolean empezarGuardarCabecerasGeneracion(String nombre){
        DateFormat formatoFecha = new SimpleDateFormat("MM-dd-HH-mm-ss-SSS");
        String fechaString = formatoFecha.format(new Date());
        String nombreFile =  fechaString;
        if (nombre != null && !nombre.equals("")){nombreFile += "-" + nombre;}
        else{nombreFile += "-CabecerasGeneraciones";}
        nombreFile += ".txt";
        try {
            BufferedWriter salida = new BufferedWriter(new FileWriter(new File(this.dir, nombreFile)));
            salida.write("Cabeceras Generacion, Fecha: " + fechaString );
            this.salidaCabeceraDir = salida;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    boolean continuarGuardarCabecerasGeneracion(Poblacion generacion){
        try {
            salidaCabeceraDir.newLine();
            salidaCabeceraDir.write(generacion.aCadenaCabecera());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    boolean terminarGuardarCabecerasGeneracion(){
        try{
            salidaCabeceraDir.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}