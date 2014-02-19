package seprini.screens;

import seprini.controllers.MenuController;
import seprini.data.Art;
import seprini.data.Config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Menu Screen, displays the menu with buttons to start and exit the game
 * 
 * @author Paulius, Miguel
 * 
 */
public class MenuScreen extends Screen {

	/**
	 * The stage for this screen
	 */
	private final Stage root;
	private final Table ui;

	/**
	 * Initialises the input handler, stage and create the layout with buttons
	 * for the menu screen
	 */
	public MenuScreen() {

		// create a stage and set it as the input processor
		root = new Stage();
		Gdx.input.setInputProcessor(root);

		// create a table layout
		ui = new Table();

		// creator the controller for this screen, this will handle basically
		// everything for this screen, including input and creation of buttons
		new MenuController(this, ui);

		// make it fill the whole screen
		ui.setFillParent(true);

		// add the table UI
		root.addActor(ui);

		Art.getSound("comeflywithme").play(1f);
	}

	@Override
	public void render() {
		root.draw();

		drawString(Config.COPYRIGHT_NOTICE, 10, 10, Color.BLACK,
				root.getSpriteBatch(), false, 0.5f);

		draw(Art.getTextureRegion("menuAircraft"), 300, 390,
				root.getSpriteBatch());

	}

	@Override
	public void update() {
		root.act();
	}

	/**
	 * Called on change of screen, disposes of unneeded resources (ie. prevents
	 * a memory leak)
	 */
	@Override
	public void removed() {
		root.dispose();
	}

}
