import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Logger;

public class Game extends Canvas implements Serializable, Runnable {

    protected static final Logger LOGGER = Logger.getLogger(Game.class.getName() );

    private static final long serialVersionUID = 1L;

    private boolean isRunning = false;
    transient private Thread thread;
    protected Handler handler;
    protected transient final Camera camera;

    public Wizard ply;


    transient private final BufferedImage level;


    public enum STATE {
        Menu,
        Game,
        Defeat,
        Victory,
        Save,
        Load
    }


    public STATE gameState = STATE.Menu;

    /**
     * Create a window of the game including the game handler.
     * Set camera focus on the player.
     * Add key and mouse listener for player's input.
     * Load the image of a level from resources.
     */
    public Game() {
        new Window(1000, 563, "Enchanted", this);
        start();

        handler = new Handler();
        camera = new Camera(0, 0);

        this.addKeyListener(new KeyInput(handler));     //listening for key input

        ImageLoader loader = new ImageLoader();


        level = loader.loadImage("/lvl1.png");

        this.addMouseListener(new MouseInput(handler, camera, this));     //listening for mouse input

        loadLevel(level);
    }

    /**
     * Start the game on new thread.
     */
    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stop running the game after ensuring that the thread has been terminated.
     */
    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Update the game window 60 times per second.
     */
    public void run() {     //updating game window 60times per second, rendering couple 1000times per second
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    /**
     * Supporting method for updating the game.
     * Find out which game object is the player and update on it the focus of the camera.
     */
    public void tick() {    //updating game
        handler.tick();

        if (gameState == STATE.Game) {
            for (int i = 0; i < handler.objects.size(); i++) {    //finding out which object is the player
                if (handler.objects.get(i).getId() == ID.Player) {
                    camera.tick(handler.objects.get(i));

                }


                if (handler.objects.get(i).getId() == ID.Door) {
                    if (ply.getBounds().intersects(handler.objects.get(i).getBounds()) && ply.inventory.contains("Door key")) {
                        //announce the player as a winner
                        gameState = Game.STATE.Victory;
                    }
                }

            }
            if (ply.hp <= 0) {
                handler.objects.clear();
                gameState = STATE.Defeat;
            }


        }


        if (gameState == STATE.Save) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.ser"));
                out.writeObject(this.handler);
                out.close();
                System.out.println("Object has been serialized");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            gameState = STATE.Game;
        }

        // Deserialization
        if (gameState == STATE.Load) {
            try {
                //BufferedImage wizImg = sh.grab(1, 1, 32, 48);

                ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.ser"));
                Handler hdlr = (Handler)in.readObject();
                this.handler = hdlr;

                for(GameObject ob : handler.objects) {
                    if(ob.getId() == ID.Player) {
                        ply = (Wizard)ob;
                    }
                }

                this.addKeyListener(new KeyInput(handler));     //listening for key input
                this.addMouseListener(new MouseInput(handler, camera, this));


                gameState = STATE.Game;

                System.out.println("Object has been deserialized");

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            }
        }
    }

    /**
     * Preload 3 frames.
     * Setting the graphic design of the background and rendering game objects.
     */
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);      //preloading 3 frames
            return;
        }
        bs.show();
    }

    /**
     * Translate the level's image.
     * Determine which game object is the player and simple block using RGB.
     *
     * @param image
     */
    protected void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; w > xx; xx++) {
            for (int yy = 0; h > yy; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (blue == 255 && green == 38) handler.addObject(new Block(xx * 32, yy * 32, ID.Block));

                if (green == 255 && blue == 33) {
                    ply = new Wizard(xx * 32, yy * 32, ID.Player, handler, this);
                    handler.addObject(ply);
                }

                if (red == 255) handler.addObject(new Enemy(xx * 32, yy * 32, ID.Enemy, handler));

                if (green == 255 && blue == 255) handler.addObject(new Chest(xx * 32, yy * 32, ID.Chest));

                if (red == 255 && blue == 255) handler.addObject(new Door(xx * 32, yy * 32, ID.Door));

            }
        }
    }


    /**
     * Initialize the game.
     *
     * @param args
     */
    public static void main(String[] args) {
        new Game();

    }

}





