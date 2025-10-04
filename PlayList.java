import java.util.*;
import java.io.*;

public class PlayList {
    private static MyDoubleLinkedList<Song> playlist = new MyDoubleLinkedList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("*** Playlist Manager! ***");
        System.out.println("Commands:");
        System.out.println("add");
        System.out.println("remove");
        System.out.println("count");
        System.out.println("play");
        System.out.println("shuffle");
        System.out.println("reverse");
        System.out.println("save");
        System.out.println("load");
        System.out.println("quit");

        boolean running = true;
        while (running) {
            System.out.print("\n: ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "add": addSong(); break;
                case "remove": removeSong(); break;
                case "count": System.out.println(playlist.countNodes()); break;
                case "play": playlist.print(); break;
                case "shuffle": shuffleSongs(); break;
                case "reverse": playlist.reverse(); break;
                case "save": saveToFile(); break;
                case "load": loadFromFile(); break;
                case "quit": running = false; break;
                default: System.out.println("Invalid command.");
            }
        }
    }

    private static void addSong() {
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        playlist.add(new Song(title, artist));
    }

    private static void removeSong() {
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        playlist.removeIf(s -> 
            s.getArtist().equalsIgnoreCase(artist) &&
            s.getTitle().equalsIgnoreCase(title)
        );
    }

    private static void shuffleSongs() {
        List<Song> list = playlist.toList();
        Collections.shuffle(list, new Random());
        playlist.clear();
        for (Song s : list) playlist.add(s);
    }

    private static void saveToFile() {
        System.out.print("Enter file: ");
        String filename = scanner.nextLine();
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Song s : playlist.toList()) {
                pw.println(s.getArtist() + ";" + s.getTitle());
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    private static void loadFromFile() {
        System.out.print("Enter file: ");
        String filename = scanner.nextLine();
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            playlist.clear();
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(";");
                if (parts.length == 2) {
                    playlist.add(new Song(parts[1], parts[0]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}
