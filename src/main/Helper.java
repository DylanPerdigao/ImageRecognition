package main;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Provide simple tools to read, write and show pictures.
 */
public final class Helper {
	
	/**
	 * Draws a rectangle over a RGB image
	 * @param r : an integer, the vertical coordinate (col) of the upper left corner.
	 * @param c : an integer, the horizontal coordinate (row) of the upper left corner.
	 * @param w : an integer, the width of the rectangle.
	 * @param h : an integer, the height of the rectangle.
	 * @param dst : a 2D integer array, the RGB image on which to draw the rectangle.
	 * @param color: an integer representing the RBG value of the line color of the rectangle
	 * @param strokeWidth: width of pencil stroke
	 */
	public static void drawBox(int r, int c, int w, int h, int[][] dst, int strokeWidth, int color) {
		if (strokeWidth < 1) strokeWidth = 1;
		for (int row = r; row < r + h && row < dst.length; ++row) {
			for (int col = c; col < c + w && col < dst[0].length; ++col) {
				if (row < r + strokeWidth || row >= r + h - strokeWidth ||
						col < c + strokeWidth || col >= c + w - strokeWidth) {
					dst[row][col] = color;
				}
			}
		}
	}

	/**
	 * Draws a red rectangle over a RGB image
	 * @param r : an integer, the vertical coordinate (col) of the upper left corner.
	 * @param c : an integer, the horizontal coordinate (row) of the upper left corner.
	 * @param w : an integer, the width of the rectangle.
	 * @param h : an integer, the height of the rectangle.
	 * @param dst : a 2D integer array, the RGB image on which to draw the rectangle.
	 */
	public static void drawBox(int r, int c, int w, int h, int[][] dst) {
		drawBox(r, c, w, h, dst, w/15, 255 << 16);
	}

	// Convert specified BufferedImage into an array
	private static int[][] fromBufferedImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] array = new int[height][width];
		for (int row = 0; row < height; ++row) {
			for (int col = 0; col < width; ++col) {
				array[row][col] = image.getRGB(col, row) & 0xffffffff;
			}
		}
		return array;
	}

	// Convert specified array into a BufferedImage
	private static BufferedImage toBufferedImage(int[][] array) {
		int width = array[0].length;
		int height = array.length;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int row = 0; row < height; ++row) {
			for (int col = 0; col < width; ++col) {
				image.setRGB(col, row, array[row][col] | 0xff000000);
			}
		}
		return image;
	}

	/**
	 * Reads specified image from disk.
	 * @param path : a String, the Input file path
	 * @return HxW integer array of packed RGB colors, or <code>null</code> on failure
	 * @see #write
	 */
	public static int[][] read(String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			return fromBufferedImage(image);
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Path: " + path);
			System.exit(1);
			return null;
		}
	}

	/**
	 * Writes specified image to disk.
	 * @param path : a String, the Output file path
	 * @param array HxW array of packed RGB colors
	 * @return {@code true} if write operation was successful, {@code false} otherwise
	 * @see #read
	 */
	public static boolean write(String path, int[][] array) {

		// Convert array to Java image
		BufferedImage image = toBufferedImage(array);

		// Get desired file format
		int index = path.lastIndexOf('.');
		if (index < 0)
			return false;
		String extension = path.substring(index + 1);

		// Export image
		try {
			return ImageIO.write(image, extension, new File(path));
		} catch (IOException e) {
			return false;
		}

	}

	/**
	 * Shows specified image in a window.
	 * @param array : a HxW integer array of packed RGB colors
	 * @param title : a String, the title to be displayed
	 */
	public static void show(int[][] array, String title) {

		// Convert array to Java image
		final BufferedImage image = toBufferedImage(array);

		// Create a panel to render this image
		@SuppressWarnings("serial")
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, Math.max(getWidth(), 100), Math.max(getHeight(), 100), null, null);
			}
		};

		// Create a frame to hold this panel
		final JFrame frame = new JFrame(title);
		frame.add(panel);
        frame.getContentPane().setPreferredSize(new Dimension(Math.max(image.getWidth(), 300), Math.max(image.getHeight(), 300)));
		frame.pack();

		// Register closing event
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.setVisible(false);
				synchronized (frame) {
					frame.notifyAll();
				}
			}
		});

		// Show this frame
		frame.setVisible(true);

		// Wait for close operation
		try {
			synchronized (frame) {
				while (frame.isVisible())
					frame.wait();
			}
		} catch (InterruptedException e) {
			// Empty on purpose
		}
		frame.dispose();
	}

}
