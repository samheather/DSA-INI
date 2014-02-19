package seprini.controllers;

import java.util.HashMap;

import seprini.data.Art;
import seprini.data.GameDifficulty;
import seprini.models.LeaderboardEntry;
import seprini.screens.GameScreen;
import seprini.screens.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.esotericsoftware.tablelayout.Cell;

/**
 * Menu Controller
 * 
 * @author Crembo
 * 
 */
public final class MenuController extends ChangeListener implements Controller {

	private final Table ui;
	private final MenuScreen screen;
	private final Leaderboard lb = new Leaderboard();
	public String theme = "earth";
	public String airportMsg;
	public String planeMsg;
	

	public void addLeaderboardEntry(String name, double score) {
		lb.addLeaderboardEntry(name, score);
	}

	private HashMap<String, TextButton> buttons;

	/**
	 * Initiate variables, set the screen and ui, add all of the buttons to the
	 * screen
	 * 
	 * @param screen
	 *            required so the controller could change the screen after
	 *            clicking the appropriate button
	 * @param ui
	 *            required for actually adding the buttons
	 */
	public MenuController(MenuScreen screen, Table ui) {
		this.ui = ui;
		this.screen = screen;
		buttons = new HashMap<String, TextButton>();

		// ui.setLayoutEnabled(false);

		addButtons();

		// ui.toFront();
		ui.row();
		ui.padTop(230);
		// ui.setHeight(50);
		ui.row().padTop(80);
		addLeaderboard();
		ui.row();
		
		setEarth();

	}

	/**
	 * Adds the leaderboard to the screen.
	 */
	private void addLeaderboard() {
		Table leaderboardEntries = new Table(Art.getSkin());
		// TODO remove bold
		leaderboardEntries.add(new Label("---Leaderboard---", Art.getSkin()));
		leaderboardEntries.row();
		for (LeaderboardEntry l : lb.leaderboardEntries) {
			leaderboardEntries.add(new Label(" " + l.getName() + ": "
					+ (int) l.getScore() + " ", Art.getSkin()));
			leaderboardEntries.row();
		}

		Cell<?> c = ui.add(leaderboardEntries);
		c.colspan(4).center();

	}

	/**
	 * Adds all the buttons on the screen with their own listener
	 */
	private void addButtons() {
		// Create a label for difficulty and add it
		Label difficultylabel = new Label("Difficulty:", Art.getSkin(), "menuLabel");
		ui.add(difficultylabel).width(100).center();

		// create a button to start the game in easy mode
		addButton("startEasy", "Easy", this).width(100);

		// create a button to start the game in medium mode
		addButton("startMedium", "Medium", this).width(100);

		// create a button to start the game in hard mode
		addButton("startHard", "Hard", this).width(100);

		// create a new row
		ui.row();
		
		ui.add(new Label("Themes:", Art.getSkin(), "menuLabel")).width(100).center();
		
		addButton("earth", "Aircraft", this).width(100);
		
		addButton("space", "Spacecraft", this).width(100);
		
		addButton("water", "Submarines", this).width(100);
		
		ui.row();

		// create the Exit button
		addButton("exit", "Exit", this).width(200).colspan(4);
	}

	/**
	 * Convenience method to add a button to the UI
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	private Cell<?> addButton(String name, String text, ChangeListener listener) {
		TextButton button = new TextButton(text, Art.getSkin());
		buttons.put(name, button);
		button.addListener(listener);

		return ui.add(button);
	}
	
	private void setEarth() {
		ui.padTop(230);
		Art.load("earth");
		theme = "earth";
		airportMsg = " Planes in airport : ";
		planeMsg = "You have snakes in the plane!";
	}

	/**
	 * Handles what happens once one of the buttons have been clicked
	 */
	@Override
	public void changed(ChangeEvent event, Actor actor) {

		// stop the anthem after entering the game
		Art.getSkin().getFont("default").setScale(1f);

		// Pass difficulty to the newly created GameScreen so the game can
		// change variables depending on it
		if (actor.equals(buttons.get("startEasy"))) {
			screen.setScreen(new GameScreen(GameDifficulty.EASY, this));
			Art.getSound("comeflywithme").stop();
		}

		if (actor.equals(buttons.get("startMedium"))) {
			screen.setScreen(new GameScreen(GameDifficulty.MEDIUM, this));
			Art.getSound("comeflywithme").stop();
		}

		if (actor.equals(buttons.get("startHard"))) {
			screen.setScreen(new GameScreen(GameDifficulty.HARD, this));
			Art.getSound("comeflywithme").stop();
		}

		if (actor.equals(buttons.get("exit"))){
			Gdx.app.exit();
		}
		
		if (actor.equals(buttons.get("earth"))){
			setEarth();
		}
		
		if (actor.equals(buttons.get("space"))){
			ui.padTop(230);
			Art.load("space");
			theme = "space";
			airportMsg = " Spaceships in spacestation : ";
			planeMsg = "Space snakes attack again!";
		}
		
		if (actor.equals(buttons.get("water"))){
			ui.padTop(230);
			Art.load("water");
			theme = "water";
			airportMsg =  " Submarines in mothership : ";
			planeMsg = "The shark has appeared!";
		}
	}
	
}
