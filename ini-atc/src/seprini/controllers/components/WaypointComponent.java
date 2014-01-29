package seprini.controllers.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import seprini.controllers.AircraftController;
import seprini.controllers.SidebarController;
import seprini.data.Config;
import seprini.data.Debug;
import seprini.models.Aircraft;
import seprini.models.Entrypoint;
import seprini.models.Exitpoint;
import seprini.models.Waypoint;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class WaypointComponent {
	
	private ArrayList<Waypoint> permanentList = new ArrayList<Waypoint>();
	private ArrayList<Waypoint> userList = new ArrayList<Waypoint>();
	private ArrayList<Entrypoint> entryList = new ArrayList<Entrypoint>();
	private ArrayList<Exitpoint> exitList = new ArrayList<Exitpoint>();

	private final AircraftController controller;
	private final SidebarController sidebar;
	
	public WaypointComponent(AircraftController controller,
			SidebarController sidebar) {

		this.sidebar = sidebar;
		this.controller = controller;

		// add entry waypoints to entryList
		createEntrypoint(0, 0);
		createEntrypoint(0, 720);
		createEntrypoint(1080, 360);
		createEntrypoint(540, 0);
		createEntrypoint(400, 345);

		// add exit waypoints to exitList
		createExitpoint(1080, 720);
		createExitpoint(1080, 0);
		createExitpoint(0, 420);
		createExitpoint(540, 720);
		createExitpoint(400, 345);

		// add some waypoints
		createWaypoint(300, 200, true);
		createWaypoint(150, 360, true);
		createWaypoint(300, 500, true);
		createWaypoint(600, 650, true);
		createWaypoint(700, 200, true);
		createWaypoint(850, 360, true);
		createWaypoint(700, 500, true);
		createWaypoint(450, 100, true);
		createWaypoint(540, 360, true);
		
		Collections.shuffle(permanentList, new Random());
	}

	/**
	 * Creates a new waypoint.
	 * <p>
	 * Creates a new user waypoint when the user left-clicks within the airspace
	 * window.
	 * 
	 * Also is convenience method for generated permanent waypoints
	 * 
	 * @param x
	 * @param y
	 * @param permanent
	 */
	public boolean createWaypoint(float x, float y, final boolean permanent) {
		Debug.msg("Creating waypoint at: " + x + ":" + y);

		if (userList.size() == Config.USER_WAYPOINT_LIMIT
				&& !permanent)
			return false;

		Debug.msg("Waypoint at: " + x + ":" + y + " created");

		final Waypoint waypoint = new Waypoint(x, y, permanent);

		// add it to the correct list according to whether it is user created or
		// not
		if (permanent)
			getPermanentList().add(waypoint);
		else
			userList.add(waypoint);

		// add it to the airspace so it is automatically drawn using root.draw()
		controller.getAirspace().addActor(waypoint);

		// add a listener is a user can remove it or redirect aircraft to it
		waypoint.addListener(new ClickListener() {

			/**
			 * Removes a user waypoint if a user waypoint is right-clicked.
			 * Alternatively, should call redirection method.
			 */
			@Override
			public boolean touchDown(InputEvent event, float tX, float tY,
					int pointer, int button) {

				if (button == Buttons.LEFT && sidebar.allowRedirection()) {
					Aircraft.ignorePath = false;
					controller.redirectAircraft(waypoint);
					return true;
				}

				if (button == Buttons.RIGHT && !permanent) {
					userList.remove(waypoint);
					controller.getAirspace().removeActor(waypoint);
					return true;
				}

				return true;
			}
		});

		return true;
	}
	
	/**
	 * Creates an exitpoint, adds it to the list of exitpoints and adds it to
	 * the airspace
	 * 
	 * @param x
	 * @param y
	 */
	private void createExitpoint(float x, float y) {
		Exitpoint point = new Exitpoint(new Vector2(x, y));
		getExitList().add(point);
		controller.getAirspace().addActor(point);
	}

	/**
	 * Creates an entry point, adds it to the list of exitpoints and adds it to
	 * the airspace
	 * 
	 * @param x
	 * @param y
	 */
	private void createEntrypoint(float x, float y) {
		Entrypoint point = new Entrypoint(new Vector2(x, y), false);
		getEntryList().add(point);
		controller.getAirspace().addActor(point);
	}

	public ArrayList<Waypoint> getPermanentList() {
		return permanentList;
	}

	public ArrayList<Entrypoint> getEntryList() {
		return entryList;
	}

	public ArrayList<Exitpoint> getExitList() {
		return exitList;
	}
}
