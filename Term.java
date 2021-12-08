/**
 * La clase Termino representa un termino matematico que consta de un coeficiente, un exponente, y una variable x a ser evaluada.
 * 
 * @author (Carlos Osvaldo Saavedra Aguilar)
 * @version (1.0)
 */
public class Term
{
    //Variables de clase
    private double coeficiente;
    private int exponente;
    
    //Constructor
    public Term(int coef, int exp){
        this.coeficiente = coef;
        this.exponente = exp;
    }
    
    //Metodos
    public double evalua(double x){
        return coeficiente * (Math.pow(x, exponente));
    }
    public int getExponente(){
        return exponente;
    }
    public double getCoeficiente(){
        return coeficiente;
    }
}
