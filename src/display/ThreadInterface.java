package display;

import graph.Graph;
import landmark.Landmark;

public class ThreadInterface extends Thread{

    /** La classe principale de cette classe */
    private Window window;

    /** Booléen show */
    public boolean show;

    /** Constructeur */
    public ThreadInterface(Landmark landmark, Graph graph){
        window = new Window(landmark, graph);
        this.show = true;
    }

    /** Fonction principale */
    @Override
    public void run(){
        while(this.show){
            try {
                window.repaint();
                Thread.sleep(16);
            }catch (InterruptedException e){
                e.printStackTrace();
                System.err.println("Mauvaise gestion des Threads");
            }
        }
    }

    /** Getters & Setters */
    public Window getWindow() {
        return window;
    }
}
