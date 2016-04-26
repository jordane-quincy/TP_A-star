package algo;


public enum TypeDistance {
	Manhattan((a,b) -> (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()))),
	Euclidean((a,b) ->(Math.sqrt( Math.pow(a.getX(), 2) - Math.pow(b.getX(), 2) + (Math.pow(a.getY(), 2) - Math.pow(b.getY(), 2) )))) ;
	CalculDistance distance;
	
	TypeDistance(CalculDistance _distance)
	{
		distance = _distance;
	}
	
}
