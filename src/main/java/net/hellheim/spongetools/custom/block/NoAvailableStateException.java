package net.hellheim.spongetools.custom.block;

public class NoAvailableStateException extends IllegalStateException {
	
	private static final long serialVersionUID = -8505232760736136326L;
	
	public NoAvailableStateException() {
        super();
    }
	
    public NoAvailableStateException(String message) {
        super(message);
    }
}
