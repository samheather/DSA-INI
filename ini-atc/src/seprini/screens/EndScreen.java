package seprini.screens;

import seprini.data.Art;
import seprini.data.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class EndScreen extends Screen {

	private final Stage root;

	public EndScreen() {
		root = new Stage();
		Gdx.input.setInputProcessor(root);

		Table ui = new Table();

		ui.setFillParent(true);
		root.addActor(ui);

		ui.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == Keys.ESCAPE)
					setScreen(new MenuScreen());

				return false;
			}
		});

		root.setKeyboardFocus(ui);

		Art.getSkin().getFont("default").setScale(1f);
		System.out.println(Math.round(State.time()));
		Label text = new Label(
				"You have failed.\n"
						+ "Two aeroplanes have collided mid-flight in a huge crash which resulted in the death of many innocent people.\n"
						+ "However, surprisingly, you managed to avoid a crash for exactly "
						+ Math.round(State.time())
						+ " seconds, which is respectable (at least by some standards).\n"
						+ "\n"
						+ "\nPRESS ESC TO RETURN TO MAIN MENU ",
				Art.getSkin(), "textStyle");
		
		
		ui.add(text).center();

		ui.row();
		
		Table t = new Table();
		
		Label l = new Label("Name: ", Art.getSkin());
		t.add(l).center();
		TextField tf = new TextField("", Art.getSkin());
		t.add(tf).left();
		
		ui.add(t);
		ui.row();

		TextButton button = new TextButton("Main Menu", Art.getSkin());

		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				setScreen(new MenuScreen());
			}
		});

		ui.add(button).width(150);
	}

	@Override
	public void render() {
		root.draw();
	}

	@Override
	public void update() {
		root.act();
	}

	@Override
	public void removed() {
		root.dispose();
	}

}
