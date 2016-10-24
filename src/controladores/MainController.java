
package controladores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import vistas.*;
import modelos.*;
import util.ExQuimica;
import util.ExTermoMeca;
import util.TempAdiabatica;

public class MainController implements ActionListener,KeyListener{
    
    MainVista vista = new MainVista();
    Sustancia modelo = new Sustancia();
    ArrayList<Sustancia> sustancias;
    double tempFuel;
    double presFuel;
    int Fuel1;
    int Fuel2;
    double yFuel1;
    double yFuel2;
    double tempAir;
    double presAir;
    double yAir;
    double tempProd;
    double presProd;
    
    public MainController(MainVista vista, Sustancia modelo)
    {
        this.vista = vista;
        cargarListener();
        this.modelo = modelo;
        
        
            
        this.sustancias = modelo.find();
        
        vista.radioTeorico.setSelected(true);
        for(Sustancia sustancia : sustancias)
        {
            vista.comboFuel1.addItem(sustancia.getNombre());
            vista.comboFuel2.addItem(sustancia.getNombre());
        }
        /** Método obsoleto para recorrer una instancia de una clase**/
        /*for(int i=0;i<numSustancias;i++)
        {
            vista.comboFuel1.addItem(sustancias.get(i).getNombre());
            vista.comboFuel2.addItem(sustancias.get(i).getNombre());

        }*/
    }
    
