/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private GRect paddle;
	
	
	/* Method: init() */
	/** Sets up the Breakout program. */
	public void init() {
		/* You fill this in, along with any subsidiary methods */
		createBricks();
		createPaddle();
		
	}

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
	
	}
	
	private void createBricks()
	{
		/* Initialize variable */
		/* Determine the amount of padding needed for initial block of bricks */
		int initXPos = (getWidth() - NBRICKS_PER_ROW * BRICK_WIDTH) / 2;
		int yPos;
		int xPos;
		Color color = Color.RED;
		
		for(int i = 0; i < NBRICK_ROWS; i++)
		{
			/* Determines the y position for each brick */
			yPos = i * BRICK_HEIGHT;
			
			for(int j = 0; j < NBRICKS_PER_ROW; j++)
			{
				/* Determines the x position of each brick */
				xPos = (j * BRICK_WIDTH) + initXPos;
				
				/* Creates a new brick */
				GRect rect = new GRect(xPos ,yPos, BRICK_WIDTH, BRICK_HEIGHT);
				
				rect.setFilled(true);
				rect.setColor(Color.WHITE);
				
				/*Determine the color of the brick */
				int temp = i / 2;
				if(temp == 0)
				{
					color = Color.RED;
				}
				else if(temp == 1)
				{
					color = Color.ORANGE;
				}
				else if(temp == 2)
				{
					color = Color.YELLOW;
				}
				else if(temp == 3)
				{
					color = Color.GREEN;
				}
				else if(temp == 4)
				{
					color = Color.CYAN;
				}
				rect.setFillColor(color);
				/* Add the padddle to the screen */						
				add(rect);
			}
		}	
	}

	private void createPaddle()
	{
		paddle = new GRect((getWidth() - PADDLE_WIDTH) / 2, getHeight() - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		add(paddle);
	}
}
