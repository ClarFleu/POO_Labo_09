import chess.ChessController;
import chess.ChessView;
import chess.views.console.ConsoleView;
import chess.views.gui.GUIView;
import engine.Game;

public class Launcher {
    public static void main(String[] args) {
        // 1. Création du contrôleur pour gérer le jeu d’échec
        ChessController controller = new Game(); // Instancier un ChessController

        // 2. Création de la vue
        ChessView view = new GUIView(controller); // mode GUI
        //ChessView view = new ConsoleView(controller); // mode Console

        // 3 . Lancement du programme.
        controller.start(view);
        view.startView();
        test1(controller);
    }

    public static void test1(ChessController c) {
        System.out.println(c.move(4, 1, 4, 3));
        System.out.println(c.move(4, 6, 4, 6));
        System.out.println(c.move(4, 6, 4, 6));
        System.out.println(c.move(7, 6, 7, 5));
        System.out.println(c.move(4, 3, 4, 4));
        System.out.println(c.move(6, 0, 7, 2));
        System.out.println(c.move(6, 7, 5, 5));
        System.out.println(c.move(4, 4, 4, 5));
        System.out.println(c.move(6, 6, 6, 5));
        System.out.println(c.move(6, 0, 7, 2));
        System.out.println(c.move(5, 7, 6, 6));
        System.out.println(c.move(5, 0, 3, 2));
        System.out.println(c.move(4, 7, 6, 7));
        System.out.println(c.move(4, 0, 6, 0));
        System.out.println(c.move(3, 2, 4, 3));
        System.out.println(c.move(5, 5, 4, 3));
        System.out.println(c.move(3, 0, 6, 3));

    }
    public static void test2(ChessController c) {
        System.out.println(c.move(4, 1, 4, 3));
        System.out.println(c.move(4, 6, 4, 6));
        System.out.println(c.move(4, 6, 4, 6));
        System.out.println(c.move(7, 6, 7, 5));
        System.out.println(c.move(4, 3, 4, 4));
        System.out.println(c.move(6, 0, 7, 2));
        System.out.println(c.move(6, 7, 5, 5));
        System.out.println(c.move(4, 4, 4, 5));
        /*System.out.println(c.move(4, 6, 4, 4));
        System.out.println(c.move(5, 6, 4, 5));
        System.out.println(c.move(5, 0, 1, 4));
        System.out.println(c.move(3, 6, 3, 5));
        System.out.println(c.move(2, 6, 2, 5));
        System.out.println(c.move(5, 5, 3, 4));
        System.out.println(c.move(4, 5, 4, 4));
        System.out.println(c.move(7, 5, 7, 4));
        System.out.println(c.move(4, 7, 5, 6));
        System.out.println(c.move(6, 0, 7, 2));
        System.out.println(c.move(5, 6, 6, 7));
        System.out.println(c.move(4, 0, 6, 0));*/
    }
}