    /** Metodos Abstractos **/
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.checkFuel)
        {
            if(vista.checkFuel.isSelected())
            {
                //Seleccionado
                habilitarCombustible(true);
                            
            }else{
                //Sin seleccionar
                habilitarCombustible(false);
            }
        }
        if(e.getSource() == vista.radioTeorico)
        {
            if(vista.radioTeorico.isSelected()==true){
                vista.txtAire.setText("");
                vista.txtAire.setEditable(false);
            }
            
        }
        if(e.getSource()== vista.radioExceso)
        {
            if(vista.radioExceso.isSelected()==true){
            
            vista.txtAire.setText("");
            vista.txtAire.setEditable(true);
            
        }
        }
        
        
        
        if(e.getSource() == vista.btnCalcular)
        {
            if(!verificarCombustible())
            {
                JOptionPane.showMessageDialog(null, "Debe Completar todos los datos del Combustible.","Error",JOptionPane.WARNING_MESSAGE);
  
            }else{
                if(!verificarAire())
                {
                    JOptionPane.showMessageDialog(null, "Debe Completar todos los datos del Aire.","Error",JOptionPane.WARNING_MESSAGE);
                }else{
                    vista.txtPreProd.setText(vista.txtPreFuel.getText());
                    
                    tempFuel = Double.parseDouble(vista.txtTempFuel.getText());
                    presFuel = Double.parseDouble(vista.txtPreFuel.getText());
                    
                    Fuel1 = vista.comboFuel1.getSelectedIndex()-1;
                    
                    if(vista.checkFuel.isSelected())
                    {
                        Fuel2 = vista.comboFuel2.getSelectedIndex()-1;
                        yFuel1 = Double.parseDouble(vista.txtFrac1.getText());
                        yFuel2 = Double.parseDouble(vista.txtFrac2.getText());
                        
                        
                    }else{

                        Fuel2 = 0;
                        yFuel1 = 1;
                        yFuel2 = 0;
                        
                    }
                    
                    
                    
                    
                    tempAir = Double.parseDouble(vista.txtTempAir.getText());
                    presAir = Double.parseDouble(vista.txtTempAir.getText());
                    
                    if(vista.radioTeorico.isSelected()==true)
                    {
                        yAir=1;
                    }
                    if(vista.radioExceso.isSelected()==true)
                    {
                         yAir = (Double.parseDouble(vista.txtAire.getText())/100)+1;
                    }
                    
                    
                    //TempAdiabatica temp = new TempAdiabatica(0,0,0,0);
                   
                    
                    double pp =0;
                    double cte=0;
                    double coefOxigeno;
                    double coefNitrogeno;
                    double coefDioxido;
                    double coefAgua;
                    
                    coefDioxido = yFuel1*sustancias.get(Fuel1).getnC()+ yFuel2*sustancias.get(Fuel2).getnC();
                    coefAgua = (yFuel1*sustancias.get(Fuel1).getnH() + yFuel2*sustancias.get(Fuel2).getnH())/2;
                    coefOxigeno = (yFuel1*(sustancias.get(Fuel1).getnC()+(sustancias.get(Fuel1).getnH()/4)-(sustancias.get(Fuel1).getnO()/2)));
                    coefOxigeno += (yFuel2*(sustancias.get(Fuel2).getnC()+(sustancias.get(Fuel2).getnH()/4)-(sustancias.get(Fuel2).getnO()/2)));
                    coefOxigeno = yAir*coefOxigeno; 
                    coefNitrogeno = coefOxigeno*3.76;
                    

                    //Reactivos
                    /**Entalpias de formación**/
                    cte -= yFuel1*sustancias.get(Fuel1).getHform();
                    cte -= yFuel2*sustancias.get(Fuel2).getHform();
                    cte -= coefOxigeno*sustancias.get(0).getHform();//Oxigeno = 0;
                    cte -= coefNitrogeno*sustancias.get(1).getHform();//Nitrogeno = 0;
                    
                    /**Cambio en la entalpia**/
                    cte -= yFuel1*(entalpia(Fuel1,tempFuel)-entalpia(Fuel1,298));
                    cte -= yFuel2*(entalpia(Fuel2,tempFuel)-entalpia(Fuel2,298));
                    cte -= coefOxigeno*(entalpia(0,tempAir)-entalpia(0,298));//Oxigeno
                    cte -= coefNitrogeno*(entalpia(1,tempAir)-entalpia(1,298));//Nitrogeno
                    //Reactivos
                    
                    //Productos
                    /**Entalpias de formación**/
                    cte += coefDioxido*sustancias.get(2).getHform();//Dioxido de Carbono
                    cte += coefAgua*sustancias.get(3).getHform();//Agua
                    cte += coefNitrogeno*sustancias.get(1).getHform();//Nitrogeno = 0
                    cte += (coefOxigeno-coefOxigeno/yAir)*sustancias.get(0).getHform();//Oxigeno en exceso = 0
                    
                    /**Entalpia a la Referencia**/
                    cte -= coefDioxido*entalpia(2,298);//Dioxido de Carbono
                    cte -= coefAgua*entalpia(3,298);//Agua
                    cte -= coefNitrogeno*entalpia(1,298);//Nitrogeno
                    cte -= (coefOxigeno-coefOxigeno/yAir)*entalpia(0,298);//Oxigeno
                    
                    
                    
                    double a=0;
                    a += coefDioxido*sustancias.get(2).getCpa();
                    a += coefAgua*sustancias.get(3).getCpa();
                    a += coefNitrogeno*sustancias.get(1).getCpa();
                    a += (coefOxigeno-coefOxigeno/yAir)*sustancias.get(0).getCpa();
                    
                    double b=0;
                    b += coefDioxido*sustancias.get(2).getCpb();
                    b += coefAgua*sustancias.get(3).getCpb();
                    b += coefNitrogeno*sustancias.get(1).getCpb();
                    b += (coefOxigeno-coefOxigeno/yAir)*sustancias.get(0).getCpb();
                    
                    double c=0;
                    c += coefDioxido*sustancias.get(2).getCpc();
                    c += coefAgua*sustancias.get(3).getCpc();
                    c += coefNitrogeno*sustancias.get(1).getCpc();
                    c += (coefOxigeno-coefOxigeno/yAir)*sustancias.get(0).getCpc();
                    
                    double d=0;
                    d += coefDioxido*sustancias.get(2).getCpd();
                    d += coefAgua*sustancias.get(3).getCpd();
                    d += coefNitrogeno*sustancias.get(1).getCpd();
                    d += (coefOxigeno-coefOxigeno/yAir)*sustancias.get(0).getCpd();
                    
                    /*Método de la secante para resover ecuación*/
                  
                    
                    double t1 =298;
                    double t2=300;
                    double f1=0;
                    double f2=0;
                    double TempPr;
                    double faux;
                    
                    int i = 0;
                    while(true)
                    {
                        i++;
                       
                        f1=funcion(t1,a,b,c,d,cte);
                    
                        f2=funcion(t2,a,b,c,d,cte);

                        TempPr = (t1*f2-t2*f1)/(f2-f1);
                        faux = funcion(TempPr,a,b,c,d,cte);
                        
                        if(Math.abs(faux)>=0.1)
                        {
                            t1 = t2;
                            t2 = TempPr;
                        }else{break;}
                        
                    }
                    
                    NumberFormat df = new DecimalFormat("#0.00");     
                    
                    vista.txtTempProd.setText(String.valueOf(df.format(TempPr)));
                    
                    /*Combustible */
                    double exTmComb=0;
                    exTmComb = yFuel1*ExTermoMeca.calcular(Fuel1, tempFuel);
                    exTmComb += yFuel2*ExTermoMeca.calcular(Fuel2,tempFuel);
                    vista.txtTmFuel.setText(String.valueOf(df.format(exTmComb)));
                    
                    double exQuComb=0;
                    exQuComb = yFuel1*ExQuimica.Fuel(Fuel1);
                    exQuComb += yFuel2*ExQuimica.Fuel(Fuel2);
                    vista.txtQuFuel.setText(String.valueOf(df.format(exQuComb)));
                    
                    double exToComb=0;
                    exToComb = exTmComb + exQuComb;
                    vista.txtToFuel.setText(String.valueOf(df.format(exToComb)));
                    /*Combustible */
                    
                    /*Aire */
                    double exTmAir=0;
                    exTmAir = ExTermoMeca.calcular(0, tempAir);
                    exTmAir += ExTermoMeca.calcular(1, tempAir);
                    vista.txtTmAir.setText(String.valueOf(df.format(exTmAir)));
                    
                    double exQuAir = 0;
                    exQuAir = ExQuimica.productos(true, coefOxigeno, coefNitrogeno, 0, 0);
                    
                    vista.txtQuAir.setText(String.valueOf(df.format(exQuAir)));
                    
                    
                    double exToAir=0;
                    exToAir = exTmAir + exQuAir;
                    vista.txtToAir.setText(String.valueOf(df.format(exToAir)));
                    
                    /*Air */
                    
                    /*Productos de Combustión*/
                    double exTmPro=0;
                    exTmPro = coefDioxido*ExTermoMeca.calcular(2, TempPr);
                    exTmPro += coefAgua*ExTermoMeca.calcular(3, TempPr);
                    exTmPro += coefNitrogeno*ExTermoMeca.calcular(1, TempPr);
                    exTmPro += coefOxigeno*ExTermoMeca.calcular(0, TempPr);
                    vista.txtTmPro.setText(String.valueOf(df.format(exTmPro)));

                    double exQuPro = 0;
                    double coefOxigenoPro = (coefOxigeno-coefOxigeno/yAir);
                    exQuPro = ExQuimica.productos(false, coefOxigenoPro, coefNitrogeno, coefAgua, coefDioxido);
                    vista.txtQuPro.setText(String.valueOf(df.format(exQuPro)));
                    
                    double exToPro=0;
                    exToPro = exTmPro + exQuPro;
                    vista.txtToPro.setText(String.valueOf(df.format(exToPro)));
                    
                    /*Productos de Combustión*/
                    
                   
                }
            }
            

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public double funcion(double x,double a,double b,double c,double d, double cte)
    {
        double resultado=0;
        resultado = (a*x + (b*Math.pow(x, 2))/2+ (c*Math.pow(x, 3))/3 + (d*Math.pow(x, 4))/4)*4.1868+cte ;
        return resultado;
    }
    
    public void habilitarCombustible(Boolean var)
    {
        if(var)
        {
           vista.txtFrac1.setEditable(var);
           vista.txtFrac2.setEditable(var);
           vista.comboFuel2.setEnabled(var); 
           
        }else{
            vista.txtFrac1.setEditable(var);
            vista.txtFrac2.setEditable(var);
            vista.comboFuel2.setEnabled(var);
            vista.txtFrac1.setText("");  
            vista.txtFrac2.setText("");
        }
    }
    
    public double entalpia(int id,double temp)
    {
        double entalpia = 0;

        double a = sustancias.get(id).getCpa();
        double b = sustancias.get(id).getCpb();
        double c = sustancias.get(id).getCpc();
        double d = sustancias.get(id).getCpd();
        entalpia += a*(temp);
        entalpia += (b*Math.pow((temp),2))/2;
        entalpia += (c*Math.pow((temp),3))/3;
        entalpia += (d*Math.pow((temp),4))/4;
        entalpia = entalpia*4.1868; //Conversion pansdo de KCal a KJ
        
        
        return entalpia;
    }
    public double entropia(int id,double temp)
    {
        double entropia =0;
        double a = sustancias.get(id).getCpa();
        double b = sustancias.get(id).getCpb();
        double c = sustancias.get(id).getCpc();
        double d = sustancias.get(id).getCpd();
        entropia += a*(Math.log(temp));
        entropia += b*(temp);
        entropia += (c*Math.pow((temp),2))/2;
        entropia += (d*Math.pow((temp),3))/3;
        entropia = entropia*4.1868; //Conversion pansdo de KCal a KJ
        return entropia;
    }
    
    public int buscarId(String formula)
    {
        int id=-1;
        String simboloUser = formula.toUpperCase();
        
        for(Sustancia sustancia : sustancias)
        {
            String simboloBD = sustancia.getFormula().toUpperCase();
            if(simboloBD.equals(simboloUser))
            {
                id = sustancia.getId()-1;
                break;
            }
        }
        return id;
    }
    
    public boolean verificarCombustible()
    {
        boolean verificar = false;
        if(!vista.txtTempFuel.getText().equals("") && !vista.txtPreFuel.getText().equals(""))
        {
            if(!vista.checkFuel.isSelected())
            {
                if(vista.comboFuel1.getSelectedIndex()!=0)
                {
                    verificar = true;
                }
            }else{
                if(vista.comboFuel1.getSelectedIndex()!=0 && vista.comboFuel2.getSelectedIndex()!=0 && !vista.txtFrac1.getText().equals("") && !vista.txtFrac2.getText().equals(""))
                {
                    verificar = true;
                }
            }
        }//Campos Temperatura y presión vacios 
        return verificar;
    }
    public boolean verificarAire()
    {
        boolean verificar = false;
        if(!vista.txtTempAir.getText().equals("") && !vista.txtPreAir.getText().equals(""))
        {
            if(vista.radioTeorico.isSelected()==true)
            {
                verificar = true;
            }
            if(vista.radioExceso.isSelected()==true)
            {
                if(!vista.txtAire.getText().equals(""))
                {
                    verificar = true;
                }
            }
        }//Campos Temperatura y presión vacios
        return verificar;
    }
     
    public void render()
    {
        this.vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
    }
     
    private void cargarListener()
    {
        vista.txtTempFuel.addActionListener(this);
        vista.txtPreFuel.addActionListener(this);
        vista.checkFuel.addActionListener(this);
        vista.comboFuel1.addActionListener(this);
        vista.txtFrac1.addActionListener(this);
        vista.comboFuel2.addActionListener(this);
        vista.txtFrac2.addActionListener(this);
        vista.txtTempAir.addActionListener(this);
        vista.txtPreAir.addActionListener(this);
        vista.radioTeorico.addActionListener(this);
        vista.radioExceso.addActionListener(this);
        vista.txtAire.addActionListener(this);
        vista.txtTempProd.addActionListener(this);
        vista.txtPreProd.addActionListener(this);
        vista.btnCalcular.addActionListener(this);
        vista.txtTmFuel.addActionListener(this);
        vista.txtQuFuel.addActionListener(this);
        vista.txtToFuel.addActionListener(this);
        vista.txtTmAir.addActionListener(this);
        vista.txtQuAir.addActionListener(this);
        vista.txtToAir.addActionListener(this);
        vista.txtTmPro.addActionListener(this);
        vista.txtQuPro.addActionListener(this);
        vista.txtToPro.addActionListener(this);
               
        vista.txtFrac1.setEditable(false);
        vista.txtFrac2.setEditable(false);
        vista.comboFuel2.setEnabled(false);
        
        vista.txtAire.setEditable(false);
        
        vista.txtTempProd.setEditable(false);
        vista.txtPreProd.setEditable(false);
        
        
    }
    
    
    
}
