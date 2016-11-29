
package util;
import java.util.ArrayList;
import modelos.Presion;

public class entradaPresion {
    static Presion m = new Presion();
    static ArrayList<Presion> presion = m.find();
    
    public static double saturacion(double p)
    {
        double t=0;
        int size = presion.size();
        if(p>22.063)
        {
            t = 373.95;
        }else{
            for(int i=0;i<size;i++)
            {
                
                if(p==presion.get(i).getP())
                {
                    t = presion.get(i).getT();
                    break;
                }else if(p>presion.get(i).getP() && p<presion.get(i+1).getP()){
                    double p1 = presion.get(i).getP();
                    double p2 = presion.get(i+1).getP();
                    double t1 = presion.get(i).getT();
                    double t2 = presion.get(i+1).getT();
                    
                    t = t1 + (((p-p1)/(p2-p1))*(t2-t1));
                    break;
                }
            }
        }
        return t;
    }
    
    public static double calidad(double p,double s)
    {
        double x = 0;
        int size = presion.size();
        for(int i=0;i<size;i++)
        {
           if(p==presion.get(i).getP())
           {
               if(s>presion.get(i).getSg())
               {
                   x = 2; // Vapor sobrecalentado
                   break;
               }else if(s>presion.get(i).getSf() && s<presion.get(i).getSg()){
                   x = (s-presion.get(i).getSf())/(presion.get(i).getSfg()); // Region de saturación
                   break;
               }else if(s==presion.get(i).getSg()){
                   x = 1; // vapor saturado
                   break;
               }else if(s==presion.get(i).getSf())
               {
                   x = 0; // liquido Saturado
               }else{
                   x = -1; //Liquido comprimido
               }
           }else if(p>presion.get(i).getP() && p<presion.get(i+1).getP()){
               double p1 = presion.get(i).getP();
               double p2 = presion.get(i+1).getP();
               double sf1 = presion.get(i).getSf();
               double sf2 = presion.get(i+1).getSf();
               double sfg1 = presion.get(i).getSfg();
               double sfg2 = presion.get(i+1).getSfg();
               double sg1 = presion.get(i).getSg();
               double sg2 = presion.get(i+1).getSg();
               double sf;
               double sfg;
               double sg;
               
               sf = sf1 + (((p-p1)/(p2-p1))*(sf2-sf1));
               sfg = sfg1 + (((p-p1)/(p2-p1))*(sfg2-sfg1));
               sg =  sg1 + (((p-p1)/(p2-p1))*(sg2-sg1)); 
               
               if(s>sg)
               {
                   x = 2; // Vapor sobrecalentado
                   break;
               }else if(s>sf && s<sg){
                   x = (s-sf)/sfg;
               }else if(s==sg){
                   x = 2;
               }else if(s==sf){
                   x = 0;
               }else{
                   x = -1;
               }
               
           }
        }
        return x;
    }
    
    public static double entalpia(double p,double x)
    {
        double h =0;
        int size;
        double hf;
        double hfg;
        size = presion.size();
        if(p>22.063)
        {
            h = -1;
        }else{
            for(int i=0;i<size;i++)
            {
                if(presion.get(i).getP()==p)
                {
                   hf = presion.get(i).getHf();
                   hfg = presion.get(i).getHfg();
                   h = hf + x*hfg;
                   break;
                }else if(p>presion.get(i).getP()&& p<presion.get(i+1).getP()){

                    double hf1 = presion.get(i).getHf();
                    double hf2 = presion.get(i+1).getHf();
                    double p1 = presion.get(i).getP();
                    double p2 = presion.get(i+1).getP();

                    hf = hf1 + (((p-p1)/(p2-p1))*(hf2-hf1));
                    
                    double hfg1 = presion.get(i).getHfg();
                    double hfg2 = presion.get(i+1).getHfg();
                    
                    hfg = hfg1 + (((p-p1)/(p2-p1))*(hfg2-hfg2));
                    h = hf + x*hfg;
                    break;
                }
            }
        }
        
        
        return h;
    }
}
