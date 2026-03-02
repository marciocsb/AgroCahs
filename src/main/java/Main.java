import gui.TelaPrincipal;
import model.AgroController;

public class Main {
    public static void main(String[] args) {
        AgroController controller = new AgroController();
        (new TelaPrincipal(controller)).setVisible(true);
    }
}