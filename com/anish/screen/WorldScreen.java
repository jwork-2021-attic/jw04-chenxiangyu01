package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;
import com.anish.calabashbros.MazeGenerator;
import com.anish.calabashbros.Wall;
import com.anish.calabashbros.Floor;
import com.anish.calabashbros.Floor2;
import com.anish.calabashbros.BFS;

import asciiPanel.AsciiPanel;
public class WorldScreen implements Screen {

    private World world;
    private Calabash player;
    String[] sortSteps;
    Color []monster =new Color[256];
    MazeGenerator mazeGenerator;
    Wall wa=new Wall(world);
    Floor fo=new Floor(world);
    Floor2 fo2=new Floor2(world);
    String p="";
    public WorldScreen() {
        world = new World();
        mazeGenerator = new MazeGenerator(60);
        mazeGenerator.generateMaze();
        
        for(int i=0;i<60;i++){
            for(int j=0;j<60;j++){
                if(mazeGenerator.maze[i][j]==0){
                    world.put(wa, i, j);
                }
            }
        }
        for(int i=0;i<60;i++){
            for(int j=0;j<60;j++){
                if(mazeGenerator.maze[i][j]==1){
                    world.put(fo, i, j);
                }
            }
        }
        player=new Calabash(new Color(255,0,0), 1, world);
        world.put(player,0,0);
        BFS b=new BFS(mazeGenerator.maze);
        b.go();
        sortSteps = this.parsePlan(b.GPlan());
        
    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Calabash[] bros, String step) {
        String[] couple = step.split("<->");
        getBroByRank(bros, Integer.parseInt(couple[0])).swap(getBroByRank(bros, Integer.parseInt(couple[1])));
    }

    private Calabash getBroByRank(Calabash[] bros, int rank) {
        for (Calabash bro : bros) {
            if (bro.getRank() == rank) {
                return bro;
            }
        }
        return null;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (i < this.sortSteps.length) {
            //this.execute(mons, sortSteps[i]);
           String[] c=sortSteps[this.sortSteps.length-1-i].split(",");
           int x1=Integer.parseInt(c[0]);
            int y1=Integer.parseInt(c[1]);
            int x2=Integer.parseInt(c[2]);
            int y2=Integer.parseInt(c[3]);
            //System.out.println(x1);
            world.put(player,x1,y1);
            world.put(fo2, x2, y2);
            i++;
        }

        return this;
    }

}
