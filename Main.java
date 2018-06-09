import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class Main {

    public static String playerCount;
    public static ArrayList<World> worlds;

    public static void main(String[] args) throws Exception {
        worlds = new ArrayList<World>();
        Fetch();
    }

    public static Elements connect() throws Exception {
        String url = "http://oldschool.runescape.com/slu";
        Document document = Jsoup.connect(url).get();

        playerCount = document.select(".main .player-count").text();
        Elements answerers = document.select(".main .adv-select .server-list .server-list__body .server-list__row");

        return answerers;
    }

    public static void Fetch() throws Exception {
        Elements auxiliar = connect();

        for (Element aux : auxiliar) {
            Elements atual = aux.select(".server-list__row-cell");

            String worldNumber = atual.first().text().substring(11);
            String i = atual.first().nextElementSibling().text().replace(" players", "");
            Integer worldPlayers;
            if(i.equals("")){
                worldPlayers = 0;
            }else{
                worldPlayers = Integer.parseInt(i);
            }

            worlds.add(new World(worldNumber, worldPlayers));
        }

        TimeUnit.SECONDS.sleep(50);

        Collections.sort(worlds);

        printWorld();
    }

    public static void printWorld(){
        for(World aux: worlds){
            aux.print();
        }
    }
}
