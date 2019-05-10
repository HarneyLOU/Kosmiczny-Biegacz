package model;

public enum Ship {
	
	BLUE("view/resources/shipchooser/playerShip1_blue.png"),
	GREEN("view/resources/shipchooser/playerShip2_green.png"),
	ORANGE("view/resources/shipchooser/playerShip3_orange.png"),
	RED("view/resources/shipchooser/playerShip1_red.png");
	
	String urlShip;
	
	private Ship(String urlShip) {
		this.urlShip = urlShip;
	}
	
	public String getUrl() {
		return this.urlShip;
	}

}
