package seprini.controllers.components;

import java.util.ArrayList;

import seprini.controllers.AircraftController;
import seprini.controllers.SidebarController;
import seprini.data.Config;
import seprini.data.Debug;
import seprini.models.Exitpoint;
import seprini.models.Waypoint;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class WaypointComponent {
	
	private ArrayList<Waypoint> permanentList = new ArrayList<Waypoint>();
	private ArrayList<Waypoint> userList = new ArrayList<Waypoint>();
	private ArrayList<Waypoint> entryList = new ArrayList<Waypoint>();
	private ArrayList<Exitpoint> exitList = new ArrayList<Exitpoint>();

	private final AircraftController controller;
	private final SidebarController sidebar;
	
	public WaypointComponent(AircraftController controller,
			SidebarController sidebar) {

		this.sidebar = sidebar;
		this.controller = controller;

		// add entry waypoints to entryList
		getEntryList().add(new Waypoint(new Vector2(0, 0), true));
		getEntryList().add(new Waypoint(new Vector2(0, 720), true));
		getEntryList().add(new Waypoint(new Vector2(1080, 360), true));

		// add exit waypoints to exitList
		getExitList().add(new Exitpoint(new Vector2(1080, 720)));
		getExitList().add(new Exitpoint(new Vector2(1080, 0)));
		getExitList().add(new Exitpoint(new Vector2(0, 420)));

		// add some waypoints
		createWaypoint(300, 200, true);
		createWaypoint(300, 500, true);
		createWaypoint(700, 200, true);
		createWaypoint(700, 500, true);
	}

	/**
	 * Creates a new waypoint.
	 * <p>
	 * Creates a new user waypoint when the user left-clicks within the airspace
	 * window.
	 * 
	 * Also is convinience method for generated permanent waypoints
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

		controller.getAirspace().addActor(waypoint);

		waypoint.addListener(new ClickListener() {

			/**
			 * Removes a user waypoint if a user waypoint is right-clicked.
			 * Alternatively, should call redirection method.
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				if (button == Buttons.LEFT && sidebar.allowRedirection()) {
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

	public ArrayList<Waypoint> getPermanentList() {
		return permanentList;
	}

	public ArrayList<Waypoint> getEntryList() {
		return entryList;
	}

	public ArrayList<Exitpoint> getExitList() {
		return exitList;
	}
}