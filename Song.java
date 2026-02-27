import java.util.Objects;

public class Song {
    String title;
    int albumid;
    double duration;    
    int id;
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAlbum(int albumid) {
        this.albumid = albumid; 
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return "INSERT INTO songs (song_id, album, title, duration) VALUES (" + id + ", " + albumid + ", '" + title + "', " + String.format("%.2f", duration) + ");";
    }
    @Override
    public boolean equals(Object obj) {
        // Check for reference equality
        if (this == obj) return true;
        // Check for null or different class
        if (obj == null || getClass() != obj.getClass()) return false;
        // Cast obj to Person
        Song a = (Song) obj;
        // Compare fields for logical equality
        return Objects.equals(title, a.title) && Objects.equals(albumid, a.albumid) && Objects.equals(duration, a.duration);
    }

    @Override
    public int hashCode() {
        // Generate hash based on the same fields used in equals()
        return Objects.hash(title, albumid, duration);
    }
}
    