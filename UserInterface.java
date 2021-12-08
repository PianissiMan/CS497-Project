
/**
 * La clase UserInterface crea toda la interfaz de usuario necesaria para graficar una ecuacion matematica
 * 
 * @author (Carlos Osvaldo Saavedra Aguilar) 
 * @version (1.0)
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.*;

public class UserInterface extends JFrame
{
    //Variables de clase
    private JButton btnGrado, btnAgregaTrm, btnGraficar, btnAceptar;
    private JTextField tfGrado, tfCoeficiente, tfExponente, tfLimInf, tfLimSup, tfIncremento;
    private JPanel entradas, graficador;
    private Equation grafica;
    private JLabel lblGrado, lblCoeficiente, lblExponente, lblLimInf, lblLimSup, lblInc;
    private int gradoPoli, coeficiente, exponente;
    private double limSup, limInf, incremento;
    private Polynomial Polynomial;
    
    //Constructor
    public UserInterface()
    {
        super("Graph Calculator");
        
        //Botones de la interfaz
        btnGrado = new JButton("Set equation's degree");
        btnGrado.setBackground(new Color(84, 96, 114));
        btnGrado.setForeground(new Color(255, 255, 255));
        btnAgregaTrm = new JButton("Add term");
        btnAgregaTrm.setBackground(new Color(84, 96, 114));
        btnAgregaTrm.setForeground(new Color(255, 255, 255));
        btnGraficar = new JButton("Draw graph");
        btnGraficar.setBackground(new Color(84, 96, 114));
        btnGraficar.setForeground(new Color(255, 255, 255));
        btnAceptar = new JButton("Accept values");
        btnAceptar.setBackground(new Color(84, 96, 114));
        btnAceptar.setForeground(new Color(255, 255, 255));
        
        //Campos de texto de la interfaz
        tfGrado = new JTextField();
        tfCoeficiente = new JTextField();
        tfExponente = new JTextField();
        tfLimInf = new JTextField();
        tfLimSup = new JTextField();
        tfIncremento = new JTextField();
        
        //Etiquetas de la interfaz
        lblGrado = new JLabel("Polynomial Degree");
        lblCoeficiente = new JLabel("Term Coefficient");
        lblExponente = new JLabel("Term Exponent");
        lblLimInf = new JLabel("Set the lower limit");
        lblLimSup = new JLabel("Set the upper limit");
        lblInc = new JLabel("Set increment");
        
        //Registrar a los botones como listeners
        btnGrado.addActionListener(new BotonGrado());
        btnAgregaTrm.addActionListener(new BotonAgregaTermino());
        btnAceptar.addActionListener(new BotonAceptar());
        btnGraficar.addActionListener(new BotonGraficar());
        
        grafica = new Equation();
        
        //Agregar los componentes de interfaz a los paneles
        entradas = new JPanel();
        graficador = new JPanel();
        entradas.setLayout(new GridLayout(16, 1, 10, 10));
        graficador.setLayout(new GridLayout(1, 1, 0, 0));
        entradas.setBackground(new Color(241, 246, 254));
        graficador.setBackground(new Color(241, 246, 254));
        entradas.add(lblGrado);
        entradas.add(tfGrado);
        entradas.add(btnGrado);
        entradas.add(lblCoeficiente);
        entradas.add(tfCoeficiente);
        entradas.add(lblExponente);
        entradas.add(tfExponente);
        entradas.add(btnAgregaTrm);
        entradas.add(lblLimInf);
        entradas.add(tfLimInf);
        entradas.add(lblLimSup);
        entradas.add(tfLimSup);
        entradas.add(lblInc);
        entradas.add(tfIncremento);
        entradas.add(btnAceptar);
        entradas.add(btnGraficar);
        graficador.add(grafica);
        
        addWindowListener(new CW());
        
        add(entradas, "West");
        add(graficador, "Center");
        setSize(785, 645);
        setVisible(true);
        setResizable(false);
    }
    
    private class CW extends WindowAdapter
    {
       public void windowClosing(WindowEvent e)
        {
            setVisible(false);
            dispose();
        }
    }
    
    private class BotonGrado implements ActionListener{
        //Crea un Polynomial del grado ingresado por el usuario
        public void actionPerformed(ActionEvent e){
            try{
                gradoPoli = Integer.parseInt(tfGrado.getText());
                if(gradoPoli < 0){
                    throw new NumberFormatException();  //se salta directo al catch??
                }
                Polynomial = new Polynomial(gradoPoli);
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null, "Invalid input: "+tfGrado.getText(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class BotonAgregaTermino implements ActionListener{
        //Agrega un termino a la vez al Polynomial creado
        public void actionPerformed(ActionEvent e){
            try{
                coeficiente = Integer.parseInt(tfCoeficiente.getText());
                exponente = Integer.parseInt(tfExponente.getText());
                if(exponente < 0){
                    throw new NumberFormatException();
                }
                if(Polynomial.getTerm(exponente) == null){   //verificar que el Polynomial se encuentre vacio en la posicion del exponente
                    Polynomial.agregaTerm(new Term(coeficiente, exponente), exponente);
                }
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null, "Letters and negative exponents not allowed", "Error", JOptionPane.ERROR_MESSAGE);
            }catch(NullPointerException npe){
                JOptionPane.showMessageDialog(null, "First the polynomial degree must be set", "Error", JOptionPane.ERROR_MESSAGE);
            }catch(ArrayIndexOutOfBoundsException aob){
                JOptionPane.showMessageDialog(null, "Polynomial degree cannot be exceeded", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class BotonAceptar implements ActionListener{
        //Captura los valores de limite inferior, limite superior e incemento. La bandera de la clase Equation cambia a verdadero
        public void actionPerformed(ActionEvent e){
            try{
                limInf = Double.parseDouble(tfLimInf.getText());
                limSup = Double.parseDouble(tfLimSup.getText());
                incremento = Double.parseDouble(tfIncremento.getText());
                grafica.agregaPolynomial(Polynomial);
                grafica.calculaCoordinates(limInf, limSup, incremento);
                grafica.setBandera(true);
                if(limSup <= limInf){
                    JOptionPane.showMessageDialog(null, "Please check limits", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if(incremento <= 0){
                    JOptionPane.showMessageDialog(null, "Increment cannot be negative nor zero", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null, "No letters allowed here", "Error", JOptionPane.ERROR_MESSAGE);
            }catch(NullPointerException npe){
                JOptionPane.showMessageDialog(null, "First the polynomial degree must be set", "Error", JOptionPane.ERROR_MESSAGE);
            }catch(ArithmeticException ae){
                JOptionPane.showMessageDialog(null, "Please check limits or increment", "Error", JOptionPane.ERROR_MESSAGE);
            }catch(IOException ioex){
            }
        }
    }
    private class BotonGraficar implements ActionListener {
        //Crea la grafica de la ecuacion
        public void actionPerformed(ActionEvent e){
            try{  
                if(grafica.getBandera() == true){
                      grafica.repaint();
                  }
                  else{
                      throw new NullPointerException();
                  }
            }catch(NullPointerException npe){
                  JOptionPane.showMessageDialog(null, "There's any equation to draw its graphic", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String args[]){
        UserInterface interg = new UserInterface();
    }
}
