package com.myproject.game.Tools;

/**
 * Created by leon on 5/1/17.
 */

public class StaticGame {

    public void doMyThing(Object obj) {
        try {
            obj.toString();
        } catch(NullPointerException npe) {
            System.out.println("I ain't fucking around wit' yo null ass!");
        }
    }


}
