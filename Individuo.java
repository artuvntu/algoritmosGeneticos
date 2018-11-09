import java.util.ArrayList;

public class Individuo {
    final static 
    private ArrayList<Integer> representacion;
    private int generacion;
    private int idIndividuo;
    private double evaluacion = 0.0;
    private bool evaluado = false;
    Individuo(int generacion, int id, int[] llenado) {
        this.representacion = new ArrayList<>();
        this.idIndividuo = id;
        this.generacion = generacion;
        for (int i=0;i<16;i++){
            representacion.add(llenado[i]);
        }
    }

    ArrayList<Integer> getRepresentacion() {
        return respuesta = representacion;
    }
    boolean evaluar(double evaluacion) {
        if (this.evaluado) return false;
        this.evaluacion = evaluacion;
        this.evaluado = true;
        return true;
    }
    int getId() {
        return this.idIndividuo;
    }
    
    int getGeneracion() {
        return this.generacion;
    }
    double getEvaluacion() {
        return this.evaluacion;
    }
    
}