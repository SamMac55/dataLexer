import java.util.Objects;

public class Accredation {
    int songid;
    int artistid;
    int id;
    public Accredation(int id, int songid, int artistid) {
        this.id = id;
        this.songid = songid;
        this.artistid = artistid;
    }
    public void setSongId(int songid) {
        this.songid = songid;
    }
    public void setArtistId(int artistid) {
        this.artistid = artistid;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "INSERT INTO accredation (accredation_id, song_id, artist_id) VALUES (" + id + ", " + songid + ", " + artistid + " );";
    }
    @Override
    public boolean equals(Object obj) {
        // Check for reference equality
        if (this == obj) return true;
        // Check for null or different class
        if (obj == null || getClass() != obj.getClass()) return false;
        // Cast obj to Person
        Accredation a = (Accredation) obj;
        // Compare fields for logical equality
        return Objects.equals(songid, a.songid) && Objects.equals(artistid, a.artistid);
    }

    @Override
    public int hashCode() {
        // Generate hash based on the same fields used in equals()
        return Objects.hash(songid, artistid);
    }
}
