
package util;

import java.util.ArrayList;
import modelos.Temperatura;


public class entradaTemperatura {
    static Temperatura m = new Temperatura();
    static ArrayList<Temperatura> temperatura = m.find();
    
    public static double entalpia(double t,double x)
    {
        double h =0;
        int size;
        double hf;
        double hfg;
        size = temperatura.size();
        if(t>373.95)
        {
            h = -1;
        }else{
            for(int i=0;i<size;i++)
            {
                if(temperatura.get(i).getT()==t)
                {
                   hf = temperatura.get(i).getHf();
                   hfg = temperatura.get(i).getHfg();
                   h = hf + x*hfg;
                   break;
                }else if(t>temperatura.get(i).getT()&& t<temperatura.get(i+1).getT()){

                    double hf1 = temperatura.get(i).getHf();
                    double hf2 = temperatura.get(i+1).getHf();
                    double t1 = temperatura.get(i).getT();
                    double t2 = temperatura.get(i+1).getT();

                    hf = hf1 + (((t-t1)/(t2-t1))*(hf2-hf1));
                    
                    double hfg1 = temperatura.get(i).getHfg();
                    double hfg2 = temperatura.get(i+1).getHfg();
                    
                    hfg = hfg1 + (((t-t1)/(t2-t1))*(hfg2-hfg2));
                    h = hf + x*hfg;
                    break;
                }
            }
        }
        
        
        return h;
    }
    
    public static double entropia(double t,double x)
    {
        double s =0;
        int size;
        double sf;
        double sfg;
        size = temperatura.size();
        if(t>373.95)
        {
            s = -1;
        }else{
            for(int i=0;i<size;i++)
            {
                if(temperatura.get(i).getT()==t)
                {
                   sf = temperatura.get(i).getSf();
                   sfg = temperatura.get(i).getSfg();
                   
                   s = sf + x*sfg;
                   break;
                }else if(t>temperatura.get(i).getT()&& t<temperatura.get(i+1).getT()){
                    
                    double t1 = temperatura.get(i).getT();
                    double t2 = temperatura.get(i+1).getT();

                    double sf1 = temperatura.get(i).getSf();
                    double sf2 = temperatura.get(i+1).getSf();

                    sf = sf1 + (((t-t1)/(t2-t1))*(sf2-sf1));
                    
                    double sfg1 = temperatura.get(i).getSfg();
                    double sfg2 = temperatura.get(i+1).getSfg();
                   
                    sfg = sfg1 + (((t-t1)/(t2-t1))*(sfg2-sfg1));
                    
                    s = sf + x*sfg;
                    break;
                }
            }
        }
        
        
        return s;
    }
    
    
    
    public static int region(double t,double presion)
    {
        int r = -1;
        double P=10000;
        int size;
        size = temperatura.size();
        if(t>373.95)
        {
            r = 0;
        }else{
            for(int i=0;i<size;i++)
            {
                if(temperatura.get(i).getT() == t)
                {
                    P = temperatura.get(i).getP();
                    break;

                }else if(t>temperatura.get(i).getT()&& t<temperatura.get(i+1).getT()){
                    double y1 = temperatura.get(i).getP();
                    double y2 = temperatura.get(i+1).getP();
                    double x1 = temperatura.get(i).getT();
                    double x2 = temperatura.get(i+1).getT();

                    P = y1 + (((t-x1)/(x2-x1))*(y2-y1));
                    System.out.println("Presion de sat "+P);
                    break;
                }       
            }
        }
        
        
        if(presion > P)
        {
            r = 1; //Saturacion
        }else{
            r = 0; //Sobrecalentamiento
        }
        return r;
    }
    
}
