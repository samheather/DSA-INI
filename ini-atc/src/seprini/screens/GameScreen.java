package seprini.screens;

import seprini.controllers.AircraftController;
import seprini.data.Art;
import seprini.data.Config;
import seprini.data.GameDifficulty;
import seprini.data.State;
import seprini.models.Airspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * The game screen - all game logic starts here
 * 
 * @author Paulius, Miguel
 * 
 */
public class GameScreen extends Screen {

	private final Stage root;
	private final AircraftController controller;

	public GameScreen(GameDifficulty diff) {

		// create a table layout, main ui
		Table ui = new Table();

		// create a separate layout for sidebar with all the buttons and
		// required info
		Table sidebar = new Table();

		if (Config.DEBUG_UI)
			sidebar.debug();

		// create and add the Airspace group, contains aircraft and waypoints
		Airspace airspace = new Airspace();

		controller = new AircraftController(diff, airspace, sidebar, this);
		root = new Stage();

		root.setKeyboardFocus(airspace);

		Gdx.input.setInputProcessor(root);

		// make it fill the whole screen
		ui.setFillParent(true);
		root.addActor(ui);

		airspace.addListener(controller);
		ui.add(airspace).width(Config.AIRSPACE_SIZE.x)
				.height(Config.AIRSPACE_SIZE.y);

		// Temporary background creator for sidebar
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(new Color(0.31f, 0.33f, 0.32f, 1));
		pixmap.fill();

		// set the temporary background
		sidebar.setBackground(new TextureRegionDrawable(Art.getTextureRegion("sidebar")));

		// move the sidebar to the top right, add it to the main table and set
		// its size
		ui.add(sidebar).width(Config.SIDEBAR_SIZE.x)
				.height(Config.SIDEBAR_SIZE.y);

		long id = Art.getSound("ambience").play(0.7f);
		Art.getSound("ambience").setLooping(id, true);
	}

	@Override
	public void render() {
		// draw every actor on the stage
		root.draw();

		// debug the ui and draw fps
		if (Config.DEBUG_UI) {
			Table.drawDebug(root);
			drawString("fps: " + Gdx.graphics.getFramesPerSecond(), 10, 20,
					Color.BLACK, root.getSpriteBatch(), false, 1);
		}
	}

	@Override
	public void update() {
		if (State.paused)
			return;

		// increment global clock
		State.tick();

		controller.update();
		root.act();
	}

	@Override
	public void removed() {
		root.dispose();
	}

}
