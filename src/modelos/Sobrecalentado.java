
package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Sobrecalentado {
    
    double p;
    double t;
    double v;
    double u;
    double h;
    double s;
    
   
    private static Conexion conn;
    
    public Sobrecalentado()
    {
        conn = new Conexion();
        p = 0;
        t = 0;
        v = 0;
        u = 0;
        h = 0;
        s = 0;
       
    }
    public ArrayList find(String presion)
    {
        ArrayList datos = new ArrayList();
        ArrayList datos2 = new ArrayList();
        ArrayList full = new ArrayList();
        Sobrecalentado propiedades;
        ArrayList<Double> response = new ArrayList();
       
        try {
            
            Connection acc = conn.getConexion();
            PreparedStatement ps = acc.prepareStatement("SELECT name FROM sqlite_master WHERE type = 'table'");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                response.add(rs.getDouble(1));    
            }
            
            Iterator<Double> it = response.iterator();
            while(it.hasNext())
            {
                if(it.next()==0)
                {
                    it.remove();
                } 
            }
            
            int size = response.size();
            Collections.sort(response);
            for(int i=0;i<size;i++)
            {
                if(response.get(i)==Double.parseDouble(presion))
                {
                    
                    String sql = "SELECT * FROM '"+presion+"'";
                    PreparedStatement pr = acc.prepareStatement(sql);
                    ResultSet rr = pr.executeQuery();
                    
                    while(rr.next())
                    {
                        propiedades = new Sobrecalentado();
                        propiedades.setT(rr.getDouble(1));
                        
                        propiedades.setV(rr.getDouble(2));
                        
                        propiedades.setU(rr.getDouble(3));
                       
                        propiedades.setH(rr.getDouble(4));
                        
                        propiedades.setS(rr.getDouble(5));
                        datos.add(propiedades);
                        datos2.add(propiedades);
                    }
                    full.add(datos);
                    full.add(datos2);
                return full;
                //break;
                }else if(Double.parseDouble(presion)>response.get(i) && Double.parseDouble(presion)<response.get(i+1)){
                    
                    Double in1 = response.get(i);
                    String p1 = String.valueOf(in1);
                    String sql1 = "SELECT * FROM '"+p1+"'";
                    PreparedStatement pr1 = acc.prepareStatement(sql1); 
                    ResultSet rr1 = pr1.executeQuery();
                    
                    while(rr1.next())
                    {
                        propiedades = new Sobrecalentado();
                        propiedades.setP(in1);
                        propiedades.setT(rr1.getDouble(1));
                        propiedades.setV(rr1.getDouble(2));
                        propiedades.setU(rr1.getDouble(3));
                        propiedades.setH(rr1.getDouble(4));
                        propiedades.setS(rr1.getDouble(5));
                        datos.add(propiedades);

                    }
                    Double in2 = response.get(i+1);   
                    String p2 = String.valueOf(in2);
                    String sql2 = "SELECT * FROM '"+p2+"'";
                    PreparedStatement pr2 = acc.prepareStatement(sql2);
                    ResultSet rr2 = pr2.executeQuery();

                    while(rr2.next())
                    {
                        propiedades = new Sobrecalentado();
                        propiedades.setP(in2);
                        propiedades.setT(rr2.getDouble(1));
                        propiedades.setV(rr2.getDouble(2));
                        propiedades.setU(rr2.getDouble(3));
                        propiedades.setH(rr2.getDouble(4));
                        propiedades.setS(rr2.getDouble(5));
                        datos2.add(propiedades);
                    }
                    full.add(datos);
                    full.add(datos2);
                    return full;
                   
        
                }
            }
        //return datos;   
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return datos;
    }
    
    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public double getU() {
        return u;
    }

    public void setU(double u) {
        this.u = u;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }
    
    


   
}
