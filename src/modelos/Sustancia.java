
package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Sustancia {
    int id;
    String nombre;
    String formula;
    double Cpa;
    double Cpb;
    double Cpc;
    double Cpd;
    double Hform;
    private static Conexion conn;
    
    public Sustancia()
    {
        conn = new Conexion();
        id = 0;
        nombre="";
        formula="";
        Cpa=0;
        Cpb=0;
        Cpc=0;
        Cpd=0;
        Hform=0;
        
    }

    
    public ArrayList<Sustancia> find()
    {
        
        ArrayList sustancias = new ArrayList();
        Sustancia sustancia;
        try {
            
            Connection acc = conn.getConexion();
            PreparedStatement ps = acc.prepareStatement("select * from Sustancias");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                sustancia = new Sustancia();
                
                sustancia.setId(rs.getInt(1));
                sustancia.setNombre(rs.getString(2));
                sustancia.setFormula(rs.getString(3));
                sustancia.setCpa(rs.getDouble(4));
                sustancia.setCpb(rs.getDouble(5));
                sustancia.setCpc(rs.getDouble(6));
                sustancia.setCpd(rs.getDouble(7));
                sustancia.setHform(rs.getDouble(8));
                
                sustancias.add(sustancia);
            }
            
            
        } catch (Exception e) {
            System.out.println(Sustancia.class.getName());
        }
        
        return sustancias;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCpa() {
        return Cpa;
    }

    public void setCpa(double Cpa) {
        this.Cpa = Cpa;
    }

    public double getCpb() {
        return Cpb;
    }

    public void setCpb(double Cpb) {
        this.Cpb = Cpb;
    }

    public double getCpc() {
        return Cpc;
    }

    public void setCpc(double Cpc) {
        this.Cpc = Cpc;
    }

    public double getCpd() {
        return Cpd;
    }

    public void setCpd(double Cpd) {
        this.Cpd = Cpd;
    }
    
    public double getHform() {
        return Hform;
    }

    public void setHform(double Hform) {
        this.Hform = Hform;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
    
    
}
