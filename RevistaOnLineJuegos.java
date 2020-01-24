import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

/**
 * La clase representa a una tienda on-line en la
 * que se publican los juegos que se van lanzando al mercado
 * 
 * Un objeto de esta clase guarda en un array los juegos 
 *
 * @author - Yumurdzhan Yalmaz
 */
public class RevistaOnLineJuegos 
{
    private String nombre;
    private Juego[] juegos;
    private int total;

    /**
     * Constructor  
     * Crea el array de juegos al tamaño máximo indicado por la constante
     * e inicializa el resto de atributos
     */
    public RevistaOnLineJuegos(String nombre, int n) {
        juegos = new Juego[n];
        this.nombre = nombre;
        total = 0;
    }

    /**
     * Devuelve true si el array está completo, false en otro caso
     */
    public boolean estaCompleta() {
        return total == juegos.length;
    }

    /**
     *    Añade un nuevo juego solo si el array no está completo y no existe otro juego
     *    ya con el mismo nombre.  Si no se puede añadir se muestra los mensajes adecuados 
     *    (diferentes en cada caso)
     *    
     *    El juego se añade de tal forma que queda insertado en orden alfabético de título
     *    (de menor a mayor)
     *     !!OJO!! No hay que ordenar ni utilizar ningún algoritmo de ordenación
     *    Hay que insertar en orden 
     *    
     */
    public void add(Juego juego) {
        if (!estaCompleta()) {
            int p = existeJuego(juego.getTitulo());
            if (p == -1)             {
                System.arraycopy(juegos, 0, juego, 1, total);
                juegos[0] = juego;   
                total++;
            }
            else {
                System.out.println("Ya existe el juego " + juego.getTitulo());
            }
        }
        else {
            System.out.println("No es posible añadir");
        }

    }

    /**
     * Efectúa una búsqueda en el array del juego cuyo titulo se
     * recibe como parámetro. Es ndiferente mayúsculas y minúsculas
     * Si existe el juego devuelve su posición, si no existe devuelve -1
     */
    public int existeJuego(String titulo) {
        for (int i = 0; i < total; i++) {
            if (juegos[i].getTitulo().equalsIgnoreCase(titulo))  {
                return i;
            }
        }
        return -1;
    }

    /**
     * Representación textual de la revista
     * Utiliza StringBuilder como clase de apoyo.
     * Se incluye el nombre de la  revista on-line.
     * (Ver resultados de ejecución)
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("\nJuegos disponibles en la tienda " +
                nombre + "\n");
        for (int i = 0; i < total; i++)   { 
            sb.append(juegos[i].toString() + "\n");
        }
        return sb.toString();

    }

    /**
     *  Se puntúa el juego de título indicado con 
     *  la puntuación recibida como parámetro. 
     *  La puntuación es un valor entre 1 y 10 (asumimos esto como correcto)
     *  Si el juego no existe se muestra un mensaje en pantalla
     */
    public void puntuar(String titulo, int puntuacion) {
        for (int i = 0; i < total; i++){
            if (juegos[i].getTitulo().equals(titulo)){
                juegos[i].puntuar(puntuacion);
            }
            else {
                System.out.println("El juego no existe");
            }
        }
    }

    /**
     * Devuelve un array con los nombres de los juegos 
     * con una valoración media mayor a la indicada  
     * 
     * El array se devuelve todo en mayúsculas y ordenado ascendentemente
     */
    public String[] valoracionMayorQue(double valoracion) {
        String[] mayor = new String[total];
        int total = 0;
        for (int i = 0; i < total; i++)    {
            if (juegos[i].getValoracionMedia() > valoracion) {
                mayor[i] = juegos[i].getTitulo();
                total++;
            }
        }
        mayor = Arrays.copyOf(mayor,total);
        Arrays.sort(mayor);
        return mayor;
    }

    /**
     * Borrar los juegos del género indicado devolviendo
     * el nº de juegos borradas
     */
    public int borrarDeGenero(Genero genero) {
        int borrados = 0;
        for (int i = 0; i < total; i++) {
            if (juegos[i].getGenero().equals(genero)) {
                juegos[i] = null;
                borrados++;
            }
            else {
                i++;
            }

        }
        return borrados;
    }

    /**
     * Lee de un fichero de texto los datos de los juegos
     * con ayuda de un objeto de la  clase Scanner
     * y los guarda en el array. 
     */
    public void leerDeFichero() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("juegos.txt"));
            while (sc.hasNextLine()) {
                Juego juego = new Juego(sc.nextLine());
                this.add(juego);

            }

        } catch (IOException e) {
            System.out.println("Error al leer del fichero");
        } finally {
            sc.close();
        }

    }

}
