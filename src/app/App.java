package app;

import model.SalaCine;
import model.SalaFactory;
import service.GestorSalas;
import service.SalaQuery;
import service.SalaService;
import service.interfaces.ISalaQuery;
import service.interfaces.ISalaService;
import view.DialogTamanoSala;
import view.MainFrame;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada principal del sistema de gestión de butacas de cine.
 * Es el único lugar autorizado para instanciar clases concretas del backend
 * e inyectarlas en el frontend a través de las interfaces de contrato.
 *
 * <p>Flujo de transición: mientras el equipo frontend implementa el PanelLobby,
 * se conserva el DialogTamanoSala para crear la primera sala. Una vez que el
 * lobby esté listo, este archivo usará directamente {@code gestorSalas} y
 * recibirá la nueva ventana principal sin el diálogo previo.</p>
 */
public class App {

    // INICIO RUTINA: Punto de entrada del sistema
    public static void main(String[] args) {
        // El gestor de salas es la única instancia global del sistema
        GestorSalas gestorSalas = new GestorSalas();

        SwingUtilities.invokeLater(() -> iniciarSistema(gestorSalas));
    }
    // FIN RUTINA: Punto de entrada del sistema

    // PARADIGMA: Orientado a Eventos — invokeLater asegura la ejecución en el EDT
    private static void iniciarSistema(GestorSalas gestorSalas) {
        DialogTamanoSala dialogo = new DialogTamanoSala();
        dialogo.setVisible(true);

        if (!dialogo.isConfirmado()) {
            System.exit(0);
        }

        SalaCine sala = crearSalaDesdeDialogo(dialogo);
        gestorSalas.agregarSala(sala);

        ISalaService servicio = new SalaService(sala);
        ISalaQuery consulta   = new SalaQuery(sala);

        // TODO (frontend): reemplazar MainFrame(servicio, consulta) por
        // MainFrame(gestorSalas) cuando el equipo frontend implemente el lobby.
        MainFrame ventana = new MainFrame(servicio, consulta);
        ventana.setVisible(true);
    }

    // INICIO RUTINA: Creación de sala desde el diálogo de configuración
    private static SalaCine crearSalaDesdeDialogo(DialogTamanoSala dialogo) {
        int filas = dialogo.getFilasSeleccionadas();
        int cols  = dialogo.getColumnasSeleccionadas();
        return SalaFactory.crearSala("Sala Principal", filas * cols, filas, cols);
    }
    // FIN RUTINA: Creación de sala desde el diálogo de configuración
}
