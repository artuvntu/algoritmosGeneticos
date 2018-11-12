import java.util.ArrayList;

public class OperadoresGeneticos {
    static int getRandom(int max){
        int resultado = (int)( Math.random() * (double) max);
        return resultado;
    }
    static double getDRandom(double max){
        return  Math.random() * max ;
    }
    static int [] getIndividuoAleatorio(){
        int [] genes = new int[Main.TAMREPRESENTACION];
            for(int j=0;j<Main.TAMREPRESENTACION;j++){
                genes[j] = getRandom(Main.MAXREPRESENTACION)+1;
            }
        return genes;
    }
    static Poblacion getGeneracionAleatoria(int idGeneracion, int tamGeneracion){
        Poblacion resultado = new Poblacion(idGeneracion);
        for (int i=0;i<tamGeneracion;i++){
            int [] genes = OperadoresGeneticos.getIndividuoAleatorio();
            resultado.agregarIndividuo(genes);
        }
        return resultado;
    }
    static int [] mutarIndividuo(Individuo individuo,int cuantos){
        int [] repetidos = new int[Main.TAMREPRESENTACION];
        int [] genes = individuo.getRepresentacionArray();
        for (int i=0;i<repetidos.length;i++){
            repetidos[i] = 0;
        }
        for (int i=0;i<cuantos;i++){
            int cual = OperadoresGeneticos.getRandom(genes.length);
            if (repetidos[cual]==0){
                int nuevoCambio = OperadoresGeneticos.getRandom(Main.MAXREPRESENTACION)+1;
                while (nuevoCambio == genes[cual]){
                    nuevoCambio++;
                    nuevoCambio = ((nuevoCambio-1)%Main.MAXREPRESENTACION)+1;
                }
                genes[cual] = nuevoCambio;
                repetidos[cual] = 1;
            }else{
                for (int j=1;j<genes.length;j++){
                    int cual2 = ((cual+j)%repetidos.length);
                    if (repetidos[cual2]==0){
                        int nuevoCambio = OperadoresGeneticos.getRandom(Main.MAXREPRESENTACION)+1;
                        while (nuevoCambio == genes[cual2]){
                            nuevoCambio++;
                            nuevoCambio = ((nuevoCambio-1)%Main.MAXREPRESENTACION)+1;
                        }
                        genes[cual2] = nuevoCambio;
                        repetidos[cual2] = 1;
                        break;
                    }
                }
            }
        }
        return genes;
    }
    static int [] cruzarIndividuo(Individuo padreIndividuo, Individuo madreIndividuo,int cuantos){
        int [] repetidos = new int[Main.TAMREPRESENTACION];
        int [] genes = madreIndividuo.getRepresentacion().stream().mapToInt(i->i).toArray();
        ArrayList<Integer> convinante = padreIndividuo.getRepresentacion();
        for (int i=0;i<repetidos.length;i++){
            repetidos[i] = 0;
        }
        for (int i=0;i<cuantos;i++){
            int cual = OperadoresGeneticos.getRandom(genes.length);
            if (repetidos[cual]==0){
                genes[cual] = convinante.get(cual).intValue();
                repetidos[cual] = 1;
            }else{
                for (int j=1;j<genes.length;j++){
                    int cual2 = ((cual+j)%repetidos.length);
                    if (repetidos[cual2]==0){
                        genes[cual2] = convinante.get(cual2).intValue();
                        repetidos[cual2] = 1;
                        break;
                    }
                }
            }
        }
        return genes;
    }
    static int evaluarPoblacionEstandar(Poblacion p,double aumentoCorrecto, double disminucionRepeticion){
        ArrayList<Individuo> generacion = p.getGeneracion();
        double sumaTotal = OperadoresGeneticos.getEvaluacionPosicionEstandar(generacion.get(0), aumentoCorrecto, disminucionRepeticion);
        double evaMin = sumaTotal;
        double evaMax = sumaTotal;
        int cualMejor = 0;
        generacion.get(0).evaluar(sumaTotal);
        for (int i=1;i<generacion.size();i++){
            double ev = OperadoresGeneticos.getEvaluacionPosicionEstandar(generacion.get(i), aumentoCorrecto, disminucionRepeticion);
            generacion.get(i).evaluar(ev);
            sumaTotal += ev;
            if (evaMin>ev){evaMin = ev;};
            if (evaMax<ev){
                evaMax = ev;
                cualMejor = i;
            }
            if (ev == aumentoCorrecto*Main.TAMREPRESENTACION){
                System.out.println("Goal");
            };
        }
        p.evaluar(cualMejor, evaMax, evaMin, sumaTotal);
        OperadoresGeneticos.giveProbabilidadRuleta(p);
        return cualMejor;
    }
    static double getEvaluacionPosicionEstandar(Individuo individuo, double valorPorCorrecto,double disminucionPorRepetidos){
        ArrayList<Integer> genes = individuo.getRepresentacion();
        double respuesta = 0.0;
        int [] repetidos = new int[Main.MAXREPRESENTACION];
        for (int i=0;i<repetidos.length;i++){
            repetidos[i] = 0;
        }
        for (int i=0;i<genes.size();i++){
            int gen = genes.get(i).intValue();
            if (gen == 0){
                System.out.println(String.format("Error gen menor %s",individuo.aCadena()));
            }
            if ( gen == i+1){
                respuesta += valorPorCorrecto;
            }
            repetidos[gen-1]++;
            if (repetidos[gen-1]>0){respuesta -= disminucionPorRepetidos;}
        }
        return respuesta;
    }
    static void giveProbabilidadRuleta(Poblacion poblacion){
        double actual = 0.0;
        ArrayList<Individuo> pGeneracion = poblacion.getGeneracion();
        double evaMin = poblacion.getPeorEvaluacion();//- ((poblacion.getMejorEvaluacion()-poblacion.getPeorEvaluacion())/100);
        double normalSumatoria = poblacion.getSumatoriaTotal() - (evaMin * (double)pGeneracion.size());
        for (int i=0;i<pGeneracion.size();i++){
            actual += (pGeneracion.get(i).getEvaluacion()-evaMin)/normalSumatoria;
            pGeneracion.get(i).ruletaProbabilidad = actual*100;
        }
    }
    static void cruzarPoblacion(Poblacion predecesora, Poblacion sucesora,int cuantos){
        ArrayList<Individuo> individuos = predecesora.getGeneracion();
        int tamPoblacion = individuos.size();
        for (int i=0;i<cuantos;i++){
            int cual = OperadoresGeneticos.giraRuleta(individuos);
            int cualC = OperadoresGeneticos.giraRuleta(individuos);
            if (cual == cualC){
                cualC = (cualC+1)%tamPoblacion;
            }
            int [] nuevosGenes = OperadoresGeneticos.cruzarIndividuo(individuos.get(cualC), individuos.get(cual), getRandom(Main.MAXREPRESENTACION)+1);
            sucesora.agregarIndividuo(nuevosGenes);
        }
    }
    static void mutarPoblacion(Poblacion predecesora, Poblacion sucesora,int cuantos){
        ArrayList<Individuo> individuos = predecesora.getGeneracion();
        for (int i=0;i<cuantos;i++){
            int cual = OperadoresGeneticos.giraRuleta(individuos);
            int [] nuevosGenes = OperadoresGeneticos.mutarIndividuo(individuos.get(cual), getRandom(Main.MAXREPRESENTACION)+1);
            sucesora.agregarIndividuo(nuevosGenes);
        }
    }
    static void seleccionarPoblacion(Poblacion predecesora, Poblacion sucesora,int cuantos){
        ArrayList<Integer> repetidos = new ArrayList<>();
        ArrayList<Individuo> individuos = predecesora.getGeneracion();
        for (int i=0;i<cuantos;i++){
            int cual = OperadoresGeneticos.giraRuleta(individuos);
            while (repetidos.contains(new Integer(cual))){
                cual = OperadoresGeneticos.giraRuleta(individuos);
            }
            int [] nuevosGenes = individuos.get(cual).getRepresentacionArray();
            sucesora.agregarIndividuo(nuevosGenes);
            repetidos.add(cual);
        }
    }
    static int giraRuleta(ArrayList<Individuo> individuos){
        int cual = 0;
        Double ruleta = OperadoresGeneticos.getDRandom(100.0);
        if (ruleta == 100.0){System.out.println("Random is over");}
        while (individuos.get(cual).ruletaProbabilidad<ruleta){cual++;}
        return cual;
    }
}