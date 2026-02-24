public class Song {
    String title;
    String album;
    String duration;
    int id;
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAlbum(String album) {
        this.album = album; 
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String toString(){
        return "INSERT INTO songs (song_id, album, title, duration) VALUES (" + id + ", '" + album + "', '" + title + "', '" + duration + "');";
    }
}
    