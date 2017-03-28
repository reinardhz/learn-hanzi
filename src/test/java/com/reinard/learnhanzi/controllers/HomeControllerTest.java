package com.reinard.learnhanzi.controllers;

import java.io.File;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

/**
 * A class to provide test to HomeController
 * @author reinard.santosa
 *
 */
public class HomeControllerTest {
	
	public static void main(String[] args) throws Exception{
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		ImageIO.write(webcam.getImage(), "PNG", new File("E:\\hello-world.png"));
		webcam.close();
	}

}
