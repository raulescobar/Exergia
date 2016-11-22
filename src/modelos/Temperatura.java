
package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Temperatura {
    double T,P,vf,vg,uf,ug,hf,hfg,hg,sf,sfg,sg;
    private static Conexion conn1;
    
    public Temperatura()
    {
        conn1 = new Conexion();
        T = 0;
        P=0;
        vf=0;
        vg=0;
        uf=0;
        ug=0;
        hf=0;
        hfg=0;
        hg=0;
        sf=0;
        sfg=0;
        sg=0;
    }
    
    public ArrayList<Temperatura> find()
    {
        
        ArrayList temperatura = new ArrayList();
        Temperatura temp;
        try {
            
            Connection acc = conn1.getConexion();
            PreparedStatement ps = acc.prepareStatement("select * from Temperatura");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                temp = new Temperatura();
                
                temp.setT(rs.getDouble(1));
                temp.setP(rs.getDouble(2));
                temp.setVf(rs.getDouble(3));
                temp.setVg(rs.getDouble(4));
                temp.setUf(rs.getDouble(5));
                temp.setUg(rs.getDouble(6));
                temp.setHf(rs.getDouble(7));
                temp.setHfg(rs.getDouble(8));
                temp.setHg(rs.getDouble(9));
                temp.setSf(rs.getDouble(10));
                temp.setSfg(rs.getDouble(11));
                temp.setSg(rs.getDouble(12));
                temperatura.add(temp);
            }
            
            
        } catch (Exception e) {
            System.out.println(Sustancia.class.getName());
        }
        
        return temperatura;
    }

    public double getT() {
        return T;
    }

    public void setT(double T) {
        this.T = T;
    }

    public double getP() {
        return P;
    }

    public void setP(double P) {
        this.P = P;
    }

    public double getVf() {
        return vf;
    }

    public void setVf(double vf) {
        this.vf = vf;
    }

    public double getVg() {
        return vg;
    }

    public void setVg(double vg) {
        this.vg = vg;
    }

    public double getUf() {
        return uf;
    }

    public void setUf(double uf) {
        this.uf = uf;
    }

    public double getUg() {
        return ug;
    }

    public void setUg(double ug) {
        this.ug = ug;
    }

    public double getHf() {
        return hf;
    }

    public void setHf(double hf) {
        this.hf = hf;
    }

    public double getHfg() {
        return hfg;
    }

    public void setHfg(double hfg) {
        this.hfg = hfg;
    }

    public double getHg() {
        return hg;
    }

    public void setHg(double hg) {
        this.hg = hg;
    }

    public double getSf() {
        return sf;
    }

    public void setSf(double sf) {
        this.sf = sf;
    }

    public double getSfg() {
        return sfg;
    }

    public void setSfg(double sfg) {
        this.sfg = sfg;
    }

    public double getSg() {
        return sg;
    }

    public void setSg(double sg) {
        this.sg = sg;
    }
    
}
