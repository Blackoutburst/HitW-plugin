package utils;

import java.util.ArrayList;
import java.util.List;

import main.Game;

/**
 * Add game value
 * will be replaced with .yml files later
 * @author Blackoutburst
 */
public class Values {
	public static List<Game> gamesQ = new ArrayList<Game>();
	public static List<Game> gamesF = new ArrayList<Game>();
	
	public static int[] colors = new int[] {-588, 6, 690, -580, 11, 715};
	
	public static void initValue() {
		gamesQ.add(new Game(
		new int[] {-569, 4, 648, -551, 15, 615}, //Area
		new int[] {-563, 7, 636, -557, 10, 636}, //Play
		new int[] {-563, 7, 620, -557, 10, 620}, //Wall
		false,false, 0));
		
		gamesQ.add(new Game(
		new int[] {-549, 4, 648, -531, 15, 615}, //Area
		new int[] {-543, 7, 636, -537, 10, 636}, //Play
		new int[] {-543, 7, 620, -537, 10, 620}, //Wall
		false,false, 0));
		
		gamesQ.add(new Game(
		new int[] {-529, 4, 648, -511, 15, 615}, //Area
		new int[] {-523, 7, 636, -517, 10, 636}, //Play
		new int[] {-523, 7, 620, -517, 10, 620}, //Wall
		false,false, 0));
		
		gamesQ.add(new Game(
		new int[] {-509, 4, 648, -491, 15, 615}, //Area
		new int[] {-503, 7, 636, -497, 10, 636}, //Play
		new int[] {-503, 7, 620, -497, 10, 620}, //Wall
		false,false, 0));
		
		gamesQ.add(new Game(
		new int[] {-489, 4, 648, -471, 15, 615}, //Area
		new int[] {-483, 7, 636, -477, 10, 636}, //Play
		new int[] {-483, 7, 620, -477, 10, 620}, //Wall
		false,false, 0));
		
		/////////////////////
		// TOURNAMENT SLOT //
		/////////////////////
		gamesQ.add(new Game(
		new int[] {-650, 4, 681, -686, 17, 666}, //Area
		new int[] {-657, 7, 676, -657, 10, 670}, //Play
		new int[] {-673, 7, 676, -673, 10, 670}, //Wall
		false,false, 0));
		
		gamesQ.add(new Game(
		new int[] {-651, 4, 664, -685, 17, 649}, //Area
		new int[] {-657, 7, 660, -657, 10, 654}, //Play
		new int[] {-673, 7, 660, -673, 10, 654}, //Wall
		false,false, 0));
		
		/////////////////////
		// TOURNAMENT SLOT //
		/////////////////////
		
		gamesF.add(new Game(
		new int[] {-569, 4, 682, -551, 15, 726}, //Area
		new int[] {-555, 7, 694, -565, 11, 694}, //Play
		new int[] {-555, 7, 721, -565, 11, 721}, //Wall
		false,false, 0));
		
		gamesF.add(new Game(
		new int[] {-549, 4, 682, -531, 15, 726}, //Area
		new int[] {-535, 7, 694, -545, 11, 694}, //Play
		new int[] {-535, 7, 721, -545, 11, 721}, //Wall
		false,false, 0));

		gamesF.add(new Game(
		new int[] {-529, 4, 682, -511, 15, 726}, //Area
		new int[] {-515, 7, 694, -525, 11, 694}, //Play
		new int[] {-515, 7, 721, -525, 11, 721}, //Wall
		false,false, 0));
		
		gamesF.add(new Game(
		new int[] {-509, 4, 682, -491, 15, 726}, //Area
		new int[] {-495, 7, 694, -505, 11, 694}, //Play
		new int[] {-495, 7, 721, -505, 11, 721}, //Wall
		false,false, 0));
		
		gamesF.add(new Game(
		new int[] {-489, 4, 682, -471, 15, 726}, //Area
		new int[] {-475, 7, 694, -485, 11, 694}, //Play
		new int[] {-475, 7, 721, -485, 11, 721}, //Wall
		false,false, 0));
		
		/////////////////////
		// TOURNAMENT SLOT //
		/////////////////////
		
		gamesF.add(new Game(
		new int[] {-708, 4, 683, -750, 17, 666}, //Area
		new int[] {-717, 7, 680, -717, 11, 670}, //Play
		new int[] {-744, 7, 680, -744, 11, 670}, //Wall
		false,false, 0));
		
		gamesF.add(new Game(
		new int[] {-709, 4, 664, -750, 17, 647}, //Area
		new int[] {-717, 7, 660, -717, 11, 650}, //Play
		new int[] {-744, 7, 660, -744, 11, 650}, //Wall
		false,false, 0));
		
		/////////////////////
		// TOURNAMENT SLOT //
		/////////////////////
	}
}
