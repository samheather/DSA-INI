package seprini.data;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

/**
 * Loads all the requires art (textures), essentially pre-loading all the
 * required texture for later use
 * 
 * @author Crembo, Miguel
 * 
 */
public class Art {

	private static Hashtable<String, Texture> textureFiles = new Hashtable<String, Texture>();

	/**
	 * A hashtable which stores all of the textures
	 */
	private static Hashtable<String, TextureRegion> textures = new Hashtable<String, TextureRegion>();

	/**
	 * A hashtable which stores all of the sounds
	 */
	private final static Hashtable<String, Sound> sounds = new Hashtable<String, Sound>();

	/**
	 * A skin is loaded from preloaded images in the hashtable textureFiles
	 */
	private final static Skin skin = new Skin();

	// Pre-loading assets for quicker use
	public static void preLoad() {
		textureFiles.put("earth",
				new Texture(Gdx.files.internal("data/earthImages.png"), true));
		textureFiles.put("space",
				new Texture(Gdx.files.internal("data/spaceImages.png"), true));
		textureFiles.put("water",
				new Texture(Gdx.files.internal("data/waterImages.png"), true));
		// load the sound effects
		sounds.put("ding", loadSound("ding.wav"));
		sounds.put("warning", loadSound("warning.mp3"));
		sounds.put("crash", loadSound("crash.mp3"));
		sounds.put("comeflywithme", loadSound("comeflywithme.mp3"));
		sounds.put("ambience", loadSound("ambience.mp3"));
	}

	/**
	 * Initialises loading of texture, should be called once
	 * 
	 */
	public static void load(String theme) {
		textures.clear();
		// loads the whole sprite which consists most of the game's textures
		Texture combined = loadTexture(theme);
		combined.setFilter(TextureFilter.MipMapLinearLinear,
				TextureFilter.Linear);

		// splits up the sprite into parts and loads them into the table

		// split(texture, x coord of image, y coord of image, height, width)
		textures.put("airspace", split(combined, 0, 0, 1079, 720));
		textures.put("sidebar", split(combined, 1080, 0, 200, 720));

		textures.put("aircraft", split(combined, 1281, 0, 72, 63));
		textures.put("fastAircraft", split(combined, 1281 + 72, 0, 72, 63));
		textures.put("slowAircraft", split(combined, 1281 + 72 + 72, 0, 72, 63));
		textures.put("snakeyaircraft",
				split(combined, 1281 + 72 + 72 + 72, 0, 72, 63));
		textures.put("clouds", split(combined, 62, 722, 146 - 62, 756 - 722));
		// CLOUDS 62, 722 -> 146, 756

		textures.put("waypoint", split(combined, 0, 720, 20, 20));

		// space waypoints
		// textures.put("waypoint", split(combined, 21, 720, 34, 20));

		textures.put("menuAircraft", split(combined, 1280, 64, 727, 249));
		textures.put("entrypoint", split(combined, 20, 720, 20, 20));
		textures.put("exitpoint", split(combined, 40, 720, 20, 20));

		// load the default skin
		loadSkin();
	}

	/**
	 * Loads the default skin
	 */
	private static void loadSkin() {
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		// Add the font to the skin; TODO: use a different one?
		BitmapFont font = new BitmapFont();

		skin.add("default", font);

		// Configure a TextButtonStyle and name it "default". Skin resources are
		// stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", 0.07f, 0.1f, 0.22f, 1);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", 0.07f, 0.1f, 0.22f,
				1);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = skin.getFont("default");
		textFieldStyle.background = skin.newDrawable("white", Color.LIGHT_GRAY);
		textFieldStyle.fontColor = Color.BLACK;
		textFieldStyle.selection = skin.newDrawable("white", Color.WHITE);
		textFieldStyle.cursor = skin.newDrawable("white", Color.WHITE);
		skin.add("default", textFieldStyle);

		// Configure a TextButtonStyle and name it "default". Skin resources are
		// stored by type, so this doesn't overwrite the font.
		TextButtonStyle toggleStyle = new TextButtonStyle();
		toggleStyle.up = skin.newDrawable("white", 0.07f, 0.1f, 0.22f, 1);
		toggleStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		toggleStyle.checked = skin.newDrawable("white", Color.BLUE);
		toggleStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		toggleStyle.font = skin.getFont("default");
		skin.add("toggle", toggleStyle);

		// labelStyle with background
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("default");
		labelStyle.fontColor = new Color(0.823f, 0.118f, 0.314f, 1);
		skin.add("default", labelStyle);

		LabelStyle labelStyleBold = new LabelStyle();
		labelStyleBold.font = skin.getFont("default"); // needs to be bold
		labelStyleBold.background = skin.newDrawable("white", Color.CLEAR);
		skin.add("bold", labelStyleBold);

		LabelStyle menuLabels = new LabelStyle();
		menuLabels.font = skin.getFont("default"); // needs to be bold
		menuLabels.background = skin.newDrawable("white", Color.DARK_GRAY);
		skin.add("menuLabel", menuLabels);

		// labelStyle without a background and black text
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = skin.getFont("default");
		textStyle.fontColor = Color.BLACK;
		skin.add("textStyle", textStyle);

	}

	/**
	 * Splits a texture from given position with size and width
	 * 
	 * @param texture
	 * @param x
	 * @param y
	 * @param size
	 * @param width
	 * @return The requested TextureRegion
	 */
	private static TextureRegion split(Texture texture, int x, int y, int size,
			int width) {
		return new TextureRegion(texture, x, y, size, width);
	}

	/**
	 * Wrapper for loading a texture
	 * 
	 * @param textureName
	 * @return Texture
	 */
	private static Texture loadTexture(String textureName) {
		return textureFiles.get(textureName);
	}

	private static Sound loadSound(String soundName) {
		return Gdx.audio.newSound(Gdx.files.internal("sounds/" + soundName));
	}

	/**
	 * Returns a texture region, should be used for all drawing all models
	 * 
	 * @param key
	 * @return the required texture region or null if doesn't exist
	 */
	public static TextureRegion getTextureRegion(String key) {
		if (!textures.containsKey(key))
			return null;

		return textures.get(key);
	}

	/**
	 * Get a sound from the sound pool
	 * 
	 * @param key
	 * @return the required sound or null if key doesn't exist
	 */
	public static Sound getSound(String key) {
		if (!sounds.containsKey(key))
			return null;

		return sounds.get(key);
	}

	public static Skin getSkin() {
		return skin;
	}
}
