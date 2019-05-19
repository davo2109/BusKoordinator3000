package at.fhv.itb.ss19.busmaster.domain;

import java.util.List;
import java.util.stream.Collectors;

import at.fhv.itb.ss19.busmaster.persistence.entities.PathEntity;
import at.fhv.itb.ss19.busmaster.persistence.entities.RouteEntity;

public class Path {
	private PathEntity _path;
	private List<PathStation> pathStations;
	private int _duration;

	public Path(PathEntity path) {
		_path = path;
	}

	public PathEntity getCapsuledEntity() {
		return _path;
	}

	public int getPathId() {
		return _path.getPathId();
	}

	public void setPathId(int pathId) {
		_path.setPathId(pathId);
	}

	public String getPathDescription() {
		return _path.getPathDescription();
	}

	public void setPathDescription(String pathDescription) {
		_path.setPathDescription(pathDescription);
	}

	public boolean isRetour() {
		return _path.isRetour();
	}

	public void setRetour(boolean retour) {
		_path.setRetour(retour);
	}

	public RouteEntity getRoute() {
		return _path.getRoute();
	}

	public void setRoute(RouteEntity route) {
		_path.setRoute(route);
	}

	public List<PathStation> getPathStations() {
		if (pathStations == null) {
			pathStations = _path.getPathStations().stream().map(PathStation::new).collect(Collectors.toList());
		}

		return pathStations;
	}

	public int getDuration() {
		if (_duration == 0) {
			_duration = calculateDuration();
		}
		
		return _duration;
	}

	private int calculateDuration() {
		int duration = 0;
		for (PathStation station : getPathStations()) {
			duration += station.getTimeFromPrevious();
		}

		return duration;
	}
}
