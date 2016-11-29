
package util;

import java.util.ArrayList;
import modelos.Sobrecalentado;

public class SobreCalentado {
    static Sobrecalentado m1 = new Sobrecalentado();
    
    //static ArrayList<ArrayList<Sobrecalentado>> r = m1.find("8.1");
    
    public static double entalpia(double t,double p)
    {
        ArrayList<ArrayList<Sobrecalentado>> r = m1.find(String.valueOf(p));
        double p1 = -1;
        double p2 = -1;
        double h1=-1;
        double h2=-1;
        double h=-1;
        if(r.size()==2)
        {
            for(int i=0;i<r.size();i++)
            {
                for(int j=0;j<r.get(i).size();j++)
                {
                    
                    if(r.get(i).get(j).getT()==t)
                    {
                        if(i==0)
                        {
                            p1 = r.get(i).get(j).getP();
                            h1 = r.get(i).get(j).getH();
                        }else{
                            p2 = r.get(i).get(j).getP();
                            h2 = r.get(i).get(j).getH();
                        }
                    }else if(t>r.get(i).get(j).getT() && t<r.get(i).get(j+1).getT()){
                        double tj1 = r.get(i).get(j).getT();
                        double tj2 = r.get(i).get(j+1).getT();
                        double hj1= r.get(i).get(j).getH();
                        double hj2= r.get(i).get(j+1).getH();
                        if(i==0)
                        {
                            p1 = r.get(i).get(j).getP();
                            h1 = hj1 + (((t-tj1)/(tj2-tj1))*(hj2-hj1));  
                            
                            
                        }else{
                            p2 = r.get(i).get(j).getP();
                            h2 = hj1 + (((t-tj1)/(tj2-tj1))*(hj2-hj1));
                        }

                    }
                    
                }
            }
        }
        if(p1!=p2)
        {
            h = h1 + (((p-p1)/(p2-p1))*(h2-h1));
        }else{
            h = h1;
        }
        
        return h;
        
    }
    
    public static double entropia(double t,double p)
    {
        ArrayList<ArrayList<Sobrecalentado>> r = m1.find(String.valueOf(p));
        double p1 = -1;
        double p2 = -1;
        double s1=-1;
        double s2=-1;
        double s=-1;
        if(r.size()==2)
        {
            for(int i=0;i<r.size();i++)
            {
                for(int j=0;j<r.get(i).size();j++)
                {
                    
                    if(r.get(i).get(j).getT()==t)
                    {
                        if(i==0)
                        {
                            p1 = r.get(i).get(j).getP();
                            s1 = r.get(i).get(j).getS();
                        }else{
                            p2 = r.get(i).get(j).getP();
                            s2 = r.get(i).get(j).getS();
                        }
                    }else if(t>r.get(i).get(j).getT() && t<r.get(i).get(j+1).getT()){
                        double tj1 = r.get(i).get(j).getT();
                        double tj2 = r.get(i).get(j+1).getT();
                        double sj1= r.get(i).get(j).getS();
                        double sj2= r.get(i).get(j+1).getS();
                        if(i==0)
                        {
                            p1 = r.get(i).get(j).getP();
                            s1 = sj1 + (((t-tj1)/(tj2-tj1))*(sj2-sj1));  
                            
                            
                        }else{
                            p2 = r.get(i).get(j).getP();
                            s2 = sj1 + (((t-tj1)/(tj2-tj1))*(sj2-sj1));
                        }

                    }
                    
                }
            }
        }
        if(p1!=p2)
        {
            s = s1 + (((p-p1)/(p2-p1))*(s2-s1));
        }else{
            s = s1;
        }
        
        return s;
        
    }
    
    public static double temperatura(double s,double p)
    {
        ArrayList<ArrayList<Sobrecalentado>> r = m1.find(String.valueOf(p));
        double p1 = -1;
        double p2 = -1;
        double t1=-1;
        double t2=-1;
        double t=-1;
        if(r.size()==2)
        {
            for(int i=0;i<r.size();i++)
            {
                for(int j=0;j<r.get(i).size();j++)
                {
                    
                    if(r.get(i).get(j).getS()==s)
                    {
                        if(i==0)
                        {
                            p1 = r.get(i).get(j).getP();
                            t1 = r.get(i).get(j).getT();
                        }else{
                            p2 = r.get(i).get(j).getP();
                            t2 = r.get(i).get(j).getT();
                        }
                    }else if(s>r.get(i).get(j).getS() && s<r.get(i).get(j+1).getS()){
                        double sj1 = r.get(i).get(j).getS();
                        double sj2 = r.get(i).get(j+1).getS();
                        double tj1= r.get(i).get(j).getT();
                        double tj2= r.get(i).get(j+1).getT();
                        if(i==0)
                        {
                            p1 = r.get(i).get(j).getP();
                            t1 = tj1 + (((s-sj1)/(sj2-sj1))*(tj2-tj1));  
                            
                            
                        }else{
                            p2 = r.get(i).get(j).getP();
                            t2 = tj1 + (((s-sj1)/(sj2-sj1))*(tj2-tj1));
                        }

                    }
                    
                }
            }
        }
        if(p1!=p2)
        {
            t = t1 + (((p-p1)/(p2-p1))*(t2-t1));
        }else{
            t = t1;
        }
        
        return t;
        
    }
    
    
}
