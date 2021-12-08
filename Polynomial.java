/**
 * La clase Polynomial tiene como objetivo agrupar terminos de la clase Term en un array.
 * 
 * @author (Carlos Osvaldo Saavedra Aguilar)
 * @version (1.0)
 */
public class Polynomial
{
    //Variables de clase
    private Term[] termino;
    
    //Constructor
    public Polynomial(int grado){
        termino = new Term[grado + 1]; // Se agrega una posicion extra para representar al termino independiente de una ecuacion matematica
    }
    
    public void agregaTerm(Term term){
        //Agrega termino en la posicion dada por el exponente del mismo
        termino[term.getExponente()] = term;
    }
    public void agregaTerm(Term term, int index){
        //Agrega termino en la posicion indicada por index
        termino[index] = term;
    }
    public Term getTerm(int index){
        //Devuelve el termino en la posicion indicada por index
        return termino[index];
    }
    
    public double evalua(double x){
        //Evaluar el polinomio entero para la variable x
        double suma = 0;
        
        for(int i = 0; i < termino.length; i++){
            if(termino[i] != null)
                suma += termino[i].evalua(x);
        }
        
        return suma;
    }
    
    public String toString(){
        //Imprime el polinomio tomando el signo, coeficiente, exponente, y variable x para cada termino
        String funcion = "";
        
        for(int i = 0; i <= termino.length - 1; i++){
            if(termino[i] != null){
                if(funcion.equals("") && termino[i].getCoeficiente() >= 0){
                    funcion = termino[i].getCoeficiente() + "x^" + termino[i].getExponente();
                }
                else if(termino[i].getCoeficiente() >= 0){
                    funcion = funcion + " + " +termino[i].getCoeficiente() + "x^" + termino[i].getExponente();
                }
                else{
                    //funcion = funcion + " " +termino[i].getCoeficiente() + "x^" + termino[i].getExponente();
                    funcion = funcion + " - " +(-1)*(termino[i].getCoeficiente())+ "x^" + termino[i].getExponente();
                }
            }
        }
        
        return "f(x): " + funcion;
    }
}