
package util;

import java.util.ArrayList;
import modelos.Sustancia;


public class ExTermoMeca {
    static Sustancia modelo = new Sustancia();
    static ArrayList<Sustancia> sustancias = modelo.find();
    
        
    
    public static double calcular(int id,double temp)
    {
        double exTm =0;
        exTm = entalpia(id,temp)-entalpia(id,298);
        exTm -= 298*(entropia(id,temp)-entropia(id,298));
        return exTm;
    }
    
    private static double entalpia(int id,double temp)
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
    
    private static double entropia(int id,double temp)
    {
        double entropia =0;
        double a = sustancias.get(id).getCpa();
        double b = sustancias.get(id).getCpb();
        double c = sustancias.get(id).getCpc();
        double d = sustancias.get(id).getCpd();
        entropia += a*Math.log(temp);
        entropia += b*temp;
        entropia += (c*Math.pow((temp),2))/2;
        entropia += (d*Math.pow((temp),3))/3;
        entropia = entropia*4.1868; //Conversion pansdo de KCal a KJ
        return entropia;
    }
}
