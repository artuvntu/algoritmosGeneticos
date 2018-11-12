import java.util.ArrayList;

public class Individuo {
    final static int TAMREPRESENTACION = Main.TAMREPRESENTACION;
    private ArrayList<Integer> representacion;
    private int generacion;
    private int idIndividuo;
    private double evaluacion = 0.0;
    private boolean evaluado = false;
    double ruletaProbabilidad = 0.0;
    Individuo(int generacion, int id, int[] llenado) {
        this.representacion = new ArrayList<>();
        this.idIndividuo = id;
        this.generacion = generacion;
        for (int i=0;i<TAMREPRESENTACION;i++){
            if (llenado[i]==0){throw new NullPointerException();}
            representacion.add(llenado[i]);
        }
    }

    ArrayList<Integer> getRepresentacion() {
        return representacion;
    }
    int [] getRepresentacionArray(){
        return getRepresentacion().stream().mapToInt(i->i).toArray();
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
        if (!evaluado){throw new NullPointerException();}
        return this.evaluacion;
    }

    String aCadena(){
        String resultado = String.format("Generacion: %4d, Individuo: %4d Genes: {%2d", this.generacion,this.idIndividuo,this.representacion.get(0));
        for (int i=1;i<TAMREPRESENTACION;i++){
            resultado += String.format(", %2d", this.representacion.get(i));
        }
        if (evaluado){
            resultado += String.format("}, Evaluacion: %4.3f : %%%3.2f", evaluacion,ruletaProbabilidad);
        }else {
            resultado += "}, No Evaluado";
        }
        return resultado;
    }
}