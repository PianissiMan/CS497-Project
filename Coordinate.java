
/**
 * Clase generica para represenar las coordenadas (x,y) de un Coordinate dobre el plano cartesiano.
 * 
 * @author (Carlos Osvaldo Saavedra Aguilar)
 * @version (1.0)
 */
public class Coordinate <E>
{
    //Variables de clase
    private E x;
    private E y;
    
    //Constructores
    public Coordinate(E x, E y){
        setX(x);
        setY(y);
    }
    public Coordinate(){
    }
    
    //Metodos set y get
    public E getX(){
        return x;
    }
    public void setX(E x){
        this.x = x;
    }
    public E getY(){
        return y;
    }
    public void setY(E y){
        this.y = y;
    }
}
