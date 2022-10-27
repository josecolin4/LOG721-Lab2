package publishsubscribe.Classes;

import publishsubscribe.Interfaces.IPublication;

import java.io.Serializable;

public class Identity implements Serializable {

    private long id;
    private int port;

    private IPublication.Format format;

    public Identity(long id, int port, IPublication.Format format) {
        this(id, port);
        this.format = format;
    }

    public Identity(long id, int port) {
        this.id = id;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identity identity = (Identity) o;

        if (id != identity.id) return false;
        return port == identity.port;
    }

    public long getId() {
        return id;
    }

    public int getPort() {
        return port;
    }

    public IPublication.Format getFormat() {
        return format;
    }

    public String toString() {
        return "id : " + id + " port : " + port;
    }
}
