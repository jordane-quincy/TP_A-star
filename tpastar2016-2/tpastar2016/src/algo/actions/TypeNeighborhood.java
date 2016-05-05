package algo.actions;

public enum TypeNeighborhood {
	avoidHurtObstacle((n,ent, vitesse, direction)->(new NeighborhoudDiagNoObstacle().getNeighbors(n, ent, vitesse, direction))),
	heightDirections((n,ent, vitesse, direction)->(new Neighborhoud8Dir().getNeighbors(n, ent, vitesse, direction))),
	fourDirectionsNSEW((n,ent, vitesse, direction)->(new Neighborhoud4Dir().getNeighbors(n, ent, vitesse, direction))),
	BB8((n,ent, vitesse, direction)->(new NeighborhoudWithObstacleBB8().getNeighbors(n, ent, vitesse, direction)));
	
	public ComputeNeighborhood search;
	
	TypeNeighborhood(ComputeNeighborhood _search)
	{search = _search;}
}
