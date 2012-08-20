/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entrocorp.pyphygui.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.entrocorp.pyphygui.communication.Communicator;
import org.entrocorp.pyphygui.world.World;

/**
 * Starts the program.  Also used as a hub for communication between the GUI
 * and the communication classes.
 *
 * @author Nick Nigro <nicholasnigro@yahoo.com>
 * Created Aug 20, 2012
 */
public class PyPhyGUI {
    
    // Used to communicate with the python section
    public static final Communicator comm;
    
    // Models the current world containing only the information necessary to render it
    private static World world;
    
    static {
        comm = new Communicator();
        world = new World();
    }
    
    private static ServerSocket server;
    
    private static Socket socket = null;
    
    /**
     * Returns the current world being modeled.
     * 
     * @return The current world.
     */
    public static World getWorld() {
        return world;
    }
    
    
    /**
     * Creates a new world, sets the currently being rendered world to this new
     * world, and returns it.
     * 
     * @return The world that was just created, now the result of calling getWorld().
     */
    public static World newWorld() {
        world = new World();
        return world;
    }
    
    /**
     * Loads the world specified by path, sets the currently being rendered world
     * to the loaded world, and returns it.
     * 
     * @param path The path to the world to load.
     * @return Thw world that was just loaded, now the results of calling getWorld().
     */
    public static World loadWorld(String path) {
        // TODO: implement this
        return new World(); // placeholder
    }
    
    /**
     * Starts up the python section of the program and sets up the GUI.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String os = System.getProperty("os.name").toLowerCase();
        String runPyPhyCommand;
        if (os.indexOf("win") > 0) {
            // Windows
            runPyPhyCommand = "";
        } else {
            // Unix based
            runPyPhyCommand = "";
        }
//        try {
//            Runtime.getRuntime().exec(runPyPhyCommand);
//        } catch (IOException ex) {
//            System.err.println("Failed to run pyPhy using command " + runPyPhyCommand + ".  IOException: " + ex);
//        }
        
        // For testing
        try {
            server = new ServerSocket(5275);
            while (true) {
                socket = server.accept();
                if (socket != null) {
                    break;
                }
            }
            BufferedReader reader = new BufferedReader( new InputStreamReader(socket.getInputStream()));

            String sentText = null;
            while(true) {
                sentText = reader.readLine();
                if (sentText != null) {
                    System.out.println(sentText);
                    break;
                }
            }

            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println("Testing");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}