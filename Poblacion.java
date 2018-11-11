import java.util.ArrayList;

public class Poblacion{
    private int generacion;
    private ArrayList<Individuo> individuos;
    private int mejorIndividuo = 0;
    private double mejorEvaluacion = 0.0;
    private double peorEvaluacion = 0.0;
    private double sumatoriaTotal = 0.0;
    private boolean evaluada = false;


    boolean evaluar(int mejorIndividuo,double mejorEvaluacion,double peorEvaluacion,double sumatoriaTotal){
        if(evaluada){return false;}
        evaluada = true;
        this.mejorIndividuo = mejorIndividuo;
        this.mejorEvaluacion = mejorEvaluacion;
        this.peorEvaluacion = peorEvaluacion;
        this.sumatoriaTotal = sumatoriaTotal;
        return true;
    }
    int getMejorIndividuio(){
        if (!this.evaluada){throw new NullPointerException();}
        return this.mejorIndividuo;
    }
    double getMejorEvaluacion(){
        if (!this.evaluada){throw new NullPointerException();}
        return this.mejorEvaluacion;
    }
    double getPeorEvaluacion(){
        if (!this.evaluada){throw new NullPointerException();}
        return this.peorEvaluacion;
    }
    double getSumatoriaTotal(){
        if (!this.evaluada){throw new NullPointerException();}
        return this.sumatoriaTotal;
    }
    Poblacion(int generacion){
        this.generacion = generacion;
        individuos = new ArrayList<>();
    }
    int agregarIndividuo(int[]genes){
        Individuo individuo = new Individuo(this.generacion, individuos.size(), genes);
        individuos.add(individuo);
        return individuos.size();
    }
    int getIdGeneracion(){
        return generacion;
    }
    ArrayList<Individuo> getGeneracion(){
        return individuos;
    }
    String aCadenaCabecera(){
        String cabecera = String.format("Generacion: %4d, Poblacion %4d",generacion,individuos.size());// "Generacion: " + String.valueOf(generacion) + ", Poblacion: "+ String.valueOf(individuos.size());
        if (this.evaluada){
            cabecera += String.format(", Evaluacion:{ Mejor: %5d, Max: %4.3f, Min: %4.3f, Suma: %7.3f}", this.mejorIndividuo,this.mejorEvaluacion,this.peorEvaluacion,this.sumatoriaTotal);
        }else {
            cabecera += ", No Evaluada";
        }
        return cabecera;
    }
    String aCadenaCompleta(){
        String resultado = this.aCadenaCabecera() + "\n" ;  
        for (Individuo individuo:this.individuos){
            resultado += "    " + individuo.aCadena() + "\n";
        }
        return resultado;
    }
}