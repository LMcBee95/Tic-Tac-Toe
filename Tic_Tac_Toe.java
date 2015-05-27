/*
 * File: Tic_Tac_Toe
 * -----------------
 * 
 * What It Does: This program creates a tic-tac-toe game that the user can play
 * 				 with by clicking on the spot that the user would like to make
 * 				 place his marker.
 * 
 */


import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Tic_Tac_Toe extends GraphicsProgram{
	
	private int[][] markersPos = new int[][]{
			{-1, -1, -1},
			{-1, -1, -1},
			{-1, -1, -1}
	};
	
	private GObject[] markers = new GObject[9];

	private int centerX; //Stores the the x coordinate of the center of the graphics window
	private int centerY; //Stores the y coordinate of the center of the graphics window
	
	private int offset = 50; //The offset each line of the tic-tac-toe board is from the center
	private int outerOffset = 3 * offset; //The distance from the center of the tic-tac-toe board to the outer edge
	
	private int markerOffset = 2 * offset;
	private int markerSize = (int)(1.3 * offset);
	
	private int scoreOffset = (int)(4.5 * offset);
	
	private int innerLeft; //The x coordinate of the left vertical line
	private int outerLeft; //The far left coordinate of the tic-tac-toe grid
	
	private int innerRight; //The x coordinate of the right vertical line
	private int outerRight; //The far right coordinate of the tic-tac-toe grid
	
	private int innerUp; //The y coordinate of the upper horizontal line
	private int outerUp; //The upper most coordinate of the tic-tac-toe grid
	
	private int innerDown; //The y coordinate of the lower horizontal line
	private int outerDown; //The lower most coordinate of the tic-tac-toe grid
	
	private int xPos; //x position of the mouse when clicked
	private int yPos; //y position of the mouse when clicked

	private int xMarkerOffset; //The x offset between the markers
	private int yMarkerOffset; //The y offset between the markers
	
	private int playerNum = 1; //The number of the player who's turn it is
	
	private int counter = 0;   //A counter that stores the number of markers that have been placed
	
	private boolean winner = false; //A boolean value that signifies if a player has won the game 
	
	private int[] playerScores = new int[]{0, 0}; //An array that contains the scores of each player; 1 - circle : 2 - square
	
	private GLabel player1Score = null; //The text that displays the score for player 1
	private GLabel player2Score = null; //The text that displays the score for player 2
	
	private GLine scoreLine = null;     //The line that shows the user the string of 3 markers that form a winning line 
	
	private int waitTime = 1750; //Time it takes (in ms) to reset the board after a game has ended 
	
	private boolean isReseting = false; //A boolean that is true when board is being reset
	
	/* Initializations for the game */
	public void init()
	{
		addMouseListeners();
		createGrid();
		displayScore();
	}
	
	/* Code while the game is running */
	public void run()
	{
		while(true)
		{
		
			if(counter == 9)
			{
				System.out.println("1");
				resetBoard(counter);
			}
			else if(winner)
			{
				updateScore(playerNum);
				displayScore();
				resetBoard(counter);
				winner = false;
			}
		}
	}
	
	/* Creates the grid of the tic-tac-toe game */
	private void createGrid()
	{
		//Calculate the center of the screen
		centerX = getWidth() / 2;
		centerY = getHeight() / 2;
		
		//Positions of all the lines
		innerLeft = centerX - offset;
		outerLeft = centerX - outerOffset;
		
		innerRight = centerX + offset;
		outerRight = centerX + outerOffset;
		
		innerUp = centerY + offset;
		outerUp = centerY + outerOffset;
		
		innerDown = centerY - offset;
		outerDown = centerY - outerOffset;
		
		//Create the lines
		GLine left = new GLine(innerLeft, outerUp, innerLeft, outerDown);
		GLine right = new GLine(innerRight, outerUp, innerRight, outerDown);
		GLine up = new GLine(outerLeft, innerUp, outerRight, innerUp);
		GLine down = new GLine(outerLeft, innerDown, outerRight, innerDown);
		
		add(left);
		add(right);
		add(up);
		add(down);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		
		xPos = e.getX();
		yPos = e.getY();
		
		boolean correctX = false; //Determine whether the user clicked inside of the tic-tac-toe board
		boolean correctY = false; //Determine whether the user clicked inside of the tic-tac-toe board
		
		boolean isValid = false;  //Determines whether the user has clicked inside a box that already contains a 
		
		//Checks for a valid mouse click and determines the x position of the marker
		
		if(xPos > (centerX - outerOffset) && xPos < (centerX - offset))
		{
			correctX = true;
			xMarkerOffset = 0;
		}
		else if(xPos > (centerX - offset) && xPos < centerX + offset)
		{
			correctX = true;
			xMarkerOffset = 1;
		}
		else if(xPos > (centerX + offset) && xPos < (centerX + outerOffset))
		{
			correctX = true;
			xMarkerOffset = 2;
		}
		
		//Checks for a valid mouse click and determine the y position of the marker
		if(yPos > (centerY - outerOffset) && yPos < (centerY - offset))
		{
			correctY = true;
			yMarkerOffset = 0;
		}
		else if(yPos > (centerY - offset) && yPos < centerY + offset)
		{
			correctY = true;
			yMarkerOffset = 1;
		}
		else if(yPos > (centerY + offset) && yPos < (centerY + outerOffset))
		{
			correctY = true;
			yMarkerOffset = 2;
		}
		
		//Place a marker if the user has clicked inside a valid box
		if(correctY && correctX && !isReseting)
		{
			isValid = checkValid(xMarkerOffset, yMarkerOffset);
			if(isValid)
			{
				
				markersPos[xMarkerOffset][yMarkerOffset] = playerNum;
				markers[counter] = drawMarker(xMarkerOffset, yMarkerOffset, playerNum);
				add(markers[counter]);
				playerNum = (playerNum % 2) + 1;
				counter++;

				winner = checkForWin(counter);
		
			}
		}
	}
	
	//Checks to see if the user has selected a valid box to put a marker in
	private boolean checkValid(int xOffset, int yOffset)
	{
		if(markersPos[xOffset][yOffset] == -1)
		{
			return(true);
		}
		return(false);
	}
	
	//Draws the marker once it is known that the user has selected a valid box
	private GObject drawMarker(int xOffset, int yOffset, int player)
	{
		if(player == 1)
		{
			GOval marker1 = new GOval(centerX + markerOffset * (xOffset - 1) - (markerSize / 2), centerY + markerOffset * (yOffset - 1) - (markerSize / 2), markerSize, markerSize);
			markersPos[xOffset][yOffset] = 1;
			return(marker1);
		}
		else if(player == 2)
		{
			GRect marker2 = new GRect(centerX + markerOffset * (xOffset - 1) - (markerSize / 2), centerY + markerOffset * (yOffset - 1) - (markerSize / 2), markerSize, markerSize);
			markersPos[xOffset][yOffset] = 2;
			return(marker2);
		}
		return(new GRect(1, 1, 1, 1));
	}
	
	private void resetBoard(int count)
	{
		isReseting = true;
		pause(waitTime);
		for(int i = 0; i < count; i++)
		{
			remove(markers[i]);
		}
		
		for(int j = 0; j < 3; j++)
		{
			for(int k = 0; k < 3; k++)
			{
				markersPos[j][k] = -1;
			}
		}
		
		remove(scoreLine); //Remove the line that shows the user the winning three markers
		
		counter = 0;
		isReseting = false;
	}
	
	private boolean checkForWin(int count)
	{
		if(count > 4)
		{
			if(checkRows() || checkColumns() || checkDiagnals())
			{
				return(true);
			}
			
		}
		return(false);
	}
	
	private void updateScore(int player)
	{
		playerScores[player - 1]++;
	}
	
	private void displayScore()
	{
		if(player1Score != null && player2Score != null)
		{
			remove(player1Score);
			remove(player2Score);
		}
		
		player1Score = new GLabel("Player 1 (square) Score: " + playerScores[0]);
		player2Score = new GLabel("Player 2 (circle) Score: " + playerScores[1]);
		
		player1Score.setFont(new Font("Serif", Font.BOLD, 30));
		player2Score.setFont(new Font("Serif", Font.BOLD, 30));
		
		int width1 = (int)(player1Score.getWidth());
		int width2 = (int)(player2Score.getWidth());
		
		int height1 = (int)(player1Score.getHeight());
		
		add(player1Score, centerX - (width1 / 2), centerY - scoreOffset);
		add(player2Score, centerX - (width2 / 2), centerY - scoreOffset + height1);
	}

	private boolean checkColumns()
	{
		for(int i = 0; i < 3; i++)
		{
			if(markersPos[i][0] == markersPos[i][1] && markersPos[i][1] == markersPos[i][2] && markersPos[i][0] != -1)
			{
				//For some reason I had the first and last columns switched in my code (whoops)
				if(i == 2)
				{
					i = 0;
				}
				else if(i == 0)
				{
					i = 2;
				}
				scoreLine = new GLine(centerX - markerOffset * (i - 1), centerY + outerOffset, centerX - markerOffset * (i - 1), centerY - outerOffset);
				add(scoreLine);
				return(true);
			}
			
		}
		return(false);
	}
	
	private boolean checkRows()
	{
		for(int i = 0; i < 3; i++)
		{
			
			if(markersPos[0][i] == markersPos[1][i] && markersPos[1][i] == markersPos[2][i] && markersPos[0][i] != -1)
			{
				//For some reason I had the first and last columns switched in my code (whoops)
				if(i == 2)
				{
					i = 0;
				}
				else if(i == 0)
				{
					i = 2;
				}
				
				scoreLine = new GLine(centerX + outerOffset, centerY - markerOffset * (i - 1), centerX - outerOffset, centerY - markerOffset * (i - 1));
				add(scoreLine);
				return(true);
			}
		}
		return(false);
	}
	
	private boolean checkDiagnals()
	{
		if(markersPos[0][0] == markersPos[1][1] && markersPos[1][1] == markersPos[2][2] && markersPos[0][0] != -1)
		{
			scoreLine = new GLine(centerX - outerOffset, centerY - outerOffset, centerX + outerOffset, centerY + outerOffset);
			add(scoreLine);
			return(true);
		}
		else if(markersPos[2][0] == markersPos[1][1] && markersPos[1][1] == markersPos[0][2] && markersPos[1][1] != -1)
		{
			scoreLine = new GLine(centerX + outerOffset, centerY - outerOffset, centerX - outerOffset, centerY + outerOffset);
			add(scoreLine);
			return(true);
		}
		return(false);
	}
}
