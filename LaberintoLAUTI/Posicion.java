public class Posicion {
	private int x;
	private int y;
	private boolean visitado = false;
	
	
	public Posicion(int x, int y) {
		this.x = x;
		this.y = y;
		visitado = false;
	}
	public Posicion(){
		x =0 ;
		y = 0;
		visitado = false;
	}
	
	public Posicion PosSiguiente(int i){
		if(i==0 && ){
			return new Posicion(this.x-0,this.y);
		}else if(i==1){
			return new Posicion(this.x-0,this.y);
		}
		return Posicion();
	}
	
	
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Posicion other = (Posicion) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
