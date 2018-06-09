import java.io.BufferedReader;
import java.io.InputStreamReader;

public class World implements Comparable<World>{
    private String worldNumber;
    private Integer worldPlayers;
    private Integer worldPing;
    private String worldURL;

    public World(String number, Integer players) throws Exception{
        this.worldNumber = number;
        this.worldPing = null;
        this.worldPlayers = players;
        this.worldURL = "oldschool" + number + ".runescape.com";
        new Thread(() -> {
            try {
                pingServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String getWorldNumber() {
        return worldNumber;
    }

    public Integer getWorldPlayers() {
        return worldPlayers;
    }

    public void setWorldPlayers(Integer worldPlayers) {
        this.worldPlayers = worldPlayers;
    }

    public Integer getWorldPing() {
        return worldPing;
    }

    public void setWorldPing(Integer worldPing) {
        this.worldPing = worldPing;
    }

    private void pingServer() throws Exception{
        String[] command = { "cmd.exe", "/C", "ping " + worldURL };
        Process commandProcess = Runtime.getRuntime().exec(command);

        BufferedReader br = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
        String readline;
        while((readline = br.readLine())!=null){
            if(readline.contains("Average")){
                String[] auxiliar = readline.toString().split("Average = ");
                setWorldPing(Integer.parseInt(auxiliar[1].replace("ms", "")));
            }
        }

        if(this.getWorldPing() == null){
            this.setWorldPing(0);
        }
    }

    public void print(){
        System.out.println("Old School " + getWorldNumber() + " - Players online: " + getWorldPlayers() + " - Ping: " + worldPing);
    }

    @Override
    public int compareTo(World waux) {
        if (this.getWorldPing() < waux.getWorldPing()){
            return -1;
        }else{
            return 1;
        }
    }
}
