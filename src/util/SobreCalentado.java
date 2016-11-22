
package util;

import java.util.ArrayList;
import modelos.Sobrecalentado;

public class SobreCalentado {
    static Sobrecalentado m1 = new Sobrecalentado();
    static ArrayList<ArrayList<Sobrecalentado>> r = m1.find("8.1");
    
    public static void listar()
    {
        System.out.println(r.size());
        if(r.size()==2)
        {
            for(int i=0;i<r.size();i++)
            {
                for(int j=0;j<r.get(i).size();j++)
                {
                    System.out.println(r.get(i).get(j).getT1());
                }
            }
        }
//        for(int i=0;i<r.size();i++)
//        {
//            System.out.println(r.get(i).getT1());
//            System.out.println(r.get(i).getT2());
//        }
    }
}
