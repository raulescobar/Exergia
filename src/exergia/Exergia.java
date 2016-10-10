
package exergia;

import controladores.*;
import java.util.Locale;
import modelos.Sustancia;
import vistas.*;


public class Exergia {

    
    public static void main(String[] args) {
        Sustancia sustancia = new Sustancia();
        MainVista vista = new MainVista();
        MainController controlador = new MainController(vista,sustancia);
        
        controlador.render();
        
    }
    
}
