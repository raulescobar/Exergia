
package util;

import java.util.ArrayList;
import modelos.Sustancia;

public class ExQuimica {
    static Sustancia modelo = new Sustancia();
    static ArrayList<Sustancia> sustancias = modelo.find();
    
    public static double Fuel(int id)
    {
        double cOxigeno = sustancias.get(id).getnC()+(sustancias.get(id).getnH()/4)-(sustancias.get(id).getnO()/2);
        double cNitrogeno = cOxigeno*3.76;
        double cDioxido = sustancias.get(id).getnC();
        double cAgua = sustancias.get(id).getnH()/2;
        
        double yo2 = 0.2035;
        double yn2 = 0.7567;
        double yh2o = 0.0312;
        double yco2 = 0.0003;
        
        double exQuimica = 0;
        exQuimica += sustancias.get(id).getGform();        
        exQuimica += cOxigeno*sustancias.get(0).getGform();
        exQuimica += cNitrogeno*sustancias.get(1).getGform();
        exQuimica -= cDioxido*sustancias.get(2).getHform();
        exQuimica -= cAgua*sustancias.get(3).getHform();
        exQuimica -= cNitrogeno*sustancias.get(1).getHform();
        exQuimica += 8.3145*298*Math.log((Math.pow(yco2, cOxigeno))/(Math.pow(yco2, cDioxido)*Math.pow(yh2o, cAgua)));
        
        return exQuimica;
    }
    
    public static double productos(boolean air,double o2,double n2,double h2o,double co2)
    {
        double yo2 = 0.2035;
        double yn2 = 0.7567;
        double yh2o = 0.0312;
        double yco2 = 0.0003;
        
        double mTotales = o2+n2+h2o+co2;
        double exQuimica =0;
        
        if(o2!=0)
        {
            exQuimica += o2*Math.log((o2/mTotales)/yo2);
        }
        
        exQuimica += n2*Math.log((n2/mTotales)/yn2);
        
        if(!air)
        {
            exQuimica += h2o*Math.log((h2o/mTotales)/yh2o);
            exQuimica += co2*Math.log((co2/mTotales)/yco2);
        }
        exQuimica *= 8.3145*298;
        
        return exQuimica;
    }
}
