package broker;

import commons.SubInterface;

public class Subscriber {
	private SubInterface sub;
	private boolean subscribed;
	public Subscriber(SubInterface sub, boolean subbed) {
		this.setSubscribe(sub);
		subscribed=subbed;
		
	}
	public SubInterface getSubscibe() {
		return sub;
	}
	public void setSubscribe(SubInterface sub) {
		this.sub = sub;
	}
	public boolean isSubscribed() {
		return subscribed;
	}
	@Override	
    public int hashCode() {
        return  11*sub.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Subscriber))
            return false;
        if (obj == this)
            return true;

        Subscriber s = (Subscriber) obj;
        return s.sub.equals(sub);
    }
}
