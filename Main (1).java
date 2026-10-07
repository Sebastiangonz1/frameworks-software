import java.util.Random;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Random rnd = new Random();

    static int agua, comida, energia, oro, dia;

    public static void main(String[] args) {
        boolean jugarDeNuevo = true;
        while (jugarDeNuevo) {
            jugar();
            jugarDeNuevo = preguntarJugarDeNuevo();
        }
        System.out.println("Gracias por jugar!");
    }

    static void reiniciar() {
        agua = 100;
        comida = 100;
        energia = 100;
        oro = 0;
        dia = 1;
    }

    static void jugar() {
        reiniciar();
        System.out.println("=== EL RETO DEL EXPLORADOR DEL DESIERTO ===");
        System.out.println("Sobrevive 10 dias para llegar al oasis.\n");

        while (dia <= 10) {
            mostrarEstado();
            boolean diaCompletado = false;

            // No avanza el dia hasta que la accion sea valida
            while (!diaCompletado) {
                mostrarMenu();
                int opcion = leerOpcion();

                switch (opcion) {
                    case 1:
                        explorar();
                        diaCompletado = true;
                        break;
                    case 2:
                        descansar();
                        diaCompletado = true;
                        break;
                    case 3:
                        racionar();
                        diaCompletado = true;
                        break;
                    case 4:
                        diaCompletado = comprar();
                        break;
                }
            }

            limitarRecursos();

            String agotado = recursoAgotado();
            if (agotado != null) {
                System.out.println("\nHas muerto en el desierto en el dia " + dia + ".");
                System.out.println("Se agoto tu " + agotado + ".");
                return;
            }

            dia++;
            System.out.println();
        }

        // Victoria
        int puntaje = (agua + comida + energia) + (oro * 2);
        System.out.println("=== ESTADO FINAL ===");
        System.out.println("Agua: " + agua + " | Comida: " + comida
                + " | Energia: " + energia + " | Oro: " + oro);
        System.out.println("\nLlegaste al oasis!");
        System.out.println("Puntaje final: " + puntaje);
    }

    static void mostrarEstado() {
        System.out.println("=== DIA " + dia + " ===");
        System.out.println("Agua: " + agua + " | Comida: " + comida
                + " | Energia: " + energia + " | Oro: " + oro);
    }

    static void mostrarMenu() {
        System.out.println("Elige una opcion:");
        System.out.println("1. Explorar");
        System.out.println("2. Descansar");
        System.out.println("3. Racionar");
        System.out.println("4. Comprar en oasis");
    }

    // Valida que la opcion sea un numero del 1 al 4
    static int leerOpcion() {
        while (true) {
            System.out.print("Opcion: ");
            String linea = sc.nextLine().trim();
            try {
                int op = Integer.parseInt(linea);
                if (op >= 1 && op <= 4) {
                    return op;
                }
            } catch (NumberFormatException e) {
                // cae al mensaje de error
            }
            System.out.println("Opcion invalida. Ingresa un numero del 1 al 4.");
        }
    }

    static void explorar() {
        agua -= 15;
        comida -= 10;
        energia -= 20;

        int encontrado = rnd.nextInt(16); // 0 a 15
        oro += encontrado;
        System.out.println("Encontraste " + encontrado + " de oro mientras explorabas.");

        if (rnd.nextInt(100) < 30) { // 30% de probabilidad
            energia -= 10;
            System.out.println("Una tormenta de arena te sorprendio! Energia -10 extra.");
        }
    }

    static void descansar() {
        agua -= 10;
        comida -= 10;
        energia += 25;
        System.out.println("Descansaste. Recuperaste 25 de energia pero gastaste agua y comida.");
    }

    static void racionar() {
        agua -= 5;
        comida -= 5;
        energia -= 5;
        System.out.println("Racionaste tus recursos. Consumo minimo, sin recuperacion.");
    }

    // Devuelve true si la compra se realizo (el dia avanza), false si no
    static boolean comprar() {
        if (oro < 10) {
            System.out.println("No tienes suficiente oro");
            return false;
        }

        System.out.println("Tienes " + oro + " de oro. Cada compra cuesta 10 de oro.");
        System.out.println("Que deseas comprar?");
        System.out.println("1. +20 de agua");
        System.out.println("2. +20 de comida");

        while (true) {
            System.out.print("Opcion: ");
            String linea = sc.nextLine().trim();
            if (linea.equals("1")) {
                oro -= 10;
                agua += 20;
                System.out.println("Compraste 20 de agua.");
                return true;
            } else if (linea.equals("2")) {
                oro -= 10;
                comida += 20;
                System.out.println("Compraste 20 de comida.");
                return true;
            }
            System.out.println("Opcion invalida. Elige 1 o 2.");
        }
    }

    // Ningun recurso baja de 0 ni sube de 100
    static void limitarRecursos() {
        agua = Math.max(0, Math.min(100, agua));
        comida = Math.max(0, Math.min(100, comida));
        energia = Math.max(0, Math.min(100, energia));
    }

    // Devuelve el nombre del recurso agotado, o null si todos estan bien
    static String recursoAgotado() {
        if (agua == 0) return "agua";
        if (comida == 0) return "comida";
        if (energia == 0) return "energia";
        return null;
    }

    static boolean preguntarJugarDeNuevo() {
        while (true) {
            System.out.print("\nDeseas jugar de nuevo? (S/N): ");
            String r = sc.nextLine().trim();
            if (r.equalsIgnoreCase("S")) {
                System.out.println();
                return true;
            } else if (r.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Responde S o N.");
        }
    }
}
