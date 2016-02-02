import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;



import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;

/**
 *
 * @author Gian Nipitella
 * Follow Steps By Jarroba
 */
public class GetQuotes {

    /**
     * @param args the command line arguments
     */
    public static final String url = "http://www.frasedehoy.com/frase/";
	
    public static void main (String args[]) {
        ArrayList<citas> c;
        c = new ArrayList<citas>();
        
        
	Gson gson = new Gson();
        
        for (int i = 1; i <= 6253; i++) {
           c.add(corriendo(url, i));
            System.out.println(String.valueOf(i));
           
        }
        
        // convert java object to JSON format,
	// and returned as JSON formatted string
	String json = gson.toJson(c);
        
        try {
		//write converted json data to a file named "file.json"
		FileWriter writer = new FileWriter("C:\\Users\\CGA\\Documents\\Proyectos\\freecodecamp\\Random Quotes Machine\\Citas.json");
		writer.write(json);
		writer.close();

	} catch (IOException e) {
		e.printStackTrace();
	}

	System.out.println(json);
        
        
        
//            for (citas c1 : c) {
//                System.out.println("url="+c1.getUrl());
//                System.out.println("qoute="+c1.getCita() );
//                System.out.println("Autor="+c1.getAutor().replace("—", "") );
//                
//        }
    }

    public static Document getHtmlDocument(String url) {

    Document doc = null;
	try {
	    doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
	    } catch (IOException ex) {
		System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
	    }
    return doc;
}
    
    public static int getStatusConnectionCode(String url) {
		
    Response response = null;
	
    try {
	response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
    } catch (IOException ex) {
	System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
    }
    return response.statusCode();
}

    
    public static citas corriendo(String url, int n){
        
	 citas a = null;
        // Compruebo si me da un 200 al hacer la petición
        if (getStatusConnectionCode(url+String.valueOf(n)) == 200) {
			
            // Obtengo el HTML de la web en un objeto Document
            Document document = getHtmlDocument(url+String.valueOf(n));
			
            // Busco todas las entradas que estan dentro de: 
            Elements entradas = document.select("div.col-md-10.col-xs-12").not("div.col-md-offset-2.col-md-4.col-xs-12");
            		
            // Paseo cada una de las entradas
            for (Element elem : entradas) {
               
                String titulo = elem.getElementById("frase").text();
                String autor = elem.getElementById("autor").text().replace("—", "");
                a = new citas(titulo,autor, url+String.valueOf(n));

                //String fecha = elem.getElementsByClass("fecha").text();
		
                //System.out.println(titulo+"\n"+autor+"\n"+"\n\n");
				
                // Con el método "text()" obtengo el contenido que hay dentro de las etiquetas HTML
                // Con el método "toString()" obtengo todo el HTML con etiquetas incluidas
            }
				
        }else
            System.out.println("El Status Code no es OK es: "+getStatusConnectionCode(url));
    
        return a;
                }
        
    
    public static class citas{
    private String Cita;
    private String Autor;
    private String url;

        public citas(String Cita, String Autor, String url) {
            this.Cita = Cita;
            this.Autor = Autor;
            this.url = url;
        }
        public citas(String Cita, String Autor) {
            this.Cita = Cita;
            this.Autor = Autor;
        }


        public String getAutor() {
            return Autor;
        }

        public void setAutor(String Autor) {
            this.Autor = Autor;
        }

        public String getCita() {
            return Cita;
        }

        public void setCita(String Cita) {
            this.Cita = Cita;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    
        
    }
    
}
