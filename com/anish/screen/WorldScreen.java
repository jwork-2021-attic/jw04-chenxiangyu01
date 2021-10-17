package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.calabashbros.QSorter;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;
import com.anish.calabashbros.Wall;
import com.anish.calabashbros.Floor;



import asciiPanel.AsciiPanel;
import java.util.Random;
public class WorldScreen implements Screen {

    private World world;
    private Calabash[][] mons;
    private Calabash[] monSter;
    String[] sortSteps;
    Color []monster =new Color[256];
    int []a=new int[256];
    Random r=new Random();
    Wall wa=new Wall(world);
    Floor fo=new Floor(world);
    String p="";
    public WorldScreen() {
        world = new World();
        for(int i=0;i<256;i++){
            monster[i]=new Color(i,Math.abs(255-i-i),255-i);
            a[i]=i;
        }
        
        for(int i=0;i<256;i++){
            int r1=r.nextInt(255);
            int temp=a[i];
            a[i]=a[r1];
            a[r1]=temp;
        }
        mons=new Calabash[16][16];
        monSter=new Calabash[256];
        for(int i=0;i<256;i++){
            mons[a[i]/16][a[i]%16]=new Calabash(monster[i], i+1, world);
        }
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                world.put(mons[i][j], 10+j*2, 10+i*2);
                monSter[i*16+j]=mons[i][j];
            }
        }
        QSorter<Calabash> b = new QSorter<>();
        b.load(monSter);
        b.sort();
        
        sortSteps = this.parsePlan(b.getPlan());
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
            this.execute(monSter, sortSteps[i]);
            i++;
        }

        return this;
    }

}
