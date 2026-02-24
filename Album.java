import java.util.Map;

public class Album {
    String name;
    String artistName;
    int id;
    int artistid;
     public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public void findArtistId(Map<String, Artist> artists) {
        if (artists.containsKey(artistName)) {
            artistid = artists.get(artistName).id;
        } else {
            artistid = -1; // or some default value indicating not found
        }
    }
    public String toString() {
        return "INSERT INTO albums (album_id, artist_id, title, release_date, duration) VALUES (" + id + ", " + artistid + ", '" + name + "', 'RELEASE_DATE', 'DURATION');";
    }
}
