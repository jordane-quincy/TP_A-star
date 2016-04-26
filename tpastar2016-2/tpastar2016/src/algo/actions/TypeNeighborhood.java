package algo.actions;

public enum TypeNeighborhood {
	avoidHurtObstacle((n,ent)->(new NeighborhoudDiagNoObstacle().getNeighbors(n, ent))),
	heightDirections((n,ent)->(new Neighborhoud8Dir().getNeighbors(n, ent))),
	fourDirectionsNSEW((n,ent)->(new Neighborhoud4Dir().getNeighbors(n, ent)));

	public ComputeNeighborhood search;
	
	TypeNeighborhood(ComputeNeighborhood _search)
	{search = _search;}
}
