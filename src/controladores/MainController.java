
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
//import util.TempAdiabatica;
import util.entradaTemperatura;
import util.entradaPresion;
import util.SobreCalentado;

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
                    double f1;
                    double f2;
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
                    double exTmComb;
                    exTmComb = yFuel1*ExTermoMeca.calcular(Fuel1, tempFuel);
                    exTmComb += yFuel2*ExTermoMeca.calcular(Fuel2,tempFuel);
                    vista.txtTmFuel.setText(String.valueOf(df.format(exTmComb)));
                    
                    double exQuComb;
                    exQuComb = yFuel1*ExQuimica.Fuel(Fuel1);
                    exQuComb += yFuel2*ExQuimica.Fuel(Fuel2);
                    vista.txtQuFuel.setText(String.valueOf(df.format(exQuComb)));
                    
                    double exToComb;
                    exToComb = exTmComb + exQuComb;
                    vista.txtToFuel.setText(String.valueOf(df.format(exToComb)));
                    /*Combustible */
                    
                    /*Aire */
                    double exTmAir;
                    exTmAir = ExTermoMeca.calcular(0, tempAir);
                    exTmAir += ExTermoMeca.calcular(1, tempAir);
                    vista.txtTmAir.setText(String.valueOf(df.format(exTmAir)));
                    
                    double exQuAir;
                    exQuAir = ExQuimica.productos(true, coefOxigeno, coefNitrogeno, 0, 0);
                    
                    vista.txtQuAir.setText(String.valueOf(df.format(exQuAir)));
                    
                    
                    double exToAir;
                    exToAir = exTmAir + exQuAir;
                    vista.txtToAir.setText(String.valueOf(df.format(exToAir)));
                    
                    /*Air */
                    
                    /*Productos de Combustión*/
                    double exTmPro;
                    exTmPro = coefDioxido*ExTermoMeca.calcular(2, TempPr);
                    exTmPro += coefAgua*ExTermoMeca.calcular(3, TempPr);
                    exTmPro += coefNitrogeno*ExTermoMeca.calcular(1, TempPr);
                    exTmPro += coefOxigeno*ExTermoMeca.calcular(0, TempPr);
                    vista.txtTmPro.setText(String.valueOf(df.format(exTmPro)));

                    double exQuPro;
                    double coefOxigenoPro = (coefOxigeno-coefOxigeno/yAir);
                    exQuPro = ExQuimica.productos(false, coefOxigenoPro, coefNitrogeno, coefAgua, coefDioxido);
                    vista.txtQuPro.setText(String.valueOf(df.format(exQuPro)));
                    
                    double exToPro;
                    exToPro = exTmPro + exQuPro;
                    vista.txtToPro.setText(String.valueOf(df.format(exToPro)));
                    
                    /*Productos de Combustión*/
                    
                    /*Calculo del calor de la reacción de combustión*/
                    double hProductos1 =0;
                    double hProductos2 =0;
                    
                    /*Entalpia de los productos*/
                    
                    
                    hProductos1 += coefDioxido*(entalpia(2,TempPr)-entalpia(2,298));//Dioxido de Carbono
                    hProductos1 += coefAgua*(entalpia(3,TempPr)-entalpia(3,298));//Agua
                    hProductos1 += coefNitrogeno*(entalpia(1,TempPr)-entalpia(1,298));//Nitrogeno
                    hProductos1 += coefOxigenoPro*(entalpia(0,TempPr)-entalpia(0,298));//Oxigeno
                   
                    /*Entalpia de los productos*/
                    
                    /*Entalpia de los Productos a la salida*/

                    /**Cambio en la entalpia**/
                    double TempPr2 = 500;
                    hProductos2 += coefDioxido*(entalpia(2,TempPr2)-entalpia(2,298));//Dioxido de Carbono
                    hProductos2 += coefAgua*(entalpia(3,TempPr2)-entalpia(3,298));//Agua
                    hProductos2 += coefNitrogeno*(entalpia(1,TempPr2)-entalpia(1,298));//Nitrogeno
                    hProductos2 += coefOxigenoPro*(entalpia(0,TempPr2)-entalpia(0,298));//Oxigeno
                    //Reactivos
                    
                   
                    
                    double presion1;
                    double temp1;
                    double h1;
                    double s1;
                    double x1;
                    
                    double presion2;
                    double temp2;
                    double h2 = 0;
                    double s2;
                    double x2;
                    
                    double presion3;
                    double temp3;
                    double h3;
                    double s3;
                    double x3;
                    
                    double presion4;
                    double temp4;
                    double h4;
                    double s4;
                    double x4;
                    
                    presion1 = Double.parseDouble(vista.txtE1Pres.getText());
                    
                    temp1 = Double.parseDouble(vista.txtE1Temp.getText());
                    presion2 = Double.parseDouble(vista.txtE2Pres.getText());
                    
                    //Estado 1
                    if(entradaTemperatura.region(temp1, presion1)==0)
                    {
                        h1 = SobreCalentado.entalpia(temp1,presion1);
                        s1 = SobreCalentado.entropia(temp1,presion1);
                        
                        vista.txtE1Region.setText("Sobrecalentado");
                        
                    }else{
                        x1 = 1;
                        h1 = entradaTemperatura.entalpia(temp1,x1);
                        s1 = entradaTemperatura.entropia(temp1, x1);
                        vista.txtE1Region.setText("Saturado");
                    }
                    vista.txtE1H.setText(String.valueOf(df.format(h1)));
                    vista.txtE1S.setText(String.valueOf(df.format(s1)));
                    //Estado 2
                    s2 = s1;
                    x2 = entradaPresion.calidad(presion2, s2);
                    if(x2>=0 && x2<=1)
                    {
                        h2 = entradaPresion.entalpia(presion2, x2);
                        temp2 = entradaPresion.saturacion(presion2);
                        vista.txtE2Region.setText("Saturado");
                    }else {
                        temp2 = SobreCalentado.temperatura(s2, presion2);
                        h2 = SobreCalentado.entalpia(temp2,presion2);
                        vista.txtE2Region.setText("Sobrecalentado");
                        
                    }
                    vista.txtE2Temp.setText(String.valueOf(df.format(temp2)));
                    vista.txtE2H.setText(String.valueOf(df.format(h2)));
                    vista.txtE2S.setText(String.valueOf(df.format(s2)));
                    //Estado 3
                    presion3 = presion2;
                    temp3 = entradaPresion.saturacion(presion3);
                    x3 = 0;
                    h3 = entradaTemperatura.entalpia(temp3, x3);
                    s3 = entradaTemperatura.entropia(temp3, x3);
                    double v3 = entradaTemperatura.volumen(temp3, x3);
                    vista.txtE3Temp.setText(String.valueOf(df.format(temp3)));
                    vista.txtE3Pres.setText(String.valueOf(df.format(presion2)));
                    vista.txtE3H.setText(String.valueOf(df.format(h3)));
                    vista.txtE3S.setText(String.valueOf(df.format(s3)));
                    vista.txtE3Region.setText("Saturado");
                    
                    //Estado 4 
                    presion4 = presion1;
                    temp4 = temp3;
                    h4 = v3*(presion4-presion3)*1000 + h3;
                    s4 = s3;
                    vista.txtE4Temp.setText(String.valueOf(df.format(temp4)));
                    vista.txtE4Pres.setText(String.valueOf(df.format(presion4)));
                    vista.txtE4H.setText(String.valueOf(df.format(h4)));
                    vista.txtE4S.setText(String.valueOf(df.format(s4)));
                    vista.txtE4Region.setText("Comprimido");
                    
                    double Wturbina;
                    double Wbomba;
                    double Qcaldera;
                    double Qcondensador;
                    double eficiencia;
                    
                    Wturbina = h1-h2;
                    Wbomba = h4-h3;
                    Qcaldera = h1-h4;
                    Qcondensador = h2-h3;
                    
                    eficiencia = ((Wturbina-Wbomba)/Qcaldera)*100;
                    
                    double Magua;
                    double Mfuel;
                    Mfuel = yFuel1*sustancias.get(Fuel1).getPeso() + yFuel2*sustancias.get(Fuel2).getPeso();
                    Magua = (hProductos1-hProductos2)/((h1-h4));
                    
                    
                    
                    
                    double exProductos1;
                    exProductos1 = coefDioxido*ExTermoMeca.calcular(2, TempPr);
                    exProductos1 += coefAgua*ExTermoMeca.calcular(3, TempPr);
                    exProductos1 += coefNitrogeno*ExTermoMeca.calcular(1, TempPr);
                    exProductos1 += coefOxigeno*ExTermoMeca.calcular(0, TempPr);
                    
                    double exProductos2;
                    exProductos2 = coefDioxido*ExTermoMeca.calcular(2, TempPr2);
                    exProductos2 += coefAgua*ExTermoMeca.calcular(3, TempPr2);
                    exProductos2 += coefNitrogeno*ExTermoMeca.calcular(1, TempPr2);
                    exProductos2 += coefOxigeno*ExTermoMeca.calcular(0, TempPr2);
                    
                    double exdCaldera;
                    exdCaldera = Magua*(h4-h1-298*(s4-s1)) + (exProductos1-exProductos2);
                    
                    double nExergetica;
                    nExergetica = ((Wturbina*Magua)/exQuComb)*100;
                    
                    vista.txtCalorIn.setText(String.valueOf(df.format(Qcaldera)));
                    vista.txtCalorOut.setText(String.valueOf(df.format(Qcondensador)));
                    vista.txtTrabajoT.setText(String.valueOf(df.format(Wturbina)));
                    vista.txtTrabajoP.setText(String.valueOf(df.format(Wbomba)));
                    vista.txtEficienciaT.setText(String.valueOf(df.format(eficiencia)));
                    vista.txtEficienciaE.setText(String.valueOf(df.format(nExergetica)));
                    
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
        double resultado;
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
        
        vista.txtE1Temp.addActionListener(this);
        vista.txtE1Pres.addActionListener(this);
        vista.txtE1H.addActionListener(this);
        vista.txtE1S.addActionListener(this);
        vista.txtE1Region.addActionListener(this);
        
        vista.txtE2Temp.addActionListener(this);
        vista.txtE2Pres.addActionListener(this);
        vista.txtE2H.addActionListener(this);
        vista.txtE2S.addActionListener(this);
        vista.txtE2Region.addActionListener(this);
        
        vista.txtE3Temp.addActionListener(this);
        vista.txtE3Pres.addActionListener(this);
        vista.txtE3H.addActionListener(this);
        vista.txtE3S.addActionListener(this);
        vista.txtE3Region.addActionListener(this);
        
        vista.txtE4Temp.addActionListener(this);
        vista.txtE4Pres.addActionListener(this);
        vista.txtE4H.addActionListener(this);
        vista.txtE4S.addActionListener(this);
        vista.txtE4Region.addActionListener(this);
        
        vista.txtCalorIn.addActionListener(this);
        vista.txtCalorOut.addActionListener(this);
        vista.txtTrabajoT.addActionListener(this);
        vista.txtTrabajoP.addActionListener(this);
        vista.txtEficienciaT.addActionListener(this);
        vista.txtEficienciaE.addActionListener(this);
               
        vista.txtFrac1.setEditable(false);
        vista.txtFrac2.setEditable(false);
        vista.comboFuel2.setEnabled(false);
        
        vista.txtAire.setEditable(false);
        
        vista.txtTempProd.setEditable(false);
        vista.txtPreProd.setEditable(false);
        
       
        vista.txtE1H.setEditable(false);
        vista.txtE1S.setEditable(false);
        vista.txtE1Region.setEditable(false);
        
        vista.txtE2Temp.setEditable(false);
        
        vista.txtE2H.setEditable(false);
        vista.txtE2S.setEditable(false);
        vista.txtE2Region.setEditable(false);
        
        vista.txtE3Temp.setEditable(false);
        vista.txtE3Pres.setEditable(false);
        vista.txtE3H.setEditable(false);
        vista.txtE3S.setEditable(false);
        vista.txtE3Region.setEditable(false);
        
        vista.txtE4Temp.setEditable(false);
        vista.txtE4Pres.setEditable(false);
        vista.txtE4H.setEditable(false);
        vista.txtE4S.setEditable(false);
        vista.txtE4Region.setEditable(false);
        
        vista.txtCalorIn.setEditable(false);
        vista.txtCalorOut.setEditable(false);
        vista.txtTrabajoT.setEditable(false);
        vista.txtTrabajoP.setEditable(false);
        vista.txtEficienciaT.setEditable(false);
        vista.txtEficienciaE.setEditable(false);
        
        
        
    }
    
    
    
}
