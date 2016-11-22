
package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Sobrecalentado {
    
    
    double t1;
    double v1;
    double u1;
    double h1;
    double s1;
    
    double t2;
    double v2;
    double u2;
    double h2;
    double s2;
    private static Conexion conn;
    
    public Sobrecalentado()
    {
        conn = new Conexion();
        t1 = 0;
        v1 = 0;
        u1 = 0;
        h1 = 0;
        s1 = 0;
        t2 = 0;
        v2 = 0;
        u2 = 0;
        h2 = 0;
        s2 = 0;
    }
    public ArrayList find(String presion)
    {
        ArrayList datos = new ArrayList();
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
                        propiedades.setT1(rr.getDouble(1));
                        propiedades.setT2(rr.getDouble(1));
                        propiedades.setV1(rr.getDouble(2));
                        propiedades.setV2(rr.getDouble(2));
                        propiedades.setU1(rr.getDouble(3));
                        propiedades.setU2(rr.getDouble(3));
                        propiedades.setH1(rr.getDouble(4));
                        propiedades.setH2(rr.getDouble(4));
                        propiedades.setS1(rr.getDouble(5));
                        propiedades.setS2(rr.getDouble(5));
                        datos.add(propiedades);
                    }
                return datos;
                //break;
                }else if(Double.parseDouble(presion)>response.get(i) && Double.parseDouble(presion)<response.get(i+1)){
                    int in1 = (int) response.get(i).doubleValue();
                   
                    String p1 = String.valueOf(in1);
                    
                    String sql1 = "SELECT * FROM '"+p1+"'";
                    PreparedStatement pr1 = acc.prepareStatement(sql1);
                    ResultSet rr1 = pr1.executeQuery();
                    ArrayList datos2 = new ArrayList();
                    ArrayList full = new ArrayList();
                    while(rr1.next())
                    {
                       
                        propiedades = new Sobrecalentado();
                        propiedades.setT1(rr1.getDouble(1));
                        propiedades.setV1(rr1.getDouble(2));
                        propiedades.setU1(rr1.getDouble(3));
                        propiedades.setH1(rr1.getDouble(4));
                        propiedades.setS1(rr1.getDouble(5));
                        datos.add(propiedades);
                        
                        
                        
                    }
                    int in2 = (int) response.get(i+1).doubleValue();
                   
                        String p2 = String.valueOf(in2);

                        String sql2 = "SELECT * FROM '"+p2+"'";
                        PreparedStatement pr2 = acc.prepareStatement(sql2);
                        ResultSet rr2 = pr2.executeQuery();

                        while(rr2.next())
                        {
                            propiedades = new Sobrecalentado();
                            propiedades.setT1(rr2.getDouble(1));
                            propiedades.setV1(rr2.getDouble(2));
                            propiedades.setU1(rr2.getDouble(3));
                            propiedades.setH1(rr2.getDouble(4));
                            propiedades.setS1(rr2.getDouble(5));
                            datos2.add(propiedades);
                        }
                        full.add(datos);
                        full.add(datos2);
                        return full;
                        //break;
                        
                }else{
                    System.err.println("Nada");
                     propiedades = new Sobrecalentado();
                     propiedades.setT2(-30);
                     datos.add(propiedades);
                     return datos;
                     //break;
                }
            }
        //return datos;   
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return datos;
    }

    public double getT1() {
        return t1;
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }

    public double getV1() {
        return v1;
    }

    public void setV1(double v1) {
        this.v1 = v1;
    }

    public double getU1() {
        return u1;
    }

    public void setU1(double u1) {
        this.u1 = u1;
    }

    public double getH1() {
        return h1;
    }

    public void setH1(double h1) {
        this.h1 = h1;
    }

    public double getS1() {
        return s1;
    }

    public void setS1(double s1) {
        this.s1 = s1;
    }

    public double getT2() {
        return t2;
    }

    public void setT2(double t2) {
        this.t2 = t2;
    }

    public double getV2() {
        return v2;
    }

    public void setV2(double v2) {
        this.v2 = v2;
    }

    public double getU2() {
        return u2;
    }

    public void setU2(double u2) {
        this.u2 = u2;
    }

    public double getH2() {
        return h2;
    }

    public void setH2(double h2) {
        this.h2 = h2;
    }

    public double getS2() {
        return s2;
    }

    public void setS2(double s2) {
        this.s2 = s2;
    }
    
    
   
}
