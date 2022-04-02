import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;

    private BufferedImage level = null;

    /**
     * Create a window of the game including the game handler.
     * Set camera focus on the player.
     * Add key and mouse listener for player's input.
     * Load the image of a level from resources.
     */
    public Game() {
        new Window(1000, 563, "Wizard Game", this);
        start();

        handler = new Handler();
        camera = new Camera(0, 0);

        this.addKeyListener(new KeyInput(handler));     //listening for key input
        this.addMouseListener(new MouseInput(handler, camera));     //listening for mouse input

        ImageLoader loader = new ImageLoader();
        level = loader.loadImage("/lvl1.png");

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
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;

            while(delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
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
        for(int i = 0; i < handler.object.size(); i++) {    //finding out which object is the player
            if(handler.object.get(i).getId() == ID.Player) {
                camera.tick(handler.object.get(i));
            }
        }

        handler.tick();
    }

    /**
     * Preload 3 frames.
     * Setting the graphic design of the background and rendering game objects.
     */
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);      //preloading 3 frames
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        //graphic design
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 563);

        g2d.translate(-camera.getX(), -camera.getY());

        handler.render(g);  // render objects after rendering the bg

        g2d.translate(camera.getX(), camera.getY());
        //
        g.dispose();
        bs.show();
    }

    /**
     * Translate the level's image.
     * Determine which game object is the player and simple block using RGB.
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

            if(blue == 255) handler.addObject(new Block(xx*32, yy*32, ID.Block));

            if(green == 255) handler.addObject(new Wizard(xx*32, yy*32, ID.Player, handler));

            }
        }
    }

    /**
     * Initialize the game.
     * @param args
     */
    public static void main(String args[]) {
        new Game();
    }
}
