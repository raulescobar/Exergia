
package controladores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import vistas.*;
import modelos.*;

public class MainController implements ActionListener,KeyListener{
    
    MainVista vista = new MainVista();
    Sustancia modelo = new Sustancia();
    ArrayList<Sustancia> sustancias;
    
    public MainController(MainVista vista, Sustancia modelo)
    {
        this.vista = vista;
        cargarListener();
        this.modelo = modelo;
        
        
            
        this.sustancias = modelo.find();
        
        
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
        if(vista.radioTeorico.isSelected()==true){
            vista.txtAire.setText("");
            vista.txtAire.setEditable(false);
        }
        if(vista.radioExceso.isSelected()==true){
            
            vista.txtAire.setText("");
            vista.txtAire.setEditable(true);
            
        }
        if(e.getSource() == vista.btnCalcular)
        {
            
            verificarCombustible();
            if(verificarCombustible())
            {
                
                
            }else{
                JOptionPane.showMessageDialog(null, "Debe Completar todos los datos del Combustible.","Error",JOptionPane.WARNING_MESSAGE);
            }

            //System.out.println(entalpia("O2",600)-entalpia("O2",298));

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
    
    public double entalpia(String formula,double temp)
    {
        int id = buscarId(formula);
        double entalpia =0;
        if(id!=-1)
        { 
            double a = sustancias.get(id).getCpa();
            double b = sustancias.get(id).getCpb();
            double c = sustancias.get(id).getCpc();
            double d = sustancias.get(id).getCpd();
            entalpia += a*(temp);
            entalpia += (b*Math.pow((temp),2))/2;
            entalpia += (c*Math.pow((temp),3))/3;
            entalpia += (d*Math.pow((temp),4))/4;
            entalpia = entalpia*4.1868; //Conversion pansdo de KCal a KJ
        }else{
            entalpia = -1; //Sustancia no encontrada en la base de datos
        }
        
        return entalpia;
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
                }else{verificar = false;}
            }else{
                if(vista.comboFuel1.getSelectedIndex()!=0 && vista.comboFuel2.getSelectedIndex()!=0 && !vista.txtFrac1.getText().equals("") && !vista.txtFrac2.getText().equals(""))
                {
                    verificar = true;
                }else{verificar = false;}
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
               
        vista.txtFrac1.setEditable(false);
        vista.txtFrac2.setEditable(false);
        vista.comboFuel2.setEnabled(false);
        
        vista.txtAire.setEditable(false);
        
        vista.txtTempProd.setEditable(false);
        vista.txtPreProd.setEditable(false);
    }
    
    
    
}
