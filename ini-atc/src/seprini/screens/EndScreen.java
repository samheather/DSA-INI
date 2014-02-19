package seprini.screens;

import seprini.controllers.Leaderboard;
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
	final TextField tf = new TextField("", Art.getSkin());

	public EndScreen() {
		root = new Stage();
		Gdx.input.setInputProcessor(root);

		Table ui = new Table();

		ui.setFillParent(true);
		root.addActor(ui);

		ui.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == Keys.ENTER) {
					if (!tf.getText().isEmpty()) {
						(new Leaderboard()).addLeaderboardEntry(tf.getText(),
								State.getScore());
					}
					setScreen(new MenuScreen());
				}

				return false;
			}
		});

		root.setKeyboardFocus(ui);

		Art.getSkin().getFont("default").setScale(1f);
		// System.out.println(Math.round(State.time()));
		Label text = new Label(
				"You have failed.\n"
						+ "Two vehicles have collided resulting in a huge explosion which caused the death of "
						+ (int) Math.max(Math.ceil(Math.random() * 500), 50)
						+ " people.\n"
						+ "However, you managed to avoid a crash for approximately "
						+ Math.round(State.time())
						+ " seconds, which is respectable (at least by some standards).\n"
						+ "Your final score is " + State.getScore(),
				Art.getSkin(), "textStyle");

		ui.add(text).center();

		ui.row();
		if (State.getScore() > (new Leaderboard()).leaderboardEntries[4]
				.getScore()) {
			Label textLB = new Label(
					"Enter your name to be entered into the Leaderboard:\n",
					Art.getSkin(), "textStyle");
			ui.add(textLB).center();
			ui.row();

			Table t = new Table();

			Label l = new Label("Name: ", Art.getSkin());
			t.add(l).center();

			tf.setMaxLength(30);
			t.add(tf).left();

			ui.add(t);
			ui.row();

			TextButton button = new TextButton("Main Menu", Art.getSkin());

			button.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if (!tf.getText().isEmpty()) {
						(new Leaderboard()).addLeaderboardEntry(tf.getText(),
								State.getScore());
					}
					setScreen(new MenuScreen());
				}
			});

			ui.add(button).width(150);
		} else {
			TextButton button = new TextButton("Main Menu", Art.getSkin());

			button.addListener(new ChangeListener() {
				public void changed(ChangeEvent event, Actor actor) {
					setScreen(new MenuScreen());
				}
			});
			ui.add(button).width(150);
		}
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
