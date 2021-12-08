/**
 * La clase Equation genera una grafica sobre un plano cartesiano, tomando como base las coordenadas (x,y) de un polinomio de terminos
 * 
 * @author (Carlos Osvaldo Saavedra Aguilar) 
 * @version (1.0)
 */
import java.util.Vector;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;

public class Equation extends Canvas implements Runnable
{
    //Variables de clase
    private Vector <Coordinate> puntos = new Vector();
    private Polynomial poli;
    private boolean bandera = false;
    private Thread hilo;
    private int p;
    private FileWriter coordenadas;
    
    //Objetos de color y fuente para el metodo paint
    Font fuente = new Font("Arial", Font.BOLD, 11);
    Color colorFondo = new Color(241, 246, 254);
    Color ejesXY = new Color(84, 96, 114);
    Color letrasXY = new Color(57, 89, 136);
    Color cuadricula = new Color(192, 206, 228);
    Color colorLineaEcuacion = new Color(47, 94, 165);
    
    //Cosntructor
    public Equation(){
    }
    
    public Vector getEquation(){
        //Devuelve un vector de puntos (coordenadas x,y)
        return new Vector(puntos);
    }
    public Coordinate getCoordinate(int num){
        //Devuelve el punto (x,y) indicado en la posicion num
        return puntos.get(num);
    }
    public void setBandera(boolean b){
        //Permite que el metodo paint dibuje la grafica de la ecuacion una vez que el polinomio existe
        bandera = b;
    }
    public boolean getBandera(){
        return bandera;
    }
    public void paint(Graphics gpc){
        Graphics2D g2d = (Graphics2D)gpc;
        
        g2d.setColor(colorFondo);
        g2d.fill(new Rectangle2D.Float(0, 0, 600, 600));   //Crear el rectangulo de fondo
        g2d.setColor(cuadricula);
        for(int line = 0; line <= 600; line += 10){   //Traza cuadricula del plano cartesiano
            g2d.draw(new Line2D.Float(0, line, 600, line));   //Lineas horizontales
            g2d.draw(new Line2D.Float(line, 0, line, 600));   //Lineas verticales
        }
        g2d.setColor(ejesXY);
        g2d.draw(new Line2D.Float(0, 300, 600, 300));   //Traza eje X
        g2d.draw(new Line2D.Float(300, 0, 300, 600));   //Traza eje Y
        g2d.setColor(letrasXY);
        g2d.setFont(fuente);
        g2d.drawString("X", 580, 290);
        g2d.drawString("Y", 310, 20);
        fuente = new Font("Arial", Font.BOLD, 12);
        
        if(bandera == true && poli != null){  //Dibuja la grafica de la ecuacion una vez que el polinomio existe
            hilo = new Thread(this);
            hilo.start();
            g2d.drawString(poli.toString(), 25, 20); //Imprime la ecuacion 
            g2d.translate(300, 300);
            g2d.setStroke(new BasicStroke(2.0f));
            GeneralPath grafica = new GeneralPath( GeneralPath.WIND_EVEN_ODD, puntos.size());
            grafica.moveTo(10*(double)puntos.elementAt(0).getX(), -1*(10*(double)puntos.elementAt(0).getY()));
            g2d.setColor(colorLineaEcuacion);
            for(p = 0; p < puntos.size(); p++){
                if(puntos.elementAt(p) != null){
                    try{
                        grafica.lineTo(10*(double)puntos.elementAt(p).getX(), -1*(10*(double)puntos.elementAt(p).getY()));
                        g2d.draw(grafica);
                        hilo.sleep(1000);
                    }catch(InterruptedException iex){
                    }
                }
            }
       }
    }
    
    public void calculaCoordinates(double limInf, double limSup, double inc) throws IOException{
        //Calcula las coorednadas (x,y) para cada termino del polinomio y las guarda en un archivo de texto y en un archivo delimitado por comas
        Coordinate pto;
        coordenadas = new FileWriter("coordenadas.csv");
        
        if((limInf < limSup) && (inc > 0)){
            for(double x = limInf; x <= limSup ; x += inc){
               pto = new Coordinate(x, poli.evalua(x));
               puntos.add(pto);
               coordenadas.write((double)pto.getX() + "," + (double)pto.getY() + "\n");
            }
        }
        
        coordenadas.close();
    }
    public void agregaPolynomial(Polynomial pno){
        poli = pno;
    }
    
    public void run(){
    }
}
