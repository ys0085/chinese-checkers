package com.tp;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "replays")
public class Replay {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String variant;

    private String moveHistory;

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public Variant getVariant(){
        return Variant.fromString(variant);
    }

    public void setVariant(Variant v){
        this.variant = v.name();
    }

    public void setMoveHistory(ArrayList<Move> moveSequence){ 
        for(Move m : moveSequence){
            moveHistory += m.from.x + "," + m.from.y + "," + m.to.x + "," + m.to.y + "|";
        }
    }

    public ArrayList<Move> getMoveHistory(){
        String tokens[] = moveHistory.split("|");
        ArrayList<Move> sequence = new ArrayList<>();
        for(String t : tokens){
            String coordinates[] = t.split(",");
            Move m = new Move(
                        new Position(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])), 
                        new Position(Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3])));
            sequence.add(m);
        }
        return sequence;
    }
}
