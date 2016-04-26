package algo.actions;

public enum TypeNeighborhood {
	avoidHurtObstacle((n,ent, vitesse)->(new NeighborhoudDiagNoObstacle().getNeighbors(n, ent, vitesse))),
	heightDirections((n,ent, vitesse)->(new Neighborhoud8Dir().getNeighbors(n, ent, vitesse))),
	fourDirectionsNSEW((n,ent, vitesse)->(new Neighborhoud4Dir().getNeighbors(n, ent, vitesse)));

	public ComputeNeighborhood search;
	
	TypeNeighborhood(ComputeNeighborhood _search)
	{search = _search;}
}
