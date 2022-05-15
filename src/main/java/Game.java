import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends Canvas implements Serializable, Runnable {

    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    private static final long serialVersionUID = 1L;

    private boolean isRunning = false;
    transient private Thread thread;
    private Handler handler;
    transient private final Camera camera;
    private final Sheet sh;
    public Wizard ply;

    transient private final BufferedImage level;
    transient private final BufferedImage sheet;
    transient private final BufferedImage floor;


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
     * Create a window of the game including new game handler and camera.
     * Add key and mouse listener for player's input.
     * Load the image of level and image of all game objects from declared resources.
     * Get only image of floor using method 'grab'.
     */
    public Game() {
        new Window(1000, 563, "Enchanted", this);
        start();

        handler = new Handler();
        camera = new Camera(0, 0);

        this.addKeyListener(new KeyInput(handler));     //listening for key input

        ImageLoader loader = new ImageLoader();


        level = loader.loadImage("/lvl1.png");
        sheet = loader.loadImage("/all.png");

        sh = new Sheet(sheet);

        floor = sh.grab(4, 2, 32, 32);

        this.addMouseListener(new MouseInput(handler, camera, this, sh));     //listening for mouse input

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
     * If wizard collides with the door and already has a 'door key' in inventory, change the game state to victory.
     * If wizard has zero health, remove objects from game handler and change the game state to defeat.
     * If player wants to save/load game, try to serialize/deserialize the game handler.
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
                LOGGER.log(Level.INFO,"Objects have been successfully serialized");
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING,"Objects could not have been serialized");
                ex.printStackTrace();
            }
            gameState = STATE.Game;
        }

        // Deserialization
        if (gameState == STATE.Load) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.ser"));
                Handler hdlr = (Handler)in.readObject();
                this.handler = hdlr;

                for(GameObject ob : handler.objects) {
                    if(ob.getId() == ID.Player) {
                        ply = (Wizard)ob;
                        LOGGER.log(Level.INFO,"Game object 'wizard' has been found");
                    }
                }

                this.addKeyListener(new KeyInput(handler));
                this.addMouseListener(new MouseInput(handler, camera, this, sh));

                gameState = STATE.Game;

                LOGGER.log(Level.INFO,"Objects have been successfully deserialized");

            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.WARNING,"Desired file has not been found");
                System.exit(0);
            }
        }
    }

    /**
     * Preload 3 frames.
     * Setting the graphic design of the background and rendering game objects.
     * Rendering health bar, amount of mana and player's inventory.
     */
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);      //preloading 3 frames
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        //graphic design////////////
        if (gameState == STATE.Menu) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1000, 563);
            g.setColor(Color.WHITE);
            Font f = new Font("Papyrus", 5, 70);
            g.setFont(f);
            g.drawString("ENCHANTED", 175, 120);
        }

        if (gameState == STATE.Game) {


            g2d.translate(-camera.getX(), -camera.getY());

            for (int xx = 0; xx < 30 * 72; xx += 32) {               //render floor
                for (int yy = 0; yy < 30 * 72; yy += 32) {
                    g.drawImage(floor, xx, yy, null);
                }
            }

            handler.render(g);  // render objects after rendering the floor bg


            g2d.translate(camera.getX(), camera.getY());

            g.setColor(Color.GRAY);
            g.fillRect(5, 5, 200, 32);
            g.setColor(Color.GREEN);
            g.fillRect(5, 5, ply.hp * 2, 32);
            g.setColor(Color.BLACK);        //outline of the health bar
            g.drawRect(5, 5, 200, 32);

            g.setColor(Color.WHITE);
            g.drawString("Mana: " + ply.mana, 5, 50);

            String inv = "Inventory: ";
            for(int i=0; i< ply.inventory.size(); i++) {
                String current = ply.inventory.get(i);
                inv += current;
            }
            g.drawString(inv, 100, 50);

            g.drawRect(400, 5, 200, 20);
            g.fillRect(400, 5, 200, 20);
            g.setColor(Color.BLACK);
            g.drawString("Save game", 470, 20);
        }


        if (gameState == STATE.Defeat || gameState == STATE.Victory) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1000, 563);
            Font f = new Font("Papyrus", 5, 70);
            g.setFont(f);


            if (gameState == STATE.Defeat) {
                g.setColor(Color.RED);
                g.drawString("YOU DIED", 255, 220);
            } else {
                g.setColor(Color.GREEN);
                g.drawString("VICTORY", 290, 220);
            }

            g.setColor(Color.WHITE);
            Font f2 = new Font("Papyrus", 5, 45);
            g.setFont(f2);
            g.drawRect(390, 300, 200, 64);
            g.drawString("Exit", 445, 345);
        }

        ///////////////////////////
        g.dispose();
        bs.show();
    }

    /**
     * Translate the level's image.
     * Determine which game object is which by using RGB.
     *
     * @param image
     */
    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; w > xx; xx++) {
            for (int yy = 0; h > yy; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (blue == 255 && green == 38) handler.addObject(new Block(xx * 32, yy * 32, ID.Block, sh));

                if (green == 255 && blue == 33) {
                    ply = new Wizard(xx * 32, yy * 32, ID.Player, handler, this, sh);
                    handler.addObject(ply);
                }

                if (red == 255) handler.addObject(new Enemy(xx * 32, yy * 32, ID.Enemy, handler, sh));

                if (green == 255 && blue == 255) handler.addObject(new Chest(xx * 32, yy * 32, ID.Chest, sh));

                if (red == 255 && blue == 255) handler.addObject(new Door(xx * 32, yy * 32, ID.Door, sh));
            }
        }
    }


    public static void main(String[] args) {
        new Game();
    }
}





