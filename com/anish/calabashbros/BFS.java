package com.anish.calabashbros;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS {
    private int ma[][];
    public BFS(int a[][]){
        ma=a;
    }
    private String plan="";
    public void go(){
        maze_path(ma);
    }
    public String GPlan(){
        return plan;
    }
    public  void maze_path(int MAZE[][]) {
        int maze[][] = MAZE;
        boolean visited[][] = new boolean[60][60];
        Queue<Node> queue = new LinkedList<Node>();
        Stack<Node> path = new Stack<Node>();
        Node start = new Node(0, 0);
        visited[0][0] = true;
        Node end = new Node(59, 59);
        Node cur = new Node(0, 0, 0, 0);
        Node move = new Node(0, 0, 0, 0);
        int dir[][] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
        queue.offer(start);
 
        while (!queue.isEmpty()) {
            cur = queue.poll();
            path.push(cur);
 
            for (int i = 0; i < 4; i++) {
                move.x = cur.x + dir[i][0];
                move.y = cur.y + dir[i][1];
                move.prev_x = cur.x;
                move.prev_y = cur.y;
 
                if (move.x == end.x && move.y == end.y) {
                    while (!path.isEmpty()) {
                        Node show_path = path.pop();
                        if (move.prev_x == show_path.x && move.prev_y == show_path.y) {
                            plan+=""+move.x+","+move.y+","+move.prev_x+","+move.prev_y+"\n";
                            move = show_path;
                        }
                    }
 
                    
                }
 
                if (move.x >= 0 && move.x < 60 && move.y >= 0 && move.y < 60 && (maze[move.x][move.y] == 1)
                        && (!visited[move.x][move.y])) {
                    Node new_node = new Node(move.x, move.y, move.prev_x, move.prev_y);
                    
                    queue.offer(new_node);
                    visited[move.x][move.y] = true;
                }
            }
        }
        
    }

    public class Node {
        public int x;
        public int y;
        public int prev_x;
        public int prev_y;
        boolean value;
        public Node(int X, int Y, int PREV_X, int PREV_Y) {
            this.x = X;
            this.y = Y;
            this.prev_x = PREV_X;
            this.prev_y = PREV_Y;
        }
        public Node(int X, int Y) {
            this.x = X;
            this.y = Y;
        }
 
}
}