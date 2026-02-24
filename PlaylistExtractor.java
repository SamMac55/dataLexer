import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;

public class PlaylistExtractor extends JSONBaseVisitor<Void> {
    Song currentTrack;
    Playlist currentPlaylist;
    Artist currentArtist;
    Album currentAlbum;

    int artistIdCounter = 0;
    int albumIdCounter = 0;
    int songIdCounter = 0;
    int existenceIdCounter = 0;
    int accredationIdCounter = 0;

    Set<Playlist> playlists = new HashSet<>();
    HashMap<String, Song> songs = new HashMap<>();
    HashMap<String, Album> albums = new HashMap<>();
    HashMap<String, Artist> artists = new HashMap<>();
    Set<Existence> existences = new HashSet<>();
    Set<Accredation> accreditations = new HashSet<>();

    public static void main(String[] args) throws Exception {

        CharStream input = CharStreams.fromStream(System.in);
        JSONLexer lexer = new JSONLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);

        ParseTree tree = parser.json();

        PlaylistExtractor extractor = new PlaylistExtractor();
        extractor.visit(tree);

        extractor.printResults();
    }

    @Override
    public Void visitPair(JSONParser.PairContext ctx) {

        String key = stripQuotes(ctx.STRING().getText());

        if (key.equals("name")) {
            currentPlaylist = new Playlist();
            currentPlaylist.setName(getValue(ctx));
        }

        else if (key.equals("pid")) {
            currentPlaylist.setPid(getValue(ctx));
            playlists.add(currentPlaylist);
        }

        else if (key.equals("artist_name") && !artists.containsKey(getValue(ctx))) {
            currentArtist = new Artist();
            currentArtist.setName(getValue(ctx));
            currentArtist.setId(artistIdCounter++);
            artists.put(currentArtist.name, currentArtist);
        }

        else if (key.equals("album_name") && !albums.containsKey(getValue(ctx))) {
            currentAlbum = new Album();
            currentAlbum.setName(getValue(ctx));
            currentAlbum.setArtistName(currentArtist.name);
            currentTrack.setAlbum(currentAlbum.name);
            currentAlbum.setId(albumIdCounter++);
            currentAlbum.findArtistId(artists);
            albums.put(currentAlbum.name, currentAlbum);
            songs.put(currentTrack.title, currentTrack);
        }

        else if (key.equals("track_name")) {
            currentTrack = new Song();
            currentTrack.setTitle(getValue(ctx));
            currentTrack.setId(songIdCounter++);
        }

        else if (key.equals("duration_ms")) {
            currentTrack.setDuration(getValue(ctx));


            // Relationship tables
            existences.add(
                new Existence(existenceIdCounter++, currentPlaylist.pid, currentTrack.id)
            );

            accreditations.add(
                new Accredation(accredationIdCounter++, currentTrack.id, currentArtist.id)
            );
        }

        return visitChildren(ctx);
    }
    private String stripQuotes(String s) {
        return s.substring(1, s.length() - 1);
    }

    private String getValue(JSONParser.PairContext ctx) {
        return ctx.value().getText().replace("\"", "");
    }

    private void printResults(){
        for ( Artist artist : artists.values()) {
            System.out.println(artist);
        }
        for (Album album : albums.values()) {
            System.out.println(album);
        }
        for (Song song : songs.values()) {
            System.out.println(song);
        }
        for (Playlist playlist : playlists) {
            System.out.println(playlist);
        }
        for (Existence existence : existences) {
            System.out.println(existence);
        }
        for (Accredation accredation : accreditations) {
            System.out.println(accredation);
        }
    }
}


