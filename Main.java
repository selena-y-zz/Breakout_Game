import main.java.Breakout;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Breakout game;
                if (args.length == 0) {
                    game = new Breakout();
                } else {
                    game = new Breakout(args[0], args[1]);
                }
            }
        });
    }
}
